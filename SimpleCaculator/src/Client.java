import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket();
        InetAddress inetAddress = InetAddress.getByName("localhost");
        Scanner scanner = new Scanner(System.in);
        byte [] give = null;
        byte [] Read;
        while (true){
            Read = new byte[3];
            give = new byte[6635];
            System.out.println("number 1 with caculator and number 2 : ");
            String Value = scanner.nextLine();
            if (Value.equals("bye")){
                break;
            }
            give = Value.getBytes();
            DatagramPacket datagramPacketSend = new DatagramPacket(give,give.length,inetAddress,1024);
            datagramSocket.send(datagramPacketSend);

            DatagramPacket datagramPacketGive = new DatagramPacket(Read,Read.length);
            datagramSocket.receive(datagramPacketGive);
            System.out.println("answer : " + new String(Read,0,Read.length) );
        }
    }
}