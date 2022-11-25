import java.util.ArrayList;

public class APICalls {


    private ArrayList<Commands> commandCount = new ArrayList<Commands>();
    private String command;
    private Integer variableID;
    private Integer value;
    private int commandCount;


    public Commands(Character command, Integer variableID, Integer value){
        this.command = command;
        this.variableID = variableID;
        this.value = value;
    }

    public Commander(char command, int var) {
        this.command = command;
        this.variableID = var;
    }


    //number of commands
    public int seeCommandCount(){
        return commandCount;
    }

    public Commander(ArrayList<Character> orders, ArrayList<Integer> vars, ArrayList<Integer> vals){
        int valcount =0;


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


    public Character getCommand() {
        return command;
    }

    public Integer getVar() {
        return variableID;
    }

    public Integer getValue() {
        return value;
    }


    }
