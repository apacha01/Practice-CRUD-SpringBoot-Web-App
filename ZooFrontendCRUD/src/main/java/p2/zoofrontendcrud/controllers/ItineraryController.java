/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoofrontendcrud.controllers;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
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
import p2.zoofrontendcrud.entities.GuideItinerary;
import p2.zoofrontendcrud.entities.Itinerary;

/**
 *
 * @author Agustín Pacheco
 */
@Controller
@RequestMapping("/itinerario")
public class ItineraryController {

    @GetMapping("/itinerarios")
    public String itineraryPage(Model m) {

        RestTemplate rt = new RestTemplate();
        List<Itinerary> its = null;
        Map<Integer, Employee> itinerariesGuides = new HashMap();

        try {
            its = rt.exchange(Constants.PREFIX_REQUEST_URL
                    + Constants.ITINERARY_REQUEST_URL
                    + Constants.GET_ALL_REQUEST_URL,
                    HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<Itinerary>>() {
            }).getBody();

            for (Itinerary i : its) {
                if (i.getAssigned()) {
                    Employee emp;
                    emp = rt.getForObject(Constants.PREFIX_REQUEST_URL
                            + Constants.ITINERARY_REQUEST_URL
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
    
    @GetMapping("/crear_itinerario")
    public String createItineraryPage(Model m){
        RestTemplate rt = new RestTemplate();
        List<Employee> guides = null;
        
        try {
            guides = rt.exchange(Constants.PREFIX_REQUEST_URL
                    + Constants.EMPLOYEE_REQUEST_URL
                    + Constants.GET_EMPLOYEE_BY_TYPE_REQUEST_URL
                    + TYPE_ENUM.GUIDE.toString().toLowerCase(),
                    HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<Employee>>() {
            }).getBody();
        } catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
            return "error";
        }
        
        m.addAttribute("guides", guides);
        
        return Constants.ITINERARY_VIEWS + "create_itinerary";
    }
    
    @PostMapping("/crear_itinerario")
    public String createItinerary(Model m,
            @RequestParam String code,
            @RequestParam String duration,
            @RequestParam String routeLength,
            @RequestParam Number maxPeople,
            @RequestParam Number numSpeciesVisited,
            @RequestParam String assigned,
            @RequestParam(required = false) String guide){
        
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Itinerary> i = null;
        ResponseEntity<GuideItinerary> gi = null;
        Double newRouteLength;
        Integer gId = -1;
        
        try{
            newRouteLength = Double.valueOf(routeLength.replaceAll("\\s", ""));
        }catch(NumberFormatException ex){
            m.addAttribute("errorMsg", "ERROR: La longitud de recorrido ingresada no es valida. Recuerde usar el '.' para numeros con coma y no ingrese signos de puntuacion para separar decenas, centenas etc...");
            return Constants.ITINERARY_VIEWS + "create_itinerary";
        }catch(NullPointerException ex){
            m.addAttribute("exception", ex.toString());
            return "error";
        }
        
        if (guide != null)
            gId = Integer.valueOf(guide);
        
        HttpEntity<Itinerary> request = new HttpEntity<>(
                new Itinerary(code,
                Time.valueOf(duration),
                newRouteLength,
                maxPeople.intValue(),
                numSpeciesVisited.intValue(),
                false)
        );
        
        try {
            i = rt.postForEntity(Constants.PREFIX_REQUEST_URL
                    + Constants.ITINERARY_REQUEST_URL
                    + Constants.CREATE_REQUEST_URL,
                    request,
                    Itinerary.class);
        } catch (RestClientException ex) {
            m.addAttribute("request", request);
            m.addAttribute("exception", ex.toString());
            return "error";
        }
        
        if (i.getStatusCode() == HttpStatus.CREATED) {
            if (gId != -1) {
                try{
                    gi = rt.exchange(Constants.PREFIX_REQUEST_URL
                            + Constants.ITINERARY_REQUEST_URL
                            + i.getBody().getId() + "/"
                            + Constants.ADD_ITINERARY_GUIDE_REQUEST_URL
                            + gId,
                            HttpMethod.PUT,
                            HttpEntity.EMPTY,
                            GuideItinerary.class);
                }catch(RestClientException ex){
                    m.addAttribute("exception", ex.toString());
                    return "error";
                }

                if (gi.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) {
                    m.addAttribute("errorMsg", "El empleado de id: '" + gId + "' no es un guia.");
                    return Constants.ITINERARY_VIEWS + "create_itinerary";
                }
                //Shoudnt happen since itinerary was just created
                if (gi.getStatusCode() == HttpStatus.NOT_FOUND) {
                    m.addAttribute("errorMsg", "El itinerario o guia ingresado no existe.");
                    return "error";
                }
                if(gi.getStatusCode() == HttpStatus.IM_USED) {
                    m.addAttribute("errorMsg", "El itinerario que se quiere asignar ya esta en uso.");
                    return "error";
                }
            }
            
            m.addAttribute("msgs", List.of(i.getBody().toString()));
        }
        else {
            m.addAttribute("errorMsg", "No se pudo crear el itinerario");
            return "error";
        }
        
        return "operation_done";
    }
    
    @GetMapping("/editar_itinerario/{id}")
    public String updateItineraryPage(Model m, @PathVariable Integer id){
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Itinerary> i = null;
        List<Employee> guides = null;
        Employee guide = null;
        
        try {
            i = rt.getForEntity(Constants.PREFIX_REQUEST_URL
                    + Constants.ITINERARY_REQUEST_URL
                    + Constants.GET_BY_ID_REQUEST_URL
                    + id,
                    Itinerary.class);
            
            if (i.getBody().getAssigned()) {
                guide = rt.getForObject(Constants.PREFIX_REQUEST_URL
                        + Constants.ITINERARY_REQUEST_URL
                        + i.getBody().getId() + "/"
                        + Constants.GET_ITINERARY_GUIDE_REQUEST_URL,
                        Employee.class);
            }
            
            guides = rt.exchange(Constants.PREFIX_REQUEST_URL
                    + Constants.EMPLOYEE_REQUEST_URL
                    + Constants.GET_EMPLOYEE_BY_TYPE_REQUEST_URL
                    + TYPE_ENUM.GUIDE.toString().toLowerCase(),
                    HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<Employee>>() {
            }).getBody();
            
        } catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
            return "error";
        }
        
        if (guides == null) guides = new ArrayList<>();
        
        if (i.getBody().getAssigned()) {
            guides.remove(guide);
            m.addAttribute("assignedGuide", guide);
        }
        
        m.addAttribute("i", i.getBody());
        m.addAttribute("guides", guides);
        
        return Constants.ITINERARY_VIEWS + "edit_itinerary";
    }
    
    @PostMapping("/editar_itinerario/{id}")
    public String updateItinerary(Model m,
            @PathVariable("id") Integer id,
            @RequestParam String code,
            @RequestParam String duration,
            @RequestParam String routeLength,
            @RequestParam Number maxPeople,
            @RequestParam Number numSpeciesVisited,
            @RequestParam String assigned,
            @RequestParam(required = false) String guide){
        
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Itinerary> i = null;
        ResponseEntity<GuideItinerary> gi = null;
        Double newRouteLength;
        Integer gId = -1;
        
        try{
            newRouteLength = Double.valueOf(routeLength.replaceAll("\\s", ""));
        }catch(NumberFormatException ex){
            m.addAttribute("errorMsg", "ERROR: La longitud de recorrido ingresada no es valida. Recuerde usar el '.' para numeros con coma y no ingrese signos de puntuacion para separar decenas, centenas etc...");
            return Constants.ITINERARY_VIEWS + "create_itinerary";
        }catch(NullPointerException ex){
            m.addAttribute("exception", ex.toString());
            return "error";
        }
        
        if (guide != null)
            gId = Integer.valueOf(guide);
        
        HttpEntity<Itinerary> request = new HttpEntity<>(
                new Itinerary(code,
                Time.valueOf(duration),
                newRouteLength,
                maxPeople.intValue(),
                numSpeciesVisited.intValue(),
                false)
        );
        
        try {
            i = rt.exchange(Constants.PREFIX_REQUEST_URL
                    + Constants.ITINERARY_REQUEST_URL
                    + Constants.UPDATE_BY_ID_REQUEST_URL
                    + id,
                    HttpMethod.PUT,
                    request,
                    Itinerary.class);
        } catch (RestClientException ex) {
            m.addAttribute("request", request);
            m.addAttribute("exception", ex.toString());
            return "error";
        }
        
        if (i.getStatusCode() == HttpStatus.NOT_FOUND) {
            m.addAttribute("errorMsg", "El itionerario de id: '" + id + "' no existe.");
            return "error";
        }
        if (i.getStatusCode() == HttpStatus.OK) {
            if (gId != -1) {
                try{
                    //remove guide if it had
                    rt.put(Constants.PREFIX_REQUEST_URL
                            + Constants.ITINERARY_REQUEST_URL
                            + i.getBody().getId() + "/"
                            + Constants.REMOVE_ITINERARY_GUIDE_REQUEST_URL,
                            HttpEntity.EMPTY);
                    
                    //assign the new one
                    gi = rt.exchange(Constants.PREFIX_REQUEST_URL
                            + Constants.ITINERARY_REQUEST_URL
                            + i.getBody().getId() + "/"
                            + Constants.ADD_ITINERARY_GUIDE_REQUEST_URL
                            + gId,
                            HttpMethod.PUT,
                            request,
                            GuideItinerary.class);
                }catch(RestClientException ex){
                    m.addAttribute("exception", ex.toString());
                    return "error";
                }

                if (gi.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) {
                    m.addAttribute("errorMsg", "El empleado de id: '" + gId + "' no es un guia.");
                    return Constants.ITINERARY_VIEWS + "update_itinerary";
                }
                if (gi.getStatusCode() == HttpStatus.NOT_FOUND) {
                    m.addAttribute("errorMsg", "El itinerario o guia ingresado no existe.");
                    return "error";
                }
                if(gi.getStatusCode() == HttpStatus.IM_USED) {
                    m.addAttribute("errorMsg", "El itinerario que se quiere asignar ya esta en uso.");
                    return "error";
                }
            }
            
            m.addAttribute("msgs", List.of(i.getBody().toString()));
        }
        
        return "operation_done";
    }
    
    @PostMapping("/eliminar_itinerario")
    public String updateItinerary(Model m, @RequestParam Integer id){
        RestTemplate rt = new RestTemplate();
        try {
            rt.delete(Constants.PREFIX_REQUEST_URL
                    + Constants.ITINERARY_REQUEST_URL
                    + Constants.DELETE_BY_ID_REQUEST_URL
                    + id);
        } catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
            return "error";
        }
        return "operation_done";
    }
    
}
