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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import p2.zoobackendcrud.entities.Species;
import p2.zoobackendcrud.entities.Zone;
import p2.zoobackendcrud.repositories.SpeciesRepository;
import p2.zoobackendcrud.repositories.ZoneRepository;

/**
 *
 * @author Agustín Pacheco
 */
@RestController
@RequestMapping("/especie")
public class SpeciesController {
    
    @Autowired
    private SpeciesRepository spRepo;

    @Autowired
    private ZoneRepository znRepo;
    
    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Species createSpecies(@RequestBody Species s) {
        if (s == null)
            return null;
        return spRepo.save(s);
    }
    
    @GetMapping("/obtenertodos")
    public List<Species> getAllSpecies() {
        return spRepo.findAll();
    }
    
    @GetMapping("/obtenerporid/{id}")
    public ResponseEntity<Species> getSpeciesById(@PathVariable("id") Integer speciesId){
        return spRepo.findById(speciesId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/obtenerpornombre/{nombre}")
    public List<Species> getSpeciesByScientificName(@PathVariable("nombre") String name){
        try{
            return spRepo.findByNameContaining(URLDecoder.decode(name, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            return new ArrayList<>();
        }
    }
}
