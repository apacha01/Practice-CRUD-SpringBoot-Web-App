/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoofrontendcrud.entities;


import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import p2.zoofrontendcrud.auxiliar.CONTINENTS_ENUM;

/**
 *
 * @author Agustín Pacheco
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Continent {
    private Integer id;
    private CONTINENTS_ENUM name;
    Set<Habitat> habitats;
}
