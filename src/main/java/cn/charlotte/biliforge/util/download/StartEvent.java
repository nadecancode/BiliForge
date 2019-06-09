package cn.charlotte.biliforge.util.download;

public class StartEvent {

    private EagletTask task;

    StartEvent(EagletTask task) {
        this.task = task;
    }

    public EagletTask getTask() {
        return task;
    }
}
