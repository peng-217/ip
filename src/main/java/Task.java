public class Task {
    private String command;
    private boolean isDone;
    private String deadline;
    private boolean needToDo;


    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public Task(String command) {
        this.command = command;
        this.isDone = false;
    }


    public void setNeedToDo() {
        this.needToDo = true;
    }


    public void taskDone(){
        this.isDone = true;
    }

    @Override
    public String toString(){
        return "[" + getStatusIcon() + "] " + command;
    }
}


