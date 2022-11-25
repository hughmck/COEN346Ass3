import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Driver {
    //public static ArrayList<APICalls> commandsAPICalls = new ArrayList<APICalls>();

    public static ArrayList<APICalls> commands = new ArrayList<APICalls>();
    public static int numberOfCommands;

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

            String userName = "User " +reader.next();

            numberOfCommands++;
            for(int i=0;i<numOfInputProcesses;i++){
                String command = "command " + i;
                int variableID= reader.nextInt();
                int value = reader.nextInt();
                commands.add(new APICalls(command, variableID, value));
            }
        }
    }
}

