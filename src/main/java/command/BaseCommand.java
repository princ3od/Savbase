package command;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.util.Callback;

public abstract class BaseCommand {
    protected boolean isExecuting = false;
    protected Throwable exception;
    protected Object result;
    protected Callback onSucceed;
    protected Callback onFail;
    protected Task task;

    public void setOnSucceed(Callback onSucceed) {
        this.onSucceed = onSucceed;
    }

    public void setOnFail(Callback onFail) {
        this.onFail = onFail;
    }

    public Throwable getException() {
        return exception;
    }

    public Object getResult() {
        return result;
    }

    public void execute() {
        execute(false);
    }

    protected void execute(boolean overrideDefault) {
        if (!overrideDefault) {
            task.setOnRunning(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    isExecuting = true;
                }
            });
            task.setOnSucceeded(
                    new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent event) {
                            isExecuting = false;
                            result = task.getValue();
                            onSucceed.call(null);
                        }
                    }
            );
            task.setOnFailed(
                    new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent event) {
                            isExecuting = false;
                            exception = task.getException();
                            exception.printStackTrace();
                            onFail.call(null);
                        }
                    }
            );

        }
        if (!isExecuting)
            new Thread(task).start();
    }
}
