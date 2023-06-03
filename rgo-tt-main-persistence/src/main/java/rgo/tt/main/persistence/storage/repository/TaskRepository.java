package rgo.tt.main.persistence.storage.repository;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import rgo.tt.main.persistence.storage.DbTxManager;
import rgo.tt.main.persistence.storage.entity.Task;
import rgo.tt.main.persistence.storage.exception.PersistenceException;
import rgo.tt.main.persistence.storage.query.TaskQuery;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TaskRepository {

    private static final Logger log = LoggerFactory.getLogger(TaskRepository.class);

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
            log.info("The task not found.");
            return Optional.empty();
        }

        if (tasks.size() > 1) {
            String errorMsg = "The number of tasks is not equal to 1.";
            log.error(errorMsg);
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
                log.error(errorMsg);
                throw new PersistenceException(errorMsg);
            }

            Optional<Task> opt = findByEntityId(key.longValue());
            if (opt.isEmpty()) {
                String errorMsg = "Task save error during searching.";
                log.error(errorMsg);
                throw new PersistenceException(errorMsg);
            }

            return opt.get();
        });
    }

    public Task update(Task task) {
        return txManager.tx(() -> {
            MapSqlParameterSource params = new MapSqlParameterSource(Map.of(
                    "entity_id", task.getEntityId(),
                    "name", task.getName()));

            KeyHolder keyHolder = new GeneratedKeyHolder();
            int result = jdbc.update(TaskQuery.update(), params, keyHolder, new String[]{"entity_id"});
            Number key = keyHolder.getKey();

            if (result != 1 || key == null) {
                String errorMsg = "Task update error.";
                log.error(errorMsg);
                throw new PersistenceException(errorMsg);
            }

            Optional<Task> opt = findByEntityId(key.longValue());
            if (opt.isEmpty()) {
                String errorMsg = "Task update error during searching.";
                log.error(errorMsg);
                throw new PersistenceException(errorMsg);
            }

            return opt.get();
        });
    }

    public void deleteByEntityId(Long entityId) {
        txManager.tx(() -> {
            MapSqlParameterSource params = new MapSqlParameterSource("entity_id", entityId);

            int result = jdbc.update(TaskQuery.deleteByEntityId(), params);
            if (result != 1) {
                String errorMsg = "Task delete by entity id error.";
                log.error(errorMsg);
                throw new PersistenceException(errorMsg);
            }
        });
    }

    @VisibleForTesting
    static final RowMapper<Task> mapper = (rs, num) -> Task.builder()
            .setEntityId(rs.getLong("entity_id"))
            .setName(rs.getString("name"))
            .build();
}
