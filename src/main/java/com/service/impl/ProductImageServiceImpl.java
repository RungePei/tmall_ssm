package com.service.impl;

import com.mapper.ProductimageMapper;
import com.pojo.Productimage;
import com.pojo.ProductimageExample;
import com.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {
    @Autowired
    ProductimageMapper mapper;

    @Override
    public void add(Productimage productimage) {
        mapper.insert(productimage);
    }

    @Override
    public void delete(int id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Productimage> list(int pid, String type) {
        ProductimageExample example=new ProductimageExample();
        example.createCriteria().andPidEqualTo(pid).andTypeEqualTo(type);
        example.setOrderByClause("id desc");
        List<Productimage> list=mapper.selectByExample(example);
        return  list;
    }

    @Override
    public Productimage get(int id) {
        return mapper.selectByPrimaryKey(id);
    }
}
