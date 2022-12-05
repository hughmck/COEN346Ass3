import java.util.ArrayList;
import java.util.HashMap;

public class APICalls {

    Driver commands = new Driver();

    public ArrayList<APICalls> commandList = Driver.commands;

    int value;
    int variableID;
    String command;

    int access;

    HashMap <Integer, Integer> virtualMemoryManager = new HashMap<Integer, Integer>();
    HashMap <Integer, Integer> diskDrive = new HashMap<Integer, Integer>();

    HashMap<Integer, Integer> accessTable = new HashMap<>();

    APICalls(String command, int variableID, int value){
        this.command  = command;
        this.variableID = variableID;
        this.value = value;
    }

    public void Store(int variableID, int value){//if the main memory is not full, add it there. if it is full, add it to the disk drive
        this.variableID = variableID;
        this.value = value;
        if (virtualMemoryManager.size() > Driver.memorySize) //as long as the hashmap size is smaller than the memory, the value will be stored in the virtual memory manager if not itll be stored in the disk
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

    public int LookUp(int variableID){
        this.variableID = variableID;

        if (virtualMemoryManager.containsKey(variableID))
        {
            System.out.println("LOOKUP succesful. Found value in the virtual memory. Variable " + variableID + ", Value: " + virtualMemoryManager.get(value));
            return virtualMemoryManager.get(value);
        }

        if (diskDrive.containsValue(variableID))
        {
            System.out.println("LOOKUP succesful. Found value in the disk drive. Variable " + variableID + ", Value: " + diskDrive.get(value));
            return diskDrive.get(value);
        }
        return -1;
    }

    public void Release(int variableID){
        this.variableID = variableID;

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

    public void run(ArrayList<APICalls> commandList) { //trying to run through the list of commands received from driver file
        for (int i = 0; i < commandList.size(); i++) {
            if (commandList.get(i).equals("Store")) {
                Store(variableID, value); //trying to check the string value
            } else if (commandList.get(i).equals("Lookup")) {
                LookUp(variableID);
            } else {
                Release(variableID);
            }
        }
    }

    public int getID(){
        return variableID;
    }

    public int getValue(){
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    public void setVariableID(int variableID) {
        this.variableID = variableID;
    }

}


