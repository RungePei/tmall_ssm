package com.service.impl;

import com.mapper.ProductMapper;
import com.pojo.Product;
import com.pojo.ProductExample;
import com.pojo.Productimage;
import com.service.ProductImageService;
import com.service.ProductService;
import com.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    ProductImageService productImageService;

    @Override
    public void add(Product product) {
        productMapper.insert(product);
    }

    @Override
    public void delete(int id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Product get(int id) {
        return productMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Product product) {
        productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public List<Product> list(int cid) {
        ProductExample example = new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        List<Product> list = productMapper.selectByExample(example);
        setFirstProductImage(list);
        return list;
    }

    @Override
    public void setFirstProductImage(Product p) {
        List<Productimage> images= productImageService.list(p.getId(),ProductImageService.type_single);
        if (!images.isEmpty()){
            p.setFirstProductImage(images.get(0));
        }
    }

    public void setFirstProductImage(List<Product> products){
        for (Product p:products){
            setFirstProductImage(p);
        }
    }
}
