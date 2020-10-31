import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

final class ChatClient {
    private ObjectInputStream sInput;
    private ObjectOutputStream sOutput;
    private Socket socket;

    private final String server;
    private final String username;
    private final int port;

    private ChatClient(String server, int port, String username) {
        this.server = server;
        this.port = port;
        this.username = username;
    }

    /*
     * This starts the Chat Client
     */
    private boolean start() {
        // Create a socket
        try {
            socket = new Socket(server, port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create your input and output streams
        try {
            sInput = new ObjectInputStream(socket.getInputStream());
            sOutput = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // This thread will listen from the server for incoming messages

        Runnable r = new ListenFromServer();
        Thread t = new Thread(r);
        t.start();

        // After starting, send the clients username to the server.
        try {
            sOutput.writeObject(username);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }


    /*
     * This method is used to send a ChatMessage Objects to the server
     */
    private void sendMessage(ChatMessage msg) {
        try {
            sOutput.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
     * To start the Client use one of the following command
     * > java ChatClient
     * > java ChatClient username
     * > java ChatClient username portNumber
     * > java ChatClient username portNumber serverAddress
     *
     * If the portNumber is not specified 1500 should be used
     * If the serverAddress is not specified "localHost" should be used
     * If the username is not specified "Anonymous" should be used
     */
    public static void main(String[] args) throws IOException {
        // Get proper arguments and override defaults

        // Create your client and start it
        ArrayList<String> inputs = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        for (String input : args) {
            inputs.add(input);
        }

        ChatClient client = null;

        if (inputs.size() == 0) {
            client = new ChatClient("localhost", 1500, "Anonymous");
        } else if (inputs.size() == 1) {
            client = new ChatClient("localhost", 1500, inputs.get(0));
        } else if (inputs.size() == 2) {
            client = new ChatClient("localhost", Integer.parseInt(inputs.get(1)), inputs.get(0));
        } else if (inputs.size() == 3) {
            client = new ChatClient(inputs.get(2), Integer.parseInt(inputs.get(1)), inputs.get(0));
        }

        client.start();
        while (true) {
            String message = scanner.nextLine();
            int type = 0;
            if (message.equals("/logout")) {
                type = 1;
            }else if (message.contains("/msg")){
                type = 2;
            }else if (message.equals("/list")){
                type = 3;
            }
            client.sendMessage(new ChatMessage(message, type));
            if (type == 1){
                break;
            }
        }

    }

    /**
     * This is a private class inside of the ChatClient
     * It will be responsible for listening for messages from the ChatServer.
     * ie: When other clients send messages, the server will relay it to the client.
     */
    private final class ListenFromServer implements Runnable {
            public void run () {
                while (true) {
                    if (socket.isClosed()) {
                        break;
                    }
                    try {
                        String msg = (String) sInput.readObject();
                        System.out.print(msg);

                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
    }
}

