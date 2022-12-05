import java.util.*;
import java.util.concurrent.Semaphore;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Driver {
    public static ArrayList<APICalls> commands = new ArrayList<APICalls>();
    public static int numberOfCommands;
    public static int memorySize;

    public static String storeString = "Store";

    public static String lookupString = "Lookup";

    public static String releaseString = "Release";

    static Semaphore sem = new Semaphore(5,true);

    static FileWriter fw;

    public static LinkedList<Process> processes = new LinkedList<Process>(); //created using arraylist for now, but may use hashmap so we can easily sort?


    //public ArrayList<APICalls> getListOfCommands() {
    //   return commands;
    //}

    //public ArrayList<APICalls> commandList = Driver.commands;

    static int value;
    int variableID;
    String command;

    static HashMap<Integer, Integer> virtualMemoryManager = new HashMap<Integer, Integer>();
    static HashMap <Integer, Integer> diskDrive = new HashMap<Integer, Integer>();


    //commented out the driver code for the other two files, not sure why they dont work stacked together

    public static void main(String[] args)

            throws InterruptedException, IOException{
        try {
            fw = new FileWriter("output.txt");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
//        Clock c = new Clock();
//        c.start();
//        c.join();
        Scanner reader = null;
//        try {
//            reader = new Scanner(new File(
//                    "commands.txt"));
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        while(reader.hasNext()){ //need to put in a for loop which iterates through all the lines
//                String command = reader.next();
//                while(command.equals(storeString)){
//                    int variableID= reader.nextInt();
//                    int value = reader.nextInt();
//                    Store(variableID, value);
//                }
//                while(command.equals(lookupString)){
//                    int variableID= reader.nextInt();
//                    LookUp(variableID);
//                }
//                while(command.equals(releaseString)){
//                    int variableID= reader.nextInt();
//                    Release(variableID);
//                }
//
//               // commands.add(new APICalls(command, variableID, value)); //put API calls directly in here, based on the reader.next() value, dictate which function is called
//                numberOfCommands++;
//        }
//        try {
//            reader = new Scanner(new File(
//                    "memconfig.txt"));
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        while(reader.hasNext()){
//            memorySize= reader.nextInt();
//            System.out.println(memorySize);
//        }

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
                int arrivalTime= reader.nextInt();
                int executionTime = reader.nextInt();
                processes.add(new Process(arrivalTime, executionTime, "Waiting", false)); //adding the processes to a LinkedList
                sem.acquire();
                Store(i, arrivalTime+executionTime);
                sem.release();
            }
        }
        fw.write("UnSorted List"); //sorts the list based on arrival time FIFO
        Driver.fw.write("\n");
        System.out.println("UnSorted List"); //sorts the list based on arrival time FIFO
        for (Process p : processes) {
            System.out.println(p.arrivalTime + " " + p.executionTime + " "
                    + p.processStatus);
            fw.write(p.arrivalTime + " " + p.executionTime + " "
                    + p.processStatus);
            Driver.fw.write("\n");
        }
        Collections.sort(processes);
        System.out.println("Sorted List");
        fw.write("Sorted List");
        Driver.fw.write("\n");
        for (Process p : processes) {
            System.out.println(p.arrivalTime + " " + p.executionTime + " "
                    + p.processStatus);
            fw.write(p.arrivalTime + " " + p.executionTime + " "
                    + p.processStatus);
            Driver.fw.write("\n");
        }
        int i=processes.size()-1;
        while (i >= 0) {
            try{
                processes.get(i).start();
                processes.get(i).printStatus();
                i--;
            }catch(Exception ex) {
                fw.close();
            }
        }
        fw.close();
    }



    public static void Store(int variableID, int value) throws IOException{//if the main memory is not full, add it there. if it is full, add it to the disk drive
        System.out.println("The virtual memory currently has" + virtualMemoryManager.size());
        System.out.println("The memory has" + memorySize + "pages");
        if (virtualMemoryManager.size() < memorySize) //as long as the hashmap size is smaller than the memory, the value will be stored in the virtual memory manager if not itll be stored in the disk
        {
            virtualMemoryManager.put(variableID, value); //trying to add the value of store
            System.out.println("STORE successfully completed in the virtual memory manager: " + " Variable: " + variableID + ", Value: " + value);
            fw.write("STORE successfully completed in the virtual memory manager: " + " Variable: " + variableID + ", Value: " + value);
            Driver.fw.write("\n");
            return;
        }
        else{
            diskDrive.put(variableID, value); //trying to add the value of store
            System.out.println("STORE was completed in the disk drive as the virtual memory was full: " + " Variable: " + variableID + ", Value: " + value);
            fw.write("STORE was completed in the disk drive as the virtual memory was full: " + " Variable: " + variableID + ", Value: " + value);
            Driver.fw.write("\n");
        }
    }

    public static int LookUp(int variableID) throws IOException{

        if (virtualMemoryManager.containsValue(variableID) == true)
        {
            System.out.println("LOOKUP succesful. Found value in the virtual memory. Variable " + variableID + ", Value: " + virtualMemoryManager.get(value));
            fw.write("LOOKUP succesful. Found value in the virtual memory. Variable " + variableID + ", Value: " + virtualMemoryManager.get(value));
            Driver.fw.write("\n");
            return virtualMemoryManager.get(value);
        }

        if (diskDrive.containsValue(variableID) == true)
        {
            System.out.println("LOOKUP succesful. Found value in the disk drive. Variable " + variableID + ", Value: " + diskDrive.get(value));
            fw.write("LOOKUP succesful. Found value in the disk drive. Variable " + variableID + ", Value: " + diskDrive.get(value));
            Driver.fw.write("\n");
            return diskDrive.get(value);
        }
        return -1;
    }

    public static void Release(int variableID) throws IOException{

        if (virtualMemoryManager.containsValue(variableID) == true)
        {
            System.out.println("RELEASE (From Main Memory)" + " Variable: " + variableID + ", Value: " + virtualMemoryManager.get(value));
            fw.write("RELEASE (From Main Memory)" + " Variable: " + variableID + ", Value: " + virtualMemoryManager.get(value));
            Driver.fw.write("\n");
            // return virtualMemoryManager.get(value);
            virtualMemoryManager.remove(variableID);
        }

        if (diskDrive.containsValue(variableID) == true)
        {
            System.out.println("LOOKUP succesful. Found value in the disk drive. Variable " + variableID + ", Value: " + diskDrive.get(value));
            fw.write("LOOKUP succesful. Found value in the disk drive. Variable " + variableID + ", Value: " + diskDrive.get(value));
            Driver.fw.write("\n");
            // return diskDrive.get(value);
            diskDrive.remove(variableID);
        }
        // return variableID; //would this need to return null if the release isnt found in either the main memory or VMM?
    }
}



