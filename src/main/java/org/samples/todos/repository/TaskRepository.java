package org.samples.todos.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.samples.todos.model.TaskGroup;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private static String TASKS_FILE_NAME = "src/main/resources/tasks.json";

    public TaskRepository(String TASKS_FILE_NAME) {
        TaskRepository.TASKS_FILE_NAME = TASKS_FILE_NAME;
    }

    public List<TaskGroup> load() {
        List<TaskGroup> taskGroups =  new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();

        try {
            taskGroups = mapper.readValue(Paths.get(TASKS_FILE_NAME).toFile(), new TypeReference<List<TaskGroup>>() {
            });
        } catch (IOException e) {
                e.printStackTrace();
        }

        return taskGroups;
    }

    public void save(List<TaskGroup> taskGroups) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writeValue(new File(TASKS_FILE_NAME), taskGroups);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
