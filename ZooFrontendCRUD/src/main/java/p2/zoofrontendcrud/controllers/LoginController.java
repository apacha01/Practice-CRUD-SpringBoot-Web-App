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
import p2.zoofrontendcrud.entities.Employee;

/**
 *
 * @author Agustín Pacheco
 */
@Controller
public class LoginController {
    @GetMapping("/login")
    public String loginPage() {
        return "../../login";
    }
    
    @GetMapping("/menu_admin")
    public String menuAdminPage(){
        return Constants.EMPLOYEE_VIEWS + "/menuAdmin";
    }
    
    @GetMapping("/menu_empleado")
    public String menuKeeperPage(){
        return Constants.EMPLOYEE_VIEWS + "/menuNonAdmin";
    }
}
