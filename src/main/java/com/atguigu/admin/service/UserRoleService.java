package com.atguigu.admin.service;

import java.sql.*;

public class UserRoleService {
    /**
     * 根据用户ID获取角色ID
     */
    public static Long getRoleIdByUserId(Long userId) {
        try {
            Class.forName(Database.DIVER);
            Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
            PreparedStatement selectStatement = connection.prepareStatement(
                    "SELECT role_id FROM user_role WHERE user_id = ?"
            );
            selectStatement.setLong(1, userId);
            ResultSet resultSet = selectStatement.executeQuery();
            Long roleId = null;
            if (resultSet.next()) {
                roleId = resultSet.getLong("role_id");
            }

            selectStatement.close();
            connection.close();
            return roleId;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 添加用户和角色的关系
     */
    public static void addUserIdRoleIdRelationship(Long userId, Long roleId) {
        try {
            Class.forName(Database.DIVER);
            Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
            PreparedStatement deleteStatement = connection.prepareStatement(
                    "DELETE FROM user_role WHERE user_id = ?"
            );
            deleteStatement.setLong(1, userId);
            deleteStatement.execute();

            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO user_role (user_id, role_id) VALUES (?, ?)"
            );
            insertStatement.setLong(1, userId);
            insertStatement.setLong(2, roleId);
            insertStatement.execute();

            deleteStatement.close();
            insertStatement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
