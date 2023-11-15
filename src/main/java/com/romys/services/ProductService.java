package com.romys.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.romys.models.ProductModel;
import com.romys.payloads.hit.ElasticHit;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import java.util.List;

@Service
public class ProductService {
        @Autowired
        private ElasticsearchClient client;

        @Value("${service.elastic.index.products}")
        private String products;

        public ArrayList<ElasticHit<ProductModel>> getProducts() throws IOException {
                SearchResponse<ProductModel> response = this.client.search(search -> search.index(this.products),
                                ProductModel.class);

                return new ArrayList<>(
                                response.hits().hits().stream()
                                                .map(product -> new ElasticHit<ProductModel>(product.id(),
                                                                product.index(),
                                                                product.source()))
                                                .collect(Collectors.toList()));
        }

        public ArrayList<ElasticHit<ProductModel>> getProductByid(String id) throws IOException {
                GetResponse<ProductModel> response = this.client.get(
                                get -> get.index(this.products).id(id),
                                ProductModel.class);
                // if (!response.found())
                // throw new AlquranException("surat not found");

                return new ArrayList<>(List
                                .of(new ElasticHit<ProductModel>(response.id(), response.index(), response.source())));

        }
}
