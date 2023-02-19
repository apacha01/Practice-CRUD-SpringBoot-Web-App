/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoofrontendcrud.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Set;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import p2.zoofrontendcrud.auxiliar.TYPE_ENUM;

/**
 *
 * @author Agustín Pacheco
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Integer id;
    private TYPE_ENUM type;
    private String user_name;
    private String password;
    private String name;
    private String address;
    private String phone;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate first_day;
    Set<GuideItinerary> guidesItineraries;
    Set<SpeciesKeeper> speciesKeepers;

    public Employee(TYPE_ENUM type, String userName, String password, String name, String address, String phone, LocalDate firstDay) {
        this.type = type;
        this.user_name = userName;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.first_day = firstDay;
    }
    
    public boolean isGuide(){
        return type == TYPE_ENUM.GUIDE;
    }
    
    public boolean isKeeper(){
        return type == TYPE_ENUM.KEEPER;
    }
    
    public boolean isAdmin(){
        return type == TYPE_ENUM.ADMIN;
    }
    
    public String formatedFirstDayAsString(){
        DateTimeFormatter dtf = new DateTimeFormatterBuilder()
                .append(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toFormatter();
        return this.first_day.format(dtf);
    }
}