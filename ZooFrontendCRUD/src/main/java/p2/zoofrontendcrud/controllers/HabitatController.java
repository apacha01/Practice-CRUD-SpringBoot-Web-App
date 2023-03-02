/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoofrontendcrud.controllers;

import java.util.List;
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
import p2.zoofrontendcrud.entities.Habitat;

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
}
