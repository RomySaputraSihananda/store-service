package com.romys.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.romys.models.UserModel;
import com.romys.payloads.hit.ElasticHit;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;

@Service
public class UserService {
        @Autowired
        private ElasticsearchClient client;

        @Value("${service.elastic.index.users}")
        private String users;

        public ArrayList<ElasticHit<UserModel>> getUsers() throws IOException {
                SearchResponse<UserModel> response = this.client.search(search -> search.index(this.users),
                                UserModel.class);

                return new ArrayList<>(
                                response.hits().hits().stream()
                                                .map(user -> new ElasticHit<>(user.id(), user.index(), user.source()))
                                                .collect(Collectors.toList()));
        }
}
