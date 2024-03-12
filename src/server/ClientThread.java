package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
/**
 * This class runs within a separate Thread - parallel to other Thread executions.
 * It is Runnable (ie it has a run method that the Thread calls)
 * It handles communication between the ChatServer and ONE client
 */
public class ClientThread implements Runnable {
    private Socket socket;
    private PrintWriter clientOut; // A PrintWriter object that writes to an output source.  In this case a Socket.
    private Scanner in;  // A Scanner object that reads from an input source.  In this case a Socket.
    private ChatServer server;

    public ClientThread(ChatServer server, Socket socket){
        this.server = server;
        this.socket = socket;

        // setup PrintWriter and Scanner to write to / read from the Socket.
        try {
            this.clientOut = new PrintWriter(socket.getOutputStream(), false);
            this.in = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private PrintWriter getWriter(){
        return clientOut;
    }
    // This method will send anything entered by this client (read by this ClientThread's Scanner) to
    // all of the ClientThreads' PrintWriter objects (including its own)
    @Override
    public void run() {
        //keep looping as long as this Socket is open
        while(!socket.isClosed()){
            //Scanner hasNextLine() blocks (waits) until it gets some data.
            //So wait until the client types something that is "scanned" by the Scanner object.

            /* Complete this Section************************************************************************* */
            // if the socket has another line of input
                //get the line of data
                    // NOTE: if you want to verify that server can read input, uncomment the next line and check server file console.
                    // System.out.println(input);
                //Now, go through all the ClientThreads the server is keeping track of,
                    //get the PrintWriter used to write to each client's socket
                    //and write to those clients using the PrintWriter (as long as it isn't null)
                    if(thatClientOut != null){
                        thatClientOut.flush();
                    }//end if thatClientOut != null
                }//end for
            }//end if
            /************************************************************************************************* */
        }//end while
    }
}