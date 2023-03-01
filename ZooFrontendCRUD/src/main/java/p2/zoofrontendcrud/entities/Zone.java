/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoofrontendcrud.entities;


import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Agustín Pacheco
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Zone {
    private Integer id;
    private String name;
    private Double extension;
    Set<Itinerary> coveredItineraries;
    Set<Species> species;

    public Zone(String name, Double extension) {
        this.name = name;
        this.extension = extension;
    }
}
