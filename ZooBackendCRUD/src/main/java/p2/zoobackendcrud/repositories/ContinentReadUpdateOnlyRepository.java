/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package p2.zoobackendcrud.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import p2.zoobackendcrud.auxiliar.CONTINENTS_ENUM;
import p2.zoobackendcrud.entities.Continent;

/**
 *
 * @author Agustín Pacheco
 */
@Repository
public interface ContinentReadUpdateOnlyRepository extends ReadUpdateOnlyRepository<Continent, Integer>{
    
    @Query("SELECT c FROM Continent c WHERE c.name LIKE :name")
    List<Continent> findByNameContaining(@Param("name") CONTINENTS_ENUM name);
}
