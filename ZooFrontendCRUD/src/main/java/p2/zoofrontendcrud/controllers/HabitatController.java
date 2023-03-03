/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoofrontendcrud.controllers;

import java.util.ArrayList;
import java.util.List;
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
import p2.zoofrontendcrud.entities.Continent;
import p2.zoofrontendcrud.entities.Habitat;
import p2.zoofrontendcrud.entities.Species;

/**
 *
 * @author Agustín Pacheco
 */

@Controller
@RequestMapping("/habitat")
public class HabitatController {
    @GetMapping("/habitats")
    public String habitatPage(Model m) {

        RestTemplate rt = new RestTemplate();
        List<Habitat> hbs = null;

        try {
            hbs = rt.getForObject(Constants.PREFIX_REQUEST_URL
                    + Constants.HABITAT_REQUEST_URL
                    + Constants.GET_ALL_REQUEST_URL,
                    List.class);
        } catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
            return "error";
        }
        
        m.addAttribute("habitats", hbs);

        return Constants.HABITAT_VIEWS + "habitats";
    }
    
    @GetMapping("/crear_habitat")
    public String createHabitatPage(Model m){
        return Constants.HABITAT_VIEWS + "create_habitat";
    }
    
    @PostMapping("/crear_habitat")
    public String createHabitat(Model m,
            @RequestParam String name,
            @RequestParam String weather,
            @RequestParam String vegetation){
        
        HttpEntity<Habitat> request = new HttpEntity<>(new Habitat(name, weather, vegetation));
        
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Habitat> h = null;
        try {
            h = rt.postForEntity(Constants.PREFIX_REQUEST_URL
                    + Constants.HABITAT_REQUEST_URL
                    + Constants.CREATE_REQUEST_URL,
                    request,
                    Habitat.class);
        } catch (RestClientException ex) {
            m.addAttribute("request", request);
            m.addAttribute("exception", ex.toString());
            return "error";
        }
        
        if (h != null && h.getStatusCode() == HttpStatus.CREATED) {
            m.addAttribute("msgs", List.of(h.getBody().toString()));
            return "operation_done";
        }
        
        return "error";
    }
    
    @GetMapping("/editar_habitat/{id}")
    public String updateHabitatPage(Model m, @PathVariable("id") Integer id){
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Habitat> h = null;

        try {
            h = rt.getForEntity(Constants.PREFIX_REQUEST_URL
                    + Constants.HABITAT_REQUEST_URL
                    + Constants.GET_BY_ID_REQUEST_URL
                    + "/" + id,
                    Habitat.class);
        } catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
            return "error";
        }
        
        if (h == null || h.getStatusCode() == HttpStatus.NOT_FOUND){
            m.addAttribute("errorMsg", "No existe ese usuario");
            return Constants.HABITAT_VIEWS + "habitats";
        }
        if (h.getStatusCode() == HttpStatus.OK)
            m.addAttribute("h", h.getBody());
        
        return Constants.HABITAT_VIEWS + "update_habitat";
    }
    
    @PostMapping("/editar_habitat/{id}")
    public String updateHabitat(Model m,
            @PathVariable("id") Integer id,
            @RequestParam String name,
            @RequestParam String weather,
            @RequestParam String vegetation){
        
        HttpEntity<Habitat> request = new HttpEntity<>(new Habitat(name, weather, vegetation));
        
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Habitat> h = null;
        try {
            h = rt.exchange(Constants.PREFIX_REQUEST_URL
                    + Constants.HABITAT_REQUEST_URL
                    + Constants.UPDATE_BY_ID_REQUEST_URL
                    + id,
                    HttpMethod.PUT,
                    request,
                    Habitat.class);
        } catch (RestClientException ex) {
            m.addAttribute("request", request);
            m.addAttribute("exception", ex.toString());
            return "error";
        }

        if (h == null) {
            return "error";
        }
        if (h.getStatusCode() == HttpStatus.OK) {
            m.addAttribute("msgs", List.of(h.getBody().toString()));
            return "operation_done";
        }
        if (h.getStatusCode() == HttpStatus.NOT_FOUND) {
            m.addAttribute("errorMsg", "No existe ese usuario");
            return Constants.HABITAT_VIEWS + "habitats";
        }
        
        return "error";
    }
    
    @PostMapping("/eliminar_habitat")
    public String deleteHabitat(Model m, @RequestParam Integer id) {
        RestTemplate rt = new RestTemplate();
        try {
            rt.delete(Constants.PREFIX_REQUEST_URL
                    + Constants.HABITAT_REQUEST_URL
                    + Constants.DELETE_BY_ID_REQUEST_URL
                    + id);
        } catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
            return "error";
        }
        return "operation_done";
    }
    
    @GetMapping("/{id}/asignarcontinentes")
    public String assignContinentsPage(Model m, @PathVariable("id") Integer id){
        List<Continent> c = null;
        List<Continent> continents = null;
        ResponseEntity<Habitat> h = null;

        RestTemplate rt = new RestTemplate();
        try {
            h = rt.getForEntity(Constants.PREFIX_REQUEST_URL
                        + Constants.HABITAT_REQUEST_URL
                        + Constants.GET_BY_ID_REQUEST_URL
                        + id,
                    Habitat.class);
            continents = rt.exchange(Constants.PREFIX_REQUEST_URL
                    + Constants.CONTINENT_REQUEST_URL
                    + Constants.GET_ALL_REQUEST_URL,
                    HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<Continent>>() {
            }).getBody();
        } catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
            return "error";
        }

        if (continents == null || h == null || h.getBody() == null)
            return "error";
        
        c = new ArrayList<>(h.getBody().getContinents());
        
        //remove duplicates
        continents.removeAll(c);
        
        m.addAttribute("name", h.getBody().getName());
        m.addAttribute("assignedContinents", c);
        m.addAttribute("continents", continents);
        return Constants.ASSIGN_VIEWS + "assignContinents";
    }
    
    @PostMapping("/{id}/asignarcontinentes")
    public String assignContinent(Model m,
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
        ResponseEntity<Habitat> responseRemove = null;
        ResponseEntity<Habitat> responseAssign = null;
        
        if(needRemove)
            requestRemove = new HttpEntity<>(toBeRemovedIds);
        if(needAssign)
            requestAssign = new HttpEntity<>(toBeAssignedIds);

        RestTemplate rt = new RestTemplate();
        try {
            
            if(requestAssign != null){
                responseAssign = rt.exchange(Constants.PREFIX_REQUEST_URL
                        + Constants.HABITAT_REQUEST_URL
                        + id + "/"
                        + Constants.ADD_HABITAT_CONTINENTS_REQUEST_URL,
                        HttpMethod.PUT,
                        requestAssign,
                        Habitat.class);
            }
            
            if(requestRemove != null){
                responseRemove = rt.exchange(Constants.PREFIX_REQUEST_URL
                        + Constants.HABITAT_REQUEST_URL
                        + id + "/"
                        + Constants.REMOVE_HABITAT_CONTINENTS_REQUEST_URL,
                        HttpMethod.PUT,
                        requestRemove,
                        Habitat.class);
            }
        }
        catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
            return "error";
        }

        if (responseAssign != null){
            if (responseAssign.getStatusCode() == HttpStatus.NOT_FOUND)
                msgs.add("El habitat de id " + id + " no se encontro.");
        }
        else msgs.add("No se asigno ningun continente.");
        
        if (responseRemove == null)
            msgs.add("No se removio ningun continente.");
        
        m.addAttribute("msgs", msgs);
        return "operation_done";
    }
    
    @GetMapping("//{id}/asignarespecies")
    public String assignSpeciesPage(Model m, @PathVariable Integer id){
        List<Species> species = null;
        List<Species> s = null;
        ResponseEntity<Habitat> h = null;

        RestTemplate rt = new RestTemplate();
        try {
            h = rt.getForEntity(Constants.PREFIX_REQUEST_URL
                        + Constants.HABITAT_REQUEST_URL
                        + Constants.GET_BY_ID_REQUEST_URL
                        + id,
                    Habitat.class);
            species = rt.getForObject(Constants.PREFIX_REQUEST_URL
                    + Constants.SPECIES_REQUEST_URL
                    + Constants.GET_ALL_REQUEST_URL,
                    List.class);
            s = rt.getForObject(Constants.PREFIX_REQUEST_URL
                    + Constants.HABITAT_REQUEST_URL
                    + id + "/"
                    + Constants.GET_SPECIES_REQUEST_URL,
                    List.class);
        } catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
            return "error";
        }

        if (h.getStatusCode() == HttpStatus.NOT_FOUND) {
            m.addAttribute("errorMsgs", List.of("No existe el habitat con el id: " + id));
            return "error";
        }

        //remove duplicates
        species.removeAll(s);
        
        m.addAttribute("name", h.getBody().getName());
        m.addAttribute("assignedSpecies", s);
        m.addAttribute("species", species);
        return Constants.ASSIGN_VIEWS + "assignSpecies";
    }
    
    @PostMapping("/{id}/asignarespecies")
    public String assignSpecies(Model m,
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
        ResponseEntity<Habitat> responseRemove = null;
        ResponseEntity<Habitat> responseAssign = null;
        
        if(needRemove)
            requestRemove = new HttpEntity<>(toBeRemovedIds);
        if(needAssign)
            requestAssign = new HttpEntity<>(toBeAssignedIds);

        RestTemplate rt = new RestTemplate();
        try {
            
            if(requestAssign != null){
                responseAssign = rt.exchange(Constants.PREFIX_REQUEST_URL
                        + Constants.HABITAT_REQUEST_URL
                        + id + "/"
                        + Constants.ADD_SPECIES_REQUEST_URL,
                        HttpMethod.PUT,
                        requestAssign,
                        Habitat.class);
            }
            
            if(requestRemove != null){
                responseRemove = rt.exchange(Constants.PREFIX_REQUEST_URL
                        + Constants.HABITAT_REQUEST_URL
                        + id + "/"
                        + Constants.REMOVE_SPECIES_REQUEST_URL,
                        HttpMethod.PUT,
                        requestRemove,
                        Habitat.class);
            }
        }
        catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
            return "error";
        }

        if (responseAssign != null) {
            if (responseAssign.getStatusCode() == HttpStatus.NOT_FOUND)
                msgs.add("La zona de id " + id + " no se encontro.");
        }
        else msgs.add("No se asigno ninguna especie.");
        
        if (responseRemove == null)
            msgs.add("No se removio ninguna especie.");
        
        m.addAttribute("msgs", msgs);
        return "operation_done";
    }
}
