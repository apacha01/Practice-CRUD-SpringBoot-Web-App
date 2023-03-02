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
import p2.zoobackendcrud.entities.Itinerary;
import p2.zoobackendcrud.entities.Species;
import p2.zoobackendcrud.repositories.ZoneRepository;
import p2.zoobackendcrud.entities.Zone;
import p2.zoobackendcrud.repositories.ItineraryRepository;
import p2.zoobackendcrud.repositories.SpeciesRepository;

/**
 *
 * @author Agustín Pacheco
 */
@RestController
@RequestMapping("/zona")
public class ZoneController {
    
    @Autowired
    private ZoneRepository znRepo;
    
    @Autowired
    private ItineraryRepository itRepo;
    
    @Autowired
    private SpeciesRepository spRepo;
    
    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Zone createZone(@RequestBody Zone z) {
        if (!isZoneSavable(z))
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
    
    @GetMapping("/{id}/obtenerespecies")
    public List<Species> getZoneSpecies(@PathVariable("id") Integer zoneId){
        return spRepo.findByZoneId(zoneId);
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
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
    
    @PutMapping("/{zoneId}/agregaritinerario/{itinId}")
    public ResponseEntity<Zone> addItineraryToZone(@PathVariable("zoneId") Integer zoneId, 
            @PathVariable("itinId") Integer itinId){
        Zone z = znRepo.findById(zoneId).orElse(null);
        Itinerary i = itRepo.findById(itinId).orElse(null);
        
        if(z == null || i == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        z.addItinerary(i);
        
        znRepo.save(z);
        
        return new ResponseEntity(z, HttpStatus.OK);
    }
    
    @PutMapping("/{zoneId}/removeritinerario/{itinId}")
    public ResponseEntity<Zone> removeItineraryFromZone(@PathVariable("zoneId") Integer zoneId, 
            @PathVariable("itinId") Integer itinId){
        Zone z = znRepo.findById(zoneId).orElse(null);
        Itinerary i = itRepo.findById(itinId).orElse(null);
        
        if(z == null || i == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        z.removeItinerary(i);
        
        znRepo.save(z);
        
        return new ResponseEntity(z, HttpStatus.OK);
    }
    
    @PutMapping("/{zoneId}/agregaritinerarios")
    public ResponseEntity<Zone> addItinerariesToZone(@PathVariable("zoneId") Integer zoneId,
            @RequestBody List<Integer> itinsId){
        Zone z = znRepo.findById(zoneId).orElse(null);
        
        if(z == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        for (Integer itId : itinsId) {
            Itinerary i = itRepo.findById(itId).orElse(null);
            if (i != null) z.addItinerary(i);
        }
        
        znRepo.save(z);
        
        return new ResponseEntity(z, HttpStatus.OK);
    }
    
    @PutMapping("/{zoneId}/removeritinerarios")
    public ResponseEntity<Zone> removeItinerariesFromZone(@PathVariable("zoneId") Integer zoneId,
            @RequestBody List<Integer> itinsId){
        Zone z = znRepo.findById(zoneId).orElse(null);
        
        if(z == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        for (Integer itId : itinsId) {
            Itinerary i = itRepo.findById(itId).orElse(null);
            if (i != null) z.removeItinerary(i);
        }
        
        znRepo.save(z);
        
        return new ResponseEntity(z, HttpStatus.OK);
    }
    
    @PutMapping("/{zoneId}/removeritinerarios/todos")
    public ResponseEntity<Zone> removeAllItinerarios(@PathVariable("zoneId") Integer zoneId){
        Zone z = znRepo.findById(zoneId).orElse(null);
        
        if(z == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        z.removeAllItineraries();
        
        znRepo.save(z);
        
        return new ResponseEntity(z, HttpStatus.OK);
    }
    
    @PutMapping("/{zoneId}/agregarespecie/{speciesId}")
    public ResponseEntity<Zone> assignSpeciesToZone(@PathVariable("speciesId") Integer speciesId, 
            @PathVariable("zoneId") Integer zoneId){
        Species s = spRepo.findById(speciesId).orElse(null);
        Zone z = znRepo.findById(zoneId).orElse(null);
        
        if (s == null || z == null)
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        
        z.addSpecies(s);
        
        return new ResponseEntity(znRepo.save(z), HttpStatus.OK);
    }
    
    @PutMapping("/{zoneId}/removerespecie/{speciesId}")
    public ResponseEntity<Zone> removeSpeciesFromZone(@PathVariable("speciesId") Integer speciesId, 
            @PathVariable("zoneId") Integer zoneId){
        Species s = spRepo.findById(speciesId).orElse(null);
        Zone z = znRepo.findById(zoneId).orElse(null);
        
        if (s == null || z == null)
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        
        z.removeSpecies(s);
        
        return new ResponseEntity(znRepo.save(z), HttpStatus.OK);
    }
    
    @PutMapping("/{zoneId}/agregarespecies")
    public ResponseEntity<Zone> assignMultipleSpeciesToZone(@PathVariable("zoneId") Integer zoneId,
            @RequestBody List<Integer> spsId){
        Zone z = znRepo.findById(zoneId).orElse(null);
        
        if (z == null)
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        
        for (Integer spId : spsId) {
            Species s = spRepo.findById(spId).orElse(null);
            if (s != null) z.addSpecies(s);
        }
        
        return new ResponseEntity(znRepo.save(z), HttpStatus.OK);
    }
    
    @PutMapping("/{zoneId}/removerespecies")
    public ResponseEntity<Zone> removeAllSpeciesFromZone(@PathVariable("zoneId") Integer zoneId){
        Zone z = znRepo.findById(zoneId).orElse(null);
        
        if (z == null)
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        
        z.removeAllSpecies();
        
        return new ResponseEntity(znRepo.save(z), HttpStatus.OK);
    }
    
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<Zone> deleteZoneById(@PathVariable("id") Integer zoneId){
        Optional<Zone> optZn = znRepo.findById(zoneId);
        if (optZn.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else {
            spRepo.updateSpeciesZoneByZoneId(zoneId, null);
            znRepo.deleteById(zoneId);
            return new ResponseEntity<>(optZn.get(), HttpStatus.OK);
        }
    }
    
    private boolean isZoneSavable(Zone z){
        return !(z == null || z.getName() == null || z.getExtension() == null);
    }
}
