package TodoList;

import java.time.LocalDate;

public class Task
{
    private String Header;
    private String Description;
    private Integer Priority;
    private LocalDate Time;
    private Status Status;
    private LocalDate EndTime;
    private Integer ID;

    @Override
    public String toString() {
        return "Task{" + "\n" +
                " Header='" + Header + "\n" +
                " Description='" + Description + "\n" +
                " Priority=" + Priority + "\n"+
                " Time=" + Time + "\n"+
                " Status=" + Status + "\n"+
                " EndTime=" + EndTime + "\n"+
                " ID=" + ID + "\n"+
                '}'+"\n"+ "\n";

    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }




    public String getHeader() {
        return Header;
    }

    public void setHeader(String header) {
        Header = header;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Integer getPriority() {
        return Priority;
    }

    public void setPriority(Integer priority) {
        Priority = priority;
    }

    public LocalDate getTime() {
        return Time;
    }

    public void setTime(LocalDate time) {
        Time = time;
    }

    public TodoList.Status getStatus() {
        return Status;
    }

    public void setStatus(TodoList.Status status) {
        Status = status;
    }

    public LocalDate getEndTime() {
        return EndTime;
    }

    public void setEndTime(LocalDate endTime) {
        EndTime = endTime;
    }

}
