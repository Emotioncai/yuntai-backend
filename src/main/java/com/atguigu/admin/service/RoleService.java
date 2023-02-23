package com.atguigu.admin.service;

import java.sql.*;

public class RoleService {
    public static String getUserRoleName(long userId) {
        try {
            Class.forName(Database.DIVER);
            Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select role.role_name AS roleName FROM user_role " +
                            "JOIN role ON user_role.role_id = role.id " +
                            "WHERE user_role.user_id = ?");
            preparedStatement.setLong(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            String roleName="";
            while (resultSet.next()){
                roleName=resultSet.getString("roleName");
            }
            preparedStatement.close();
            connection.close();
            return roleName;
        } catch (SQLException|ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
