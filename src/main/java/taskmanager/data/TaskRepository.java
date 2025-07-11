package taskmanager.data;

import taskmanager.models.Task;

import java.util.List;

public interface TaskRepository {
    //Crud (Create, Read, Update, Delete)
    List<Task> findAll() throws DataAccessException;
    Task findById(int taskId) throws DataAccessException;
    Task create(Task task) throws DataAccessException;
    boolean update(Task task) throws DataAccessException;
    boolean delete(int taskId) throws DataAccessException;
}
