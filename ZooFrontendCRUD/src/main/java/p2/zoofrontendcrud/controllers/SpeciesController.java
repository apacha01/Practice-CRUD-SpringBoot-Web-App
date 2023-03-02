/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoofrontendcrud.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import p2.zoofrontendcrud.auxiliar.Constants;
import p2.zoofrontendcrud.auxiliar.TYPE_ENUM;
import p2.zoofrontendcrud.entities.Employee;
import p2.zoofrontendcrud.entities.Habitat;
import p2.zoofrontendcrud.entities.Species;
import p2.zoofrontendcrud.entities.Zone;

/**
 *
 * @author Agustín Pacheco
 */
@Controller
@RequestMapping("/especie")
public class SpeciesController {

    @GetMapping("/especies")
    public String speciesPage(Model m) {
        RestTemplate rt = new RestTemplate();
        List<Species> species = null;
        Map<Integer, List<Employee>> speciesKeepers = new HashMap();
        
        try {
            species = rt.exchange(Constants.PREFIX_REQUEST_URL
                    + Constants.SPECIES_REQUEST_URL
                    + Constants.GET_ALL_REQUEST_URL,
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<List<Species>>() {
            }).getBody();
        } catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
            return "error";
        }

        for (Species sp : species) {
            List<Employee> e = null;
            try {
                e = rt.getForObject(Constants.PREFIX_REQUEST_URL
                        + Constants.SPECIES_REQUEST_URL
                        + sp.getId() + "/"
                        + Constants.GET_SPECIES_KEEPERS_REQUEST_URL,
                        List.class);
            } catch (RestClientException ex) {
                m.addAttribute("excepcion", ex.toString());
                return "error";
            }
            speciesKeepers.put(sp.getId(), e);
        }

        m.addAttribute("speciesKeepers", speciesKeepers);
        m.addAttribute("species", species);

