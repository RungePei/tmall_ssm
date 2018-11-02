package com.service;

import com.pojo.Category;
import com.util.Page;

import java.util.List;

public interface CategoryService {
//    List<Category> list(Page page);

    List<Category> list();

    Category get(int id);

    void add(Category category);

    void delete(int id);

    void update(Category category);
}
