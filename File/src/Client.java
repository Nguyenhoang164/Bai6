import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static final int Size = 1024 * 32;
    private DatagramSocket clientSocket;
    private int serverPort = 8080;
    private String serverHost = "localhost";
    public void openServer() throws SocketException {
       clientSocket = new DatagramSocket();

    }
    public void sendMessage(String link , String Dir){
        DatagramPacket datagramPacket;
        InetAddress inetAddress;
        byte [] data;
        try {
            File file = new File(link);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            inetAddress = InetAddress.getByName(serverHost);
            data = new byte[Size];

            Long FileSize = file.length();
            int filePices = (int) (FileSize/Size);
            int lastByte = (int) (FileSize % Size);
            if (lastByte > 0){
                filePices ++;
            }
          byte[][] database = new byte[filePices][Size];
            int count = 0;
            while (bufferedInputStream.read(data,0,Size) > 0){
                database[count++] = data;
                data = new byte[Size];
            }
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFilename(file.getName());
            fileInfo.setFileSize(file.length());
            fileInfo.setPiecesOfFile(filePices);
            fileInfo.setLastByteLength(lastByte);
            fileInfo.setDestinationDirectory(Dir);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(fileInfo);
            datagramPacket = new DatagramPacket(byteArrayOutputStream.toByteArray(),byteArrayOutputStream.toByteArray().length,inetAddress,serverPort);
            clientSocket.send(datagramPacket);

            System.out.println("now sending message...............");
            for (int i = 0 ; i < (count - 1); i++){
                datagramPacket = new DatagramPacket(database[count - 1],Size,inetAddress,serverPort);
                clientSocket.send(datagramPacket);
                wait(40);
            }
            outputStream.close();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("sent");
    }

    public static void main(String[] args) throws SocketException {
        Client client = new Client();
        client.openServer();
        Scanner scanner = new Scanner(System.in);
        System.out.println("sourecPath : ");
        String sourcePath = scanner.nextLine();
        System.out.println("Dir : ");
        String destinationDir = scanner.nextLine();
        client.sendMessage(sourcePath,destinationDir);
    }
}