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
import p2.zoobackendcrud.entities.SpeciesKeeper;

/**
 *
 * @author Agustín Pacheco
 */
@Repository
public interface SpeciesKeeperRepository extends JpaRepository<SpeciesKeeper, Integer>{
    
    @Query("SELECT k FROM SpeciesKeeper k WHERE k.keeper.id = :idEmployee")
    List<SpeciesKeeper> findByEmployeeId(@Param("idEmployee") Integer idEmployee);
    
    @Query("SELECT k FROM SpeciesKeeper k WHERE k.species.id = :idSpecies")
    List<SpeciesKeeper> findBySpeciesId(@Param("idSpecies") Integer idSpecies);
    
    @Query("SELECT k FROM SpeciesKeeper k WHERE k.keeper.id = :idEmp AND k.species.id = :idSpc")
    SpeciesKeeper findByIds(@Param("idEmp") Integer idEmp, @Param("idSpc") Integer idSpc);
}
