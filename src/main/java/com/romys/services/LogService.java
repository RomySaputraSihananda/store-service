package com.romys.services;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.romys.models.LogModel;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Refresh;

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
}
