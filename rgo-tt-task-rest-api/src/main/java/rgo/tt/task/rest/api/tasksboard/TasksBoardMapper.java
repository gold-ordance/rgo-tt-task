package rgo.tt.task.rest.api.tasksboard;

import com.google.common.annotations.VisibleForTesting;
import rgo.tt.common.utils.HelperUtils;
import rgo.tt.task.persistence.storage.entity.TasksBoard;
import rgo.tt.task.rest.api.tasksboard.request.TasksBoardSaveRequest;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

import static rgo.tt.common.utils.HelperUtils.SPACE;

public final class TasksBoardMapper {

    private TasksBoardMapper() {
    }

    public static TasksBoard map(TasksBoardSaveRequest rq) {
        return TasksBoard.builder()
                .setName(rq.getName())
                .setShortName(shortName(rq.getName()))
                .build();
    }

    @VisibleForTesting
    static String shortName(String name) {
        if (name == null) return null;
        return Arrays.stream(name.split(SPACE))
                .map(HelperUtils::getFirstSymbol)
                .collect(Collectors.joining())
                .toUpperCase(Locale.ENGLISH);
    }
}
