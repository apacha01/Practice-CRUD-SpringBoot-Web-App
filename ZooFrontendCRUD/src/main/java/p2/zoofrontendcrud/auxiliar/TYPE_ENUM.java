/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoofrontendcrud.auxiliar;

/**
 *
 * @author Agustín Pacheco
 */
public enum TYPE_ENUM {
    ADMIN ("Administrador"),
    KEEPER ("Cuidador"),
    GUIDE ("Guia");
    
    private final String name;
    
    TYPE_ENUM(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public static TYPE_ENUM getTypeFromSpanishString(String s){
        switch (s) {
            case "Administrador":   return TYPE_ENUM.ADMIN;
            case "Cuidador":        return TYPE_ENUM.KEEPER;
            case "Guia":            return TYPE_ENUM.GUIDE;
        }
        return null;
    }
}
