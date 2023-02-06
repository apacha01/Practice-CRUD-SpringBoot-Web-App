package p2.zoobackendcrud.auxiliar;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Agustín Pacheco
 */
public enum CONTINENTS_ENUM {
    AFRICA ("AFRICA"),
    AMERICA ("AMERICA"),
    ANTARTIDA ("ANTARTIDA"),
    ASIA ("ASIA"),
    EUROPA ("EUROPA"),
    OCEANIA ("OCEANIA");
    
    private final String name;
    
    CONTINENTS_ENUM(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
        
}
