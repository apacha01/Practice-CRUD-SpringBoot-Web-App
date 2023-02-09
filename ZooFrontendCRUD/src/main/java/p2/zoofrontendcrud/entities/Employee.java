/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoofrontendcrud.entities;

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
    private String userName;
    private String password;
    private String name;
    private String address;
    private String phone;
    private Date firstDay;
    Set<GuideItinerary> guidesItineraries;
    Set<SpeciesKeeper> speciesKeepers;
}