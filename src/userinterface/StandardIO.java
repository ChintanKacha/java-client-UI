package userinterface;

import java.io.*;

public class StandardIO implements Runnable , clientinterface.ClientInterface{

    BufferedReader console = null;
    //servermessagehandler.ServerMessageHandler myServerMessageHandler;
    usercommandhandler.UserCommandHandler myUserCommandHandler;
    public StandardIO() {
        console = new BufferedReader(new InputStreamReader(System.in));
        if (console == null) {
            System.err.println("No Standard Input device, exiting program.");
            System.exit(0);
        }
    }

    public void setCommandHandler(usercommandhandler.UserCommandHandler myUserCommandHandler) {
        this.myUserCommandHandler = myUserCommandHandler;
    }

    public void update(String theResult) {
        System.out.println(theResult);
    }

    @Override
    public void run() {
        String userInput = "no input";
        while (true) {
            try {
                userInput = console.readLine();
            } catch (IOException e) {
                System.err.println("Error reading from "
                        + "Standard Input device, "
                        + "exiting program.");
                System.exit(0);
            }
            myUserCommandHandler.handleUserCommand(userInput);
            //This should not take too long, else 
            //user-interface will be non-responsive
        }
    }
}
