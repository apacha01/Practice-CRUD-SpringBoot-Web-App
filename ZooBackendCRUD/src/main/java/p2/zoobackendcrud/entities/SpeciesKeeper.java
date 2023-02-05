/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoobackendcrud.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Agustín Pacheco
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "species_keepers")
public class SpeciesKeeper implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_employee")
    Employee keeper;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_species")
    Species species;
    
    @Column(name = "assigned_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "UTC")
    @Temporal(TemporalType.TIMESTAMP)
    Date assignedDate;

    public SpeciesKeeper(Employee keeper, Species species, Date assignedDate) {
        this.keeper = keeper;
        this.species = species;
        this.assignedDate = assignedDate;
    }
    
    public String formatedAssignedDateAsString(){
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.format(this.assignedDate);
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(this.id, this.assignedDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        
        final SpeciesKeeper other = (SpeciesKeeper) obj;
        if (!Objects.equals(this.id, other.id) || !Objects.equals(this.keeper, other.keeper) || 
                !Objects.equals(this.species, other.species) || !Objects.equals(this.assignedDate, other.assignedDate))
            return false;
        
        return true;
    }
}
