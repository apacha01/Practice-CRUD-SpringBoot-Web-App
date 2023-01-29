/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoobackendcrud;

import java.sql.Time;
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
    private final ItineraryRepository itir;
    private final ZoneRepository zonr;

    public static void main(String[] args) {
        SpringApplication.run(ZooBackendCRUDapp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Boolean keepgoing = true;
        while (keepgoing) {
            switch (menu()) {
                case 1: create();   break;
                case 2: read();     break;
                case 3: update();   break;
                case 4: delete();   break;
                case 5: keepgoing = false;  break;
                default:    System.err.println("Not an option");
            }
        }
    }

    private int menu() {
        int opc = -1;
        Scanner inp = new Scanner(System.in);
        while (opc > 5 || opc < 1) {
            System.out.println("\n\n");
            System.out.println("1. create");
            System.out.println("2. read");
            System.out.println("3. update");
            System.out.println("4. delete");
            System.out.println("5. exit");
            opc = Integer.parseInt(inp.next());
        }
        return opc;
    }

    private TYPE_ENUM employeeTypeMenu() {
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
    
    private int menuTypes(){
        int opc = -1;
        Scanner inp = new Scanner(System.in);
        while(opc > 7 || opc < 1){
            System.out.println("1. Employee");
            System.out.println("2. Itinerary");
            System.out.println("3. Zones");
            System.out.println("4. Species");
            System.out.println("5. Habitat");
            System.out.println("6. Exit");
            opc = Integer.parseInt(inp.next());
        }
        return opc;
    }

    private Employee makeEmpl() {
        Scanner inp = new Scanner(System.in);
        Employee e = new Employee();
        e.setType(employeeTypeMenu());
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
    private Employee updateEmpl(Integer id){
        Employee e = makeEmpl();
        Employee savedEmployee = empr.findById(id).orElse(null);
        if (e.getType() != null)        savedEmployee.setType(e.getType());
        if (e.getUserName() != null)    savedEmployee.setUserName(e.getUserName());
        if (e.getPassword() != null)    savedEmployee.setPassword(e.getPassword());
        if (e.getName() != null)        savedEmployee.setName(e.getName());
        if (e.getAddress() != null)     savedEmployee.setAddress(e.getAddress());
        if (e.getPhone() != null)       savedEmployee.setPhone(e.getPhone());
        if (e.getFirstDay() != null)    savedEmployee.setFirstDay(e.getFirstDay());
        return empr.save(savedEmployee);
    }
    
    private Itinerary makeIt(){
        Scanner inp = new Scanner(System.in);
        Itinerary i = new Itinerary();
        System.out.print("Code: ");
        i.setCode(inp.next());
        i.setDuration(new Time(15,35,10));
        System.out.print("\nRoute Length: ");
        i.setRouteLength(Double.parseDouble(inp.next()));
        System.out.print("\nMax people: ");
        i.setMaxPeople(Integer.parseInt(inp.next()));
        System.out.print("\nNumber of species visited in route: ");
        i.setNumSpeciesVisited(Integer.parseInt(inp.next()));
        System.out.print("\nIs assigned? (true / false): ");
        i.setAssigned(Boolean.parseBoolean(inp.next()));
        return i;
    }
    private Itinerary updateIt(Integer id){
        Itinerary i = makeIt();
        Itinerary savedItinerary = itir.findById(id).orElse(null);
        if (i.getCode() != null) savedItinerary.setCode(i.getCode());
        if (i.getDuration() != null) savedItinerary.setDuration(i.getDuration());
        if (i.getRouteLength() != null) savedItinerary.setRouteLength(i.getRouteLength());
        if (i.getMaxPeople() != null) savedItinerary.setMaxPeople(i.getMaxPeople());
        if (i.getNumSpeciesVisited() != null) savedItinerary.setNumSpeciesVisited(i.getNumSpeciesVisited());
        if (i.getAssigned() != null) savedItinerary.setAssigned(i.getAssigned());
        return itir.save(savedItinerary);
    }
    
    private Zone makeZn(){
        Scanner inp = new Scanner(System.in);
        Zone z = new Zone();
        System.out.print("Name: ");
        z.setName(inp.next());
        System.out.print("\nExtension: ");
        z.setExtension(Double.parseDouble(inp.next()));
        return z;
    }
    private Zone updateZn(Integer id){
        Zone z = makeZn();
        Zone savedZone = zonr.findById(id).orElse(null);
        if (z.getName() != null) savedZone.setName(z.getName());
        if (z.getExtension() != null) savedZone.setExtension(z.getExtension());
        return z;
    }
    
    private void create() {
        Scanner inp = new Scanner(System.in);
        
        System.out.println("What do you want to create?");
        switch(menuTypes()){
            case 1: Employee e = makeEmpl(); empr.save(e); break;
            case 2: Itinerary i = makeIt(); itir.save(i); break;
            case 3: Zone z = makeZn(); zonr.save(z); break;
            case 6: break;
            default: System.err.println("Not implemented yet");
        }
    }

    private void read() {
        Scanner inp = new Scanner(System.in);
        Integer id;
        System.out.println("What do you want to read?");
        switch(menuTypes()){
            case 1:
                System.out.println("Id to read: ");
                id = Integer.parseInt(inp.next());

                System.out.println("Employee of id \"" + id + "\": " + empr.findById(id));
                System.out.println("**************************ALL EMPLOYEES:*************************");
                System.out.print(empr.findAll());
                break;
            case 2:
                System.out.println("Id to read: ");
                id = Integer.parseInt(inp.next());

                System.out.println("Itinerary of id \"" + id + "\": " + itir.findById(id));
                System.out.println("**************************ALL ITINERARIES:*************************");
                System.out.print(itir.findAll());
                break;
            case 3:
                System.out.println("Id to read: ");
                id = Integer.parseInt(inp.next());

                System.out.println("Zone of id \"" + id + "\": " + zonr.findById(id));
                System.out.println("**************************ALL ZONES:*************************");
                System.out.print(zonr.findAll());
            case 6: break;
            default: System.err.println("Not implemented yet");
        }
        
        
    }

    private void update() {
        Scanner inp = new Scanner(System.in);
        Integer id;
        
        System.out.println("What do you want to update?");
        switch(menuTypes()){
            case 1:
                System.out.println(empr.findAll());
                System.out.println("Id to update: ");
                id = Integer.parseInt(inp.next());
                Employee updatedEmployee = updateEmpl(id);
                System.out.println(updatedEmployee.toString());
                break;
            case 2:
                System.out.println(itir.findAll());
                System.out.println("Id to update: ");
                id = Integer.parseInt(inp.next());
                Itinerary updatedItinerary = updateIt(id);
                System.out.println(updatedItinerary.toString());
                break;
            case 3:
                System.out.println(zonr.findAll());
                System.out.println("Id to update: ");
                id = Integer.parseInt(inp.next());
                Zone updatedZone = updateZn(id);
                System.out.println(updatedZone.toString());
                break;
            case 6: break;
            default: System.err.println("Not implemented yet");
        }
    }

    private void delete() {
        Scanner inp = new Scanner(System.in);
        Integer i;
        
        System.out.println("What do you want to delete?");
        switch(menuTypes()){
            case 1: 
                System.out.println(empr.findAll());
                System.out.println("Id to delete: ");
                i = Integer.parseInt(inp.next());
                if (empr.existsById(i)) empr.deleteById(i);
                break;
            case 2: 
                System.out.println(itir.findAll());
                System.out.println("Id to delete: ");
                i = Integer.parseInt(inp.next());
                if (itir.existsById(i)) itir.deleteById(i);
                break;
            case 3:
                System.out.println(zonr.findAll());
                System.out.println("Id to delete: ");
                i = Integer.parseInt(inp.next());
                if (zonr.existsById(i)) zonr.deleteById(i);
                break;
            case 6: break;
            default: System.err.println("Not implemented yet");
        }
    }
}
