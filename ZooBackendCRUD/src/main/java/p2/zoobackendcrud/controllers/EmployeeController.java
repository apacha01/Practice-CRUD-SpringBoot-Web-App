/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoobackendcrud.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import p2.zoobackendcrud.repositories.EmployeeRepository;
import p2.zoobackendcrud.entities.Employee;

/**
 *
 * @author Agustín Pacheco
 */
@RestController
@RequestMapping("/empleado")
public class EmployeeController {
    
    @Autowired
    private EmployeeRepository empRepo;
    
    @PostMapping("/crear")
    public Employee create(@RequestBody Employee e) {
        if (e == null)
            return null;
        return empRepo.save(e);
    }
    
    @GetMapping("/obtenertodos")
    public List<Employee> getAllEmployees() {
        return empRepo.findAll();
    }
}
