package com.romys.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romys.models.ProductModel;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    private ElasticsearchClient client;

    @Value("${service.elastic.index.products}")
    private String products;

    @GetMapping
    public ArrayList<?> test() throws IOException {
        SearchResponse<?> response = this.client.search(search -> search.index(this.products),
                ProductModel.class);

        return new ArrayList<>(
                response.hits().hits().stream().map(surat -> surat.source()).collect(Collectors.toList()));
        // return response;
    }
}
