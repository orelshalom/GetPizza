package Holders;

import PizzaApp.Worker;

public class WorkerDataHolder {

    private Worker worker = null;
    private static final WorkerDataHolder data = new WorkerDataHolder();

    private WorkerDataHolder() {
        worker = new Worker();
    }

    public static WorkerDataHolder getWorkerDataHolder() {
        return data;
    }

    public Worker getWorker() {
        return worker;
    }

}