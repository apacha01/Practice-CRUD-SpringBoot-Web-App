/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoofrontendcrud.controllers;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import p2.zoofrontendcrud.auxiliar.Constants;
import p2.zoofrontendcrud.entities.Habitat;

/**
 *
 * @author Agustín Pacheco
 */

@Controller
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
}
