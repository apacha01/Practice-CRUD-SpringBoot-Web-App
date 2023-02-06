/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package p2.zoobackendcrud.repositories;

import org.springframework.stereotype.Repository;
import p2.zoobackendcrud.entities.Continent;

/**
 *
 * @author Agustín Pacheco
 */
@Repository
public interface ContinentReadOnlyRepository extends ReadOnlyRepository<Continent, Integer>{
    
}
