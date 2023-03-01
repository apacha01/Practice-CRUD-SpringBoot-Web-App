/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoofrontendcrud.controllers;

import java.util.HashSet;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import p2.zoofrontendcrud.auxiliar.Constants;
import p2.zoofrontendcrud.entities.Species;
import p2.zoofrontendcrud.entities.Zone;

/**
 *
 * @author Agustín Pacheco
 */
@Controller
public class ZoneController {

    @GetMapping("/zonas")
    public String zonePage(Model m) {

        RestTemplate rt = new RestTemplate();
        List<Zone> zns = null;

        try {
            zns = rt.exchange(Constants.PREFIX_REQUEST_URL
                    + Constants.ZONE_REQUEST_URL
                    + Constants.GET_ALL_REQUEST_URL,
                    HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<Zone>>() {
            }).getBody();

            for (Zone z : zns) {
                List<Species> sps;
                sps = rt.getForObject(Constants.PREFIX_REQUEST_URL
                        + Constants.ZONE_REQUEST_URL
                        + z.getId() + "/"
                        + Constants.GET_SPECIES_REQUEST_URL,
                        List.class);

                z.setSpecies(new HashSet(sps));
            }
        } catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
            return "error";
        }
        
        m.addAttribute("zones", zns);

        return Constants.ZONE_VIEWS + "zones";
    }
    
    @GetMapping("/crear_zona")
    public String createZonePage(){
        return Constants.ZONE_VIEWS + "create_zone";
    }
    
    @PostMapping("/crear_zona")
    public String createZone(Model m, @RequestParam String name, @RequestParam String extension){
        Double newExtension;
        
        try{
            newExtension = Double.parseDouble(extension.replaceAll("\\s", ""));
        }catch(NumberFormatException ex){
            m.addAttribute("errorMsg", "ERROR: El numero ingresado no es valido. Recuerde usar el '.' para numeros con coma y no ingrese signos de puntuacion para separar decenas, centenas etc...");
            return Constants.ZONE_VIEWS + "create_zone";
        }catch(NullPointerException ex){
            m.addAttribute("exception", ex.toString());
            return "error";
        }
        
        if (newExtension <= 0) {
            m.addAttribute("errorMsg", "ERROR: El numero ingresado no es valido. Debe ser mayor a 0.");
            return Constants.ZONE_VIEWS + "create_zone";
        }
        
        HttpEntity<Zone> request = new HttpEntity<>(new Zone(name, newExtension));
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Zone> z = null;
        
        try {
            z = rt.postForEntity(Constants.PREFIX_REQUEST_URL
                    + Constants.ZONE_REQUEST_URL
                    + Constants.CREATE_REQUEST_URL,
                    request,
                    Zone.class);
        } catch (RestClientException ex) {
            m.addAttribute("request", request);
            m.addAttribute("exception", ex.toString());
            return "error";
        }
        
        if (z == null) {
            return "error";
        }
        if (z.getStatusCode() == HttpStatus.CREATED) {
            m.addAttribute("msgs", List.of(z.getBody().toString()));
            return "operation_done";
        }
        
        return "error";
    }
}
