/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoofrontendcrud.entities;


import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Agustín Pacheco
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Habitat {
    private Integer id;
    private String name;
    private String weather;
    private String vegetation;
    Set<Species> species;
    Set<Continent> continents;
}
