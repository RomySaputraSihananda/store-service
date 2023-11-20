package com.romys.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.romys.models.UserModel;
import com.romys.DTOs.UserDTO;
import com.romys.DTOs.UserDetailDTO;
import com.romys.exceptions.ProductException;
import com.romys.exceptions.UserException;
import com.romys.payloads.hit.ElasticHit;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.core.search.TotalHits;

@Service
public class UserService {
        @Autowired
        private ElasticsearchClient client;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Value("${service.elastic.index.users}")
        private String index;

        public ArrayList<ElasticHit<UserModel>> createUser(UserDTO user) throws IOException {
                String id = UUID.randomUUID().toString();
                user.setPassword(this.passwordEncoder.encode(user.getPassword()));

                this.client.create(request -> request.index(this.index).document(new UserModel(user))
                                .id(id)
                                .refresh(Refresh.True))
                                .result();

                return this.getUserByid(id);
        }

        public ArrayList<ElasticHit<UserModel>> getUsers() throws IOException {
                SearchResponse<UserModel> response = this.client.search(search -> search.index(this.index),
                                UserModel.class);

                return new ArrayList<>(
                                response.hits().hits().stream()
                                                .map(user -> new ElasticHit<>(user.id(), user.index(), user.source()))
                                                .collect(Collectors.toList()));
        }

        public ArrayList<ElasticHit<UserModel>> getUserByid(String id) throws IOException {
                GetResponse<UserModel> response = this.client.get(
                                get -> get.index(this.index).id(id),
                                UserModel.class);

                if (!response.found())
                        throw new ProductException("surat not found");

                return new ArrayList<>(List
                                .of(new ElasticHit<UserModel>(response.id(), response.index(), response.source())));

        }

        public ElasticHit<UserModel> getUserByUsername(String username) throws IOException {
                return this.getByStr("username", username);
        }

        public ElasticHit<UserModel> updateUser(UserDetailDTO user, String id) {
                return null;
        }

        public ArrayList<ElasticHit<UserModel>> deleteUser(UserModel user) {
                return null;
        }

        private ElasticHit<UserModel> getByStr(String field, String value) throws IOException {
                try {
                        SearchResponse<UserModel> response = this.client.search(search -> search
                                        .index(this.index)
                                        .query(query -> query
                                                        .bool(bool -> bool
                                                                        .must(must -> must
                                                                                        .match(match -> match
                                                                                                        .field(field)
                                                                                                        .query(value))))),
                                        UserModel.class);

                        HitsMetadata<UserModel> meta = response.hits();
                        TotalHits total = meta.total();
                        if (!(total != null && total.value() >= 1))
                                throw new UserException("not found");

                        List<Hit<UserModel>> hits = meta.hits();
                        Hit<UserModel> hit = hits.get(0);
                        return new ElasticHit<UserModel>(
                                        hit.id(),
                                        hit.index(),
                                        hit.source());
                } catch (IOException e) {
                        throw e;
                }
        }
}
