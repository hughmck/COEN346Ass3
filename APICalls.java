import java.security.Timestamp;
import java.sql.Array;
import java.util.ArrayList;

public class APICalls {

    Driver commands = new Driver();

    public ArrayList<APICalls> commandList = Driver.commands;

    int value;
    int variableID;
    String command;

    public int memorySize = Driver.memorySize;
    public Array mainMemory[];


    public void virtualMemoryManager(int memorySize)
    {
        this.memorySize = memorySize;
        mainMemory =  mainMemory[memorySize]; //adding the size of the memory
    }

    APICalls(String command, int variableID, int value){
        this.command  = command;
        this.variableID = variableID;
        this.value = value;
    }

    public void Store(int variableID, int value){//if the main memory is not full, add it there. if it is full, add it to the disk drive
        this.variableID = variableID;
        this.value = value;
        for (int i = 0; i<memorySize; i++)
        {
            if (i != mainMemory.length - 1) //checking to see if its at the last index in the loop
            {
                APICalls.setId(variableID); //adding the ID and value into main memory if a spot is found
                APICalls.setValue(value);

                mainMemory[i] = variable;
                System.out.println("STORE (Found a Spot in Main Memory):" + " Variable: " + variableID + ", Value: " + value);
                return;
            }
            else{
                //add to disk drive (create a function)
            }
        }


    }

    public int LookUp(int variableID){
        for (int i = 0; i<size; i++)
        {
            if (mainMemory[i].getId() == variableID)
            {
                System.out.println("LOOKUP (Found Variable in Main Memory): Variable " + variableID + ", Value: " + mainMemory[i].getValue());
                mainMemory[i].setLastAccessTime(new Timestamp(System.currentTimeMillis()));
                return mainMemory[i].getValue();
            }
        }
        return -1;
    }

    public void Release(int variableID){
        for (int i = 0; i<size; i++)
        {
            if (mainMemory[i].getId() == variableId)
            {
                System.out.println("RELEASE (From Main Memory)" + " Variable: " + variableId + ", Value: " + String.valueOf(mainMemory[i].getValue()));
                mainMemory[i] = null; //RELEASE
                return;
            }
        }
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
}


