package taskmanager.models;

public class Task {
    private int id;
    private String createdOn;
    private String title;
    private String description;
    private String dueDate;
    private Status status;

    //constructors
    public Task(int id, String createdOn, String title, String description, String dueDate, Status status){
        this.id = id;
        this.createdOn = createdOn;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }

    public Task(){

    }
    //getters and setters


    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getCreatedOn(){
        return createdOn;
    }

    public void setCreatedOn(String createdOn){
        this.createdOn = createdOn;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate){
        this.dueDate = dueDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    @Override
    public String toString(){
        return "Task{" +
                "id=" + id +
                ", createOn=' " + createdOn + '\'' +
                ", title=' " + title + '\'' +
                ", description=' " + description + '\'' +
                ", dueDate=' " + dueDate + '\'' +
                ", status=' " + status + '\'' +
                '}';

    }
}
