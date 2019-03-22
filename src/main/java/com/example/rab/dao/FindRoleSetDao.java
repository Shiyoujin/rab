package com.example.rab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class FindRoleSetDao {

    //    @Select("SELECT role FROM role WHERE u_id = ?")
////    Set<String> FindRoleSet(String u_id);

    public Set<String> FindRoleSet(String u_id){
        String sql = "SELECT role FROM role WHERE u_id = ?";
        Connection connection = JDBC.getConnection();
        PreparedStatement pstmt = null;
        ResultSet res = null;
        Set<String> roleSet = new HashSet<>();
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1,u_id);
            res = pstmt.executeQuery();
            while (res.next()){
                roleSet.add(res.getString("role"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            new JDBC().close(res,pstmt,connection);
        }
        return roleSet;
    }
}
