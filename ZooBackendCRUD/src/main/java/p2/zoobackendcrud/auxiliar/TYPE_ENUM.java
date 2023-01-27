/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoobackendcrud.auxiliar;

/**
 *
 * @author Agustín Pacheco
 */
public enum TYPE_ENUM {
    ADMIN ("Administrator"),
    KEEPER ("Species Keeper"),
    GUIDE ("Guide");
    
    private final String name;
    
    TYPE_ENUM(String name){
        this.name = name;
    }
}
