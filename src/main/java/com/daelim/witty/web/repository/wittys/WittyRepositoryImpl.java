package com.daelim.witty.web.repository.wittys;

import com.daelim.witty.domain.v1.User;
import com.daelim.witty.domain.v1.Witty;
import com.daelim.witty.web.controller.dto.wittys.CreateWittyDTO;
import com.daelim.witty.web.controller.dto.wittys.UpdateWittyDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class WittyRepositoryImpl implements WittyRepository{
    private final JdbcTemplate jdbcTemplate;

    public WittyRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Witty save(CreateWittyDTO createWittyDTO, User user) throws Exception{
        Number key = null;

        Witty witty = new Witty();
        witty.setUserId(user.getId());
        witty.setContent(createWittyDTO.getContent());
        witty.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("witty").usingGeneratedKeyColumns("witty_id");
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", witty.getUserId());
        params.put("witty_created_date", witty.getCreatedAt());
        params.put("content", witty.getContent());

        try {
            key = jdbcInsert.executeAndReturnKey(params);
        }catch (Exception e) {
            throw new Exception("DB 에러");
        }

        return findById(key.intValue()).get();
    }

    @Override
    public Witty update(Integer id, UpdateWittyDTO updateWittyDTO, User user) throws Exception {
        String sql = "update witty set content = ? where witty_id = ?";

        try {
            jdbcTemplate.update(sql, updateWittyDTO.getContent(), id);
        }catch (Exception e) {
            throw new Exception("DB 에러!");
        }

        return findById(id).get();
    }

    @Override
    public Witty delete(Integer id, User user) throws Exception {
        Witty witty = findById(id).get();

        String sql = "delete from witty where witty_id = ?";

        try {
            jdbcTemplate.update(sql, id);
        }catch (Exception e){
            throw new Exception("DB 에러 !");
        }

        return witty;
    }

    @Override
    public Optional<Witty> findById(Integer id) throws Exception{
        String findByIdSQL = "select * from witty where witty_id = ?";

        List<Witty> result = jdbcTemplate.query(findByIdSQL, wittyRowMapper(), id);

        return result.stream().findAny();
    }

    private RowMapper<Witty> wittyRowMapper() {
        return new RowMapper<Witty>() {
            @Override
            public Witty mapRow(ResultSet rs, int rowNum) throws SQLException {
                Witty witty = new Witty();
                witty.setId(rs.getInt("witty_id"));
                witty.setUserId(rs.getString("user_id"));
                witty.setContent(rs.getString("content"));
                witty.setCreatedAt(rs.getTimestamp("witty_created_date"));

                return witty;
            }
        };
    }
}
