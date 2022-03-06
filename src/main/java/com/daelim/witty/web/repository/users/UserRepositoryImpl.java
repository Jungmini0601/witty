package com.daelim.witty.web.repository.users;

import com.daelim.witty.domain.v1.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User save(User user) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("witty_user");

        Map<String, Object> params = new HashMap<>();
        params.put("user_id", user.getId());
        params.put("user_email", user.getEmail());
        params.put("user_department", user.getDepartment());
        params.put("user_password", user.getPassword());

        jdbcInsert.execute(params);

        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        String findByIdSQL = "select * from witty_user where user_id = ?";

        List<User> result = jdbcTemplate.query(findByIdSQL, userRowMapper(), id);

        return result.stream().findAny();
    }

    private RowMapper<User> userRowMapper() {
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getString("user_id"));
                user.setEmail(rs.getString("user_email"));
                user.setDepartment(rs.getString("user_department"));
                user.setPassword(rs.getString("user_password"));

                return user;
            }
        };
    }
}
