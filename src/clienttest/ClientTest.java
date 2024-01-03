package clienttest;

import usercommandhandler.UserCommandHandler;

/**
 *
 * @author patel
 */
public class ClientTest {
    public static void main(String[] args) {
        //userinterface.StandardIO myUI = new userinterface.StandardIO();
        userinterface.rpgui myUI = new userinterface.rpgui();
        client.Client myClient = new client.Client(myUI);
        //servermessagehandler.ServerMessageHandler myServerMessageHandler = new servermessagehandler.ServerMessageHandler(myClient, myUI);
        usercommandhandler.UserCommandHandler myUserCommandHandler = new UserCommandHandler(myClient,myUI);
        myUI.setCommandHandler(myUserCommandHandler);
        Thread myClientThread = new Thread(myClient);
        myClientThread.start();
        //Thread myUIThread = new Thread(myUI);
        //myUIThread.start();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                myUI.setVisible(true);
            }
        });
//        myUI.update("1:\tQuit\n"
//                + "2:\tConnect to server\n"
//                + "3:\tDisconnect server\n"
//                + "t:\tGet time from server\n"
//        );
    }
}
