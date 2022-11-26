public class Clock extends Thread { //creating clock function which runs on its own thread

    public void run() {
        while(true){ //while true for now, might need to change this condition
            int secondsGoneBy = 0;
            try {
                Thread.sleep(1000);
                secondsGoneBy++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
