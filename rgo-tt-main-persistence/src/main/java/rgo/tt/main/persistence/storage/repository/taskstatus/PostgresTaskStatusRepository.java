package rgo.tt.main.persistence.storage.repository.taskstatus;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import rgo.tt.main.persistence.storage.DbTxManager;
import rgo.tt.main.persistence.storage.entity.TaskStatus;
import rgo.tt.main.persistence.storage.query.TaskStatusQuery;

import java.util.List;

public class PostgresTaskStatusRepository implements TaskStatusRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public PostgresTaskStatusRepository(DbTxManager dataSource) {
        this.jdbc = dataSource.jdbc();
    }

    @Override
    public List<TaskStatus> findAll() {
        return jdbc.query(TaskStatusQuery.findAll(), mapper);
    }

    private static final RowMapper<TaskStatus> mapper = (rs, num) -> TaskStatus.builder()
            .setEntityId(rs.getLong("entity_id"))
            .setName(rs.getString("name"))
            .build();
}
