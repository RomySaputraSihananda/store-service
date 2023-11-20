package com.romys.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.romys.models.ProductModel;
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
import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {
        @Autowired
        private ElasticsearchClient client;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Value("${service.elastic.index.users}")
        private String index;

        public List<ElasticHit<UserModel>> createUser(UserDTO user) throws IOException {
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

        public List<ElasticHit<UserModel>> getUserByid(String id) throws IOException {
                GetResponse<UserModel> response = this.client.get(
                                get -> get.index(this.index).id(id),
                                UserModel.class);

                if (!response.found())
                        throw new ProductException("surat not found");

                return List
                                .of(new ElasticHit<UserModel>(response.id(), response.index(), response.source()));

        }

        public ElasticHit<UserModel> getUserByUsername(String username) throws IOException {
                return this.getByStr("username", username);
        }

        public ArrayList<ElasticHit<UserModel>> deleteUser(UserModel user) {
                return null;
        }

        public ElasticHit<UserModel> updateUser(UserDetailDTO userDetailDTO, ElasticHit<UserModel> hit,
                        HttpServletRequest request) throws IOException {
                if (userDetailDTO.getFirstName() != null)
                        hit.source().setFirstName(userDetailDTO.getFirstName());
                if (userDetailDTO.getLastName() != null)
                        hit.source().setLastName(userDetailDTO.getLastName());
                if (userDetailDTO.getMaidenName() != null)
                        hit.source().setMaidenName(userDetailDTO.getMaidenName());
                if (userDetailDTO.getAge() != 0)
                        hit.source().setAge(userDetailDTO.getAge());
                if (userDetailDTO.getGender() != null)
                        hit.source().setGender(userDetailDTO.getGender());
                if (userDetailDTO.getEmail() != null)
                        hit.source().setEmail(userDetailDTO.getEmail());
                if (userDetailDTO.getPhone() != null)
                        hit.source().setPhone(userDetailDTO.getPhone());
                if (userDetailDTO.getBirthDate() != null)
                        hit.source().setBirthDate(userDetailDTO.getBirthDate());
                if (userDetailDTO.getImage() != null)
                        hit.source().setImage(userDetailDTO.getImage());
                if (userDetailDTO.getBloodGroup() != null)
                        hit.source().setBloodGroup(userDetailDTO.getBloodGroup());
                if (userDetailDTO.getHeight() != 0)
                        hit.source().setHeight(userDetailDTO.getHeight());
                if (userDetailDTO.getWeight() != 0)
                        hit.source().setWeight(userDetailDTO.getWeight());
                if (userDetailDTO.getEyeColor() != null)
                        hit.source().setEyeColor(userDetailDTO.getEyeColor());
                if (userDetailDTO.getHair() != null)
                        hit.source().setHair(userDetailDTO.getHair());
                if (userDetailDTO.getDomain() != null)
                        hit.source().setDomain(userDetailDTO.getDomain());
                if (userDetailDTO.getAddress() != null)
                        hit.source().setAddress(userDetailDTO.getAddress());
                if (userDetailDTO.getMacAddress() != null)
                        hit.source().setMacAddress(userDetailDTO.getMacAddress());
                if (userDetailDTO.getUniversity() != null)
                        hit.source().setUniversity(userDetailDTO.getUniversity());
                if (userDetailDTO.getBank() != null)
                        hit.source().setBank(userDetailDTO.getBank());
                if (userDetailDTO.getCompany() != null)
                        hit.source().setCompany(userDetailDTO.getCompany());
                if (userDetailDTO.getEin() != null)
                        hit.source().setEin(userDetailDTO.getEin());
                if (userDetailDTO.getSsn() != null)
                        hit.source().setSsn(userDetailDTO.getSsn());

                hit.source().setIp(this.getClientIP(request));
                hit.source().setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));

                this.client.update(
                                update -> update.index(this.index).id(hit.id()).doc(hit.source())
                                                .refresh(Refresh.True),
                                UserModel.class);

                return hit;
        }

        // public List<ElasticHit<ProductModel>> updateProduct(ProductModel product,
        // String id) throws IOException {
        // ElasticHit<ProductModel> hit = this.getProductByid(id).get(0);

        // hit.source().setTitle(product.getTitle());
        // hit.source().setDescription(product.getDescription());
        // hit.source().setPrice(product.getPrice());
        // hit.source().setDiscountPercentage(product.getDiscountPercentage());
        // hit.source().setRating(product.getRating());
        // hit.source().setStock(product.getStock());
        // hit.source().setBrand(product.getBrand());
        // hit.source().setCategory(product.getCategory());
        // hit.source().setThumbnail(product.getThumbnail());
        // hit.source().setImages(product.getImages());

        // this.client.update(
        // update -> update.index(this.products).id(hit.id()).doc(hit.source())
        // .refresh(Refresh.True),
        // ProductModel.class);

        // return new ArrayList<>(List.of(hit));
        // }

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
