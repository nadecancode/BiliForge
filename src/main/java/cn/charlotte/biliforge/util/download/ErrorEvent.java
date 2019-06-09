package cn.charlotte.biliforge.util.download;

public class ErrorEvent {

    private Throwable e;
    private EagletTask task;

    public ErrorEvent(Throwable e, EagletTask task) {
        this.e = e;
        this.task = task;
    }

    public EagletTask getTask() {
        return task;
    }

    public Throwable getException() {
        return e;
    }
}
