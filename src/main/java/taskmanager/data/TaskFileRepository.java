package taskmanager.data;

import taskmanager.models.Status;
import taskmanager.models.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskFileRepository implements TaskRepository {

    //Fields
    private static final String DELIMITER = ",";
    private static final String DELIMITER_REPLACEMENT = "@@@";
    private final String filePath;

    public TaskFileRepository(String filePath){
        this.filePath = filePath;
    }

    @Override
    public List<Task> findAll() throws DataAccessException {
        // create a list of tasks
        List<Task> result = new ArrayList<>();
        //Try with resources
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
        //skip the headerline
            reader.readLine();

            for(String line = reader.readLine(); line != null; line = reader.readLine()){
                Task task = lineToTask(line);
                result.add(task);
            }
        }catch(FileNotFoundException ex){
            //leave this blank
        }catch(IOException ex){
            throw new DataAccessException("Could not open file path: " + filePath);
        }
        return result;
    }

    @Override
    public Task findById(int taskId) {
        return null;
    }

    @Override
    public Task create(Task task){
        return null;
    }

    @Override
    public boolean update(Task task){
        return false;
    }

    @Override
    public boolean delete(int task){
        return false;
    }

    //helper methods
    private String restore(String value){
        return value.replace(DELIMITER_REPLACEMENT, DELIMITER);
    }

    private String clean(String value){
        return value.replace(DELIMITER, DELIMITER_REPLACEMENT);
    }
    //deserialize
    private Task lineToTask(String line){
        String [] fields = line.split(DELIMITER);
        if(fields.length != 6){
            return null;
        }
        Task task = new Task(
                Integer.parseInt(fields[0]),
                restore(fields[1]), //String createdOn
                restore(fields[2]), // String title
                restore(fields[3]),// String description
                restore(fields[4]), // Due date
                Status.valueOf(fields[5])
        );
        return task;
    }
    //Serializing
    private String taskToLine(Task task){
        //add a comma after each field
        StringBuilder buffer = new StringBuilder(100);
        buffer.append(task.getId()).append(DELIMITER);
        //This is the line that would look at each date which is a string if there are, we would replace that with @@@ here
        buffer.append(clean(task.getCreatedOn())).append(DELIMITER);
        buffer.append(clean(task.getTitle())).append(DELIMITER);
        buffer.append(clean(task.getDescription())).append(DELIMITER);
        buffer.append(clean(task.getDueDate())).append(DELIMITER);
        buffer.append(task.getStatus());
        return buffer.toString();
    }

    //write to file
    private void WriteToFile(List<Task> tasks) throws DataAccessException {
        try(PrintWriter writer = new PrintWriter(filePath)){
            writer.println("id,createdOn,title,description,dueDate,status");

            for(Task task : tasks){
                String line = taskToLine(task);
                writer.println(line);
            }
        } catch(IOException ex){
            throw new DataAccessException("Could not write to filepath: " + filePath);
        }

    }

    //get next id
    private int getNextId(List<Task> tasks){
        int maxId = 0;
        for(Task task: tasks){
            if(maxId < task.getId()){
                maxId = task.getId();
            }
        }
        return maxId + 1;
    }




}
