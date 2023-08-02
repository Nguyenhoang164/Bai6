import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {
    private static final int Size = 1024 * 32;
    private DatagramSocket datagramSocket;
    private int Port = 8080;
    public void openSever() throws IOException{
        datagramSocket = new DatagramSocket(Port);
        System.out.println("server hien duoc lien ket voi cong co ma " + Port);
        receiveData();
    }
    public void receiveData(){
        byte[] data = new byte[Size];
        DatagramPacket datagramPacket;
        try {
            datagramPacket = new DatagramPacket(data,data.length);
            datagramSocket.receive(datagramPacket);
            InetAddress inetAddress = datagramPacket.getAddress();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(datagramPacket.getData());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            FileInfo fileInfo = (FileInfo) objectInputStream.readObject();
            if (fileInfo != null){
                System.out.println("File name : " + fileInfo.getFilename());
                System.out.println("File size : " + fileInfo.getFileSize());
                System.out.println("File long pices : " + fileInfo.getPiecesOfFile());
                System.out.println("last byte long size : " + fileInfo.getLastByteLength());
            }
            System.out.println("dang gui lai............");
            File file = new File(fileInfo.getDestinationDirectory() + fileInfo.getFilename());
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            for (int i = 0 ; i < (fileInfo.getPiecesOfFile() - 1) ; i++){
                datagramPacket = new DatagramPacket(data,data.length,inetAddress,Port);
                datagramSocket.receive(datagramPacket);
                bufferedOutputStream.write(data,0,Size);
            }
            datagramPacket = new DatagramPacket(data,data.length,inetAddress,Port);
            datagramSocket.receive(datagramPacket);
            bufferedOutputStream.write(data,0,fileInfo.getLastByteLength());
            bufferedOutputStream.flush();
            bufferedOutputStream.close();

        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.openSever();
    }
}
