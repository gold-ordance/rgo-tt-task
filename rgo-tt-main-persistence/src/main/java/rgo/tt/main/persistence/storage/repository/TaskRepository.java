package rgo.tt.main.persistence.storage.repository;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import rgo.tt.common.exceptions.InvalidEntityException;
import rgo.tt.main.persistence.storage.DbTxManager;
import rgo.tt.main.persistence.storage.entity.Task;
import rgo.tt.common.exceptions.PersistenceException;
import rgo.tt.main.persistence.storage.entity.TaskStatus;
import rgo.tt.main.persistence.storage.query.TaskQuery;
import rgo.tt.main.persistence.storage.query.TaskStatusQuery;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TaskRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRepository.class);

    private final DbTxManager txManager;
    private final NamedParameterJdbcTemplate jdbc;

    public TaskRepository(DbTxManager txManager) {
        this.txManager = txManager;
        this.jdbc = txManager.jdbc();
    }

    public List<Task> findAll() {
        return txManager.tx(() -> jdbc.query(TaskQuery.findAll(), mapper));
    }

    public Optional<Task> findByEntityId(Long entityId) {
        return txManager.tx(() -> {
            MapSqlParameterSource params = new MapSqlParameterSource("entity_id", entityId);
            return first(jdbc.query(TaskQuery.findByEntityId(), params, mapper));
        });
    }

    private Optional<Task> first(List<Task> tasks) {
        if (tasks.isEmpty()) {
            LOGGER.info("The task not found.");
            return Optional.empty();
        }

        if (tasks.size() > 1) {
            String errorMsg = "The number of tasks is not equal to 1.";
            LOGGER.error(errorMsg);
            throw new PersistenceException(errorMsg);
        }

        return Optional.of(tasks.get(0));
    }

    public List<Task> findSoftlyByName(String name) {
        return txManager.tx(() -> {
            MapSqlParameterSource params = new MapSqlParameterSource("name", "%" + name + "%");
            return jdbc.query(TaskQuery.findSoftlyByName(), params, mapper);
        });
    }

    public Task save(Task task) {
        return txManager.tx(() -> {
            MapSqlParameterSource params = new MapSqlParameterSource(Map.of(
                    "name", task.getName()));

            KeyHolder keyHolder = new GeneratedKeyHolder();
            int result = jdbc.update(TaskQuery.save(), params, keyHolder, new String[]{"entity_id"});
            Number key = keyHolder.getKey();

            if (result != 1 || key == null) {
                String errorMsg = "Task save error.";
                LOGGER.error(errorMsg);
                throw new PersistenceException(errorMsg);
            }

            Optional<Task> opt = findByEntityId(key.longValue());
            if (opt.isEmpty()) {
                String errorMsg = "Task save error during searching.";
                LOGGER.error(errorMsg);
                throw new PersistenceException(errorMsg);
            }

            return opt.get();
        });
    }

    public Task update(Task task) {
        return txManager.tx(() -> {
            checkStatusIdForExistence(task.getStatus().getEntityId());

            MapSqlParameterSource params = new MapSqlParameterSource(Map.of(
                    "entity_id", task.getEntityId(),
                    "name", task.getName(),
                    "lmd", LocalDateTime.now(ZoneOffset.UTC),
                    "status", task.getStatus().getEntityId()));

            KeyHolder keyHolder = new GeneratedKeyHolder();
            int result = jdbc.update(TaskQuery.update(), params, keyHolder, new String[]{"entity_id"});
            Number key = keyHolder.getKey();

            if (result == 0) {
                String errorMsg = "The entityId not found in the storage.";
                LOGGER.error(errorMsg);
                throw new InvalidEntityException(errorMsg);
            }

            if (result > 1 || key == null) {
                String errorMsg = "Task update error.";
                LOGGER.error(errorMsg);
                throw new PersistenceException(errorMsg);
            }

            Optional<Task> opt = findByEntityId(key.longValue());
            if (opt.isEmpty()) {
                String errorMsg = "Task update error during searching.";
                LOGGER.error(errorMsg);
                throw new PersistenceException(errorMsg);
            }

            return opt.get();
        });
    }

    private void checkStatusIdForExistence(Long statusId) {
        MapSqlParameterSource params = new MapSqlParameterSource("entity_id", statusId);
        List<?> list = jdbc.query(TaskStatusQuery.findByEntityId(), params, (rs, num) -> rs);

        if (list.isEmpty()) {
            String errorMsg = "The statusId not found in the storage.";
            LOGGER.error(errorMsg);
            throw new InvalidEntityException(errorMsg);
        }
    }

    public boolean deleteByEntityId(Long entityId) {
        return txManager.tx(() -> {
            MapSqlParameterSource params = new MapSqlParameterSource("entity_id", entityId);
            int result = jdbc.update(TaskQuery.deleteByEntityId(), params);

            if (result > 1) throw new PersistenceException("Tasks by object ID greater than 1: " + result);

            return result == 1;
        });
    }

    @VisibleForTesting
    static final RowMapper<Task> mapper = (rs, num) -> Task.builder()
            .setEntityId(rs.getLong("t_entity_id"))
            .setName(rs.getString("t_name"))
            .setCreatedDate(rs.getTimestamp("t_created_date").toLocalDateTime())
            .setLastModifiedDate(rs.getTimestamp("t_last_modified_date").toLocalDateTime())
            .setStatus(TaskStatus.builder()
                    .setEntityId(rs.getLong("ts_entity_id"))
                    .setName(rs.getString("ts_name"))
                    .build())
            .build();
}
