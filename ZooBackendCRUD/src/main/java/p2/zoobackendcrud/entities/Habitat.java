/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoobackendcrud.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
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
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author Agustín Pacheco
 */
@Entity
@Data
@Table(name = "habitats")
public class Habitat implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_habitat",nullable = false)
    private Integer id;
    
    @Column(name = "name", nullable = false, length = 20)
    private String name;
    
    @Column(name = "weather", nullable = false, length = 20)
    private String weather;
    
    @Column(name = "vegetation", nullable = false, length = 20)
    private String vegetation;
    
    @ManyToMany(mappedBy = "habitats", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonIgnore
    Set<Species> species;

    public Habitat(){
        species = new HashSet<>();
    }
    
    public Habitat(String name, String weather, String vegetation) {
        this.name = name;
        this.weather = weather;
        this.vegetation = vegetation;
        species = new HashSet<>();
    }
    
    public void addSpecies(Species s){
        species.add(s);
        if(!s.getHabitats().contains(this)) s.addHabitat(this);
    }
    
    public void removeSpecies(Species s){
        species.remove(s);
        if(s.getHabitats().contains(this)) s.removeHabitat(this);
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        
        final Habitat other = (Habitat) obj;
        if (!Objects.equals(this.name, other.name) || !Objects.equals(this.weather, other.weather)
                || !Objects.equals(this.vegetation, other.vegetation) || !Objects.equals(this.id, other.id))
            return false;
        
        return true;
    }
}
