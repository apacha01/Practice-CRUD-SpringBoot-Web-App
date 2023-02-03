/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package p2.zoobackendcrud.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import p2.zoobackendcrud.entities.GuideItinerary;

/**
 *
 * @author Agustín Pacheco
 */
@Repository
public interface GuideItineraryRepository extends JpaRepository<GuideItinerary, Integer>{
    List<GuideItinerary> findByEmployeeId(Integer id);
    List<GuideItinerary> findByItineraryId(Integer id);
}
