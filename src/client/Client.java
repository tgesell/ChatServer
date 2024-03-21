package client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String host = "localhost";
    private static final int portNumber = 4444;

    private String userName;
    private String serverHost;
    private int serverPort;
    private Scanner userInputScanner;

    public static void main(String[] args){
        String userName = null;
        Scanner scan = new Scanner(System.in);
        System.out.println("Please input username:");
        while(userName == null || userName.trim().equals("")){
            // null, empty, whitespace(s) not allowed.
            userName = scan.nextLine();
            if(userName.trim().equals("")){
                System.out.println("Invalid. Please enter again:");
            }
        }

        Client client = new Client(userName, host, portNumber);
        client.startClient(scan);
    }

    private Client(String userName, String host, int portNumber){
        this.userName = userName;
        this.serverHost = host;
        this.serverPort = portNumber;
    }

    private void startClient(Scanner scan){
        try{
            Socket socket = new Socket(serverHost, serverPort);
            Thread.sleep(1000); // wait a short bit for network communication

            /*Complete this section******************************************************************************************************/
            //1)  Create a ServerThread object.  Send it the socket, and the userName
            ServerThread serverThread = new ServerThread(socket, userName );
            //2)  Create a Java Thread object.  Send it the ServerThread object (which implements Runnable) as a parameter
            Thread serverAccessThread = new Thread(serverThread);
            //3)  start the Java Thread object.
            serverAccessThread.start();

            //4)  loop as long as the Java Thread object isAlive()
            while (serverAccessThread.isAlive()) {
                //5)  whenever scan (the Scanner object) has an additional line of input, add that message to the ServerThread object.
                if (scan.hasNextLine()) {
                    String msg = scan.nextLine();
                    serverThread.addNextMessage(msg);
                }
            }
            // NOTE: scan.hasNextLine() awaits input (in other words blocks this thread's process until it has another line of input).
            //       If instead of Scanner, you use Buffered Reader or something else that does not await input,
            //       I recommend waiting a short time like following:
            // else {
            //    Thread.sleep(200);
            // }
            /*****************************************************************************************************************************/
        }catch(IOException ex){
            System.err.println("Fatal Connection error!");
            ex.printStackTrace();
        }catch(InterruptedException ex){
            System.out.println("Interrupted");
        }
    }
}
