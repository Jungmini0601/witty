package com.daelim.witty.web.repository.user;

import com.daelim.witty.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean save(User user) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("witty_user");

        Map<String, Object> params = new HashMap<>();
        params.put("user_id", user.getId());
        params.put("user_email", user.getId());
        params.put("user_department", user.getId());
        params.put("user_password", user.getId());

        try {
            jdbcInsert.execute(params);
        }catch (Exception e) {
            return false;
        }

        return true;
    }
}
