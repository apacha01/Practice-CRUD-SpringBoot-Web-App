/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoobackendcrud.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import p2.zoobackendcrud.repositories.ItineraryRepository;
import p2.zoobackendcrud.entities.Itinerary;

/**
 *
 * @author Agustín Pacheco
 */
@RestController
@RequestMapping("itinerario")
public class ItineraryController {
    
    @Autowired
    private ItineraryRepository itRepo;
    
    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Itinerary create(@RequestBody Itinerary i) {
        if (i == null)
            return null;
        return itRepo.save(i);
    }
}
