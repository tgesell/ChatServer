package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
/**
 * This class is for starting up the ChatServer.
 * It establishes a ServerSocket that waits for client connections.
 * It creates a separate Thread for communicating with each client.  (Threads can run in parallel)
 * The Threads (ClientThread instances) will be responsible for the actual communication.
 */
public class ChatServer {

    private static final int portNumber = 4444; //ports are a layer 4 TCP "address"
    //You want to use a port that won't be used by another application running on your computer
    //You can see port assignment information here:  https://en.wikipedia.org/wiki/List_of_TCP_and_UDP_port_numbers

    private int serverPort; // this is the serverPort number set in the constructor.
    // The current main method sends portNumber to the constructor to initialize serverPort
    private List<ClientThread> clients; //There might be lots of clients.
    //Each will have its own Thread, running in parallel, sending and receiving communication from one client.

    public static void main(String[] args){
        ChatServer server = new ChatServer(portNumber);
        server.startServer();
    }

    public ChatServer(int portNumber){
        clients = new ArrayList<ClientThread>();
        this.serverPort = portNumber;
    }

    public List<ClientThread> getClients(){
        return clients;
    }

    private void startServer(){
        ServerSocket serverSocket = null;
        try {
            //Here create the TCP socket.  This is again a layer 5 entity that supports establishing a TCP session
            //between two parties.  It requires that they communicate on an agreed upon TCP port.  (basically a layer 4 address)
            //A ServerSocket if for passively listening/waiting for a connection.
            serverSocket = new ServerSocket(serverPort);
            acceptClients(serverSocket); //this method contains an infinite loop - so it will just keep running waiting for clients.
        } catch (IOException e){
            System.err.println("Could not listen on port: "+serverPort);
            System.exit(1);
        }
    }

    private void acceptClients(ServerSocket serverSocket){
        System.out.println("server starts port = " + serverSocket.getLocalSocketAddress());
        //An infinite loop of accepting clients upon requeest
        while(true){
            try{
                //Listens for a connection to be made to this socket and accepts it. The method blocks until a connection is made.
                //It returns a Socket - which can be used for 2 way communication.
                Socket socket = serverSocket.accept();  //https://docs.oracle.com/javase/8/docs/api/java/net/ServerSocket.html#accept
                System.out.println("accepts : " + socket.getRemoteSocketAddress());
                //A thread just for this client-server communication will be created and communicate on this socket.
                /************** Complete this Section ************************************************************/
                //1)  Create a ClientThread using this Server object, and the socket  (ClientThread is a Runnable)
                //2)  Create a Java Thread with the ClientThread object
                //3)  start the Thread
                //4) add the client Thread to the clients List
                /**************************************************************************************************/
            } catch (IOException ex){
                System.out.println("Accept failed on : "+serverPort);
            }
        }
    }
}