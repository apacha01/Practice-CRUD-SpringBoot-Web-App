/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoofrontendcrud.controllers;

import java.time.LocalDate;
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
import p2.zoofrontendcrud.entities.Itinerary;
import p2.zoofrontendcrud.entities.Species;

/**
 *
 * @author Agustín Pacheco
 */
@Controller
@RequestMapping("/empleado")
public class EmployeeController {

    @GetMapping("/empleados")
    public String employeesPage(Model m) {
        RestTemplate rt = new RestTemplate();
        List<Employee> employees = null;
        
        try {
            employees = rt.exchange(Constants.PREFIX_REQUEST_URL
                    + Constants.EMPLOYEE_REQUEST_URL
                    + Constants.GET_ALL_REQUEST_URL,
                    HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<Employee>>() {
            }).getBody();
        } catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
            return "error";
        }

        Map<Integer, List<Species>> keepersSpecies = new HashMap();
        Map<Integer, List<Itinerary>> guidesItineraries = new HashMap();
        Map<Integer, String> formatedFirstDay = new HashMap();

        for (Employee employee : employees) {
            formatedFirstDay.put(employee.getId(), employee.formatedFirstDayAsString());
            if (employee.isAdmin()) {
            } else if (employee.isKeeper()) {
                List<Species> s = null;
                try {
                    s = rt.getForObject(Constants.PREFIX_REQUEST_URL
                            + Constants.EMPLOYEE_REQUEST_URL
                            + employee.getId() + "/"
                            + Constants.GET_SPECIES_REQUEST_URL,
                            List.class);
                } catch (RestClientException ex) {
                    m.addAttribute("excepcion", ex.toString());
                    return "error";
                }
                keepersSpecies.put(employee.getId(), s);
            } else if (employee.isGuide()) {
                List<Itinerary> s = null;
                try {
                    s = rt.getForObject(Constants.PREFIX_REQUEST_URL
                            + Constants.EMPLOYEE_REQUEST_URL
                            + employee.getId() + "/"
                            + Constants.GET_EMPLOYEE_ITINERARIES_REQUEST_URL,
                            List.class);
                } catch (RestClientException ex) {
                    m.addAttribute("exception", ex.toString());
                    return "error";
                }
                guidesItineraries.put(employee.getId(), s);
            }
        }

        m.addAttribute("keepersSpecies", keepersSpecies);
        m.addAttribute("guidesItineraries", guidesItineraries);
        m.addAttribute("formatedFirstDay", formatedFirstDay);
        m.addAttribute("employees", employees);

