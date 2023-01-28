/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoobackendcrud.entities;

import java.io.Serializable;
import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Agustín Pacheco
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "itineraries")
public class Itinerary implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_itinerary", nullable = false)
    private Integer id;
    
    @Column(name = "code", nullable = false, length = 20)
    private String code;
    
    @Column(name = "duration", nullable = false)
    private Time duration;
    
    @Column(name = "route_length", nullable = false)
    private Double routeLength;
    
    @Column(name = "max_people", nullable = false)
    private Integer maxPeople;
    
    @Column(name = "num_species_visited", nullable = false)
    private Integer numSpeciesVisited;
    
    @Column(name = "assigned", nullable = false)
    private Boolean assigned;

    //Id autogenerated by database
    public Itinerary(String code, Time duration, Double routeLength, Integer maxPeople, Integer numSpeciesVisited, Boolean assigned) {
        this.code = code;
        this.duration = duration;
        this.routeLength = routeLength;
        this.maxPeople = maxPeople;
        this.numSpeciesVisited = numSpeciesVisited;
        this.assigned = assigned;
    }
    
}