        return Constants.SPECIES_VIEWS + "species";
    }

    @GetMapping("/crear_especie")
    public String createSpeciesPage(Model m){
        RestTemplate rt = new RestTemplate();
        List<Zone> zones = null;
        
        try {
            zones = rt.getForObject(Constants.PREFIX_REQUEST_URL
                    + Constants.ZONE_REQUEST_URL
                    + Constants.GET_ALL_REQUEST_URL,
                    List.class);
        } catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
            return "error";
        }
        
        m.addAttribute("zones", zones);
        
        return Constants.SPECIES_VIEWS + "create_species";
    }
    
    @PostMapping("/crear_especie")
    public String createSpecies(Model m,
            @RequestParam String name,
            @RequestParam String scientificName,
            @RequestParam String description,
            @RequestParam String zoneName){
        
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Zone> z = null;
        Species s = null;
        
        String zId = zoneName.substring(zoneName.indexOf('=') + 1, zoneName.length() - 1);
        
        try {
            List<Species> sps;
            
            z = rt.getForEntity(Constants.PREFIX_REQUEST_URL
                    + Constants.ZONE_REQUEST_URL
                    + Constants.GET_BY_ID_REQUEST_URL
                    + zId,
                    Zone.class);
            
            sps = rt.getForObject(Constants.PREFIX_REQUEST_URL
                    + Constants.ZONE_REQUEST_URL
                    + zId + "/"
                    + Constants.GET_SPECIES_REQUEST_URL,
                    List.class);
            
            z.getBody().setSpecies(new HashSet(sps));
            
            HttpEntity<Species> request = new HttpEntity(
                    new Species(name, scientificName, description, z.getBody())
            );
                    
            s = rt.postForObject(Constants.PREFIX_REQUEST_URL
                    + Constants.SPECIES_REQUEST_URL
                    + Constants.CREATE_REQUEST_URL,
                    request,
                    Species.class);
                    
        } catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
            return "error";
        }
        
        if (s == null) {
            m.addAttribute("errorMsg", "Esa especie no se puede crear, revise que todos los campos esten completos.");
            return "error";
        }
        
        m.addAttribute("msgs", List.of(s.toString()));
        
        return "operation_done";
    }
    
    @GetMapping("/editar_especie/{id}")
    public String updateSpeciesPage(Model m, @PathVariable("id") Integer id){
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Species> s = null;
        List<Zone> zones = null;
        
        try {
            zones = rt.getForObject(Constants.PREFIX_REQUEST_URL
                    + Constants.ZONE_REQUEST_URL
                    + Constants.GET_ALL_REQUEST_URL,
                    List.class);
            
            s = rt.getForEntity(Constants.PREFIX_REQUEST_URL
                    + Constants.SPECIES_REQUEST_URL
                    + Constants.GET_BY_ID_REQUEST_URL
                    + id,
                    Species.class);
        } catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
            return "error";
        }
        
        if(s == null || s.getStatusCode() == HttpStatus.NOT_FOUND) {
            m.addAttribute("errorMsg", "No se encontro esa especie.");
            return "error";
        }
        
        m.addAttribute("s", s.getBody());
        m.addAttribute("zones", zones);
        
        return Constants.SPECIES_VIEWS + "update_species";
    }
    
    @PostMapping("/editar_especie/{id}")
    public String updateSpecies(Model m,
            @PathVariable("id") Integer id,
            @RequestParam String name,
            @RequestParam String scientificName,
            @RequestParam String description,
            @RequestParam String zoneName){
        
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Zone> z = null;
        ResponseEntity<Species> s = null;
        
        Integer zId = Integer.parseInt(zoneName.substring(zoneName.indexOf('=') + 1, zoneName.length() - 1));
        
        try {
            List<Species> sps;
                        
            z = rt.getForEntity(Constants.PREFIX_REQUEST_URL
                    + Constants.ZONE_REQUEST_URL
                    + Constants.GET_BY_ID_REQUEST_URL
                    + zId,
                    Zone.class);

            sps = rt.getForObject(Constants.PREFIX_REQUEST_URL
                    + Constants.ZONE_REQUEST_URL
                    + zId + "/"
                    + Constants.GET_SPECIES_REQUEST_URL,
                    List.class);

            Zone zn = z.getBody();
            zn.setSpecies(new HashSet(sps));
            
            HttpEntity<Species> request = new HttpEntity(new Species(name, scientificName, description, zn));
            
            s = rt.exchange(Constants.PREFIX_REQUEST_URL
                    + Constants.SPECIES_REQUEST_URL
                    + Constants.UPDATE_BY_ID_REQUEST_URL
                    + id,
                    HttpMethod.PUT,
                    request,
                    Species.class);
                    
        } catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
            return "error";
        }
        
        if (s == null) {
            m.addAttribute("errorMsg", "Esa especie no se puede crear, revise que todos los campos esten completos.");
            return "error";
        }
        
        if(s.getBody() != null)
            m.addAttribute("msgs", List.of(s.getBody().toString()));
        
        return "operation_done";
    }
    
    @GetMapping("/{id}/asignarcuidadores")
    public String assignKeeperPage(Model m, @PathVariable("id") Integer id) {
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Species> s = null;
        List<Employee> keepers;
        List<Employee> speciesKeepers;
        
        try {
            s = rt.getForEntity(Constants.PREFIX_REQUEST_URL
                    + Constants.SPECIES_REQUEST_URL
                    + Constants.GET_BY_ID_REQUEST_URL
                    + id,
                    Species.class);
            
            keepers = rt.exchange(Constants.PREFIX_REQUEST_URL
                    + Constants.EMPLOYEE_REQUEST_URL
                    + Constants.GET_EMPLOYEE_BY_TYPE_REQUEST_URL
                    + TYPE_ENUM.KEEPER.toString().toLowerCase(),
                    HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<Employee>>() {
            }).getBody();
            
            speciesKeepers = rt.exchange(Constants.PREFIX_REQUEST_URL
                    + Constants.SPECIES_REQUEST_URL
                    + id + "/"
                    + Constants.GET_SPECIES_KEEPERS_REQUEST_URL,
                    HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<Employee>>() {
            }).getBody();
        } catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
            return "error";
        }
        
        if(s == null || s.getStatusCode() == HttpStatus.NOT_FOUND) {
            m.addAttribute("errorMsg", "No se encontro esa especie.");
            return "error";
        }
        if (keepers == null) keepers = new ArrayList<>();
        if (speciesKeepers == null) speciesKeepers  = new ArrayList<>();
        
        keepers.removeAll(speciesKeepers);
        
        m.addAttribute("name", s.getBody().getName());
        m.addAttribute("speciesKeepers", speciesKeepers);
        m.addAttribute("keepers", keepers);
        
        return Constants.SPECIES_VIEWS + "assignKeepers";
    }
    
    @PostMapping("/eliminar_especie")
    public String deleteSpecies(Model m, @RequestParam Integer id){
        RestTemplate rt = new RestTemplate();
        try {
            rt.delete(Constants.PREFIX_REQUEST_URL
                    + Constants.SPECIES_REQUEST_URL
                    + Constants.DELETE_BY_ID_REQUEST_URL
                    + id);
        } catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
            return "error";
        }
        return "operation_done";
    }
    
    @PostMapping("/{id}/asignarcuidadores")
    public String assignKeeper(Model m,
            @PathVariable("id") Integer id,
            @RequestParam(name = "toBeRemoved", required = false) List<Integer> toBeRemovedIds,
            @RequestParam(name = "alreadyAssigned", required = false) List<Integer> alreadyAssignedIds,
            @RequestParam(name = "toBeAssigned", required = false) List<Integer> toBeAssignedIds) {
        
        List<String> msgs = new ArrayList<>();
        Boolean needRemove = toBeRemovedIds != null;
        Boolean needAssign = toBeAssignedIds != null;
        
        //Requests
        HttpEntity<List<Integer>> requestRemove = null;
        HttpEntity<List<Integer>> requestAssign = null;
        //Responses
        ResponseEntity<Species> responseRemove = null;
        ResponseEntity<Species> responseAssign = null;
        
        if(needRemove)
            requestRemove = new HttpEntity<>(toBeRemovedIds);
        if(needAssign)
            requestAssign = new HttpEntity<>(toBeAssignedIds);

        RestTemplate rt = new RestTemplate();
        try {
            
            if(requestAssign != null){
                responseAssign = rt.exchange(Constants.PREFIX_REQUEST_URL
                        + Constants.SPECIES_REQUEST_URL
                        + id + "/"
                        + Constants.ADD_SPECIES_KEEPERS_REQUEST_URL,
                        HttpMethod.PUT,
                        requestAssign,
                        Species.class);
            }
            
            if(requestRemove != null){
                responseRemove = rt.exchange(Constants.PREFIX_REQUEST_URL
                        + Constants.SPECIES_REQUEST_URL
                        + id + "/"
                        + Constants.REMOVE_SPECIES_KEEPERS_REQUEST_URL,
                        HttpMethod.PUT,
                        requestRemove,
                        Species.class);
            }
        }
        catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
            return "error";
        }

        if (responseAssign != null) {
            if (responseAssign.getStatusCode() == HttpStatus.PARTIAL_CONTENT)
                msgs.add("Algunas de los cuidadores ya estaban asignadas a esta especie, por lo que no se asignaron.");
            if (responseAssign.getStatusCode() == HttpStatus.NOT_FOUND)
                msgs.add("La especie de id " + id + " no se encontro.");
        }
        else msgs.add("No se asigno ningun cuidador.");
        
        if (responseRemove == null)
            msgs.add("No se removio ningun cuidador.");
        
        m.addAttribute("msgs", msgs);
        return "operation_done";
    }
    
    @GetMapping("/{id}/asignarhabitats")
    public String assignHabitatPage(Model m, @PathVariable("id") Integer id){
        ResponseEntity<Species> s = null;
        List<Habitat> speciesHabitats = new ArrayList<>();
        List<Habitat> habitats = new ArrayList<>();
        RestTemplate rt = new RestTemplate();
        
        try {
            s = rt.getForEntity(Constants.PREFIX_REQUEST_URL
                    + Constants.SPECIES_REQUEST_URL
                    + Constants.GET_BY_ID_REQUEST_URL
                    + id,
                    Species.class);
            
            habitats = rt.exchange(Constants.PREFIX_REQUEST_URL
                    + Constants.HABITAT_REQUEST_URL
                    + Constants.GET_ALL_REQUEST_URL,
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<List<Habitat>>() {
            }).getBody();
            
        } catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
            return "error";
        }
        
        if (s.getStatusCode() == HttpStatus.NOT_FOUND) {
            m.addAttribute("errorMsgs", List.of("No existe la especie con el id: " + id));
            return "error";
        }

        speciesHabitats = List.copyOf(s.getBody().getHabitats());
        habitats.removeAll(speciesHabitats);
        
        m.addAttribute("name", s.getBody().getName());
        m.addAttribute("speciesHabitats", speciesHabitats);
        m.addAttribute("habitats", habitats);
        
        return Constants.SPECIES_VIEWS + "assignHabitats";
    }
    
    @PostMapping("/{id}/asignarhabitats")
    public String assignHabitat(Model m,
            @PathVariable("id") Integer id,
            @RequestParam(name = "toBeRemoved", required = false) List<Integer> toBeRemovedIds,
            @RequestParam(name = "alreadyAssigned", required = false) List<Integer> alreadyAssignedIds,
            @RequestParam(name = "toBeAssigned", required = false) List<Integer> toBeAssignedIds){
        
        List<String> msgs = new ArrayList<>();
        Boolean needRemove = toBeRemovedIds != null;
        Boolean needAssign = toBeAssignedIds != null;
        
        //Requests
        HttpEntity<List<Integer>> requestRemove = null;
        HttpEntity<List<Integer>> requestAssign = null;
        //Responses
        ResponseEntity<Species> responseRemove = null;
        ResponseEntity<Species> responseAssign = null;
        
        if(needRemove)
            requestRemove = new HttpEntity<>(toBeRemovedIds);
        if(needAssign)
            requestAssign = new HttpEntity<>(toBeAssignedIds);

        RestTemplate rt = new RestTemplate();
        try {
            
            if(requestAssign != null){
                responseAssign = rt.exchange(Constants.PREFIX_REQUEST_URL
                        + Constants.SPECIES_REQUEST_URL
                        + id + "/"
                        + Constants.ADD_SPECIES_HABITATS_REQUEST_URL,
                        HttpMethod.PUT,
                        requestAssign,
                        Species.class);
            }
            
            if(requestRemove != null){
                responseRemove = rt.exchange(Constants.PREFIX_REQUEST_URL
                        + Constants.SPECIES_REQUEST_URL
                        + id + "/"
                        + Constants.REMOVE_SPECIES_HABITATS_REQUEST_URL,
                        HttpMethod.PUT,
                        requestRemove,
                        Species.class);
            }
        }
        catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
            return "error";
        }

        if (responseAssign != null) {
            if (responseAssign.getStatusCode() == HttpStatus.NOT_FOUND)
                msgs.add("La especie de id " + id + " no se encontro.");
        }
        else msgs.add("No se asigno ningun habitat.");
        
        if (responseRemove == null)
            msgs.add("No se removio ningun habitat.");
        
        m.addAttribute("msgs", msgs);
        return "operation_done";
    }
}
