package com.service;

import com.pojo.Propertyvalue;

import java.util.List;

public interface PropertyValueService {
    void init(int pid);

    List<Propertyvalue> list(int pid);

    void update(Propertyvalue pv);

    Propertyvalue get(int pid, int ptid);
}
