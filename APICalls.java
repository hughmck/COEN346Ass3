import java.security.Timestamp;
import java.util.ArrayList;

public class APICalls {

    Driver commands = new Driver();

    public ArrayList<APICalls> commandList = Driver.commands;

    int value;
    int variableID;
    String command;

    APICalls(String command, int variableID, int value){
        this.command  = command;
        this.variableID = variableID;
        this.value = value;
    }

    public void Store(int variableID, int value){//if the main memory is not full, add it there. if it is full, add it to the disk drive
        this.variableID = variableID;
        this.value = value;
        for (int i = 0; i<size; i++)
        {
            if (mainMemory[i] == null)
            {
                Variable variable = new Variable();
                variable.setId(variableId);
                variable.setValue(value);

                //Update timestamp
                variable.setLastAccessTime(new Timestamp(System.currentTimeMillis()));

                mainMemory[i] = variable;
                System.out.println("STORE (Found a Spot in Main Memory):" + " Variable: " + variableId + ", Value: " + value);
                return;
            }
        }


    }

    public void LookUp(int variableID){
        for (int i = 0; i<size; i++)
        {
            if (mainMemory[i].getId() == variableId)
            {
                System.out.println("LOOKUP (Found Variable in Main Memory): Variable " + variableId + ", Value: " + mainMemory[i].getValue());
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
                Store(); //trying to check the string value
            } else if (commandList.get(i).equals("Lookup")) {
                LookUp();
            } else {
                Release();
            }
        }
    }
}



//

//
//
//    //check next command to run
//    public synchronized Commander nextCommand(){
//        Commander temp = new Commander(commandList.get(0).command, commandList.get(0).var, commandList.get(0).value);
//        commandList.remove(0);
//        commandCount--;
//        return temp;
//
//    }
//
//
//    public String getCommand() {
//        return command;
//    }
//
//    public Integer getVariableID() {
//        return variableID;
//    }
//
//    public Integer getValue() {
//        return value;
//    }
//
//

