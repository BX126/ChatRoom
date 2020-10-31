import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

final class ChatServer {
    private static int uniqueId = 0;
    private final List<ClientThread> clients = new ArrayList<>();
    private final int port;
    private final String file;
    private Object object;


    private ChatServer(int port, String file) {
        this.port = port;
        this.file = file;
    }

    /*
     * This is what starts the ChatServer.
     * Right now it just creates the socketServer and adds a new ClientThread to a list to be handled
     */
    private void start() {
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                while (true) {
                    Socket socket = serverSocket.accept();
                    Runnable r = new ClientThread(socket, uniqueId++);
                    Thread t = new Thread(r);
                    clients.add((ClientThread) r);
                    t.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    /*
     *  > java ChatServer
     *  > java ChatServer portNumber
     *  If the port number is not specified 1500 is used
     */
    public static void main(String[] args) {
        ChatServer server = new ChatServer(1500,"ChatRoom/badwords.txt");
        server.start();
    }




   /**
    * This is a private class inside of the ChatServer
    * A new thread will be created to run this every time a new client connects.
    */
    private final class ClientThread implements Runnable {
        Socket socket;
        ObjectInputStream sInput;
        ObjectOutputStream sOutput;
        int id;
        String username;
        ChatMessage cm;
        String ncm;

        private ClientThread(Socket socket, int id) {
            this.id = id;
            this.socket = socket;
            try {
                sOutput = new ObjectOutputStream(socket.getOutputStream());
                sInput = new ObjectInputStream(socket.getInputStream());
                username = (String) sInput.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


       private boolean writeMessage(String message) throws IOException {

           if (!socket.isConnected()){
               return false;
           }
            ChatMessage message1 = new ChatMessage(message,0);
            sOutput.writeObject(message1.getMessage());
            sOutput.flush();
            return true;

       }

       private void broadcast(String message) throws IOException {
            for (ClientThread thread : clients) {
                thread.writeMessage(message);
            }
       }

       private String getTime() {
           SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
           Date date = new Date();
           String time = formatter.format(date);
           return time;

       }

       private void remove(int id){
            synchronized (object) {
                clients.remove(id);
            }
       }

       private void close()  {
            try {
                socket.close();
                sInput.close();
                sOutput.close();
            }catch (IOException ex){
                ex.printStackTrace();
            }
       }

       private void directMessage(String message, String username) throws IOException {

            for (int i =0; i<clients.size();i++) {

                if (clients.get(i).username.equals(username)){
                    clients.get(i).writeMessage(message);
                }

           }
       }
        /*
         * This is what the client thread actually runs.
         */
        @Override
        public void run() {
    // Read the username sent to you by client
    while (true) {
        try {
            cm = (ChatMessage) sInput.readObject();
            ChatFilter cf = new ChatFilter("ChatRoom/badwords.txt");
            ncm = cm.getMessage();
            ncm = cf.filter(ncm);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(username + ": " + ncm + " " + getTime());

        // Send message back to the client
        try {
            if (cm.getType() == 0){
            broadcast(username + ": " + ncm + " " + getTime() + "\n");
            } else if (cm.getType() == 1) {
                remove(id);
                broadcast(username + "logout!\n");
                close();
                break;
            }else if (cm.getType() == 2){
                String[] parts = ncm.split(" ");
                directMessage(parts[2],parts[1]);
            }else if (cm.getType() == 3){
                ArrayList <String>userList = new ArrayList<>();
                for (int i=0; i<clients.size();i++){
                    userList.add(clients.get(i).username);
                }
                for (String users: userList){
                    broadcast(users+"\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
     }
        }
    }
}


