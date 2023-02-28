package com.atguigu.admin.service;

import com.atguigu.admin.bean.Permission;
import com.atguigu.admin.util.PermissionHelper;
import lombok.var;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolePermissionService {
    /**
     * 根据角色ID获取角色对应的所有权限
     */
    public static List<Permission> getPermissionsByRoleId(Long roleId) {
        try {
            Class.forName(Database.DIVER);
            Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
            // 获取所有权限
            PreparedStatement selectRolePermissionStatement = connection.prepareStatement(
                    "SELECT permission_id FROM role_permission WHERE role_id = ?"
            );
            selectRolePermissionStatement.setLong(1, roleId);
            ResultSet resultSet = selectRolePermissionStatement.executeQuery();
            List<Long> allPermissions = new ArrayList<>();
            while (resultSet.next()) {
                allPermissions.add(resultSet.getLong("permission_id"));
            }

            PreparedStatement selectPermissionStatement = connection.prepareStatement(
                    "SELECT id, parent_id, permission_name, permission_code FROM permission"
            );
            selectPermissionStatement = selectPermissionStatement;
            resultSet = selectPermissionStatement.executeQuery();
            List<Permission>permissions = new ArrayList<>();
            while (resultSet.next()) {
                Permission permission = new Permission();
                permission.setId(resultSet.getLong("id"));
                permission.setParentId(resultSet.getLong("parent_id"));
                permission.setPermissionName(resultSet.getString("permission_name"));
                permission.setPermissionCode(resultSet.getString("permission_code"));
                if (allPermissions.contains(permission.getId())) {
                    permission.setSelect(true);
                }
                permissions.add(permission);
            }

            List<Permission> permissionsList = PermissionHelper.build(permissions);

            selectRolePermissionStatement.close();
            selectPermissionStatement.close();
            connection.close();

            return permissionsList;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 添加角色和权限的关系
     */
    public static void addRolePermissions(Long roleId, List<Long> permissionIds) {
        try {
            Class.forName(Database.DIVER);
            Connection connection = DriverManager.getConnection(Database.URL, Database.USERNAME, Database.PASSWORD);
            PreparedStatement deleteStatement = connection.prepareStatement(
                    "DELETE FROM role_permission WHERE role_id = ?"
            );
            deleteStatement.setLong(1, roleId);
            deleteStatement.execute();

            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO role_permission (role_id, permission_id) VALUES (?, ?)"
            );

            for (Long permissionId : permissionIds) {
                insertStatement.setLong(1, roleId);
                insertStatement.setLong(2, permissionId);
                insertStatement.execute();
            }

            deleteStatement.close();
            insertStatement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
