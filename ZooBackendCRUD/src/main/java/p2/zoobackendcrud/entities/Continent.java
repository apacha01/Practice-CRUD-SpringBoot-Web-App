/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoobackendcrud.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import p2.zoobackendcrud.auxiliar.CONTINENTS_ENUM;

/**
 *
 * @author Agustín Pacheco
 */
@Entity
@Data
@Table(name = "continents")
public class Continent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_continent", nullable = false)
    private Integer id;
    
    @Column(name = "name", nullable = false, length = 20, columnDefinition = "enum", unique = true)
    @Enumerated(EnumType.STRING)
    private CONTINENTS_ENUM name;
    
    @ManyToMany(mappedBy = "continents", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Set<Habitat> habitats;

    public Continent(){
        habitats = new HashSet<>();
    }
    
    public Continent(CONTINENTS_ENUM name) {
        this.name = name;
        habitats = new HashSet<>();
    }
    
    public void addHabitat(Habitat h){
        if(h == null) return;
        habitats.add(h);
        if(!h.getContinents().contains(this)) h.addContinent(this);
    }
    
    public void removeHabitat(Habitat h){
        habitats.remove(h);
        if(h.getContinents().contains(this)) h.removeContinent(this);
    }
    
    public void removeAllHabitats(){
        List<Habitat> habs = habitats.stream().toList();
        for (Habitat hab : habs) {
            habitats.remove(hab);
            hab.removeContinent(this);
        }
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
        
        final Continent other = (Continent) obj;
        if (!Objects.equals(this.name, other.name) || !Objects.equals(this.id, other.id))
            return false;
        
        return true;
    }
}
