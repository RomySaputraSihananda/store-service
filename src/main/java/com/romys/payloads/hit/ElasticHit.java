package com.romys.payloads.hit;

public record ElasticHit<Source>(String id, String index, Source source) {
}
