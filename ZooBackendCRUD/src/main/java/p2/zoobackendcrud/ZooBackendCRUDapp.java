/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoobackendcrud;

import java.util.Date;
import java.util.Scanner;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import p2.zoobackendcrud.auxiliar.TYPE_ENUM;
import p2.zoobackendcrud.entities.*;
import p2.zoobackendcrud.repositories.*;

/**
 *
 * @author Agustín Pacheco
 */
@RequiredArgsConstructor
@SpringBootApplication
public class ZooBackendCRUDapp implements CommandLineRunner {

    private final EmployeeRepository empr;

    public static void main(String[] args) {
        SpringApplication.run(ZooBackendCRUDapp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Boolean keepgoing = true;
        while (keepgoing) {
            switch (menu()) {
                case 1:
                    create();
                    break;
                case 2:
                    read();
                    break;
                case 3:
                    update();
                    break;
                case 4:
                    delete();
                    break;
                case 5:
                    keepgoing = false;
                    break;
                default:
                    System.err.println("Not an option");
            }
        }
    }

    private int menu() {
        int opc = -1;
        Scanner inp = new Scanner(System.in);
        while (opc > 5 || opc < 1) {
            System.out.println("1. create");
            System.out.println("2. read");
            System.out.println("3. update");
            System.out.println("4. delete");
            System.out.println("5. exit");
            opc = Integer.parseInt(inp.next());
        }
        return opc;
    }

    private TYPE_ENUM typeMenu() {
        int opc = -1;
        TYPE_ENUM type;
        Scanner inp = new Scanner(System.in);
        while (opc > 3 || opc < 1) {
            System.out.println("1. Admin");
            System.out.println("2. Cuidador");
            System.out.println("3. Guia");
            opc = Integer.parseInt(inp.next());
        }
        return type = TYPE_ENUM.values()[opc - 1];
    }

    private Employee makeEmpl() {
        Scanner inp = new Scanner(System.in);
        Employee e = new Employee();
        e.setType(typeMenu());
        System.out.print("Name: ");
        e.setName(inp.next());
        System.out.print("\nUser name: ");
        e.setUserName(inp.next());
        System.out.print("\nPass: ");
        e.setPassword(inp.next());
        System.out.print("\nAddres: ");
        e.setAddress(inp.next());
        System.out.print("\nPhone: ");
        e.setPhone(inp.next());
        e.setFirstDay(new Date());
        return e;
    }

    private void create() {
        Scanner inp = new Scanner(System.in);
        Employee e = makeEmpl();
        empr.save(e);
    }

    private void read() {
        Scanner inp = new Scanner(System.in);

        System.out.println("Id to read: ");
        Integer i = Integer.parseInt(inp.next());

        System.out.println("Employee of id" + empr.findById(i));
        System.out.println("*************ALL EMPLOYEES:************");
        System.out.print(empr.findAll());
    }

    private void update() {
        Scanner inp = new Scanner(System.in);

        System.out.println("Id to update: ");
        Integer i = Integer.parseInt(inp.next());
        Employee e = makeEmpl();
        Employee savedEmployee = empr.findById(i).orElse(null);
        if (e.getType() != null) {
            savedEmployee.setType(e.getType());
        }
        if (e.getUserName() != null) {
            savedEmployee.setUserName(e.getUserName());
        }
        if (e.getPassword() != null) {
            savedEmployee.setPassword(e.getPassword());
        }
        if (e.getName() != null) {
            savedEmployee.setName(e.getName());
        }
        if (e.getAddress() != null) {
            savedEmployee.setAddress(e.getAddress());
        }
        if (e.getPhone() != null) {
            savedEmployee.setPhone(e.getPhone());
        }
        if (e.getFirstDay() != null) {
            savedEmployee.setFirstDay(e.getFirstDay());
        }
        Employee updatedEmployee = empr.save(savedEmployee);
        System.out.println(updatedEmployee.toString());
    }

    private void delete() {
        Scanner inp = new Scanner(System.in);

        System.out.println("Id to delete: ");
        Integer i = Integer.parseInt(inp.next());
        
        empr.deleteById(i);
    }
}
