package com.dxc.assignment.bookstore.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class UserRole {
    private String username;
    private String role;
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserRole [username=" + username + ", role=" + role + "]";
    }

    public static UserRole convertFromSqlRowSet(SqlRowSet rs){
        
        UserRole userRole = new UserRole();
        userRole.setUsername(rs.getString("username"));
        userRole.setRole(rs.getString("user_roles"));

        return userRole;
    }
}
