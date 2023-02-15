/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoofrontendcrud.auxiliar;

import org.springframework.web.client.RestTemplate;
import p2.zoofrontendcrud.entities.Employee;

/**
 *
 * @author Agustín Pacheco
 */
public class LoginSession {
    public String login(String userName, String pass) {
        RestTemplate rt = new RestTemplate();
        Employee e
                = rt.getForObject(Constants.PREFIX_REQUEST_URL
                        + Constants.EMPLOYEE_REQUEST_URL
                        + Constants.GET_EMPLOYEE_BY_USERNAME_REQUEST_URL
                        + userName, Employee.class);

        if (e == null) {
            return "Usuario inexistente";
        }
        if (!e.getPassword().equals(pass))
            return "Contraseña incorrecta";
        return "OK";
    }
    
    public TYPE_ENUM getEmployeeType(String userName){
        RestTemplate rt = new RestTemplate();
        Employee e
                = rt.getForObject(Constants.PREFIX_REQUEST_URL
                        + Constants.EMPLOYEE_REQUEST_URL
                        + Constants.GET_EMPLOYEE_BY_USERNAME_REQUEST_URL
                        + userName, Employee.class);

        if (e != null)
            return e.getType();
        return null;
    }
}
