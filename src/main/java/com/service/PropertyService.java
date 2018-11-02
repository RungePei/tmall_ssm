package com.service;

import com.pojo.Property;

import java.util.List;

public interface PropertyService {
    void add(Property property);

    void delete(int id);

    void update(Property property);

    List<Property> list(int cid);

    Property get(int id);
}
