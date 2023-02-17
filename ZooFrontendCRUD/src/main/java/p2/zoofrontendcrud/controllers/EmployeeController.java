/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoofrontendcrud.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class EmployeeController {

    @GetMapping("/empleados")
    public String employeesPage(Model m) {
        RestTemplate rt = new RestTemplate();

        List<Employee> employees = rt.exchange(Constants.PREFIX_REQUEST_URL
                + Constants.EMPLOYEE_REQUEST_URL
                + Constants.GET_ALL_EMPLOYEES_REQUEST_URL,
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<Employee>>(){}).getBody();
        
        Map<Integer, List<Species>> keepersSpecies = new HashMap();
        Map<Integer, List<Itinerary>> guidesItineraries = new HashMap();
        Map<Integer, String> formatedFirstDay = new HashMap();
        
        for (Employee employee : employees) {
            formatedFirstDay.put(employee.getId(), employee.formatedFirstDayAsString());
            if (employee.isAdmin()) {
            } else if (employee.isKeeper()) {
                List<Species> s = rt.getForObject(Constants.PREFIX_REQUEST_URL
                        + Constants.EMPLOYEE_REQUEST_URL
                        + employee.getId() + "/"
                        + Constants.GET_EMPLOYEE_SPECIES_REQUEST_URL,
                        List.class);
                keepersSpecies.put(employee.getId(), s);
            } else if (employee.isGuide()) {
                List<Itinerary> s = rt.getForObject(Constants.PREFIX_REQUEST_URL
                        + Constants.EMPLOYEE_REQUEST_URL
                        + employee.getId() + "/"
                        + Constants.GET_EMPLOYEE_ITINERARIES_REQUEST_URL,
                        List.class);
                guidesItineraries.put(employee.getId(), s);
            }
        }
        
        m.addAttribute("keepersSpecies", keepersSpecies);
        m.addAttribute("guidesItineraries", guidesItineraries);
        m.addAttribute("formatedFirstDay", formatedFirstDay);
        m.addAttribute("employees", employees);
        
        return "employeeViews/employees";
    }

    @PostMapping("/eliminar_empleado/{id}")
    public String deleteEmployeeById(Model m, @PathVariable Integer id) {
        RestTemplate rt = new RestTemplate();
        rt.delete(Constants.PREFIX_REQUEST_URL
                + Constants.EMPLOYEE_REQUEST_URL
                + Constants.DELETE_EMPLOYEE_BY_ID_REQUEST_URL
                + id);
        return "operation_done";
    }

    @GetMapping("/crear_empleado")
    public String createEmployeePage() {
        return "employeeViews/create_employee";
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

        SimpleDateFormat df = new SimpleDateFormat();
        Date d = new Date();
        try {
            d = df.parse(firstDay);
        } catch (ParseException ex) {
            m.addAttribute("exception", ex.toString());
        }

        HttpEntity<Employee> request = new HttpEntity<>(
                new Employee(TYPE_ENUM.getTypeFromSpanishString(type),
                        userName,
                        password,
                        name,
                        address,
                        String.valueOf(phone),
                        d));

        RestTemplate rt = new RestTemplate();
        ResponseEntity<Employee> e = null;
        try {
            e = rt.postForEntity(Constants.PREFIX_REQUEST_URL
                    + Constants.EMPLOYEE_REQUEST_URL
                    + Constants.CREATE_EMPLOYEE_REQUEST_URL,
                    request,
                    Employee.class);
        } catch (RestClientException ex) {
            m.addAttribute("request", request);
            m.addAttribute("exception", ex.toString());
        }

        if (e == null) {
            return "error";
        }
        if (e.getStatusCode() == HttpStatus.CREATED) {
            return "operation_done";
        }
        if (e.getStatusCode() == HttpStatus.CONFLICT) {
            m.addAttribute("errorMsg", "Ese nombre de usuario ya existe, pruebe con otro.");
            return "employeeViews/create_employee";
        }
        if (e.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) {
            m.addAttribute("errorMsg", "El empleado no se pudo crear, revise que todos los datos hayan sido ingresados.");
            return "employeeViews/create_employee";
        }
        m.addAttribute("errorMsg", "Desconocido.");
        return "error";
    }

    @GetMapping("/editar_empleado/{id}")
    public String updateEmployeePage(Model m, @PathVariable Integer id) {
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Employee> e = null;

        try {
            e = rt.getForEntity(Constants.PREFIX_REQUEST_URL
                    + Constants.EMPLOYEE_REQUEST_URL
                    + Constants.GET_EMPLOYEE_BY_ID_REQUEST_URL
                    + id,
                    Employee.class);
        } catch (RestClientException ex) {
            m.addAttribute("exception", ex.toString());
        }

        if (e == null) {
            return "error";
        }
        if (e.getStatusCode() == HttpStatus.OK) {
            m.addAttribute("employee", e.getBody());
            return "employeeViews/update_employee";
        }
        if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
            m.addAttribute("errorMsg", "No existe ese usuario");
            return "employeeViews/employees";
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

        SimpleDateFormat df = new SimpleDateFormat("yyy-MM-dd");
        Date d = new Date();
        try {
            d = df.parse(firstDay);
        } catch (ParseException ex) {
            m.addAttribute("exception", ex.toString());
        }

        HttpEntity<Employee> request = new HttpEntity<>(
                new Employee(TYPE_ENUM.getTypeFromSpanishString(type),
                        userName,
                        password,
                        name,
                        address,
                        String.valueOf(phone),
                        d));

        RestTemplate rt = new RestTemplate();
        ResponseEntity<Employee> e = null;
        try {
            e = rt.exchange(Constants.PREFIX_REQUEST_URL
                    + Constants.EMPLOYEE_REQUEST_URL
                    + Constants.UPDATE_EMPLOYEE_BY_ID_REQUEST_URL
                    + id,
                    HttpMethod.PUT,
                    request,
                    Employee.class);
        } catch (RestClientException ex) {
            m.addAttribute("request", request);
            m.addAttribute("exception", ex.toString());
        }

        if (e == null) {
            return "error";
        }
        if (e.getStatusCode() == HttpStatus.OK) {
            return "operation_done";
        }
        if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
            m.addAttribute("errorMsg", "No existe ese usuario");
            return "employeeViews/employees";
        }
        return "error";
    }
}
