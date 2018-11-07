package com.service.impl;

import com.mapper.UserMapper;
import com.pojo.User;
import com.pojo.UserExample;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    @Override
    public void delete(int id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User get(int id) {
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    @Override
    public List<User> list() {
        UserExample example = new UserExample();
        example.setOrderByClause("id desc");
        List<User> users = userMapper.selectByExample(example);
        return users;
    }

    @Override
    public boolean isExits(String name) {
        UserExample example=new UserExample();
        example.createCriteria().andNameEqualTo(name);
        List<User> user=userMapper.selectByExample(example);
        if (user.isEmpty())
            return false;
        return true;
    }

    @Override
    public User get(User user) {
        UserExample example=new UserExample();
        example.createCriteria().andNameEqualTo(user.getName()).andPasswordEqualTo(user.getPassword());
        List<User> u=userMapper.selectByExample(example);
        return u.get(0);
    }
}
