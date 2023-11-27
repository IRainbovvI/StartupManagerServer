package org.dev.controller;

import org.dev.model.Startup;
import org.dev.repository.StartupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class StartupController {
    @Autowired
    StartupRepository startupRepository;

    @GetMapping("/startups")
    public ResponseEntity<List<Startup>> getAllStartups() {
        try {
            List<Startup> startups = new ArrayList<Startup>();

            startupRepository.findAll().forEach(startups::add);

            return new ResponseEntity<>(startups, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/startups/{id}")
    public ResponseEntity<Startup> getStartupById(@PathVariable("id") long id) {
        Optional<Startup> startupData = startupRepository.findById(id);

        if (startupData.isPresent()) {
            return new ResponseEntity<>(startupData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/startups")
    public ResponseEntity<Startup> createStartup(@RequestBody Startup startup) {
        try {
            Startup _startup = startupRepository.save(new Startup(startup.getTitle(), startup.getDescription()));
            return new ResponseEntity<>(_startup, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
