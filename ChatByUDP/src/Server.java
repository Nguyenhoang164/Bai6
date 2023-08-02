import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
public class Server {
    public static void main(String[] args) throws IOException {
        DatagramSocket serverSocket = new DatagramSocket(8888);
        byte[] receiveData = new byte[1024];
        byte[] sendData;

        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            serverSocket.receive(receivePacket);

            InetAddress clientIP = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();

            String message = new String(receivePacket.getData());

            System.out.println("Received from client: " + message);

            String responseMessage = "Hello, client!";
            sendData = responseMessage.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientIP, clientPort);

            serverSocket.send(sendPacket);
        }
    }
}