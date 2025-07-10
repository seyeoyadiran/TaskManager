package taskmanager.data;

import taskmanager.models.Task;

import java.util.List;

public interface TaskRepository {
    //Crud (Create, Read, Update, Delete)
    List<Task> findAll() throws DataAccessException;
    Task findById(int taskId);
    Task create(Task task);
    boolean update(Task task);
    boolean delete(int taskId);
}
