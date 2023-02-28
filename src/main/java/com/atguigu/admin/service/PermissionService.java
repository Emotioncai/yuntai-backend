package com.atguigu.admin.service;

import com.atguigu.admin.bean.Permission;
import com.atguigu.admin.util.PermissionHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PermissionService {
    /**
     * 获取所有权限
     */
    public static List<Permission> getAllPermissions() {
        try {
            Class.forName(Database.DIVER);
            Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
            PreparedStatement selectStatement = connection.prepareStatement(
                    "SELECT id, parent_id, permission_name, permission_code FROM permission"
            );
            ResultSet resultSet = selectStatement.executeQuery();
            List<Permission> permissions = new ArrayList<>();
            while (resultSet.next()) {
                Permission permission = new Permission();
                permission.setId(resultSet.getLong("id"));
                permission.setParentId(resultSet.getLong("parent_id"));
                permission.setPermissionName(resultSet.getString("permission_name"));
                permission.setPermissionCode(resultSet.getString("permission_code"));
                permissions.add(permission);
            }
            permissions = PermissionHelper.build(permissions);

            selectStatement.close();
            connection.close();
            return permissions;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 根据用户ID获取用户对应的所有权限
     */
    public static List<String> getPermissionByUserId(Long userId) {
        try {
            Class.forName(Database.DIVER);
            Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
            PreparedStatement selectStatement = connection.prepareStatement(
                    "SELECT permission.permission_code AS permissionCode FROM user" +
                            "  INNER JOIN user_role" +
                            "  INNER JOIN role_permission" +
                            "  INNER JOIN permission" +
                            "  ON user.id = user_role.user_id" +
                            "  AND user_role.role_id = role_permission.role_id" +
                            "  AND role_permission.permission_id = permission.id" +
                            "  WHERE user.id = ?"
            );
            selectStatement.setLong(1, userId);
            ResultSet resultSet = selectStatement.executeQuery();
            List<String> permissions = new ArrayList<>();
            while (resultSet.next()) {
                permissions.add(resultSet.getString("permissionCode"));
            }
            return permissions;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
