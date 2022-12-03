import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Driver {
    public static ArrayList<APICalls> commands = new ArrayList<APICalls>();
    public static int numberOfCommands;
    public static int memorySize;

    //public ArrayList<APICalls> getListOfCommands() {
     //   return commands;
    //}

    //public ArrayList<APICalls> commandList = Driver.commands;

    static int value;
    int variableID;
    String command;

    static HashMap<Integer, Integer> virtualMemoryManager = new HashMap<Integer, Integer>();
    static HashMap <Integer, Integer> diskDrive = new HashMap<Integer, Integer>();


    public static void main(String[] args)
    throws InterruptedException{
    //    Clock c = new Clock();
      //  c.start();
        //c.join();
        Scanner reader = null;
        try {
            reader = new Scanner(new File(
                    "commands.txt"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        while(reader.hasNext()){ //need to put in a for loop which iterates through all the lines
                String command = reader.next();
                while(command == "Store"){
                    int variableID= reader.nextInt();
                    int value = reader.nextInt();
                    Store(variableID, value);
                }
                while(command == "Lookup"){
                    int variableID= reader.nextInt();
                    LookUp(variableID);
                }
                while(command == "Release"){
                    int variableID= reader.nextInt();
                    Release(variableID);
                }


               // commands.add(new APICalls(command, variableID, value)); //put API calls directly in here, based on the reader.next() value, dictate which function is called
                numberOfCommands++;
        }
        try {
            reader = new Scanner(new File(
                    "memconfig.txt"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        while(reader.hasNext()){
            memorySize= reader.nextInt();
            System.out.println(memorySize);
        }
        try {
            reader = new Scanner(new File(
                    "processes.txt"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        while(reader.hasNext()){
            int numberOfCores = reader.nextInt();
            int numberOfProcesses = reader.nextInt();
            for(int i=0;i<numberOfProcesses;i++){
                int readyTime= reader.nextInt();
                int processingTime = reader.nextInt();
            }
        }
    }

    public static void Store(int variableID, int value){//if the main memory is not full, add it there. if it is full, add it to the disk drive

        if (virtualMemoryManager.size() > memorySize) //as long as the hashmap size is smaller than the memory, the value will be stored in the virtual memory manager if not itll be stored in the disk
        {
            virtualMemoryManager.put(variableID, value); //trying to add the value of store
            System.out.println("STORE successfully completed in the virtual memory manager: " + " Variable: " + variableID + ", Value: " + value);
            return;
        }
        else{
            diskDrive.put(variableID, value); //trying to add the value of store
            System.out.println("STORE was completed in the disk drive as the virtual memory was full: " + " Variable: " + variableID + ", Value: " + value);
        }
    }

    public static int LookUp(int variableID){

        if (virtualMemoryManager.containsValue(variableID) == true)
        {
            System.out.println("LOOKUP succesful. Found value in the virtual memory. Variable " + variableID + ", Value: " + virtualMemoryManager.get(value));
            return virtualMemoryManager.get(value);
        }

        if (diskDrive.containsValue(variableID) == true)
        {
            System.out.println("LOOKUP succesful. Found value in the disk drive. Variable " + variableID + ", Value: " + diskDrive.get(value));
            return diskDrive.get(value);
        }
        return -1;
    }

    public static void Release(int variableID){

        if (virtualMemoryManager.containsValue(variableID) == true)
        {
            System.out.println("RELEASE (From Main Memory)" + " Variable: " + variableID + ", Value: " + virtualMemoryManager.get(value));
            // return virtualMemoryManager.get(value);
            virtualMemoryManager.remove(variableID);
        }

        if (diskDrive.containsValue(variableID) == true)
        {
            System.out.println("LOOKUP succesful. Found value in the disk drive. Variable " + variableID + ", Value: " + diskDrive.get(value));
            // return diskDrive.get(value);
            diskDrive.remove(variableID);
        }
        // return variableID; //would this need to return null if the release isnt found in either the main memory or VMM?
    }

}



