package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestTmall {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String sql = "insert into orderitem values(null,1,1,1,?)";
        try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/tmall_ssm?serverTimezone=GMT&useSSL=false",
                "root", "199508");
             PreparedStatement statement = c.prepareStatement(sql)) {

            for (int i = 0; i < 5; i++) {
                statement.setInt(1,i);
                statement.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
