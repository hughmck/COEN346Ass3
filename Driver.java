import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Driver {
    public static ArrayList<APICalls> commandsAPICalls = new ArrayList<APICalls>();

    ArrayList<String> commands = new ArrayList<String>(); //receives the string of the command
    ArrayList<Integer> variableID = new ArrayList<Integer>(); //returns the variableID of the command
    ArrayList<Integer> value = new ArrayList<Integer>(); //returns the value stored in the command

    APICalls apiCalls;

    apiCalls = new Commands(commands, variableID, value);

    public static void main(String[] args){
        Scanner reader = null;
        try {
            reader = new Scanner(new File(
                    "commands.txt"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        while(reader.hasNext()){
            commands.add(reader.nextLine());
        }
        for(int i = 0; i < commands.size(); i++) {
            System.out.print(commands.get(i));
            System.out.print(", ");
        }
    }
}

