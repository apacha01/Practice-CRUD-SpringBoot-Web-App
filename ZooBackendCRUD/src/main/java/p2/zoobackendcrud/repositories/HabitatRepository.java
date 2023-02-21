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
import p2.zoobackendcrud.entities.Habitat;

/**
 *
 * @author Agustín Pacheco
 */
@Repository
public interface HabitatRepository extends JpaRepository<Habitat, Integer>{
    
    @Query("SELECT h FROM Habitat h LEFT JOIN FETCH h.continents WHERE h.name = :name")
    public List<Habitat> findByNameContaining(@Param("name") String name);
    
    @Override
    @Query("SELECT DISTINCT h FROM Habitat h LEFT JOIN FETCH h.continents")
    public List<Habitat> findAll();
    
    @Override
    @Query("SELECT h FROM Habitat h LEFT JOIN FETCH h.continents WHERE h.id = :id")
    public Optional<Habitat> findById(@Param("id") Integer id);
}
