package com.romys.configurations;

import org.springframework.context.annotation.Bean;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;

@Configuration
public class ElasticConfiguration {
        @Value("${service.elastic.host}")
        private String host;

        @Value("${service.elastic.cluster.uuid}")
        private String uuid;

        @Bean
        public ElasticsearchClient esClient() {
                RestClient restClient = RestClient.builder(HttpHost.create(this.host)).build();

                ElasticsearchTransport restClientTransport = new RestClientTransport(restClient,
                                new JacksonJsonpMapper(new ObjectMapper()
                                                .registerModule(
                                                                new JavaTimeModule())));

                return new ElasticsearchClient(restClientTransport);
        }
}
