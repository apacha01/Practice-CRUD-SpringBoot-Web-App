/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoofrontendcrud.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import p2.zoofrontendcrud.auxiliar.Constants;
import p2.zoofrontendcrud.entities.Employee;
import p2.zoofrontendcrud.entities.Species;

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
            m.addAttribute("excepcion", ex.toString());
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

        return "speciesViews/species";
    }
}
