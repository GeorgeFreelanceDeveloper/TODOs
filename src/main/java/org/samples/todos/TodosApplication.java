package org.samples.todos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.samples.todos.model.Priority;
import org.samples.todos.model.Task;
import org.samples.todos.model.TaskGroup;
import org.samples.todos.repository.TaskRepository;
import org.samples.todos.service.TaskManager;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class TodosApplication {

    private static final String taskFilePath = TodosApplication.class.getClassLoader().getResource("tasks.json").getPath();
    private final TaskManager taskManager;

    private final Scanner scanner;


    public TodosApplication() {
        ObjectMapper objectMapper = new ObjectMapper();
        TaskRepository taskRepository = new TaskRepository(taskFilePath, objectMapper);
        taskManager = new TaskManager(taskRepository);
        scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            System.out.println("------ TODOS MENU ------");
            System.out.println("1. List of all tasks");
            System.out.println("2. Create task");
            System.out.println("3. Update task");
            System.out.println("4. Delete task");
            System.out.println("5. Exit");

            System.out.print("Select an option:");
            int choice = scanner.nextInt();
            scanner.nextLine();  // To consume the rest of the line after reading the number

            switch (choice) {
                case 1:
                    displayAllTasks();
                    break;
                case 2:
                    createTask();
                    break;
                case 3:
                    updateTask();
                    break;
                case 4:
                    deleteTask();
                    break;
                case 5:
                    taskManager.saveTasksToFile();
                    System.out.println("Exit app. Thank you!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Select again.");
            }
        }
    }

    private void displayAllTasks() {
        List<TaskGroup> taskGroups = taskManager.getAll();

        for (var taskGroup : taskGroups) {
            System.out.println("-----");
            System.out.println(String.format("Task group: %s", taskGroup.getName()));
            System.out.println("-----");
            for (var task : taskGroup.getTasks()) {
                System.out.println(task);
            }
        }
    }

    private void createTask() {
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        System.out.print("Group:");
        String group = scanner.nextLine();
        System.out.print("Priority (LOW, MEDIUM, HIGH): ");
        String priority = scanner.nextLine();

        Task task = new Task(UUID.randomUUID(),
                title,
                description,
                Priority.valueOf(priority.toUpperCase()),
                false,
                new Date());
        boolean result = taskManager.createTask(task, group);
        if (result) {
            System.out.println("Successful create task");
        } else {
            System.out.println("Failed create task");
        }
    }

    private void updateTask() {
        System.out.print("ID task for update: ");
        UUID taskId = UUID.fromString(scanner.nextLine());
        System.out.print("New title: ");
        String newTitle = scanner.nextLine();
        System.out.print("New description: ");
        String newDescription = scanner.nextLine();
        System.out.print("New priority (LOW, MEDIUM, HIGH): ");
        String newPriority = scanner.nextLine();

        Task task = new Task(taskId,
                newTitle,
                newDescription,
                Priority.valueOf(newPriority.toUpperCase()),
                false,
                new Date());
        boolean result = taskManager.updateTask(task);
        if (result) {
            System.out.println("Successful update task");
        } else {
            System.out.println("Failed update task");
        }
    }

    private void deleteTask() {
        System.out.print("ID task for delete: ");
        UUID deleteTaskId = UUID.fromString(scanner.nextLine());
        boolean result = taskManager.deleteTask(deleteTaskId);
        if (result) {
            System.out.println("Successful delete task");
        } else {
            System.out.println("Failed delete task");
        }
    }

    public static void main(String[] args) {
        System.out.println("Start TodosApplication");
        TodosApplication app = new TodosApplication();
        app.run();
        System.out.println("Finished TodosApplication");
    }
}
