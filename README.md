# Description
This is a Chat Application with ChatServer, and Client components
This code was modified from original source found here: http://www.instructables.com/id/Creating-a-Chat-Server-Using-Java/

## Details
There are two packages here:  client and server.
Each has its own executable class:  Client and ChatServer.

Each utilizes a Thread (or a group of Threads)  A Thread can be run independently from / parallel to other Threads.

For Client, there is a ServerThread that is constantly listening for messages from the Server.

For Server, there are likely multiple ClientThreads spawned, and running in parallel, each listening to and responding to a particular client.

ChatServer should be run on one computer.  (java ChatServer)
Client should be run on each computer that wants to run the chat application.  (java Client)
