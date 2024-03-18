package client;
/* This class is a service to a Client.  It provides a Runnable (can be run in a separate Thread)
    that is constantly listening to the Socket's input Stream (via a Scanner) for messages coming from the Server, and displaying them on the screen
    It is also able to recieve messages from its Client, and writes those methods to the Socket's output Stream via a PrintWriter.
 */

import java.net.Socket;
import java.util.Stack;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.IOException;

public class ServerThread implements Runnable {
    private Socket socket;
    private String userName;
    private final Stack<String> messagesToSend;
    private boolean hasMessages = false;

    public ServerThread(Socket socket, String userName){
        this.socket = socket;
        this.userName = userName;
        messagesToSend = new Stack<String>();
    }

    public void addNextMessage(String message){
        synchronized (messagesToSend){
            hasMessages = true;
            messagesToSend.push(message);
        }
    }

    @Override
    public void run(){
        System.out.println("Welcome :" + userName);
        System.out.println("Local Port :" + socket.getLocalPort());
        System.out.println("Server = " + socket.getRemoteSocketAddress() + ":" + socket.getPort());

        try{
            /*Complete this Section **************************************************************************************************/
            //Create a PrintWriter object that writes to the socket's output Stream
            PrintWriter serverOut = new PrintWriter(socket.getOutputStream());
            //Create an InputStream object variable that stores the socket's inputStream (for convenience)
            InputStream serverIn = socket.getInputStream();
            //Create a Scanner object that reads from the InputStream object
            Scanner scan = new Scanner(serverIn);
            //Keep looping as long as the socket isn't closed
            while (!socket.isClosed()) {
                //if there are bytes available in the InputStream (coming from the Server)
                if (serverIn.available() > 0)
                    //then if there is a line of data ready for the Scanner
                    if (scan.hasNextLine())
                        //print the line on the screen so this Client user can see the message
                        System.out.println(scan.nextLine());

                //then if this Client has sent messages{
                if (hasMessages) {
                    String nextMessage = null;
                    //Create a String variable to store the next message
                    /*  In a multi-threaded environment, a race condition occurs when two or more threads attempt to update mutable shared data at the same time.
                     * Java offers a mechanism to avoid race conditions by synchronizing thread access to shared data.
                     * A piece of logic marked with synchronized becomes a synchronized block, allowing only one thread to execute at any given time. */
                    synchronized (messagesToSend) {
                        //pop the next message off of the messagesToSend Stack
                        nextMessage = messagesToSend.pop();
                        //update hasMessages to false if the stack is empty, or true otherwise
                        hasMessages = !messagesToSend.isEmpty();
                    }

                    //print the username followed by the ">" character, followed by the message, to the PrintWriter
                    serverOut.println(userName + " > " + nextMessage);
                    serverOut.flush();
                    //flush the PrintWriter
                }
            }
            /*****************************************************************************************************************************/
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
