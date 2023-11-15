package com.romys.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romys.models.UserModel;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private ElasticsearchClient client;

    @Value("${service.elastic.index.users}")
    private String users;

    @GetMapping
    public ArrayList<?> test() throws IOException {
        SearchResponse<?> response = this.client.search(search -> search.index(this.users),
                UserModel.class);

        return new ArrayList<>(
                response.hits().hits().stream().map(surat -> surat.source()).collect(Collectors.toList()));
        // return response;
    }
}
