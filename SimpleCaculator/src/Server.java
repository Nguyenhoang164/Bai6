import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.StringTokenizer;

public class Server {
    public static void main(String[] args) throws IOException {
        DatagramSocket serverData = new DatagramSocket(1024);
        DatagramPacket datagramPacketGet = null;
        DatagramPacket datagramPacketSend = null;
        byte [] give = null;
        byte [] Send;
        while (true){
            give = new byte[65535];
            datagramPacketGet = new DatagramPacket(give,give.length);
            serverData.receive(datagramPacketGet);
            String read = new String(give,0, give.length);
            if (read.equals("bye")){
                System.out.println("Client bye ");
                break;
            }
            read = read.trim();
            System.out.println("client : " + read);

            int result;
            String[] array = read.split(" ");


            int oprnd1 = Integer.parseInt(array[0]);
            String operation = array[1];
            int oprnd2 = Integer.parseInt(array[2]);


            // Perform the required operation
            if (operation.equals("+")) {
                result = oprnd1 + oprnd2;
            } else if (operation.equals("-")) {
                result = oprnd1 - oprnd2;
            }
            else if (operation.equals("*")){
                result = oprnd1 * oprnd2;
            }
            else {
                result = oprnd1 / oprnd2;
            }
            System.out.println("Send result...............");
            Send = new byte[1024];
            String res = String.valueOf(result);
            Send = res.getBytes();
            int Port = datagramPacketGet.getPort();
            datagramPacketSend = new DatagramPacket(Send,Send.length, InetAddress.getLocalHost(),Port);
            serverData.send(datagramPacketSend);

        }
    }
}
