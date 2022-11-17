import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Driver {
    public static ArrayList<String> commands = new ArrayList<String>();


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
        System.out.print(commands);
        }

    public ArrayList<String> getCommands() {
        return commands;
    }
    }

