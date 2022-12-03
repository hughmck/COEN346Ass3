import java.time.LocalDateTime;
import java.util.concurrent.*;



public class Clock extends Thread { //creating clock function which runs on its own thread

    public void time()
    {
        LocalDateTime myObj = LocalDateTime.now();
        System.out.println(myObj);
    }
    Semaphore sem = new Semaphore(5,true);
    public static int secondsGoneBy = 0;

    public void run() {
        while(true){ //while true for now, might need to change this condition
            try {
                System.out.print("Thread Stated at: ");
                time();
                //start the semaphore so only one thread can enter at once
                //acquire function will stop other threads to enter for 1000ms
                sem.acquire();
                Thread.sleep(1000);
                secondsGoneBy++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            sem.release();
        }
    }
}
