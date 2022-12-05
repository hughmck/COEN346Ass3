import java.security.Key;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Driver{
    public static ArrayList<APICalls> commands = new ArrayList<APICalls>();
    public static int numberOfCommands;
    public static int memorySize;

    public static String storeString = "Store";

    public static String lookupString = "Lookup";

    public static String releaseString = "Release";

    static Semaphore sem = new Semaphore(5, true);

    static FileWriter fw;

    public static LinkedList<Process> processes = new LinkedList<Process>(); //created using arraylist for now, but may use hashmap so we can easily sort?


    //public ArrayList<APICalls> getListOfCommands() {
    //   return commands;
    //}

    //public ArrayList<APICalls> commandList = Driver.commands;

    static int accessTime;



    static HashMap<Integer, Integer> virtualMemoryManager = new HashMap<Integer, Integer>();
    static HashMap<Integer, Integer> diskDrive = new HashMap<Integer, Integer>();

    static HashMap<Integer, Integer> accessTable = new HashMap<Integer,Integer>();


    //commented out the driver code for the other two files, not sure why they dont work stacked together

    public static void main(String[] args) throws InterruptedException, IOException {

        Clock clock = new Clock();
        clock.start();

        try {
            fw = new FileWriter("output.txt");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        String[] files = {"memconfig.txt", "processes.txt" , "commands.txt", }; // needs to check the files in this order
        for (String file : files) {
            Scanner reader = null;
            try {
                reader = new Scanner(new File(file));
                }
                catch (IOException e) {
                    e.printStackTrace();
                    }
            if (file == "memconfig.txt") {
                while (reader.hasNext()) {
                    memorySize = reader.nextInt();
                }
                reader.close();
            } else if (file == "processes.txt") {
                while (reader.hasNext()) {
                    int numberOfCores = reader.nextInt();
                    int numberOfProcesses = reader.nextInt();
                    for (int i = 0; i < numberOfProcesses; i++) {
                        int arrivalTime = reader.nextInt();
                        int executionTime = reader.nextInt();
                        Process p = new Process(arrivalTime, executionTime, "Waiting", false);
                        processes.add(p); //adding the processes to a LinkedList
                        p.start();
                        sem.acquire();
                        accessTime = clock.getSecondsGoneBy();
//                        Store(i + 1, arrivalTime + executionTime, accessTime);
                        sem.release();
                    }
                }
                reader.close();
            } else {
            while (reader.hasNext()) {//need to put in a for loop which iterates through all the lines
                String command = reader.next();
                if (command.equals(storeString)) {
                    int variableID = reader.nextInt();
                    int value = reader.nextInt();
                    commands.add(new APICalls(command, variableID, value, false)); //put API calls directly in here, based on the reader.next() value, dictate which function is called
                    numberOfCommands++;
                }
                if (command.equals(releaseString)) {
                    int variableID = reader.nextInt();
                    int value = LookUp(variableID);
                    commands.add(new APICalls(command, variableID, value, false)); //put API calls directly in here, based on the reader.next() value, dictate which function is called
                    numberOfCommands++;
                }
                if (command.equals(lookupString)) {
                    int variableID = reader.nextInt();
                    int value = LookUp(variableID);
                    accessTime = clock.getSecondsGoneBy();
                    commands.add(new APICalls(command, variableID, value, false)); //put API calls directly in here, based on the reader.next() value, dictate which function is called
                    numberOfCommands++;
                }


            }reader.close();
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
                int i = processes.size() - 1;
                while (i >= 0) {
                    try {
                        processes.get(i).start();
                        processes.get(i).printStatus();
                        i--;
                    } catch (Exception ex) {
                        fw.close();
                    }
                }
                fw.close();
            }

        }
    }

        public static void Store ( int variableID, int value, int accessTime) throws IOException {//if the main memory is not full, add it there. if it is full, add it to the disk drive
            if (virtualMemoryManager.size() < memorySize) //as long as the hashmap size is smaller than the memory, the value will be stored in the virtual memory manager if not itll be stored in the disk
            {
                virtualMemoryManager.put(variableID, value); //trying to add the value of store
                accessTable.put(variableID, accessTime);
                System.out.println("STORE successfully completed in the virtual memory manager: " + " Variable: " + variableID + ", Value: " + value);
                fw.write("STORE successfully completed in the virtual memory manager: " + " Variable: " + variableID + ", Value: " + value);
                Driver.fw.write("\n");
                return;
            } else {
                diskDrive.put(variableID, value); //trying to add the value of store
                System.out.println("STORE was completed in the disk drive as the virtual memory was full: " + " Variable: " + variableID + ", Value: " + value);
                fw.write("STORE was completed in the disk drive as the virtual memory was full: " + " Variable: " + variableID + ", Value: " + value);
                Driver.fw.write("\n");
            }
        }

        public static int LookUp ( int variableID) throws IOException {

            if (virtualMemoryManager.containsKey(variableID)) {
                System.out.println("LOOKUP successful. Found value in the virtual memory. Variable " + variableID + ", Value: " + virtualMemoryManager.get(variableID));
                fw.write("LOOKUP successful. Found value in the virtual memory. Variable " + variableID + ", Value: " + virtualMemoryManager.get(variableID));
                Driver.fw.write("\n");
                return virtualMemoryManager.get(variableID);
            }

            else if (diskDrive.containsKey(variableID)) {
                System.out.println("LOOKUP successful. Found value in the disk drive. Variable " + variableID + ", Value: " + diskDrive.get(variableID));
                fw.write("LOOKUP successful. Found value in the disk drive. Variable " + variableID + ", Value: " + diskDrive.get(variableID));
                Driver.fw.write("\n");
                int tempKey = minValue().getKey();
                int tempValue = virtualMemoryManager.get(tempKey);
                Release((minValue().getKey())); // removing the least recently accessed from memory
                virtualMemoryManager.put(variableID, diskDrive.get(variableID));
                diskDrive.remove(variableID);
                diskDrive.put(tempKey, tempValue); //addding least recently accessed to disk
                System.out.println("Min key is " + tempKey + " and the value with that key is " +tempValue);

                //adding the lookup variable and value to memory
                return virtualMemoryManager.get(variableID);
            }
//            accessTime = need to change accesTime
            return -1;
        }

        public static void Release (int variableID) throws IOException {

//            if (virtualMemoryManager.containsValue(variableID) == true) {
                System.out.println("RELEASE (From Main Memory)" + " Variable: " + variableID + ", Value: " + virtualMemoryManager.get(variableID));
                fw.write("RELEASE (From Main Memory)" + " Variable: " + variableID + ", Value: " + virtualMemoryManager.get(variableID));
                Driver.fw.write("\n");
                virtualMemoryManager.remove(variableID);

//            }

//            if (diskDrive.containsValue(variableID) == true) {
//                System.out.println("LOOKUP succesful. Found value in the disk drive. Variable " + variableID + ", Value: " + diskDrive.get(value));
//                fw.write("LOOKUP succesful. Found value in the disk drive. Variable " + variableID + ", Value: " + diskDrive.get(value));
//                Driver.fw.write("\n");
//                // return diskDrive.get(value);
//                diskDrive.remove(variableID);
//            }
//            // return variableID; //would this need to return null if the release isnt found in either the main memory or VMM?
        }

    public static Map.Entry<Integer, Integer> minValue() { //gets the lowest value for variable accessTime

        Map.Entry<Integer, Integer> minEntry = null;

        for (Map.Entry<Integer, Integer> entry : accessTable.entrySet()) {
            if (virtualMemoryManager.containsKey(entry.getKey())) //only gets the minimum from the memory
            {
                if (minEntry == null
                        || entry.getValue().compareTo(minEntry.getValue()) < 0) {
                    minEntry = entry;
                }
            }
        }
        return minEntry;
    }

}



