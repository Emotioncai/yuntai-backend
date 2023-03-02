package com.atguigu.scheduler.job;

import com.atguigu.scheduler.bean.MonitorDetail;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.*;

public class MySQLMonitorJob implements Job {
    public MySQLMonitorJob() {}

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();

        String databaseName = dataMap.getString("databaseName");
        String tableName = dataMap.getString("tableName");
        String fieldName = dataMap.getString("fieldName");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://150.158.58.165:13307/" + databaseName + "?useSSL=false",
                    "root",
                    "cpf85963286."
            );

            MonitorDetail monitorDetail = new MonitorDetail();
            monitorDetail.setDatabaseName(databaseName);
            monitorDetail.setTableName(tableName);
            monitorDetail.setFieldName(fieldName);

            PreparedStatement selectNullRate = connection.prepareStatement(
                    "SELECT" +
                            "   count(1) as TotalAll," +
                            "   count(" + fieldName + ") as TotalNotNull," +
                            "   count(1) - count(" + fieldName + ") as TotalNull," +
                            "   100.0 * count(" + fieldName + ") / count(1) as PercentNotNull" +
                            "  FROM " + tableName
            );
            ResultSet resultSet = selectNullRate.executeQuery();
            if (resultSet.next()) {
                monitorDetail.setFieldNullRate(resultSet.getDouble("PercentNotNull"));
            }

            Connection insertConnection = DriverManager.getConnection(
                    "jdbc:mysql://150.158.58.165:13307/yuntai_government?useSSL=false",
                    "root",
                    "cpf85963286."
            );

            PreparedStatement insertStatement = insertConnection.prepareStatement(
                    "INSERT INTO mysql_data_monitor (database_name, table_name, field_name, field_null_rate) VALUES (?, ?, ?, ?)"
            );

            insertStatement.setString(1, databaseName);
            insertStatement.setString(2, tableName);
            insertStatement.setString(3, fieldName);
            insertStatement.setDouble(4, monitorDetail.getFieldNullRate());
            insertStatement.execute();

            selectNullRate.close();
            insertStatement.close();
            insertConnection.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
