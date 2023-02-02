/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoobackendcrud.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.io.Serializable;
import java.sql.Time;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author Agustín Pacheco
 */
@Entity
@Data
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
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
        name = "itineraries_route",
        joinColumns = @JoinColumn(name = "id_itinerary"),
        inverseJoinColumns = @JoinColumn(name = "id_zone"))
    Set<Zone> coveredZones;
    
    //avoid null pointer excpt.
    public Itinerary(){
        coveredZones = new HashSet<>();
    }
    
    //Id autogenerated by database
    public Itinerary(String code, Time duration, Double routeLength, Integer maxPeople, Integer numSpeciesVisited, Boolean assigned) {
        this.code = code;
        this.duration = duration;
        this.routeLength = routeLength;
        this.maxPeople = maxPeople;
        this.numSpeciesVisited = numSpeciesVisited;
        this.assigned = assigned;
        coveredZones = new HashSet<>();
    }
    
    public void setCoveredZones(Collection zones){
        if (zones == null) return;
        for (var z : zones.toArray()){
            System.out.println(z);
            coveredZones.add((Zone)z);
        }
    }
    
    public void addZone(Zone z){
        coveredZones.add(z);
        if(!(z.getCoveredItineraries().contains(this))) z.addItinerary(this);
    }
    
    @Override
    public String toString(){
        String s = "Itinerary id: " + id + ", code: " + code + ", duration: " + duration + ", Route length: " + routeLength
                + ", Max people: " + maxPeople + ", Numb of species visited: " + numSpeciesVisited;
        if (!coveredZones.isEmpty()) {
            s += ". This itinerary covers the following zones:";
            for (Zone z : coveredZones) {
                s += "\n\tZone name:" + z.getName() + " (id:" + z.getId() + ")";
            }
        }
        else{
            s += ". This itinerary doesn't cover any zone.";
        }
        return s;
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(this.id, this.code);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)    return true;
        if (obj == null)    return false;
        if (getClass() != obj.getClass())   return false;
        final Itinerary other = (Itinerary) obj;
        if (!Objects.equals(this.coveredZones, other.coveredZones)) return false;
        if (Objects.equals(this.id, other.getId()) 
                && (this.code == null ? other.getCode() == null : this.code.equals(other.getCode())) 
                && Objects.equals(this.duration, other.getDuration())
                && Objects.equals(this.routeLength, other.getRouteLength())
                && Objects.equals(this.maxPeople, other.getMaxPeople())
                && Objects.equals(this.numSpeciesVisited, other.getNumSpeciesVisited())
                && Objects.equals(this.assigned, other.getAssigned())
                && this.coveredZones == other.getCoveredZones()) {
            return true;
        }
        return true;
    }
    
}
