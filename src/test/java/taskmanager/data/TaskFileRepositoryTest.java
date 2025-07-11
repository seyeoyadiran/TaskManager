package taskmanager.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskmanager.models.Status;
import taskmanager.models.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskFileRepositoryTest {
    private static final String SEED_FILE_PATH = "./data/tasks-seed.csv";
    private static final String TEST_FILE_PATH = "./data/tasks-test.csv";

    private final TaskFileRepository repository = new TaskFileRepository(TEST_FILE_PATH);

    //known good state
    @BeforeEach
    public void setUp() throws IOException{
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() throws DataAccessException {
        List<Task> actual = repository.findAll();
        assertEquals(3, actual.size());

        //1,2023-10-08,Review Curriculum,check content for spelling and grammar, 2023-10-10,TODO
        Task task = actual.get(0);
        assertEquals(1, task.getId());
        assertEquals("2023-10-08", task.getCreatedOn());
        assertEquals("Review Curriculum", task.getTitle());
        assertEquals("check content for spelling and grammar", task.getDescription());
        assertEquals("2023-10-10", task.getDueDate());
        assertEquals(Status.TODO, task.getStatus());
    }

    @Test
    public void shouldFindByExistingID() throws DataAccessException{

        Task taskOne = repository.findById(1);

        assertNotNull(taskOne);
        assertEquals(1, taskOne.getId());
        assertEquals("2023-10-08", taskOne.getCreatedOn());
        assertEquals("Review Curriculum", taskOne.getTitle());
        assertEquals("check content for spelling and grammar", taskOne.getDescription());
        assertEquals("2023-10-10", taskOne.getDueDate());
        assertEquals(Status.TODO, taskOne.getStatus());

    }



    @Test
    public void shouldNotFindNonExistingID() throws DataAccessException {
        Task notValid = repository.findById(9999);
        assertNull(notValid);
    }

    @Test
    public void shouldCreate() throws DataAccessException {
        Task task = new Task(
                0, // ID will be assigned by the repository
                "2023-10-08", // createdOn
                "New Task", // title
                "This is a new task description", // description
                "2023-10-15", // dueDate
                Status.TODO // status
        );

        Task actual = repository.create(task);
        assertEquals(4, actual.getId());

        List<Task> all = repository.findAll();
        assertEquals(4, all.size());

        assertEquals("2024-02-01", actual.getCreatedOn());
        assertEquals("New Task", actual.getTitle());
        assertEquals("This is a new task description", actual.getDescription());
        assertEquals("2023-10-15", actual.getDueDate());
        assertEquals(Status.TODO, actual.getStatus());
    }

    //create with commas
    @Test
    public void shouldCreateWithCommas() throws DataAccessException {
        Task task = new Task(
                0, // ID will be assigned by the repository
                "2023-10-09", // createdOn
                "New Task, Old Task, Even Newer Task", // title
                "This is a new task description - someDescrip, oldDescrip, newerDescrip", // description
                "2023-10-15", // dueDate
                Status.IN_PROGRESS // status
        );

        Task actual = repository.create(task);
        assertEquals(4, actual.getId());

        List<Task> all = repository.findAll();
        assertEquals(4, all.size());

        assertEquals("2023-10-09", actual.getCreatedOn());
        assertEquals("New Task, Old Task, Even Newer Task", actual.getTitle());
        assertEquals("This is a new task description - someDescrip, oldDescrip, newerDescrip", actual.getDescription());
        assertEquals("2023-10-15", actual.getDueDate());
        assertEquals(Status.TODO, actual.getStatus());
    }

    //1,2023-10-08,Review Curriculum,check content for spelling and grammar, 2023-10-10,TODO


    @Test
    void shouldUpdate() throws DataAccessException {
        Task task = repository.findById(1);
        task.setStatus(Status.IN_PROGRESS);
        task.setDescription("check for content for spelling and grammar, punctuation, and formatting");

        boolean result = repository.update(task);
        assertTrue(result);

        assertNotNull(task);

        assertEquals(1, task.getId());
        assertEquals("2023-10-08", task.getCreatedOn());
        assertEquals("Review Curriculum", task.getTitle());
        assertEquals("check for content for spelling and grammar, punctuation, and formatting", task.getDescription());
        assertEquals("2023-10-10", task.getDueDate());

    }

    @Test
    public void shouldNotUpdateUnknownID() throws DataAccessException {
        Task task = new Task(
                9999, // Non-existing ID
                "", // createdOn
                "", // title
                "", // description
                "", // dueDate
                Status.TODO // status
        );

        boolean result = repository.update(task);
        assertFalse(result);
    }


    @Test
    void shouldDelete() throws DataAccessException {
        boolean result = repository.delete(1);
        assertTrue(result);

        List<Task> all = repository.findAll();
        assertEquals(2, all.size());

        Task task = repository.findById(1);
        assertNull(task); // Task with ID 1 should no longer exist


    }

    @Test
    void shouldNotDeleteNonExistingID() throws DataAccessException {
        boolean result = repository.delete(9999);
        assertFalse(result);


    }
}
