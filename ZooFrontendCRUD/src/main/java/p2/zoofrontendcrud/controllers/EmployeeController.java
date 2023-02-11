/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoofrontendcrud.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import p2.zoofrontendcrud.auxiliar.Constants;
import p2.zoofrontendcrud.auxiliar.TYPE_ENUM;
import p2.zoofrontendcrud.entities.Employee;

/**
 *
 * @author Agustín Pacheco
 */
@Controller
public class EmployeeController {
    
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @PostMapping("/login")
    public String goToMenu(Model m, @RequestParam(name = "userName") String userName, 
            @RequestParam(name = "password") String password) {
        RestTemplate rt = new RestTemplate();
        Employee employee =
                rt.getForObject(Constants.PREFIX_REQUEST_URL + 
                        Constants.EMPLOYEE_REQUEST_URL + 
                        Constants.GET_EMPLOYEE_BY_USERNAME_REQUEST_URL
                        + userName, Employee.class);
        
        if(employee == null){
            m.addAttribute("errorMsg", "Usuario inexistente.");
            return "login";
        }
        if(employee.getPassword().equals(password)){
            m.addAttribute("name", employee.getName());
            switch (employee.getType()) {
                case ADMIN:
                    return "menuAdmin";
                case GUIDE:
                    return "menuGuide";
                case KEEPER:
                    return "menuKeeper";
            }
        }
        else{
            m.addAttribute("errorMsg", "Contraseña equivocada.");
            return "login";
        }
        m.addAttribute("errorMsg", "Error desconocido.");
            return "error";
    }
}
