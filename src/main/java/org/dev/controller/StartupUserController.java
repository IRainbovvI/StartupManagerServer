package org.dev.controller;

import org.dev.model.StartupUser;
import org.dev.repository.StartupUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/startupUsers")
public class StartupUserController {

    @Autowired
    private StartupUserRepository startupUserRepository;

    @GetMapping
    public ResponseEntity<List<StartupUser>> getAllStartupUsers() {
        List<StartupUser> startupUsers = startupUserRepository.findAll();
        return new ResponseEntity<>(startupUsers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StartupUser> getStartupUserById(@PathVariable("id") long id) {
        Optional<StartupUser> startupUserData = startupUserRepository.findById(id);

        return startupUserData.map(startupUser -> new ResponseEntity<>(startupUser, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<StartupUser> createStartupUser(@RequestBody StartupUser startupUser) {
        try {
            StartupUser _startupUser = startupUserRepository.save(startupUser);
            return new ResponseEntity<>(_startupUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<StartupUser> updateStartupUser(@PathVariable("id") long id, @RequestBody StartupUser startupUser) {
        Optional<StartupUser> startupUserData = startupUserRepository.findById(id);

        if (startupUserData.isPresent()) {
            StartupUser _startupUser = startupUserData.get();
            _startupUser.setUser(startupUser.getUser());
            _startupUser.setStartup(startupUser.getStartup());
            _startupUser.setAuthor(startupUser.getIsAuthor());

            return new ResponseEntity<>(startupUserRepository.save(_startupUser), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStartupUser(@PathVariable("id") long id) {
        try {
            startupUserRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
