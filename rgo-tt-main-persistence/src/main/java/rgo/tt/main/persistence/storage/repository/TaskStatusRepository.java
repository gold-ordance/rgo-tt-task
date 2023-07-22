package rgo.tt.main.persistence.storage.repository;

import com.google.common.annotations.VisibleForTesting;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import rgo.tt.main.persistence.storage.DbTxManager;
import rgo.tt.main.persistence.storage.entity.TaskStatus;
import rgo.tt.main.persistence.storage.query.TaskStatusQuery;

import java.util.List;

public class TaskStatusRepository {

    private final DbTxManager txManager;
    private final NamedParameterJdbcTemplate jdbc;

    public TaskStatusRepository(DbTxManager txManager) {
        this.txManager = txManager;
        this.jdbc = txManager.jdbc();
    }

    public List<TaskStatus> findAll() {
        return txManager.tx(() -> jdbc.query(TaskStatusQuery.findAll(), mapper));
    }

    @VisibleForTesting
    static final RowMapper<TaskStatus> mapper = (rs, num) -> TaskStatus.builder()
            .setEntityId(rs.getLong("entity_id"))
            .setName(rs.getString("name"))
            .build();
}
