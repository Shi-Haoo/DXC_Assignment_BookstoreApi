package com.dxc.assignment.bookstore.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.dxc.assignment.bookstore.models.UserRole;
import static com.dxc.assignment.bookstore.repository.DBQueries.*;

@Repository
public class UserRepository {
    
    @Autowired
    JdbcTemplate template;

    public Optional<UserRole> findUserRoleByUsername(String username){
        SqlRowSet rs = template.queryForRowSet(GET_USER_BY_USERNAME , username);

        if(rs.first()){
            return Optional.of(UserRole.convertFromSqlRowSet(rs));
        }

        return Optional.empty();
    }
}
