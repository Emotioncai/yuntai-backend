package com.atguigu.admin.service;

import com.atguigu.admin.bean.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleService {
    /**
     * 获取所有角色
     */
    public static List<Role> getAllRoles() {
        try {
            Class.forName(Database.DIVER);
            Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
            PreparedStatement selectStatement = connection.prepareStatement(
                    "SELECT id, role_name FROM role"
            );
            List<Role> roles = new ArrayList<>();
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getLong("id"));
                role.setRoleName(resultSet.getString("role_name"));
                roles.add(role);
            }
            selectStatement.close();
            connection.close();
            return roles;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 根据用户id获取角色名
     * @param userId
     * @return
     */
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


    /**
     * 添加新角色
     */
    public static void addRole(String roleName) {
        try {
            Class.forName(Database.DIVER);
            Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO role (role_name) VALUES (?)"
            );
            insertStatement.setString(1, roleName);
            insertStatement.execute();

            insertStatement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 删除角色
     */
    public static void deleteRole(Long roleId) {
        try {
            Class.forName(Database.DIVER);
            Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);

            PreparedStatement deleteRoleStatement = connection.prepareStatement(
                    "DELETE FROM role WHERE id = ?"
            );
            deleteRoleStatement.setLong(1, roleId);
            deleteRoleStatement.execute();

            PreparedStatement deleteRolePermissionStatement = connection.prepareStatement(
                    "DELETE FROM role_permission WHERE role_id = ?"
            );
            deleteRolePermissionStatement.setLong(1, roleId);
            deleteRolePermissionStatement.execute();

            PreparedStatement deleteUserRoleStatement = connection.prepareStatement(
                    "DELETE FROM user_role WHERE role_id = ?"
            );
            deleteUserRoleStatement.setLong(1, roleId);
            deleteUserRoleStatement.execute();

            deleteRoleStatement.close();
            deleteRolePermissionStatement.close();
            deleteUserRoleStatement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
