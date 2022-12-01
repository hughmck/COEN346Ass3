import java.util.*;

public class Process extends Thread {

    public class ProcessSorter implements Comparator<Process> {

        public int compare(Process process1, Process process2) {
            return Integer.compare(process1.getArrivalTime(), process2.getArrivalTime());
        }
    }


    public LinkedList<Process> readyQueue;

    public void addProcessesToReadyQueue(ArrayList<Process>listOfAllProcesses) {
        for (Process process: listOfAllProcesses) {
            if(process.arrivalTime <= Clock.secondsGoneBy) {
                readyQueue.clear();
                readyQueue.addLast(process);
            }
        }
    }



    int processingTime;

    int getArrivalTime() {
        return arrivalTime;
    }

    int getExecutionTime() {
        return executionTime;
    }

    void setProcessStatus(String newPhase) {
        processStatus = newPhase;
    }

    void decrementExecutionTime(int t) //call this function every time along with the clock
    {
        executionTime -= t;
    }

    public boolean isComplete;
    public String processStatus;
    public int executionTime;
    public int arrivalTime;

    Process(int arrivalTime, int executionTime, String processStatus, boolean isComplete){
        this.arrivalTime = arrivalTime;
        this.executionTime = executionTime;
        this.processStatus = processStatus;
        this.isComplete = false;
    }





    public void run(){



    }

}
