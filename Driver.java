import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Driver {

    //gets stored in the form of ID value

    public Integer[] ID = new Integer[]{}; //array to write to from input file for IDs

    public Integer[] value = new Integer[]{}; //array to write to from input file for values

    public static ArrayList<String> commands = new ArrayList<String>();


    public static void main(String[] args){
        Scanner reader = null;
        try {
            reader = new Scanner(new File(
                    "commands.txt"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        while(reader.hasNext()){
            commands.add(reader.nextLine());

//            for(int i=0;i<numOfInputProcesses;i++){
//                String processName = "Process " + i;
//                int readyTime= reader.nextInt();
//                int processingTime = reader.nextInt();
//                processes.add(new UserProcess(userName, processName, readyTime, processingTime, "Waiting", false));
            }
        System.out.print(commands);
        }
    }

