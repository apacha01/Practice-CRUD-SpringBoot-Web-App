/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package p2.zoobackendcrud.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import p2.zoobackendcrud.entities.Itinerary;

/**
 *
 * @author Agustín Pacheco
 */
@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary, Integer>{
    
    @Query("SELECT DISTINCT i FROM Itinerary i LEFT JOIN FETCH i.coveredZones WHERE i.code = :code")
    public List<Itinerary> findByCode(String code);
    
    @Override
    @Query("SELECT DISTINCT i FROM Itinerary i LEFT JOIN FETCH i.coveredZones")
    public List<Itinerary> findAll();
    
    @Override
    @Query("SELECT i FROM Itinerary i LEFT JOIN FETCH i.coveredZones WHERE i.id = :id")
    public Optional<Itinerary> findById(@Param("id") Integer id);
}
