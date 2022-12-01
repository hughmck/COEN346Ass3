import java.util.*;

public class Process extends Thread {

    public static String waitingString = "Waiting";

    public static String lookupString = "Lookup";

    public static String releaseString = "Release";
    ArrayList<Process> procList = Driver.processes;

    public class ProcessSorter implements Comparator<Process> { //wanted to use this to compare the arrival time

        public int compare(Process process1, Process process2) {
            return Integer.compare(process1.getArrivalTime(), process2.getArrivalTime());
        }
    }


    public static ArrayList<Process> listOfAllProcesses;

    public LinkedList<Process> readyQueue;

    public void addProcessesToReadyQueue(ArrayList<Process>procList) {
        for (Process process: procList) {
            if(process.arrivalTime <= Clock.secondsGoneBy) {
                readyQueue.clear();
                readyQueue.addLast(process);
            }
        }
    }

    public void runProcesses(LinkedList<Process>readyQueue){


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

        for (Process process : procList) {
            // If its the first time running a process, set isStarted to true
            if(process.processStatus.equals(waitingString)) {
                System.out.print("Time " + Clock.secondsGoneBy + ", ");
                process.processStatus = "Started";
            }

            if(process.executionTime < Clock.secondsGoneBy) {
                System.out.print("Time " + Clock.secondsGoneBy + ", ");
                process.run();
            }
            else {
                process.run();
            }

            this.currentTime += quantumPerProcess; //need to decrement the current execution time of the process

            // If the process is finished, remove it from the queue
            if (process.executionTime == 0) {
                System.out.println("Time " + Clock.secondsGoneBy + ", Process " + process.processID + " has been finished");
                this.procList.remove(process);
                // If the process is not finished, pause it , and move it to the back of the queue
            }
        }
    }
}
