package com.atguigu.admin.service;

import com.atguigu.admin.bean.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    /**
     * 获取所有用户信息
     * @return
     */
    public static List<User> getAllUsers(){
        try {
            Class.forName(Database.DIVER);
            Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("select id, username,password from user");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                //根据用户id查询角色名
                user.setRoleName(RoleService.getUserRoleName(resultSet.getLong("id")));
                users.add(user);
            }
            preparedStatement.close();
            connection.close();
            return users;
        } catch (SQLException |ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 根据用户名获取用户信息
     */

    public static User getUserByName(String username){
        try {
            Class.forName(Database.DIVER);
            Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("select id, username,password from user " +
                    "where user_name=?");
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = new User();
            if (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setRoleName(RoleService.getUserRoleName(resultSet.getLong("id")));
            }
            preparedStatement.close();
            connection.close();
            return user;
        } catch (SQLException |ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据用户id获取用户信息
     * @param userId
     * @return
     */
    public static User getUserById(Long userId) {
        try {
            Class.forName(Database.DIVER);
            Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT id, username, password FROM user WHERE id = ?"
            );
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = new User();
            if (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setRoleName(RoleService.getUserRoleName(resultSet.getLong("id")));
            }

            preparedStatement.close();
            connection.close();
            return user;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 根据用户ID删除用户及用户对应的角色信息
     */
    public static void deleteUserById(Long userId) {
        try {
            Class.forName(Database.DIVER);
            Connection connection = DriverManager.getConnection(
                    Database.URL,
                    Database.USERNAME,
                    Database.PASSWORD
            );
            // 删除用户数据
            PreparedStatement deleteUser = connection.prepareStatement(
                    "DELETE FROM `user` WHERE `id` = ?"
            );
            deleteUser.setLong(1, userId);
            deleteUser.execute();
            deleteUser.close();

            // 删除用户对应的角色数据
            PreparedStatement deleteUserRole = connection.prepareStatement(
                    "DELETE FROM user_role WHERE user_id = ?"
            );
            deleteUserRole.setLong(1, userId);
            deleteUserRole.execute();
            deleteUserRole.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 添加新用户
     */
    public static void addUser(String username, String password) {
        try {
            Class.forName(Database.DIVER);
            Connection connection = DriverManager.getConnection(
                    Database.URL,
                    Database.USERNAME,
                    Database.PASSWORD
            );
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO user (username, password) VALUES (?, ?)"
            );
            insertStatement.setString(1, username);
            insertStatement.setString(2, password);
            insertStatement.execute();
            insertStatement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
