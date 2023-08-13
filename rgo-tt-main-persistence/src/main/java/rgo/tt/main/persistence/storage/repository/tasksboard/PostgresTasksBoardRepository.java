package rgo.tt.main.persistence.storage.repository.tasksboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import rgo.tt.common.exceptions.PersistenceException;
import rgo.tt.main.persistence.storage.DbTxManager;
import rgo.tt.main.persistence.storage.entity.TasksBoard;
import rgo.tt.main.persistence.storage.query.TasksBoardQuery;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PostgresTasksBoardRepository implements TasksBoardRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostgresTasksBoardRepository.class);

    private final NamedParameterJdbcTemplate jdbc;

    public PostgresTasksBoardRepository(DbTxManager dataSource) {
        this.jdbc = dataSource.jdbc();
    }

    @Override
    public List<TasksBoard> findAll() {
        return jdbc.query(TasksBoardQuery.findAll(), mapper);
    }

    @Override
    public Optional<TasksBoard> findByEntityId(Long entityId) {
        MapSqlParameterSource params = new MapSqlParameterSource("entity_id", entityId);
        return first(jdbc.query(TasksBoardQuery.findByEntityId(), params, mapper));
    }

    private Optional<TasksBoard> first(List<TasksBoard> boards) {
        if (boards.isEmpty()) {
            LOGGER.info("The board not found.");
            return Optional.empty();
        }

        if (boards.size() > 1) {
            throw new PersistenceException("The number of boards is not equal to 1.");
        }

        return Optional.of(boards.get(0));
    }

    @Override
    public TasksBoard save(TasksBoard board) {
        MapSqlParameterSource params = new MapSqlParameterSource(Map.of("name", board.getName()));

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = jdbc.update(TasksBoardQuery.save(), params, keyHolder, new String[]{"entity_id"});
        Number key = keyHolder.getKey();

        if (result != 1 || key == null) {
            throw new PersistenceException("TasksBoard save error.");
        }

        Optional<TasksBoard> opt = findByEntityId(key.longValue());
        if (opt.isEmpty()) {
            throw new PersistenceException("TasksBoard save error during searching.");
        }

        return opt.get();
    }

    @Override
    public boolean deleteByEntityId(Long entityId) {
        MapSqlParameterSource params = new MapSqlParameterSource("entity_id", entityId);
        int result = jdbc.update(TasksBoardQuery.deleteByEntityId(), params);
        return result == 1;
    }

    private static final RowMapper<TasksBoard> mapper = (rs, rowNum) -> TasksBoard.builder()
            .setEntityId(rs.getLong("entity_id"))
            .setName(rs.getString("name"))
            .build();
}
