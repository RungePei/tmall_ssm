package com.test;

import org.junit.Test;
import com.util.Page;

import java.sql.*;

public class TestTmall {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        String sql="insert into category values(null,?)";
        try (Connection connection=DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/tmall?characterEncoding=utf-8","root","199508");
             PreparedStatement statement=connection.prepareStatement(sql)
        ){
            for (int i = 0; i < 11; i++) {
                statement.setString(1,"测试"+i);
                statement.execute();
            }

            System.out.println("成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void pageTest(){
        Page page=new Page();

        page.setStart(11);
        System.out.println(page.getLast());
        page.setTotal(12);
        System.out.println(page.getStart()+"  "+page.getLast());
    }
}
