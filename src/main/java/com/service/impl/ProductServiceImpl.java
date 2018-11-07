package com.service.impl;

import com.mapper.ProductMapper;
import com.pojo.Category;
import com.pojo.Product;
import com.pojo.ProductExample;
import com.pojo.Productimage;
import com.service.*;
import com.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    CategoryService categoryService;

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
        List<Productimage> images = productImageService.list(p.getId(), ProductImageService.type_single);
        if (!images.isEmpty()) {
            p.setFirstProductImage(images.get(0));
        }
    }

    public void setFirstProductImage(List<Product> products) {
        for (Product p : products) {
            setFirstProductImage(p);
        }
    }

    public void fill(Category category) {
        List<Product> ps = list(category.getId());
        category.setProducts(ps);
    }

    @Override
    public void fill(List<Category> cs) {
        for (Category c : cs) {
            fill(c);
        }
    }

    @Override
    public void fillByRow(List<Category> cs) {
        int numberOfEachRow = 8;
        for (Category c : cs) {
            List<Product> ps = list(c.getId());
            List<List<Product>> psByRow = new ArrayList<>();
            for (int i = 0; i < ps.size(); i += numberOfEachRow) {
                int endIndex = i + numberOfEachRow;
                endIndex = endIndex > ps.size() ? ps.size() : endIndex;
                List<Product> psEachRow = ps.subList(i, endIndex);
                psByRow.add(psEachRow);
            }
            c.setProductsByRow(psByRow);
        }

    }

    @Override
    public void setSaleAndReviewNumber(Product product) {
        int saleNumber = orderItemService.getSaleCount(product.getId());
        int reviewNumber = reviewService.getCount(product.getId());
        product.setSaleCount(saleNumber);
        product.setReviewCount(reviewNumber);
    }

    @Override
    public void setSaleAndReviewNumber(List<Product> products) {
        for (Product product : products) {
            setSaleAndReviewNumber(product);
        }
    }

    @Override
    public List<Product> search(String keyword) {
        ProductExample example=new ProductExample();
        example.createCriteria().andNameLike("%"+keyword+"%");
        example.setOrderByClause("id desc");

        List<Product> products=productMapper.selectByExample(example);
        setFirstProductImage(products);
        setCategory(products);
        return products;
    }

    public void setCategory(List<Product> products){
        for (Product p:products) {
            p.setCategory(categoryService.get(p.getCid()));
        }
    }


}
