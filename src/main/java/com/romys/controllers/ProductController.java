package com.romys.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romys.models.ProductModel;
import com.romys.payloads.responses.BodyResponse;
import com.romys.services.ProductService;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<BodyResponse<ProductModel>> getAll() throws IOException {
        return new ResponseEntity<>(
                new BodyResponse<>("ok", HttpStatus.OK.value(), "all data of products", this.service.getProducts()),
                HttpStatus.OK);
    }
}
