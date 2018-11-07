package com.service;

import com.pojo.Category;
import com.pojo.Product;

import java.util.List;

public interface ProductService {
    void add(Product product);

    void delete(int id);

    Product get(int id);

    void update(Product product);

    List<Product> list(int cid);

    void setFirstProductImage(Product p);

    void fill(List<Category> cs);

    void fillByRow(List<Category> cs);

    void setSaleAndReviewNumber(Product product);

    void setSaleAndReviewNumber(List<Product> products);

    List<Product> search(String keyword);
}
