/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package p2.zoobackendcrud.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import p2.zoobackendcrud.entities.GuideItinerary;

/**
 *
 * @author Agustín Pacheco
 */
@Repository
public interface GuideItineraryRepository extends JpaRepository<GuideItinerary, Integer>{
    
    @Query("SELECT g FROM GuideItinerary g WHERE g.guide.id = :idEmployee")
    List<GuideItinerary> findByEmployeeId(@Param("idEmployee") Integer idEmployee);
    
    @Query("SELECT g FROM GuideItinerary g WHERE g.itinerary.id = :idItinerary")
    List<GuideItinerary> findByItineraryId(@Param("idItinerary") Integer idItinerary);
    
    @Query("SELECT g FROM GuideItinerary g WHERE g.itinerary.id = :idItinerary AND g.guide.id = :idEmployee")
    GuideItinerary findByIds(@Param("idItinerary") Integer idItn, @Param("idEmployee") Integer idEmp);
}
