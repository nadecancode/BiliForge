package cn.charlotte.biliforge.util.download;

public class RetryFailedException extends RuntimeException {

    private EagletTask task;

    RetryFailedException(EagletTask task) {
        this.task = task;
    }

    public EagletTask getTask() {
        return task;
    }
}
