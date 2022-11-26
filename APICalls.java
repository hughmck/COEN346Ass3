import java.util.ArrayList;

public class APICalls {

    Driver commands = new Driver();

    public ArrayList<APICalls> commandList = Driver.commands;

    int value;
    int variableID;
    String command;

    APICalls virtualMemoryManager[] = new APICalls[Driver.memorySize];
    APICalls diskDrive[] = new APICalls[1000];

    APICalls(String command, int variableID, int value){
        this.command  = command;
        this.variableID = variableID;
        this.value = value;
    }

    public void Store(int variableID, int value){//if the main memory is not full, add it there. if it is full, add it to the disk drive
        this.variableID = variableID;
        this.value = value;
        for (int i = 0; i<Driver.memorySize; i++) //going through the length of the driver memorysize (virtual memory maneger size)
        {
            if (i != virtualMemoryManager.length - 1) //checking to see if its at the last index in the loop, as long as its not, add the values
            {
                virtualMemoryManager[i] = new APICalls(); //trying to add the value of store
                System.out.println("STORE (Found a Spot in Main Memory):" + " Variable: " + variableID + ", Value: " + value);
                return;
            }
            else{
                virtualMemoryManager[i] = new APICalls(); //trying to add the value of store into the disk drive if the virtual memory manager is full
                System.out.println("STORE (Virtual Memory was full, adding to disk drive):" + " Variable: " + variableID + ", Value: " + value);            }
        }
    }

    public int LookUp(int variableID){
        for (int i = 0; i<Driver.memorySize; i++)
        {
            if (virtualMemoryManager[i].getId() == variableID)
            {
                System.out.println("LOOKUP (Found Variable in Main Memory): Variable " + variableID + ", Value: " + virtualMemoryManager[i].getValue());
                return virtualMemoryManager[i].getValue();
            }
        }
        return -1;
    }

    public void Release(int variableID){
        for (int i = 0; i<Driver.memorySize; i++)
        {
            if (virtualMemoryManager[i].getId() == variableId)
            {
                System.out.println("RELEASE (From Main Memory)" + " Variable: " + variableId + ", Value: " + String.valueOf(virtualMemoryManager[i].getValue()));
                virtualMemoryManager[i] = null; //release the object
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


