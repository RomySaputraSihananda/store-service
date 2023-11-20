package com.romys.payloads.responses;

import java.util.List;

import com.romys.models.ProductModel;
import com.romys.payloads.hit.ElasticHit;

public record ProductResponse<Product>(List<ElasticHit<ProductModel>> product) {

}
