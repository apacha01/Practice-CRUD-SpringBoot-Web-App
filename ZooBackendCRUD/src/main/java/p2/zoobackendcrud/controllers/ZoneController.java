/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoobackendcrud.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import p2.zoobackendcrud.repositories.ZoneRepository;
import p2.zoobackendcrud.entities.Zone;

/**
 *
 * @author Agustín Pacheco
 */
@RestController
@RequestMapping("/zona")
public class ZoneController {
    
    @Autowired
    private ZoneRepository znRepo;
    
    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Zone createZone(@RequestBody Zone z) {
        if (z == null)
            return null;
        return znRepo.save(z);
    }
    
    @GetMapping("/obtenertodos")
    public List<Zone> getAllZones() {
        return znRepo.findAll();
    }
    
    @GetMapping("/obtenerporid/{id}")
    public ResponseEntity<Zone> getZoneById(@PathVariable("id") Integer zoneId){
        return znRepo.findById(zoneId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/obtenerpornombre/{nombre}")
    public List<Zone> getZoneByName(@PathVariable("nombre") String name){
        try{
            return znRepo.findByNameContaining(URLDecoder.decode(name, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            return new ArrayList<>();
        }
    }
    
    @PutMapping("/modificarporid/{id}")
    public ResponseEntity<Zone> updateZoneById(@PathVariable("id") Integer zoneId, @RequestBody Zone z){
        return znRepo.findById(zoneId)
                .map(savedZone -> {
                    if (z.getName() != null) savedZone.setName(z.getName());
                    if (z.getExtension() != null) savedZone.setExtension(z.getExtension());
                    Zone updatedZone = znRepo.save(savedZone);
                    return new ResponseEntity<>(updatedZone, HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<Zone> deleteZoneById(@PathVariable("id") Integer zoneId){
        Optional<Zone> optZn = znRepo.findById(zoneId);
        if (optZn.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else {
            znRepo.deleteById(zoneId);
            return new ResponseEntity<>(optZn.get(), HttpStatus.OK);
        }
    }
}
