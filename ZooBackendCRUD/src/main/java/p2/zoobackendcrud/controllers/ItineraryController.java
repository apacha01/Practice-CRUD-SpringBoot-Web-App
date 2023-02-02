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
import p2.zoobackendcrud.repositories.ItineraryRepository;
import p2.zoobackendcrud.entities.Itinerary;
import p2.zoobackendcrud.entities.Zone;
import p2.zoobackendcrud.repositories.ZoneRepository;

/**
 *
 * @author Agustín Pacheco
 */
@RestController
@RequestMapping("itinerario")
public class ItineraryController {
    
    @Autowired
    private ItineraryRepository itRepo;
    
    @Autowired
    private ZoneRepository znRepo;
    
    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Itinerary createItinerary(@RequestBody Itinerary i) {
        if (!isItinerarySavable(i))
            return null;
        return itRepo.save(i);
    }
    
    @GetMapping("/obtenertodos")
    public List<Itinerary> getAllItineraries() {
        return itRepo.findAll();
    }
    
    @GetMapping("/obtenerzonas/{id}")
    public List<Zone> getZonesFromItinerary(@PathVariable("id") Integer itineraryId){
        Itinerary i = itRepo.findById(itineraryId).orElse(null);
        List<Zone> zones = new ArrayList<>();
        if (i != null)
            zones.addAll(i.getCoveredZones());
        return zones;
    }
    
    @GetMapping("/obtenerporid/{id}")
    public ResponseEntity<Itinerary> getItineraryById(@PathVariable("id") Integer itineraryId){
        return itRepo.findById(itineraryId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/obtenerporcodigo/{codigo}")
    public List<Itinerary> getItineraryByCode(@PathVariable("codigo") String code){
        try{
            return itRepo.findByCode(URLDecoder.decode(code, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            return new ArrayList<>();
        }
    }
    
    @PutMapping("/modificarporid/{id}")
    public ResponseEntity<Itinerary> updateItineraryById(@PathVariable("id") Integer itineraryId, @RequestBody Itinerary i){
        return itRepo.findById(itineraryId)
                .map(savedItinerary -> {
                    if (i.getCode() != null) savedItinerary.setCode(i.getCode());
                    if (i.getDuration() != null) savedItinerary.setDuration(i.getDuration());
                    if (i.getRouteLength() != null) savedItinerary.setRouteLength(i.getRouteLength());
                    if (i.getMaxPeople() != null) savedItinerary.setMaxPeople(i.getMaxPeople());
                    if (i.getNumSpeciesVisited() != null) savedItinerary.setNumSpeciesVisited(i.getNumSpeciesVisited());
                    if (i.getAssigned() != null) savedItinerary.setAssigned(i.getAssigned());
                    Itinerary updatedItinerary = itRepo.save(savedItinerary);
                    return new ResponseEntity<>(updatedItinerary, HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{itinId}/agregarzona/{zoneId}")
    public ResponseEntity<Itinerary> addZoneToItinerary(@PathVariable("zoneId") Integer zoneId, 
            @PathVariable("itinId") Integer itinId){
        Zone z = znRepo.findById(zoneId).orElse(null);
        Itinerary i = itRepo.findById(itinId).orElse(null);
        
        if(z == null || i == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        i.addZone(z);
        
        itRepo.save(i);
        
        return new ResponseEntity(i, HttpStatus.OK);
    }
    
    @PutMapping("/{itinId}/removerzona/{zoneId}")
    public ResponseEntity<Itinerary> removeZoneFromItinerary(@PathVariable("zoneId") Integer zoneId, 
            @PathVariable("itinId") Integer itinId){
        Zone z = znRepo.findById(zoneId).orElse(null);
        Itinerary i = itRepo.findById(itinId).orElse(null);
        
        if(z == null || i == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        i.removeZone(z);
        
        itRepo.save(i);
        
        return new ResponseEntity(i, HttpStatus.OK);
    }
    
    @PutMapping("/{itinId}/agregarzonas")
    public ResponseEntity<Itinerary> addZonesToItinerary(@PathVariable("itinId") Integer itinId,
            @RequestBody List<Integer> zonesId){
        Itinerary i = itRepo.findById(itinId).orElse(null);
        
        if(i == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        for (Integer zoneId : zonesId) {
            Zone z = znRepo.findById(zoneId).orElse(null);
            if (z != null) i.addZone(z);
        }
        
        itRepo.save(i);
        
        return new ResponseEntity(i, HttpStatus.OK);
    }
    
    @PutMapping("/{itinId}/removerzonas")
    public ResponseEntity<Itinerary> removeZonesToItinerary(@PathVariable("itinId") Integer itinId,
            @RequestBody List<Integer> zonesId){
        Itinerary i = itRepo.findById(itinId).orElse(null);
        
        if(i == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        for (Integer zoneId : zonesId) {
            Zone z = znRepo.findById(zoneId).orElse(null);
            if (z != null) i.removeZone(z);
        }
        
        itRepo.save(i);
        
        return new ResponseEntity(i, HttpStatus.OK);
    }
    
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<Itinerary> deleteItineraryById(@PathVariable("id") Integer itineraryId){
        Optional<Itinerary> optItn = itRepo.findById(itineraryId);
        if (optItn.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else {
            itRepo.deleteById(itineraryId);
            return new ResponseEntity<>(optItn.get(), HttpStatus.OK);
        }
    }
    
    private boolean isItinerarySavable(Itinerary i){
        if (i == null)
            return false;
        
        if(i.getCode() == null || i.getDuration() == null || i.getMaxPeople() == null 
                || i.getNumSpeciesVisited() == null || i.getRouteLength() == null || i.getAssigned() == null)
            return false;
        
        return true;
    }
}
