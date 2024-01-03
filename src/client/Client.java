package client;

import java.io.IOException;
import java.net.*;
import servermessagehandler.ServerMessageHandler;
import java.io.*;

/**
 *
 * @author patel
 */
public class Client implements Runnable{
    int DEFAULT_SERVER_PORT = 9761;
    char termination = 0xFF;
    byte[] serverAddressBytes = new byte[]{(byte)192,(byte)168,(byte)1,(byte)189};
    
    static String message="";
    
    OutputStream output; 
    InputStream input;
    
    int serverPort = DEFAULT_SERVER_PORT;
    Socket serverSocket;
    //ServerSocket serverSocket;
    boolean stopThread = false;
    InetAddress serverAddress;
    servermessagehandler.ServerMessageHandler serverMessageHandler;
    //userinterface.StandardIO myUI;
    clientinterface.ClientInterface myUI;
    
    public Client(clientinterface.ClientInterface myUI){
        this.myUI=myUI;
        try{
            serverAddress = Inet4Address.getByAddress(serverAddressBytes);
        }catch(UnknownHostException e){
            System.out.println(e);
        }
        this.serverPort=DEFAULT_SERVER_PORT;
        serverMessageHandler = new ServerMessageHandler(this,myUI);
    }
    
    public void connectToServer(){
        if(serverSocket != null){
            sendMessageToUI("Server already connected");
        }else{
            try{
                serverSocket = new Socket(serverAddress,serverPort);
                output = serverSocket.getOutputStream();
                input = serverSocket.getInputStream();
                myUI.update("Server connected");
            }catch(IOException e){
                System.out.println(e);
            }
        }
    }
    //overloaded for convinience
    public void connectToServer(int port){
        serverPort=port;
        connectToServer();
    }
    public void disconnctServer(){
        try{
            serverSocket.close();
        }catch(IOException e){
            sendMessageToUI("could not disconnect server because:"+e);
        }
    }
    public void sendMessageToServer(byte message){
        try{
            output.write(message);
            output.flush();
        }catch(IOException e){
            myUI.update("Failed to send message to server: "+e);
        }
    }
    
    public void sendMessageToServer(char message){
        try{
            output.write(message);
            output.flush();
        }catch(IOException e){
            myUI.update("Failed to send message to server: "+e);
        }
    }
    
    public void sendMessageToServer(String message){
        byte msg;
        for(int i=0;i<message.length();i++){
            msg = message.getBytes()[i];
            sendMessageToServer(msg);
        }
        sendMessageToServer(termination);
    }
    public boolean isConnected(){
        return (serverSocket != null);
    }
    
    public void stopThread(){
        stopThread = true;
    }
    
    public void setPort(int portNum){
        this.serverPort = portNum;
    }
    public int getPort(){
        return this.serverPort;
    }
    
    public void sendMessageToUI(String message){
        myUI.update(message);
    }
    
    public InputStream getInputStream(){
        return input;
    }
    
    public OutputStream getOutputStream(){
        return output;
    }
    
    public void getServerMessage(){
        if(input != null && serverSocket != null){
            //System.out.println("Handle message entered");
            byte msg;
            try{
                msg = (byte)input.read();
                if(msg != -1){
                    message += (char)msg;
                }else{
                    myUI.update(message);
                    message = "";
                }
            }catch(IOException e) {
                myUI.update("Failed to read incoming message: "+e);
            }
        }
    }
    
    @Override
    public void run(){
        while(!stopThread){
            //System.out.print("test");
            getServerMessage();
        }
//        while(!stopThread){
//            try{
//                clientSocket = new Socket(serverAddress,serverPort);
//            }catch(IOException e){
//                System.out.println(e);
//            }
//        }
    }

    
}
