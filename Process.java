import java.util.*;

public class Process extends Thread implements Comparable<Process>{

    public static String waitingString = "Waiting";

    public static String lookupString = "Lookup";

    public static String releaseString = "Release";
    LinkedList<Process> procList = Driver.processes;
    public LinkedList<Process> readyQueue;

    @Override public int compareTo(Process processes)
    {
        if (arrivalTime > processes.arrivalTime) {
            return 1;
        }
        else if (arrivalTime == processes.arrivalTime) {
            return 0;
        }
        else {
            return -1;
        }
    }


    public void addProcessesToReadyQueue(ArrayList<Process>procList) { //this should add
        for (Process process: procList) {
            if(process.arrivalTime <= Clock.secondsGoneBy) {
                readyQueue.clear();
                readyQueue.addLast(process);
            }
        }
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

    public static void printStatus()  {
        System.out.println("Current Time: " + Clock.secondsGoneBy);
        System.out.println("Process currently being completed: " + this.procList); //need to figure out which process is being worked on
    }

    public void run(){

        for (Process process : procList) {
            if(process.arrivalTime != Clock.secondsGoneBy){
                Clock.secondsGoneBy++; //increments the clock until it reaches the first arrival time
            }

            if(process.processStatus.equals(waitingString) && process.arrivalTime == Clock.secondsGoneBy) { //checking to see if the first process is waiting, if the processes' arrival time is equal to the clock, run it
                System.out.print("Time " + Clock.secondsGoneBy + ", "); //output the current time on the clock
                process.processStatus = "Started"; //start the process
                process.run();
            }

            while(process.executionTime > 0) { //while the process has execution time left, run it
                System.out.print("Time " + Clock.secondsGoneBy + ", ");
                process.executionTime--;
                Clock.secondsGoneBy++;
            }

            if (process.executionTime == 0) { //once the process has finished, remove it
                System.out.println("Time " + Clock.secondsGoneBy + ", Process has been finished"); //need to input a process ID?
                process.isComplete = true;
                this.procList.remove(process);
            }
        }
    }
}
