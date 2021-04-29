package com.example.planit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


// creating the task object
public class Task {
    private int ID;
    private String taskName;
    private String taskType;
    private Date taskDueDate;
    private Boolean task_isCompleted;
    private String testDate;

    public Task(int id, String tName, String tType, Date dueDate, boolean isCompleted){
        ID = id;
        taskName = tName;
        taskType = tType;
        taskDueDate = dueDate;
        task_isCompleted = isCompleted;
    }

    public Task(int id, String name, String type, Boolean isComp){
        ID = id;
        taskName = name;
        taskType = type;
        task_isCompleted = isComp;
    }


    public Task(int id, String tname, String type, Date d){
        ID = id;
        taskName = tname;
        taskType = type;
        taskDueDate = d;
    }

    public Task(String name, String type, Date d, boolean c) {
        taskName = name;
        taskType = type;
        taskDueDate = d;
        task_isCompleted = c;
    }



    //getter and setters of ID

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    // getters and setters of task name
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    // getters and setters of task type
    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    //getters and setters of task Due date

    public Date getTaskDueDate() {
        return taskDueDate;
    }

    public void setTaskDueDate(Date taskDueDate) {
        this.taskDueDate = taskDueDate;
    }

    // getters and setters of iscompleted
    public Boolean getTask_isCompleted() {
        return task_isCompleted;
    }

    public void setTask_isCompleted(Boolean task_isCompleted) {
        this.task_isCompleted = task_isCompleted;
    }
}
