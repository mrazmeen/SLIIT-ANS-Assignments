import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("Server socket"+serverSocket);
        while (true){
            Socket socket= null;
            try {
                socket=serverSocket.accept();
                System.out.println("A new client is connected"+socket);

                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                System.out.println("Assigning a new thread for a client");
                Thread t = new MultiHandler(dis,dos,socket);
                t.start();
            }catch (Exception e){
                socket.close();
                e.printStackTrace();
            }
        }

    }
}
class MultiHandler extends Thread{
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket socket;

    public MultiHandler(DataInputStream dis, DataOutputStream dos, Socket socket) {
        this.dis = dis;
        this.dos = dos;
        this.socket = socket;
    }
    public void run(){
        String recieved;
        while (true){
            try{
                dos.writeUTF("Send message to server (Type exit to terminate connection)");
                recieved =dis.readUTF();
                if(recieved.equals("exit")){
                    System.out.println("client"+this.socket+"sends exit");
                    System.out.println("closing the connection");
                    this.socket.close();
                    System.out.println("Connection closed");
                    break;
                }
                dos.writeUTF(recieved);
                System.out.println("responese of client"+recieved);
            }catch(Exception e){

            }
        }
        try{
            this.dis.close();
            this.dos.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }




}
