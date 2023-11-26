package com.romys.services;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.romys.exceptions.UserNotFoundException;
import com.romys.models.LogModel;
import com.romys.payloads.hit.ElasticHit;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.core.search.TotalHits;

@Service
public class LogService {
    @Autowired
    private ElasticsearchClient client;

    @Value("${service.elastic.index.logs}")
    private String index;

    public String create(LogModel log) throws IOException {
        this.client.create(request -> request.index(this.index).document(log)
                .id(UUID.randomUUID().toString())
                .refresh(Refresh.True))
                .result();

        return null;
    }

    public List<ElasticHit<LogModel>> getByUsername(String username) throws IOException {
        return this.getByStr("username", username);
    }

    private List<ElasticHit<LogModel>> getByStr(String field, String value) throws IOException {
        try {
            SearchResponse<LogModel> response = this.client.search(search -> search
                    .index(this.index)
                    .query(query -> query
                            .bool(bool -> bool
                                    .must(must -> must
                                            .match(match -> match
                                                    .field(field)
                                                    .query(value))))),
                    LogModel.class);

            HitsMetadata<LogModel> meta = response.hits();
            TotalHits total = meta.total();

            if (!(total != null && total.value() >= 1))
                throw new UserNotFoundException("not found");

            return meta.hits().stream().map(hit -> new ElasticHit<LogModel>(hit.id(), hit.index(), hit.source()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw e;
        }
    }
}
