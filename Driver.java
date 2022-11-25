import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Driver {
    public static ArrayList<APICalls> commands = new ArrayList<APICalls>();
    public static int numberOfCommands;

    public ArrayList<APICalls> getListOfCommands() {
        return commands;
    }
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
                String command = reader.next();
                int variableID= reader.nextInt();
                int value = reader.nextInt();
                commands.add(new APICalls(command, variableID, value)); //need a command which can take release 1 (doesnt have 3 attributes)
                numberOfCommands++;
        }
    }
}

