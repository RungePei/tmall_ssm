package com.service;

import com.pojo.Product;

import java.util.List;

public interface ProductService {
    void add(Product product);

    void delete(int id);

    Product get(int id);

    void update(Product product);

    List<Product> list(int cid);
}
