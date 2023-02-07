/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoobackendcrud.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import p2.zoobackendcrud.auxiliar.CONTINENTS_ENUM;
import p2.zoobackendcrud.entities.Continent;
import p2.zoobackendcrud.entities.Habitat;
import p2.zoobackendcrud.repositories.HabitatRepository;
import p2.zoobackendcrud.repositories.ContinentReadUpdateOnlyRepository;

/**
 *
 * @author Agustín Pacheco
 */
@RestController
@RequestMapping("/continente")
public class ContinentController {
    @Autowired
    private HabitatRepository hbRepo;
    
    @Autowired
    private ContinentReadUpdateOnlyRepository conRepo;
    
    @GetMapping("/obtenertodos")
    public List<Continent> getAllContinents() {
        return conRepo.findAll();
    }
    
    @GetMapping("/obtenerporid/{id}")
    public ResponseEntity<Continent> getContinentById(@PathVariable("id") Integer continentId){
        return conRepo.findById(continentId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/obtenerpornombre/{nombre}")
    public List<Continent> getContinentByName(@PathVariable("nombre") String name){
        try{
            return conRepo.findByNameContaining(CONTINENTS_ENUM.valueOf(URLDecoder.decode(name, "UTF-8").toUpperCase()));
        } catch (UnsupportedEncodingException ex) {
            return new ArrayList<>();
        }
    }
    
    @PutMapping("/{contId}/agregarhabitat/{habId}")
    public ResponseEntity<Continent> addHabitatToContinent(@PathVariable("contId") Integer contId, 
            @PathVariable("habId") Integer habId){
        Continent c = conRepo.findById(contId).orElse(null);
        Habitat h = hbRepo.findById(habId).orElse(null);
          
        if(c == null || h == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        c.addHabitat(h);
        
        return new ResponseEntity(conRepo.save(c), HttpStatus.OK);
    }
    
    @PutMapping("/{contId}/removerhabitat/{habId}")
    public ResponseEntity<Continent> removeHabitatFromContinent(@PathVariable("contId") Integer contId, 
            @PathVariable("habId") Integer habId){
        Continent c = conRepo.findById(contId).orElse(null);
        Habitat h = hbRepo.findById(habId).orElse(null);
          
        if(c == null || h == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        c.removeHabitat(h);
        
        return new ResponseEntity(conRepo.save(c), HttpStatus.OK);
    }
    
    @PutMapping("/{conId}/agregarhabitats")
    public ResponseEntity<Continent> addHabitatsToContinent(@PathVariable("conId") Integer conId,
            @RequestBody List<Integer> habsId){
        Continent c = conRepo.findById(conId).orElse(null);
        
        if(c == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        for (Integer habId : habsId) {
            Habitat h = hbRepo.findById(habId).orElse(null);
            if (h != null)  c.addHabitat(h);
        }
        
        return new ResponseEntity(conRepo.save(c), HttpStatus.OK);
    }
    
    @PutMapping("/{conId}/removerhabitats")
    public ResponseEntity<Continent> removeHabitatsFromContinent(@PathVariable("conId") Integer conId,
            @RequestBody List<Integer> habsId){
        Continent c = conRepo.findById(conId).orElse(null);
        
        if(c == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        for (Integer habId : habsId) {
            Habitat h = hbRepo.findById(habId).orElse(null);
            if (h != null)  c.removeHabitat(h);
        }
        
        return new ResponseEntity(conRepo.save(c), HttpStatus.OK);
    }
    
    @PutMapping("/{conId}/removerhabitats/todos")
    public ResponseEntity<Continent> removeAllHabitats(@PathVariable("conId") Integer conId){
        Continent c = conRepo.findById(conId).orElse(null);
        
        if(c == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        c.removeAllHabitats();
        
        return new ResponseEntity(conRepo.save(c), HttpStatus.OK);
    }
}
