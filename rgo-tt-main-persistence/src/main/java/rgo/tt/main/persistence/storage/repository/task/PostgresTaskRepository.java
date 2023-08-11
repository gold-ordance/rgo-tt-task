package rgo.tt.main.persistence.storage.repository.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import rgo.tt.common.exceptions.InvalidEntityException;
import rgo.tt.common.exceptions.PersistenceException;
import rgo.tt.main.persistence.storage.DbTxManager;
import rgo.tt.main.persistence.storage.entity.Task;
import rgo.tt.main.persistence.storage.entity.TaskStatus;
import rgo.tt.main.persistence.storage.entity.TaskType;
import rgo.tt.main.persistence.storage.entity.TasksBoard;
import rgo.tt.main.persistence.storage.query.TaskQuery;
import rgo.tt.main.persistence.storage.query.TaskStatusQuery;
import rgo.tt.main.persistence.storage.query.TaskTypeQuery;
import rgo.tt.main.persistence.storage.query.TasksBoardQuery;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PostgresTaskRepository implements TaskRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostgresTaskRepository.class);

    private final NamedParameterJdbcTemplate jdbc;

    public PostgresTaskRepository(DbTxManager dataSource) {
        this.jdbc = dataSource.jdbc();
    }

    @Override
    public List<Task> findAll(Long boardId) {
        checkBoardIdForExistence(boardId);
        MapSqlParameterSource params = new MapSqlParameterSource("board_id", boardId);
        return jdbc.query(TaskQuery.findAll(), params, mapper);
    }

    @Override
    public Optional<Task> findByEntityId(Long entityId) {
        MapSqlParameterSource params = new MapSqlParameterSource("entity_id", entityId);
        return first(jdbc.query(TaskQuery.findByEntityId(), params, mapper));
    }

    private Optional<Task> first(List<Task> tasks) {
        if (tasks.isEmpty()) {
            LOGGER.info("The task not found.");
            return Optional.empty();
        }

        if (tasks.size() > 1) {
            throw new PersistenceException("The number of tasks is not equal to 1.");
        }

        return Optional.of(tasks.get(0));
    }

    @Override
    public List<Task> findSoftlyByName(String name, Long boardId) {
        checkBoardIdForExistence(boardId);

        MapSqlParameterSource params = new MapSqlParameterSource(Map.of(
                "board_id", boardId,
                "name", "%" + name + "%"));

        return jdbc.query(TaskQuery.findSoftlyByName(), params, mapper);
    }

    @Override
    public Task save(Task task) {
        checkBoardIdForExistence(task.getBoard().getEntityId());
        checkTypeIdForExistence(task.getType().getEntityId());

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", task.getName())
                .addValue("board_id", task.getBoard().getEntityId())
                .addValue("type_id", task.getType().getEntityId())
                .addValue("description", task.getDescription());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = jdbc.update(TaskQuery.save(), params, keyHolder, new String[]{"entity_id"});
        Number key = keyHolder.getKey();

        if (result != 1 || key == null) {
            throw new PersistenceException("Task save error.");
        }

        Optional<Task> opt = findByEntityId(key.longValue());
        if (opt.isEmpty()) {
            throw new PersistenceException("Task save error during searching.");
        }

        return opt.get();
    }

    private void checkBoardIdForExistence(Long boardId) {
        String errorMsg = "The boardId not found in the storage.";
        checkForExistence(boardId, TasksBoardQuery.findByEntityId(), errorMsg);
    }

    private void checkTypeIdForExistence(Long typeId) {
        String errorMsg = "The typeId not found in the storage.";
        checkForExistence(typeId, TaskTypeQuery.findByEntityId(), errorMsg);
    }

    private void checkForExistence(Long entityId, String sql, String errorMsg) {
        MapSqlParameterSource params = new MapSqlParameterSource("entity_id", entityId);
        List<?> list = jdbc.query(sql, params, (rs, num) -> rs);

        if (list.isEmpty()) {
            throw new InvalidEntityException(errorMsg);
        }
    }

    @Override
    public Task update(Task task) {
        checkStatusIdForExistence(task.getStatus().getEntityId());
        checkTypeIdForExistence(task.getType().getEntityId());

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("entity_id", task.getEntityId())
                .addValue("name", task.getName())
                .addValue("lmd", LocalDateTime.now(ZoneOffset.UTC))
                .addValue("type_id", task.getType().getEntityId())
                .addValue("status_id", task.getStatus().getEntityId())
                .addValue("description", task.getDescription());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = jdbc.update(TaskQuery.update(), params, keyHolder, new String[]{"entity_id"});
        Number key = keyHolder.getKey();

        if (result == 0) {
            throw new InvalidEntityException("The entityId not found in the storage.");
        }

        if (result > 1 || key == null) {
            throw new PersistenceException("Task update error.");
        }

        Optional<Task> opt = findByEntityId(key.longValue());
        if (opt.isEmpty()) {
            throw new PersistenceException("Task update error during searching.");
        }

        return opt.get();
    }

    private void checkStatusIdForExistence(Long statusId) {
        String errorMsg = "The statusId not found in the storage.";
        checkForExistence(statusId, TaskStatusQuery.findByEntityId(), errorMsg);
    }

    @Override
    public boolean deleteByEntityId(Long entityId) {
        MapSqlParameterSource params = new MapSqlParameterSource("entity_id", entityId);
        int result = jdbc.update(TaskQuery.deleteByEntityId(), params);
        return result == 1;
    }

    private static final RowMapper<Task> mapper = (rs, num) -> Task.builder()
            .setEntityId(rs.getLong("t_entity_id"))
            .setName(rs.getString("t_name"))
            .setCreatedDate(rs.getTimestamp("t_created_date").toLocalDateTime())
            .setLastModifiedDate(rs.getTimestamp("t_last_modified_date").toLocalDateTime())
            .setDescription(rs.getString("t_description"))
            .setBoard(TasksBoard.builder()
                    .setEntityId(rs.getLong("tb_entity_id"))
                    .setName(rs.getString("tb_name"))
                    .build())
            .setType(TaskType.builder()
                    .setEntityId(rs.getLong("tt_entity_id"))
                    .setName(rs.getString("tt_name"))
                    .build())
            .setStatus(TaskStatus.builder()
                    .setEntityId(rs.getLong("ts_entity_id"))
                    .setName(rs.getString("ts_name"))
                    .build())
            .build();
}
