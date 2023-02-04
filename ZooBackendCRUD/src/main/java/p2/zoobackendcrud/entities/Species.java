/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoobackendcrud.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Agustín Pacheco
 */
@Entity
@Data
@Table(name = "species")
@NoArgsConstructor
public class Species implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_species",nullable = false)
    private Integer id;
    
    @Column(name = "name", nullable = false, length = 20)
    private String name;
    
    @Column(name = "scientific_name", nullable = false, length = 60)
    private String scientificName;
    
    @Column(name = "description", nullable = false, length = 100)
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_zone", nullable = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Zone zone;

    public Species(String name, String scientificName, String description, Zone zone) {
        this.name = name;
        this.scientificName = scientificName;
        this.description = description;
        this.zone = zone;
    }
    
    public void setZone(Zone z){
        //z.removeSpecies(this);    // throws exception, cant modify and access at the same time
        Zone zaux = zone;
        zone = z;
        if (zaux != null) zaux.removeSpecies(this);
    }
    
    @Override
    public String toString(){
        String s = "Id: " + id + ". Name: " + name + ". Scientifi Name: " + scientificName +
        ". Zone: " + zone.getName() + "(" + zone.getId() + ")";
        return s;
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(this.id, this.scientificName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        
        final Species other = (Species) obj;
        if (!Objects.equals(this.name, other.name) || !Objects.equals(this.scientificName, other.scientificName)
                || !Objects.equals(this.description, other.description) || !Objects.equals(this.id, other.id)
                || !Objects.equals(this.zone, other.zone)) {
            return false;
        }
        
        return true;
    }
}
