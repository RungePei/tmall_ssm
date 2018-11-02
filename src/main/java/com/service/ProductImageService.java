package com.service;

import com.pojo.Productimage;

import java.util.List;

public interface ProductImageService {

    String type_single = "type_single";
    String type_detail = "type_detail";

    void add(Productimage productimage);

    void delete(int id);

    List<Productimage> list(int pid, String type);

    Productimage get(int id);
}
