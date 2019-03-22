package com.example.rab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SetRoleDao {


    //    @Insert("INSERT INTO role(u_id,role) VALUE(#{u_id,jdbcType=VARCHAR},#{role,jdbcType=VARCHAR}")
//    String setRole(String u_id,String role);

    public String setRole(String u_id,String role){
        //设置角色前，先检验 role表里面 是否存在
        //若不存在 才进行 role 表的插入
        if (new CheckRoleDao().checkRole(u_id, role)) {

            String sql = "INSERT INTO role(u_id,role) VALUE(?,?)";
            Connection connection = JDBC.getConnection();
            PreparedStatement pstmt = null;
            try {
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, u_id);
                pstmt.setString(2, role);
                int result = pstmt.executeUpdate();
                if (result > 0) {
                    return "top--为用户设置角色成功";
                } else {
                    return "top----为用户设置角色失败";
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                pstmt.close();  //前面 pstmt必需有 null 才可以 pstmt.close
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return "设置失败";
        } else {
            return "top---设置用户的角色已经存在";
        }
    }
}
