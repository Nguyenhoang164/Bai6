import java.io.IOException;
import java.net.*;

public class client {
    public static void main(String[] args) throws IOException {
        DatagramSocket clientSocket = new DatagramSocket();

        byte[] sendData;
        byte[] receiveData = new byte[1024];

        InetAddress serverIP = InetAddress.getByName("localhost");

        String message = "Hello, server";
        sendData = message.getBytes();

        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverIP, 8888);

        clientSocket.send(sendPacket);

        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        clientSocket.receive(receivePacket);

        String responseMessage = new String(receivePacket.getData());

        System.out.println("Received from server: " + responseMessage);

        clientSocket.close();


    }
}
