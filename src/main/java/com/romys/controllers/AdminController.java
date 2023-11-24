package com.romys.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    @GetMapping
    public ResponseEntity<String> getAll() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getUser(@PathVariable String id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody String test) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody String test) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id, @RequestBody String test) {
        return null;
    }

    @GetMapping("/logs/{id}")
    public ResponseEntity<String> getLogs(@PathVariable String id) {
        return null;
    }

    @GetMapping("/search")
    public ResponseEntity<String> search(@RequestBody String test) {
        return null;
    }
}