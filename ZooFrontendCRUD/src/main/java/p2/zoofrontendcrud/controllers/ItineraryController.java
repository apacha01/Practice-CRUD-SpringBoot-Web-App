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
import p2.zoofrontendcrud.entities.Itinerary;

/**
 *
 * @author Agustín Pacheco
 */
@Controller
public class ItineraryController {

    @GetMapping("/itinerarios")
    public String itineraryPage(Model m) {

        RestTemplate rt = new RestTemplate();
        List<Itinerary> its = null;
        Map<Integer, Employee> itinerariesGuides = new HashMap();

        try {
            its = rt.exchange(Constants.PREFIX_REQUEST_URL
                    + Constants.ITINERARIES_REQUEST_URL
                    + Constants.GET_ALL_REQUEST_URL,
                    HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<Itinerary>>() {
            }).getBody();

            for (Itinerary i : its) {
                if (i.getAssigned()) {
                    Employee emp;
                    emp = rt.getForObject(Constants.PREFIX_REQUEST_URL
                            + Constants.ITINERARIES_REQUEST_URL
                            + i.getId() + "/"
                            + Constants.GET_ITINERARY_GUIDE_REQUEST_URL,
                            Employee.class);
                    
                    itinerariesGuides.put(i.getId(), emp);
                }
            }

        } catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
            return "error";
        }

        m.addAttribute("itineraries", its);
        m.addAttribute("itinerariesGuides", itinerariesGuides);

        return Constants.ITINERARY_VIEWS + "itineraries";
    }
}
