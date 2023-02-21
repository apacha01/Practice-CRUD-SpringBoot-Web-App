/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoobackendcrud.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
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
import p2.zoobackendcrud.entities.Employee;
import p2.zoobackendcrud.entities.Habitat;
import p2.zoobackendcrud.entities.Species;
import p2.zoobackendcrud.entities.SpeciesKeeper;
import p2.zoobackendcrud.entities.Zone;
import p2.zoobackendcrud.repositories.EmployeeRepository;
import p2.zoobackendcrud.repositories.HabitatRepository;
import p2.zoobackendcrud.repositories.SpeciesKeeperRepository;
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
    
    @Autowired
    private EmployeeRepository empRepo;
    
    @Autowired
    private SpeciesKeeperRepository skRepo;
    
    @Autowired
    private HabitatRepository hbRepo;
    
    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Species createSpecies(@RequestBody Species s) {
        if (!isSpeciesSavable(s))
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
    public List<Species> getSpeciesByName(@PathVariable("nombre") String name){
        try{
            return spRepo.findByNameContaining(URLDecoder.decode(name, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            return new ArrayList<>();
        }
    }
    
    @GetMapping("/{id}/obtenercuidadores")
    public List<Employee> getSpeciesKeepers(@PathVariable("id") Integer id) {
        
        List<SpeciesKeeper> sks = skRepo.findBySpeciesId(id);
        List<Employee> e = new ArrayList<>();
        for (SpeciesKeeper sk : sks) {
            e.add(sk.getKeeper());
        }
        
        return e;
    }
    
    @PutMapping("/modificarporid/{id}")
    public ResponseEntity<Species> updateSpeciesById(@PathVariable("id") Integer speciesId, @RequestBody Species s){
        return spRepo.findById(speciesId)
                .map(savedSpecies -> {
                    if (s.getName() != null) savedSpecies.setName(s.getName());
                    if (s.getScientificName() != null) savedSpecies.setScientificName(s.getScientificName());
                    savedSpecies.setZone(s.getZone());
                    Species updatedSpecies = spRepo.save(savedSpecies);
                    return new ResponseEntity<>(updatedSpecies, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
    
    @PutMapping("/{speciesId}/agregarzona/{zoneId}")
    public ResponseEntity<Species> assignZoneToSpecies(@PathVariable("speciesId") Integer speciesId, 
            @PathVariable("zoneId") Integer zoneId){
        Species s = spRepo.findById(speciesId).orElse(null);
        Zone z = znRepo.findById(zoneId).orElse(null);
        
        if (s == null || z == null)
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        
        s.setZone(z);
        
        return new ResponseEntity(spRepo.save(s), HttpStatus.OK);
    }
    
    @PutMapping("/{speciesId}/removerzona/")
    public ResponseEntity<Species> removeZoneFromSpecies(@PathVariable("speciesId") Integer speciesId){
        Species s = spRepo.findById(speciesId).orElse(null);
        
        if (s == null)
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        
        s.setZone(null);
        
        return new ResponseEntity(spRepo.save(s), HttpStatus.OK);
    }
    
    @PutMapping("/{spcId}/agregarcuidador/{empId}")
    public ResponseEntity<SpeciesKeeper> assignKeeperToSpecies(@PathVariable("empId") Integer empId, 
            @PathVariable("spcId") Integer spcId){
        Employee e = empRepo.findById(empId).orElse(null);
        Species s = spRepo.findById(spcId).orElse(null);
        
        if (e == null || s == null)
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        
        if(!e.isKeeper())
            return new ResponseEntity(null, HttpStatus.UNPROCESSABLE_ENTITY);
        
        if(e.hasSpecies(s))
            return new ResponseEntity(null, HttpStatus.NOT_MODIFIED);
        
        SpeciesKeeper sk = skRepo.save(new SpeciesKeeper(e,s,new Date()));
        
        return new ResponseEntity(sk, HttpStatus.OK);
    }
    
    @PutMapping("/{spcId}/removercuidador/{empId}")
    public ResponseEntity<SpeciesKeeper> removeKeeperFromSpecies(@PathVariable("empId") Integer empId, 
            @PathVariable("spcId") Integer spcId){
        SpeciesKeeper sk = skRepo.findByIds(empId, spcId);
        
        if (sk != null)
            skRepo.delete(sk);
        
        return new ResponseEntity(null, HttpStatus.OK);
    }
    
    @PutMapping("/{spcId}/agregarcuidadores")
    public ResponseEntity<Species> assignKeepersToSpecies(@PathVariable("spcId") Integer spcId, 
            @RequestBody List<Integer> empIds){
        Species s = spRepo.findById(spcId).orElse(null);
        Boolean assigned = false;
        
        if (s == null)
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        
        for (Integer empId : empIds) {
            Employee e = empRepo.findById(empId).orElse(null);
            if (e != null && e.isKeeper()){
                if(e.hasSpecies(s))
                    assigned = true;
                else skRepo.save(new SpeciesKeeper(e,s,new Date()));
            }
        }
        
        //If species was already assigned to employee dont asign again and notify
        return (assigned ? new ResponseEntity(s, HttpStatus.PARTIAL_CONTENT) : new ResponseEntity(s, HttpStatus.OK));
    }
    
    @PutMapping("/{empId}/removercuidadores")
    public ResponseEntity<Species> removeKeepersFromSpecies(@PathVariable("empId") Integer spcId, 
            @RequestBody List<Integer> empIds){
        
        for (Integer empId : empIds) {
            SpeciesKeeper sk = skRepo.findByIds(empId, spcId);
            if (sk != null)
                skRepo.delete(sk);
        }
        
        return new ResponseEntity(null, HttpStatus.OK);
    }
    
    @PutMapping("/{spcId}/removercuidadores/todos")
    public ResponseEntity<Employee> removeAllKeepersFromSpecies(@PathVariable("spcId") Integer spcId){
        List<SpeciesKeeper> sks = skRepo.findBySpeciesId(spcId);
        for (SpeciesKeeper sk : sks)
            skRepo.delete(sk);
        
        return new ResponseEntity(null, HttpStatus.OK);
    }
    
    @PutMapping("/{speciesId}/agregarhabitat/{habId}")
    public ResponseEntity<Species> addHabitatToSpecies(@PathVariable("speciesId") Integer spcId, 
            @PathVariable("habId") Integer habId){
        Species s = spRepo.findById(spcId).orElse(null);
        Habitat h = hbRepo.findById(habId).orElse(null);
          
        if(s == null || h == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        s.addHabitat(h);
        
        spRepo.save(s);
        
        return new ResponseEntity(null, HttpStatus.OK);
    }
    
    @PutMapping("/{speciesId}/removerhabitat/{habId}")
    public ResponseEntity<Species> removeHabitatFromSpecies(@PathVariable("speciesId") Integer spcId, 
            @PathVariable("habId") Integer habId){
        Species s = spRepo.findById(spcId).orElse(null);
        Habitat h = hbRepo.findById(habId).orElse(null);
          
        if(s == null || h == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        s.removeHabitat(h);
        
        spRepo.save(s);
        
        return new ResponseEntity(null, HttpStatus.OK);
    }
    
    @PutMapping("/{speciesId}/agregarhabitats")
    public ResponseEntity<Species> addHabitatsToSpecies(@PathVariable("speciesId") Integer spId,
            @RequestBody List<Integer> habsId){
        Species s = spRepo.findById(spId).orElse(null);
        
        if(s == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        for (Integer hbId : habsId) {
            Habitat h = hbRepo.findById(hbId).orElse(null);
            if (h != null) s.addHabitat(h);
        }
        
        spRepo.save(s);
        
        return new ResponseEntity(null, HttpStatus.OK);
    }
    
    @PutMapping("/{speciesId}/removerhabitats")
    public ResponseEntity<Species> removeHabitatsFromSpecies(@PathVariable("speciesId") Integer spId,
            @RequestBody List<Integer> habsId){
        Species s = spRepo.findById(spId).orElse(null);
        
        if(s == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        for (Integer hbId : habsId) {
            Habitat h = hbRepo.findById(hbId).orElse(null);
            if (h != null) s.removeHabitat(h);
        }
        
        spRepo.save(s);
        
        return new ResponseEntity(null, HttpStatus.OK);
    }
    
    @PutMapping("/{speciesId}/removerhabitats/todos")
    public ResponseEntity<Species> removeAllSpecies(@PathVariable("speciesId") Integer spId){
        Species s = spRepo.findById(spId).orElse(null);
        
        if(s == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        
        s.removeAllHabitats();
        
        spRepo.save(s);
        
        return new ResponseEntity(null, HttpStatus.OK);
    }
    
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<Species> deleteSpeciesById(@PathVariable("id") Integer speciesId){
        Species s = spRepo.findById(speciesId).orElse(null);
        if (s == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else {
            spRepo.deleteById(speciesId);
            return new ResponseEntity<>(s, HttpStatus.OK);
        }
    }
    
    private boolean isSpeciesSavable(Species s){
        return !(s == null || s.getName() == null || s.getScientificName() == null || s.getDescription() == null);
    }
}
