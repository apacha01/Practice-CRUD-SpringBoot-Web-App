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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import p2.zoobackendcrud.entities.Continent;
import p2.zoobackendcrud.entities.Habitat;
import p2.zoobackendcrud.entities.Species;
import p2.zoobackendcrud.repositories.HabitatRepository;
import p2.zoobackendcrud.repositories.SpeciesRepository;
import p2.zoobackendcrud.repositories.ContinentReadUpdateOnlyRepository;

/**
 *
 * @author Agustín Pacheco
 */
@RestController
@RequestMapping("/habitat")
public class HabitatController {
    @Autowired
    private HabitatRepository hbRepo;
    
    @Autowired
    private SpeciesRepository spRepo;
    
    @Autowired
    private ContinentReadUpdateOnlyRepository conRepo;
    
    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Habitat createHabitat(@RequestBody Habitat h) {
        if (!isHabitatSavable(h))
            return null;
        return hbRepo.save(h);
    }
    
    @GetMapping("/obtenertodos")
    public List<Habitat> getAllHabitats() {
        return hbRepo.findAll();
    }
    
    @GetMapping("/obtenerporid/{id}")
    public ResponseEntity<Habitat> getHabitatById(@PathVariable("id") Integer habitatId){
        return hbRepo.findById(habitatId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/obtenerpornombre/{nombre}")
    public List<Habitat> getHabitatByName(@PathVariable("nombre") String name){
        try{
            return hbRepo.findByNameContaining(URLDecoder.decode(name, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            return new ArrayList<>();
        }
    }
    
    @GetMapping("/{id}/obtenerespecies")
    public List<Species> getHabitatSpecies(@PathVariable("id") Integer id){
        return hbRepo.getHabitatSpecies(id);
    }
    
    @PutMapping("/modificarporid/{id}")
    public ResponseEntity<Habitat> updateHabitatById(@PathVariable("id") Integer habitatId, @RequestBody Habitat h){
        return hbRepo.findById(habitatId)
                .map(savedHabitat -> {
                    if (h.getName() != null) savedHabitat.setName(h.getName());
                    if (h.getWeather() != null) savedHabitat.setWeather(h.getWeather());
                    if (h.getVegetation() != null) savedHabitat.setVegetation(h.getVegetation());
                    Habitat updatedHabitat = hbRepo.save(savedHabitat);
                    return new ResponseEntity<>(updatedHabitat, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
    
    @PutMapping("/{habId}/agregarespecie/{speciesId}")
    public ResponseEntity<Habitat> addSpeciesToHabitat(@PathVariable("speciesId") Integer spcId, 
            @PathVariable("habId") Integer habId){
        Species s = spRepo.findById(spcId).orElse(null);
        Habitat h = hbRepo.findById(habId).orElse(null);
          
        if(s == null || h == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        h.addSpecies(s);
        
        return new ResponseEntity(hbRepo.save(h), HttpStatus.OK);
    }
    
    @PutMapping("/{habId}/removerespecie/{speciesId}")
    public ResponseEntity<Habitat> removeSpeciesFromHabitat(@PathVariable("speciesId") Integer spcId, 
            @PathVariable("habId") Integer habId){
        Species s = spRepo.findById(spcId).orElse(null);
        Habitat h = hbRepo.findById(habId).orElse(null);
          
        if(s == null || h == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        h.removeSpecies(s);
        
        return new ResponseEntity(hbRepo.save(h), HttpStatus.OK);
    }
    
    @PutMapping("/{habId}/agregarespecies")
    public ResponseEntity<Habitat> addMultipleSpeciesToHabitat(@PathVariable("habId") Integer habId,
            @RequestBody List<Integer> spsId){
        Habitat h = hbRepo.findById(habId).orElse(null);
        
        if(h == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        for (Integer spId : spsId) {
            Species s = spRepo.findById(spId).orElse(null);
            if (s != null) h.addSpecies(s);
        }
        
        return new ResponseEntity(hbRepo.save(h), HttpStatus.OK);
    }
    
    @PutMapping("/{habId}/removerespecies")
    public ResponseEntity<Habitat> removeMultipleSpeciesFromHabitat(@PathVariable("habId") Integer habId,
            @RequestBody List<Integer> spsId){
        Habitat h = hbRepo.findById(habId).orElse(null);
        
        if(h == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        for (Integer spId : spsId) {
            Species s = spRepo.findById(spId).orElse(null);
            if (s != null) h.removeSpecies(s);
        }
        
        return new ResponseEntity(hbRepo.save(h), HttpStatus.OK);
    }
    
    @PutMapping("/{habId}/removerespecies/todas")
    public ResponseEntity<Habitat> removeAllSpecies(@PathVariable("habId") Integer habId){
        Habitat h = hbRepo.findById(habId).orElse(null);
        
        if(h == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        h.removeAllSpecies();
        
        return new ResponseEntity(hbRepo.save(h), HttpStatus.OK);
    }
    
    @PutMapping("/{habId}/agregarcontinente/{contId}")
    public ResponseEntity<Habitat> addContinentToHabitat(@PathVariable("contId") Integer contId, 
            @PathVariable("habId") Integer habId){
        Continent c = conRepo.findById(contId).orElse(null);
        Habitat h = hbRepo.findById(habId).orElse(null);
          
        if(c == null || h == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        h.addContinent(c);
        
        return new ResponseEntity(hbRepo.save(h), HttpStatus.OK);
    }
    
    @PutMapping("/{habId}/removercontinente/{contId}")
    public ResponseEntity<Habitat> removeContinentFromHabitat(@PathVariable("contId") Integer contId, 
            @PathVariable("habId") Integer habId){
        Continent c = conRepo.findById(contId).orElse(null);
        Habitat h = hbRepo.findById(habId).orElse(null);
          
        if(c == null || h == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        h.removeContinent(c);
        
        return new ResponseEntity(hbRepo.save(h), HttpStatus.OK);
    }
    
    @PutMapping("/{habId}/agregarcontinentes")
    public ResponseEntity<Habitat> addContinentsToHabitat(@PathVariable("habId") Integer habId,
            @RequestBody List<Integer> consId){
        Habitat h = hbRepo.findById(habId).orElse(null);
        
        if(h == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        for (Integer conId : consId) {
            Continent c = conRepo.findById(conId).orElse(null);
            if (c != null) h.addContinent(c);
        }
        
        return new ResponseEntity(hbRepo.save(h), HttpStatus.OK);
    }
    
    @PutMapping("/{habId}/removercontinentes")
    public ResponseEntity<Habitat> removeContinentsFromHabitat(@PathVariable("habId") Integer habId,
            @RequestBody List<Integer> consId){
        Habitat h = hbRepo.findById(habId).orElse(null);
        
        if(h == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        for (Integer conId : consId) {
            Continent c = conRepo.findById(conId).orElse(null);
            if (c != null) h.removeContinent(c);
        }
        
        return new ResponseEntity(hbRepo.save(h), HttpStatus.OK);
    }
    
    @PutMapping("/{habId}/removercontinentes/todos")
    public ResponseEntity<Habitat> removeAllContinents(@PathVariable("habId") Integer habId){
        Habitat h = hbRepo.findById(habId).orElse(null);
        
        if(h == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        h.removeAllContinents();
        
        return new ResponseEntity(hbRepo.save(h), HttpStatus.OK);
    }
    
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<Habitat> deleteHabitatById(@PathVariable("id") Integer habitatId){
        Habitat h = hbRepo.findById(habitatId).orElse(null);
        if (h == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else {
            hbRepo.deleteById(habitatId);
            return new ResponseEntity<>(h, HttpStatus.OK);
        }
    }
    
    private boolean isHabitatSavable(Habitat h){
        return !(h == null || h.getName() == null || h.getWeather() == null || h.getVegetation() == null);
    }
}
