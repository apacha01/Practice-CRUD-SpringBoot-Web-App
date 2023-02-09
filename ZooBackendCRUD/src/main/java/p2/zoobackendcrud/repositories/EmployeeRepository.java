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
import p2.zoobackendcrud.auxiliar.TYPE_ENUM;
import p2.zoobackendcrud.entities.Employee;

/**
 *
 * @author Agustín Pacheco
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
    
    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
    public List<Employee> findByNameContaining(String name);
    public Employee findByUserName(String userName);
    
    @Query("SELECT e FROM Employee e WHERE e.type = :type")
    List<Employee> findByType(@Param("type") TYPE_ENUM name);
    
}
