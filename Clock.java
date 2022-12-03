import java.time.LocalDateTime;
import java.util.concurrent.*;
//class Shared
//{
//  static int count = 0;
//}
public class Clock extends Thread { //creating clock function which runs on its own thread

   // public void time()
    //{
      //  LocalDateTime myObj = LocalDateTime.now();
        //System.out.println(myObj);
    //}

    //Semaphore sem = new Semaphore(5, true);
    public void run() {
        while(true){ //changed the condition
            try {

                System.out.println("Thread Stated at: ");
             //   time();
                //Start the semaphore so only one thread can enter at once
                // acquire function will stop other threads to enter for 1000ms, so that shared count can be increased at once
              //  sem.acquire();
                Thread.sleep(1000);
             //   Shared.count++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //releasing semaphore after shared count is incremented
         //   sem.release();
        }
    }
}
