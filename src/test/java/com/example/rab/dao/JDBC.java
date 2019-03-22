package com.example.rab.dao;

import java.sql.*;

public class JDBC {

    //设置通用的 连接
    public static Connection getConnection() {
        Connection connection = null;
        try {
            String driverName = "com.mysql.cj.jdbc.Driver";
            Class.forName(driverName);
            //这里和之前有点区别，手写的话需要转化，从之前的话直接复制
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/rabc?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8", "root", "");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //一个 close()  方法关闭 res，pstmt，connection
    public void close(ResultSet res, PreparedStatement pstmt, Connection con) {
        try {
            res.close();
            pstmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String findU_id(String name){
        String sql = "SELECT u_id FROM user WHERE u_name = ?";
        Connection connection = JDBC.getConnection();
        PreparedStatement pstmt = null;
        ResultSet res =null;
        String u_id = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1,name);
            res = pstmt.executeQuery();
            if (res.next()){
                u_id = res.getString("u_id");
                return u_id;
            } else
                u_id = "没有查询到u_id";
            return u_id;
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            new JDBC().close(res,pstmt,connection);
        }
        return "查询失败";
    }
}
