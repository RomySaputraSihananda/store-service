package com.romys.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romys.models.ProductModel;
import com.romys.payloads.hit.ElasticHit;
import com.romys.payloads.responses.BodyResponse;
import com.romys.services.ProductService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
        @Autowired
        private ProductService service;

        @GetMapping
        public ResponseEntity<BodyResponse<ElasticHit<ProductModel>>> getAll() throws IOException {
                return new ResponseEntity<>(
                                new BodyResponse<>("ok", HttpStatus.OK.value(), "all data of products",
                                                this.service.getProducts()),
                                HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<BodyResponse<ElasticHit<ProductModel>>> getById(@PathVariable String id)
                        throws IOException {
                return new ResponseEntity<>(
                                new BodyResponse<>("ok", HttpStatus.OK.value(),
                                                String.format("data of products by id %s", id),
                                                this.service.getProductByid(id)),
                                HttpStatus.OK);
        }

        @PostMapping
        public ResponseEntity<BodyResponse<ElasticHit<ProductModel>>> addData(
                        @RequestBody(required = true) ProductModel product) throws IOException {
                return new ResponseEntity<>(
                                new BodyResponse<>("ok", HttpStatus.CREATED.value(),
                                                String.format("success create product with id %s", product.getId()),
                                                this.service.createProduct(product)),
                                HttpStatus.CREATED);
        }

        @PutMapping("/{id}")
        public ResponseEntity<BodyResponse<ElasticHit<ProductModel>>> updateData(@PathVariable String id,
                        @RequestBody(required = true) ProductModel product)
                        throws IOException {
                return new ResponseEntity<>(
                                new BodyResponse<>("ok", HttpStatus.OK.value(),
                                                String.format("product with id %s success deleted", id),
                                                this.service.getProducts()),
                                HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<BodyResponse<ElasticHit<ProductModel>>> deleteData(@PathVariable String id)
                        throws IOException {
                return new ResponseEntity<>(
                                new BodyResponse<>("ok", HttpStatus.OK.value(),
                                                String.format("product with id %s success deleted", id),
                                                this.service.getProducts()),
                                HttpStatus.OK);
        }
}
