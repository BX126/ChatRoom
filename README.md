# ChatRoom

> A simple localhost chatroom written in Java with the ability to filter specific information.

This project is a simple chatroom written based on the Java Networking Class (Socket/ServerSocket) I/O, dynamic storage, and multi-threading.

## Getting Started Guide

First, please run `ChatServer.java`. You have two ways to set up the chatroom:

1. Use the default port number:
   
   The default port number is 1500 (you can modify the default port number in the main class of `ChatServer.java`).

   ```
   > java ChatServer
   ```

2. Choose your own port number:

   If you want a different port number, enter your port number as a compilation argument (replace portNumber with your desired port number).

   ```
   > java ChatServer portNumber
   ```

At this point, the chatroom has been set up. You can add users to the chatroom by running `ChatClient.java`. You have four ways to add users:

1. If you want to join the default port number chatroom anonymously:

   ```
   > java ChatClient
   ```

2. If you want to join the default port number chatroom with a nickname you input (replace username with your nickname):

   ```
   > java ChatClient username
   ```

3. If you want to join a specific port number chatroom with a nickname you input (replace username with your nickname and portNumber with the port number of the chatroom you wish to join):

   ```
   > java ChatClient username portNumber
   ```

4. If you want to join a specific chatroom with a nickname you input (replace username with your nickname, portNumber with the port number of the chatroom you wish to join, and serverAddress with the server address):

   ```
   > java ChatClient username portNumber serverAddress
   ```

Now, you can chat with other users in the chatroom!

This chatroom also filters specific information. If a user sends this information, the server will automatically replace it with "*". You can add or remove the information you want to filter in [badwords.txt](ChatRoom/badwords.txt).
