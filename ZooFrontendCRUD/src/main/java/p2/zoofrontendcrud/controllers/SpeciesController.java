/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoofrontendcrud.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import p2.zoofrontendcrud.auxiliar.Constants;
import p2.zoofrontendcrud.auxiliar.TYPE_ENUM;
import p2.zoofrontendcrud.entities.Employee;
import p2.zoofrontendcrud.entities.Species;
import p2.zoofrontendcrud.entities.Zone;

/**
 *
 * @author Agustín Pacheco
 */
@Controller
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
    public String createSpecies(Model m){
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
    
    @PostMapping("/{id}/asignarcuidadores")
    public String assignKeeper(Model m,
            @PathVariable("id") Integer id,
            @RequestParam(name = "toBeRemoved", required = false) List<Integer> toBeRemovedIds,
            @RequestParam(name = "alreadyAssigned", required = false) List<Integer> alreadyAssignedIds,
            @RequestParam(name = "toBeAssigned", required = false) List<Integer> toBeAssignedIds) {
        m.addAttribute("msgs", List.of((toBeRemovedIds == null ? "" : toBeRemovedIds.toString())
                                    ,(toBeAssignedIds == null ? "" : toBeAssignedIds.toString())
                               ));
        return "operation_done";
    }
}
