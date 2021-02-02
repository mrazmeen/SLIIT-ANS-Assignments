import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class MultiClient2 {
    public static void main(String[] args) {
        try {
            Scanner sc =new Scanner(System.in);
            InetAddress ip = InetAddress.getByName("localhost");
            Socket s= new Socket(ip,9999);

            DataInputStream dis= new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            while (true){
                System.out.println(dis.readUTF());
                String toSend="MC-2:"+sc.nextLine();
                dos.writeUTF(toSend);

                if (toSend.equals("exit")){
                    s.close();
                    System.out.println("Connection closed");
                    break;
                }
                String recieved= dis.readUTF();
                System.out.println("Server Says" + " " + recieved);
            }
            sc.close();
            dis.close();
            dos.close();

        }catch(Exception e){
    e.printStackTrace();
        }
    }
}
