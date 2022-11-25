import java.util.ArrayList;

public class APICalls {



    APICalls(String command, int variableID, int value){
        this.command  = command;
        this.variableID = variableID;
        this.value = value;
    }

    //number of commands
    public int seeCommandCount(){
        return commandCount;
    }
    
        for(int i=0; i< orders.size(); i++){
            if(orders.get(i).equals('s')){
                commandList.add(new Commander('s', vars.get(i), vals.get(valcount++)));
            } else if (orders.get(i).equals('l')){
                commandList.add(new Commander('l', vars.get(i)));
            }else{
                commandList.add(new Commander('r', vars.get(i)));
            }
        }
        commandCount = commandList.size();
    }


    //check next command to run
    public synchronized Commander nextCommand(){
        Commander temp = new Commander(commandList.get(0).command, commandList.get(0).var, commandList.get(0).value);
        commandList.remove(0);
        commandCount--;
        return temp;

    }


    public String getCommand() {
        return command;
    }

    public Integer getVariableID() {
        return variableID;
    }

    public Integer getValue() {
        return value;
    }


    }
