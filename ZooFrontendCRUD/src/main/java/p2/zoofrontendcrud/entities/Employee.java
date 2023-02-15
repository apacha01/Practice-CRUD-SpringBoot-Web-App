/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoofrontendcrud.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
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
    private Date first_day;
    Set<GuideItinerary> guidesItineraries;
    Set<SpeciesKeeper> speciesKeepers;

    public Employee(TYPE_ENUM type, String userName, String password, String name, String address, String phone, Date firstDay) {
        this.type = type;
        this.user_name = userName;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.first_day = firstDay;
    }
}