        return Constants.EMPLOYEE_VIEWS + "employees";
    }

    @PostMapping("/eliminar_empleado")
    public String deleteEmployeeById(Model m, @RequestParam Integer id) {
        RestTemplate rt = new RestTemplate();
        try {
            rt.delete(Constants.PREFIX_REQUEST_URL
                    + Constants.EMPLOYEE_REQUEST_URL
                    + Constants.DELETE_BY_ID_REQUEST_URL
                    + id);
        } catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
            return "error";
        }
        return "operation_done";
    }

    @GetMapping("/crear_empleado")
    public String createEmployeePage() {
        return Constants.EMPLOYEE_VIEWS + "create_employee";
    }

    @PostMapping("/crear_empleado")
    public String createEmployee(Model m,
            @RequestParam String type,
            @RequestParam String userName,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam String address,
            @RequestParam Number phone,
            @RequestParam String firstDay) {
        
        List<String> msgs = new ArrayList<>();
        
        LocalDate ld = null;
        try {
            ld = LocalDate.parse(firstDay);
        } catch (RuntimeException e) {
            m.addAttribute("excepcion", e);
        }

        HttpEntity<Employee> request = new HttpEntity<>(
                new Employee(TYPE_ENUM.getTypeFromSpanishString(type),
                        userName,
                        password,
                        name,
                        address,
                        String.valueOf(phone),
                        ld));

        RestTemplate rt = new RestTemplate();
        ResponseEntity<Employee> e = null;
        try {
            e = rt.postForEntity(Constants.PREFIX_REQUEST_URL
                    + Constants.EMPLOYEE_REQUEST_URL
                    + Constants.CREATE_REQUEST_URL,
                    request,
                    Employee.class);
        } catch (RestClientException ex) {
            m.addAttribute("request", request);
            m.addAttribute("exception", ex.toString());
            return "error";
        }

        if (e == null) {
            return "error";
        }
        if (e.getStatusCode() == HttpStatus.CREATED) {
            msgs.add(e.getBody().toString());
            m.addAttribute("msgs", msgs);
            return "operation_done";
        }
        if (e.getStatusCode() == HttpStatus.CONFLICT) {
            m.addAttribute("errorMsg", "Ese nombre de usuario ya existe, pruebe con otro.");
            return Constants.EMPLOYEE_VIEWS + "create_employee";
        }
        if (e.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) {
            m.addAttribute("errorMsg", "El empleado no se pudo crear, revise que todos los datos hayan sido ingresados.");
            return Constants.EMPLOYEE_VIEWS + "create_employee";
        }
        return "error";
    }

    @GetMapping("/editar_empleado/{id}")
    public String updateEmployeePage(Model m, @PathVariable Integer id) {
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Employee> e = null;

        try {
            e = rt.getForEntity(Constants.PREFIX_REQUEST_URL
                    + Constants.EMPLOYEE_REQUEST_URL
                    + Constants.GET_BY_ID_REQUEST_URL
                    + id,
                    Employee.class);
        } catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
            return "error";
        }

        if (e == null) {
            return "error";
        }
        if (e.getStatusCode() == HttpStatus.OK) {
            m.addAttribute("employee", e.getBody());
            return Constants.EMPLOYEE_VIEWS + "update_employee";
        }
        if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
            m.addAttribute("errorMsg", "No existe ese usuario");
            return Constants.EMPLOYEE_VIEWS + "employees";
        }
        return "error";
    }

    @PostMapping("/editar_empleado/{id}")
    public String updateEmployeeById(Model m,
            @PathVariable Integer id,
            @RequestParam String type,
            @RequestParam String userName,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam String address,
            @RequestParam Number phone,
            @RequestParam String firstDay) {

        List<String> msgs = new ArrayList<>();
        
        LocalDate ld = null;
        try {
            ld = LocalDate.parse(firstDay);
        } catch (RuntimeException e) {
            m.addAttribute("excepcion", e);
        }

        HttpEntity<Employee> request = new HttpEntity<>(
                new Employee(TYPE_ENUM.getTypeFromSpanishString(type),
                        userName,
                        password,
                        name,
                        address,
                        String.valueOf(phone),
                        ld));

        RestTemplate rt = new RestTemplate();
        ResponseEntity<Employee> e = null;
        try {
            e = rt.exchange(Constants.PREFIX_REQUEST_URL
                    + Constants.EMPLOYEE_REQUEST_URL
                    + Constants.UPDATE_BY_ID_REQUEST_URL
                    + id,
                    HttpMethod.PUT,
                    request,
                    Employee.class);
        } catch (RestClientException ex) {
            m.addAttribute("request", request);
            m.addAttribute("exception", ex.toString());
            return "error";
        }

        if (e == null) {
            return "error";
        }
        if (e.getStatusCode() == HttpStatus.OK) {
            msgs.add(e.getBody().toString());
            m.addAttribute("msgs", msgs);
            return "operation_done";
        }
        if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
            m.addAttribute("errorMsg", "No existe ese usuario");
            return Constants.EMPLOYEE_VIEWS + "employees";
        }
        return "error";
    }

    @GetMapping("/{id}/asignarespecies")
    public String assignSpeciesPage(Model m, @PathVariable Integer id) {
        List<Species> species = null;
        List<Species> s = null;
        ResponseEntity<Employee> e = null;

        RestTemplate rt = new RestTemplate();
        try {
            e = rt.getForEntity(Constants.PREFIX_REQUEST_URL
                        + Constants.EMPLOYEE_REQUEST_URL
                        + Constants.GET_BY_ID_REQUEST_URL
                        + id,
                    Employee.class);
            species = rt.getForObject(Constants.PREFIX_REQUEST_URL
                    + Constants.SPECIES_REQUEST_URL
                    + Constants.GET_ALL_REQUEST_URL,
                    List.class);
            s = rt.getForObject(Constants.PREFIX_REQUEST_URL
                    + Constants.EMPLOYEE_REQUEST_URL
                    + id + "/"
                    + Constants.GET_SPECIES_REQUEST_URL,
                    List.class);
        } catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
            return "error";
        }

        if (species == null || s == null) {
            return "error";
        }

        //remove duplicates
        species.removeAll(s);
        
        if(e != null && e.getBody() != null) m.addAttribute("name", e.getBody().getName());
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
        ResponseEntity<Employee> responseRemove = null;
        ResponseEntity<Employee> responseAssign = null;
        
        if(needRemove)
            requestRemove = new HttpEntity<>(toBeRemovedIds);
        if(needAssign)
            requestAssign = new HttpEntity<>(toBeAssignedIds);

        RestTemplate rt = new RestTemplate();
        try {
            
            if(requestAssign != null){
                responseAssign = rt.exchange(Constants.PREFIX_REQUEST_URL
                        + Constants.EMPLOYEE_REQUEST_URL
                        + id + "/"
                        + Constants.ADD_EMPLOYEE_SPECIES_REQUEST_URL,
                        HttpMethod.PUT,
                        requestAssign,
                        Employee.class);
            }
            
            if(requestRemove != null){
                responseRemove = rt.exchange(Constants.PREFIX_REQUEST_URL
                        + Constants.EMPLOYEE_REQUEST_URL
                        + id + "/"
                        + Constants.REMOVE_EMPLOYEE_SPECIES_REQUEST_URL,
                        HttpMethod.PUT,
                        requestRemove,
                        Employee.class);
            }
        }
        catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
            return "error";
        }

        if (responseAssign != null) {
            if (responseAssign.getStatusCode() == HttpStatus.PARTIAL_CONTENT)
                msgs.add("Algunas de las especies ya estaban asignadas, por lo que se evito la re-asignacion.");
            if (responseAssign.getStatusCode() == HttpStatus.NOT_FOUND)
                msgs.add("El empleado de id " + id + " no se encontro.");
            if (responseAssign.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY)
                msgs.add("El empleado de id " + id + " no es un cuidador.");
        }
        else msgs.add("No se asigno ninguna especie.");
        
        if (responseRemove == null)
            msgs.add("No se removio ninguna especie.");
        
        m.addAttribute("msgs", msgs);
        return "operation_done";
    }

    @GetMapping("/{id}/asignaritinerarios")
    public String assignItinerariesPage(Model m, @PathVariable Integer id) {
        List<Itinerary> itineraries = null;
        List<Itinerary> i = null;
        ResponseEntity<Employee> e = null;

        RestTemplate rt = new RestTemplate();
        try {
            e = rt.getForEntity(Constants.PREFIX_REQUEST_URL
                        + Constants.EMPLOYEE_REQUEST_URL
                        + Constants.GET_BY_ID_REQUEST_URL
                        + id,
                    Employee.class);
            itineraries = rt.getForObject(Constants.PREFIX_REQUEST_URL
                    + Constants.ITINERARIES_REQUEST_URL
                    + Constants.GET_ALL_REQUEST_URL,
                    List.class);
            i = rt.getForObject(Constants.PREFIX_REQUEST_URL
                    + Constants.EMPLOYEE_REQUEST_URL
                    + id + "/"
                    + Constants.GET_EMPLOYEE_ITINERARIES_REQUEST_URL,
                    List.class);
        } catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
            return "error";
        }

        if (itineraries == null || i == null) {
            return "error";
        }

        //remove duplicates
        itineraries.removeAll(i);
        
        if(e != null && e.getBody() != null) m.addAttribute("name", e.getBody().getName());
        m.addAttribute("assignedItineraries", i);
        m.addAttribute("itineraries", itineraries);
        return Constants.ASSIGN_VIEWS + "assignItineraries";
    }
    
    @PostMapping("/{id}/asignaritinerarios")
    public String assignItineraries(Model m,
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
        ResponseEntity<Employee> responseRemove = null;
        ResponseEntity<Employee> responseAssign = null;
        
        if(needRemove)
            requestRemove = new HttpEntity<>(toBeRemovedIds);
        if(needAssign)
            requestAssign = new HttpEntity<>(toBeAssignedIds);

        RestTemplate rt = new RestTemplate();
        try {
            
            if(requestAssign != null){
                responseAssign = rt.exchange(Constants.PREFIX_REQUEST_URL
                        + Constants.EMPLOYEE_REQUEST_URL
                        + id + "/"
                        + Constants.ADD_EMPLOYEE_ITINERARIES_REQUEST_URL,
                        HttpMethod.PUT,
                        requestAssign,
                        Employee.class);
            }
            
            if(requestRemove != null){
                responseRemove = rt.exchange(Constants.PREFIX_REQUEST_URL
                        + Constants.EMPLOYEE_REQUEST_URL
                        + id + "/"
                        + Constants.REMOVE_EMPLOYEE_ITINERARIES_REQUEST_URL,
                        HttpMethod.PUT,
                        requestRemove,
                        Employee.class);
            }
        }
        catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
            return "error";
        }

        if (responseAssign != null) {
            if (responseAssign.getStatusCode() == HttpStatus.PARTIAL_CONTENT)
                msgs.add("Algunas de los itinerarios ya estaban asignadas a otros guias, asique no se asignaron.");
            if (responseAssign.getStatusCode() == HttpStatus.NOT_FOUND)
                msgs.add("El empleado de id " + id + " no se encontro.");
            if (responseAssign.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY)
                msgs.add("El empleado de id " + id + " no es un guia.");
        }
        else msgs.add("No se asigno ningun itinerario.");
        
        if (responseRemove == null)
            msgs.add("No se removio ningun itinerario.");
        
        m.addAttribute("msgs", msgs);
        return "operation_done";
    }
}
