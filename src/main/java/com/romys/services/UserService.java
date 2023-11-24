package com.romys.services;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.romys.models.UserModel;
import com.romys.DTOs.PasswordDTO;
import com.romys.DTOs.UserDTO;
import com.romys.DTOs.UserDetailDTO;
import com.romys.exceptions.PasswordNotMatchException;
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
import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {
        @Autowired
        private ElasticsearchClient client;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Value("${service.elastic.index.users}")
        private String index;

        /*
         * create user
         */
        public List<ElasticHit<UserModel>> createUser(UserDTO user) throws IOException {

                String id = UUID.randomUUID().toString();
                user.setPassword(this.passwordEncoder.encode(user.getPassword()));

                this.client.create(request -> request.index(this.index).document(new UserModel(user))
                                .id(id)
                                .refresh(Refresh.True))
                                .result();

                return this.getUserByid(id);
        }

        /*
         * get all users
         */
        public List<ElasticHit<UserModel>> getUsers() throws IOException {
                SearchResponse<UserModel> response = this.client.search(search -> search.index(this.index),
                                UserModel.class);

                return response.hits().hits().stream()
                                .map(user -> new ElasticHit<>(user.id(), user.index(), user.source()))
                                .collect(Collectors.toList());
        }

        /*
         * get user by id
         */
        public List<ElasticHit<UserModel>> getUserByid(String id) throws IOException {
                GetResponse<UserModel> response = this.client.get(
                                get -> get.index(this.index).id(id),
                                UserModel.class);

                if (!response.found())
                        throw new ProductException("surat not found");

                return List
                                .of(new ElasticHit<UserModel>(response.id(), response.index(), response.source()));

        }

        /*
         * get user by name
         */
        public ElasticHit<UserModel> getUserByUsername(String username) throws IOException {
                return this.getByStr("username", username);
        }

        /*
         * delete user
         */
        public ArrayList<ElasticHit<UserModel>> deleteUser(UserModel user) {
                return null;
        }

        /*
         * update all field user
         */
        public ElasticHit<UserModel> updateUser(UserDetailDTO userDetailDTO, ElasticHit<UserModel> hit,
                        HttpServletRequest request) throws IOException {
                Class<?> hitSourceClass = hit.source().getClass();
                Field[] fields = userDetailDTO.getClass().getDeclaredFields();

                for (Field field : fields) {
                        try {
                                Object value = field.get(userDetailDTO);
                                if (value != null) {
                                        Field hitField = hitSourceClass.getDeclaredField(field.getName());
                                        hitField.setAccessible(true);
                                        hitField.set(hit.source(), value);
                                }
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                                System.out.println(e.toString());
                        }

                }

                hit.source().setIp(this.getClientIP(request));
                hit.source().setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));

                this.client.update(
                                update -> update.index(this.index).id(hit.id()).doc(hit.source())
                                                .refresh(Refresh.True),
                                UserModel.class);

                return hit;
        }

        /*
         * update password user with validation old password
         */
        public ElasticHit<UserModel> resetPassword(ElasticHit<UserModel> hit, PasswordDTO password) throws IOException {
                if (!passwordEncoder.matches(password.getOldPassword(), hit.source().getPassword()))
                        throw new PasswordNotMatchException("old password not match");

                hit.source().setPassword(passwordEncoder.encode(password.getNewPassword()));
                this.client.update(
                                update -> update.index(this.index).id(hit.id()).doc(hit.source())
                                                .refresh(Refresh.True),
                                UserModel.class);
                return hit;
        }

        /*
         * check username isExists
         */
        public boolean usernameIsExists(String username) throws IOException {
                try {
                        return this.getUserByUsername(username).source().getUsername().equals(username);
                } catch (UsernameNotFoundException | UserException e) {
                        return false;
                }

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

        private String getClientIP(HttpServletRequest request) {
                String xForwardedForHeader = request.getHeader("X-Forwarded-For");

                if (xForwardedForHeader == null) {
                        return request.getRemoteAddr();
                }

                return xForwardedForHeader.split(",")[0].trim();
        }
}
