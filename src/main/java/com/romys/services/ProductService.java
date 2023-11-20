package com.romys.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.romys.exceptions.ProductException;
import com.romys.models.ProductModel;
import com.romys.payloads.hit.ElasticHit;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
        @Autowired
        private ElasticsearchClient client;

        @Value("${service.elastic.index.products}")
        private String products;

        public List<ElasticHit<ProductModel>> getProducts() throws IOException {
                SearchResponse<ProductModel> response = this.client.search(search -> search.index(this.products),
                                ProductModel.class);

                return response.hits().hits().stream()
                                .map(product -> new ElasticHit<ProductModel>(product.id(),
                                                product.index(),
                                                product.source()))
                                .collect(Collectors.toList());
        }

        public List<ElasticHit<ProductModel>> getProductByid(String id) throws IOException {
                GetResponse<ProductModel> response = this.client.get(
                                get -> get.index(this.products).id(id),
                                ProductModel.class);

                if (!response.found())
                        throw new ProductException("product not found");

                return List.of(new ElasticHit<ProductModel>(response.id(), response.index(), response.source()));
        }

        public List<ElasticHit<ProductModel>> searchProduct(String field, String value) throws IOException {
                SearchResponse<ProductModel> response = this.client.search(search -> search
                                .index(this.products)
                                .query(query -> query
                                                .bool(bool -> bool
                                                                .must(must -> must
                                                                                .matchPhrasePrefix(match -> match
                                                                                                .field(field)
                                                                                                .query(value))))),
                                ProductModel.class);

                return response.hits().hits().stream().map(
                                product -> new ElasticHit<>(product.id(), product.index(), product.source()))
                                .collect(Collectors.toList());
        }

        public List<ElasticHit<ProductModel>> createProduct(ProductModel product) throws IOException {
                String id = UUID.randomUUID().toString();

                this.client.create(request -> request.index(this.products).document(product)
                                .id(id)
                                .refresh(Refresh.True))
                                .result();

                return this.getProductByid(id);
        }

        public List<ElasticHit<ProductModel>> updateProduct(ProductModel product, String id) throws IOException {
                ElasticHit<ProductModel> hit = this.getProductByid(id).get(0);

                hit.source().setTitle(product.getTitle());
                hit.source().setDescription(product.getDescription());
                hit.source().setPrice(product.getPrice());
                hit.source().setDiscountPercentage(product.getDiscountPercentage());
                hit.source().setRating(product.getRating());
                hit.source().setStock(product.getStock());
                hit.source().setBrand(product.getBrand());
                hit.source().setCategory(product.getCategory());
                hit.source().setThumbnail(product.getThumbnail());
                hit.source().setImages(product.getImages());

                this.client.update(
                                update -> update.index(this.products).id(hit.id()).doc(hit.source())
                                                .refresh(Refresh.True),
                                ProductModel.class);

                return new ArrayList<>(List.of(hit));
        }

        public List<ElasticHit<ProductModel>> deleteProduct(String id) throws IOException {
                GetResponse<ProductModel> response = this.client.get(
                                get -> get.index(this.products).id(id),
                                ProductModel.class);

                if (!response.found())
                        throw new ProductException("product not found");

                this.client.delete(delete -> delete.index(this.products).id(id));
                return null;
        }
}
