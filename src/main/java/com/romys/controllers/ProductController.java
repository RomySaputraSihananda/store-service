package com.romys.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.romys.models.ProductModel;
import com.romys.payloads.hit.ElasticHit;
import com.romys.payloads.responses.BodyResponses;
import com.romys.services.JwtService;
import com.romys.services.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
        @Autowired
        private ProductService service;

        @Autowired
        private JwtService jwt;

        /*
         * get all data
         */
        @GetMapping
        @Operation(summary = "Get all products", description = "API for get all products")
        public ResponseEntity<BodyResponses<ElasticHit<ProductModel>>> getAll() throws IOException {
                return new ResponseEntity<>(
                                new BodyResponses<>("ok", HttpStatus.OK.value(), "all data of products",
                                                this.service.getProducts()),
                                HttpStatus.OK);
        }

        /*
         * get data by id
         */
        @GetMapping("/{id}")
        @Operation(summary = "Get product by id", description = "API for get product by id")
        public ResponseEntity<BodyResponses<ElasticHit<ProductModel>>> getById(@PathVariable String id)
                        throws IOException {
                return new ResponseEntity<>(
                                new BodyResponses<>("ok", HttpStatus.OK.value(),
                                                String.format("data of products by id %s", id),
                                                this.service.getProductByid(id)),
                                HttpStatus.OK);
        }

        /*
         * get data by name
         */
        @GetMapping("/search")
        @Operation(summary = "Get product by name", description = "API for get product by name")
        public ResponseEntity<BodyResponses<ElasticHit<ProductModel>>> getByName(
                        @RequestParam(required = true) String field, @RequestParam(required = true) String value)
                        throws IOException {
                return new ResponseEntity<>(
                                new BodyResponses<>("ok", HttpStatus.OK.value(),
                                                String.format("data of products by value %s on field %s", value, field),
                                                this.service.searchProduct(field, value)),
                                HttpStatus.OK);
        }

        /*
         * create data
         */
        @PostMapping
        @Operation(summary = "Create new product", description = "API for create new product")
        public ResponseEntity<BodyResponses<ElasticHit<ProductModel>>> addData(
                        @RequestBody(required = true) ProductModel product) throws IOException {
                List<ElasticHit<ProductModel>> response = this.service.createProduct(product);

                return new ResponseEntity<>(
                                new BodyResponses<>("ok", HttpStatus.CREATED.value(),
                                                String.format("product with id success created"),
                                                response),
                                HttpStatus.CREATED);
        }

        /*
         * update data
         */
        @PutMapping("/{id}")
        @Operation(summary = "Update product", description = "API for update product")
        public ResponseEntity<BodyResponses<ElasticHit<ProductModel>>> updateData(
                        @PathVariable String id,
                        @RequestBody(required = true) ProductModel product)
                        throws IOException {
                return new ResponseEntity<>(
                                new BodyResponses<>("ok", HttpStatus.OK.value(),
                                                String.format("product with id %s success updated", id),
                                                this.service.updateProduct(product, id)),
                                HttpStatus.OK);
        }

        /*
         * delete data
         */
        @DeleteMapping("/{id}")
        @Operation(summary = "Delete product", description = "API for delete product")
        public ResponseEntity<BodyResponses<ElasticHit<ProductModel>>> deleteData(
                        @PathVariable String id)
                        throws IOException {
                return new ResponseEntity<>(
                                new BodyResponses<>("ok", HttpStatus.OK.value(),
                                                String.format("product with id %s success deleted", id),
                                                this.service.deleteProduct(id)),
                                HttpStatus.OK);
        }

        @GetMapping("/test")
        @Operation(summary = "4 test", description = "4 test")
        public String fortest(HttpServletRequest request)
                        throws IOException {
                String token = request.getHeader(HttpHeaders.AUTHORIZATION);
                return this.jwt.extractUsername(token);
        }
}
