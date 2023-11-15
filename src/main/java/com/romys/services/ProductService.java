package com.romys.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.romys.models.ProductModel;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;

@Service
public class ProductService {
    @Autowired
    private ElasticsearchClient client;

    @Value("${service.elastic.index.products}")
    private String products;

    public ArrayList<ProductModel> getProducts() throws IOException {
        SearchResponse<ProductModel> response = this.client.search(search -> search.index(this.products),
                ProductModel.class);

        return new ArrayList<>(
                response.hits().hits().stream().map(surat -> surat.source()).collect(Collectors.toList()));
    }
}
