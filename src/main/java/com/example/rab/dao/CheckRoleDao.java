package com.example.rab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckRoleDao {

    //    @Select("SELECT * FROM rabc WHERE u_id = #{u_id} AND role = #{role}")
//    boolean checkRole(@Param("u_id") String u_id, @Param("role") String role);
    public boolean checkRole(String u_id, String role) {
        String sql = "SELECT * FROM role WHERE u_id = ? AND role = ?";
        Connection connection = JDBC.getConnection();
        PreparedStatement pstmt = null;
        ResultSet res = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, u_id);
            pstmt.setString(2, role);
            res = pstmt.executeQuery();
            if (res.next()) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            new JDBC().close(res, pstmt, connection);
        }
        return false;
    }
}
