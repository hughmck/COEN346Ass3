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

    public void Store(){


    }

    public void LookUp(){


    }

    public void Release(){


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

