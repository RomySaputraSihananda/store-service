package com.romys.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.romys.models.UserModel;
import com.romys.DTOs.UserDTO;
import com.romys.exceptions.ProductException;
import com.romys.payloads.hit.ElasticHit;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch.core.GetResponse;
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

        public ArrayList<ElasticHit<UserModel>> getUserByid(String id) throws IOException {
                GetResponse<UserModel> response = this.client.get(
                                get -> get.index(this.users).id(id),
                                UserModel.class);

                if (!response.found())
                        throw new ProductException("surat not found");

                return new ArrayList<>(List
                                .of(new ElasticHit<UserModel>(response.id(), response.index(), response.source())));

        }

        public ArrayList<ElasticHit<UserModel>> createUser(UserDTO user) throws IOException {
                String id = UUID.randomUUID().toString();

                this.client.create(request -> request.index(this.users).document(new UserModel(user))
                                .id(id)
                                .refresh(Refresh.True))
                                .result();

                return this.getUserByid(id);
        }

        public ArrayList<ElasticHit<UserModel>> updateUser(UserModel user, String id) {
                return null;
        }

        public ArrayList<ElasticHit<UserModel>> deleteUser(UserModel user) {
                return null;
        }

}
