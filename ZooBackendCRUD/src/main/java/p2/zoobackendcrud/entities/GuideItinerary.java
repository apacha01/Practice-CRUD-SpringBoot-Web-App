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
@Table(name = "guides")
public class GuideItinerary implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_employee")
    Employee guide;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_itinerary")
    Itinerary itinerary;
    
    @Column(name = "assigned_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "UTC")
    @Temporal(TemporalType.TIMESTAMP)
    Date assignedDate;

    public GuideItinerary(Employee guide, Itinerary itinerary, Date assignedDate) {
        this.guide = guide;
        this.itinerary = itinerary;
        this.assignedDate = assignedDate;
    }
    
    public GuideItinerary(Employee guide, Date assignedDate) {
        this.guide = guide;
        this.assignedDate = assignedDate;
    }
    
    public GuideItinerary(Itinerary itinerary, Date assignedDate) {
        this.itinerary = itinerary;
        this.assignedDate = assignedDate;
    }
    
    public String formatedAssignedDateAsString(){
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.format(this.assignedDate);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        
        GuideItinerary other = (GuideItinerary) obj;
        return Objects.equals(guide,other.getGuide()) &&
                Objects.equals(itinerary,other.getItinerary()) &&
                Objects.equals(assignedDate,other.getAssignedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(guide.getId(), itinerary.getId(), assignedDate);
    }
}
