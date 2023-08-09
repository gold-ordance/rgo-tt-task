package rgo.tt.main.persistence.storage.repository.tasktype;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import rgo.tt.main.persistence.storage.DbTxManager;
import rgo.tt.main.persistence.storage.entity.TaskType;
import rgo.tt.main.persistence.storage.query.TaskTypeQuery;

import java.util.List;

public class PostgresTaskTypeRepository implements TaskTypeRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public PostgresTaskTypeRepository(DbTxManager dataSource) {
        this.jdbc = dataSource.jdbc();
    }

    @Override
    public List<TaskType> findAll() {
        return jdbc.query(TaskTypeQuery.findAll(), mapper);
    }

    private static final RowMapper<TaskType> mapper = (rs, num) -> TaskType.builder()
            .setEntityId(rs.getLong("entity_id"))
            .setName(rs.getString("name"))
            .build();
}
