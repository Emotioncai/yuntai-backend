package com.atguigu.report.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateTableService {
    public static String createMySQLTable(String sql) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://150.158.58.165:13307/gmall_report?useSSL=false",
                    "root",
                    "cpf85963286."
            );
            PreparedStatement createTableStatement = connection.prepareStatement(sql);
            createTableStatement.execute();
            createTableStatement.close();
            connection.close();

            return "create table success";
        } catch (SQLException e) {
            e.printStackTrace();
            return "create table error";
        }
    }

    public static String createClickHouseTable(String sql) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:clickhouse://150.158.58.165:18123/default?useSSL=false","default","cpf85963286."
            );
            PreparedStatement createTableStatement = connection.prepareStatement(sql);
            createTableStatement.execute();
            createTableStatement.close();
            connection.close();

            return "create table success";
        } catch (SQLException e) {
            e.printStackTrace();
            return "create table error";
        }
    }
}
