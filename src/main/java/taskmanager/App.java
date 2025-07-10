package taskmanager;

import taskmanager.data.TaskFileRepository;
import taskmanager.data.DataAccessException;
import taskmanager.models.Task;

import java.util.List;

public class App {
    public static void main(String[] args) throws DataAccessException {
        TaskFileRepository repository = new TaskFileRepository("./data/tasks.csv");
        List<Task> tasks = repository.findAll();

        for(Task task: tasks){
            System.out.println(task);
        }

    }
}
