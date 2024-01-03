package usercommandhandler;

/**
 *
 * @author patel
 */
public class UserCommandHandler {
    client.Client myClient;
    clientinterface.ClientInterface myUI;
    boolean[] ledStatus = {false,false,false,false};
    
    public UserCommandHandler(client.Client myClient,clientinterface.ClientInterface myUI){
        this.myClient = myClient;
        this.myUI = myUI;
    }
    
    public void handleUserCommand(String command){
        byte msg;
        if(command.equals("2")){
            myUI.update("Connecting to server");
            myClient.connectToServer();
        }else if(command.equals("t")){
            msg = command.getBytes()[0];
            myClient.sendMessageToServer(msg);
        }
    }
    
    public void toggleLed(int num){
        if(ledStatus[num-1]){
            ledOff(num);
            ledStatus[num-1]=false;
        }else{
            ledOn(num);
            ledStatus[num-1]=true;
        }
    }
    
    public void ledOn(int num){
        String message = "";
        switch (num){
            case 1:message+=1;
            break;
            case 2:message+=3;
            break;
            case 3:message+=5;
            break;
            case 4:message+=7;
        }
        myClient.sendMessageToServer(message);
    }
    
    public void ledOff(int num){
        String message = "";
        switch (num){
            case 1:message+=2;
            break;
            case 2:message+=4;
            break;
            case 3:message+=6;
            break;
            case 4:message+=8;
        }
        myClient.sendMessageToServer(message);
    }
    
    public void getPushButton(int num){
        String message = "";
        switch(num){
            case 1:message+="a";
            case 2:message+="s";
            case 3:message+="d";
        }
        myClient.sendMessageToServer(message);
    }
    
    public void connectServer(int port){
        myClient.connectToServer(port);
    }
}
