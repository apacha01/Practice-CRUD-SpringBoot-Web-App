/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoofrontendcrud.entities;


import java.sql.Time;
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
public class Itinerary{
    
    
    private Integer id;
    private String code;
    private Time duration;
    private Double routeLength;
    private Integer maxPeople;
    private Integer numSpeciesVisited;
    private Boolean assigned;
    Set<Zone> coveredZones;
    Set<GuideItinerary> guidesItineraries;
    
}
