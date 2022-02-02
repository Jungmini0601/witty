package com.daelim.witty.web.repository.users;

import com.daelim.witty.domain.EmailConfrim;
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
public class EmailConfirmRepositoryImpl implements EmailConfirmRepository{

    private final JdbcTemplate jdbcTemplate;

    public EmailConfirmRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean save(EmailConfrim emailConfrim) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("email_confirm");

        Map<String, Object> params = new HashMap<>();
        params.put("emailKey", emailConfrim.getKey());
        params.put("email", emailConfrim.getEmail());

        try {
            jdbcInsert.execute(params);
        }catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public Optional<EmailConfrim> findByEmail(String email) {
        String findByIdSQL = "select * from email_confirm where email = ?";

        List<EmailConfrim> result = jdbcTemplate.query(findByIdSQL, emailConfrimRowMapper(), email);

        return result.stream().findAny();
    }

    private RowMapper<EmailConfrim> emailConfrimRowMapper() {
        return new RowMapper<EmailConfrim>() {
            @Override
            public EmailConfrim mapRow(ResultSet rs, int rowNum) throws SQLException {
                EmailConfrim emailConfrim = new EmailConfrim();
                emailConfrim.setKey(rs.getString("emailKey"));
                emailConfrim.setEmail(rs.getString("email"));

                return emailConfrim;
            }
        };
    }
}
