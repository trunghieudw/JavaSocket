import java.net.*;

public class ClientManager {
    private DatagramSocket clientSocket;
    private InetAddress serverAddress;
    private int serverPort;

    public ClientManager(String serverAddress, int serverPort) throws SocketException, UnknownHostException {
        this.clientSocket = new DatagramSocket();
        this.serverAddress = InetAddress.getByName(serverAddress);
        this.serverPort = serverPort;
    }

    public String sendRequest(String request) {
        try {
            byte[] sendData = request.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            clientSocket.send(sendPacket);

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            return new String(receivePacket.getData(), 0, receivePacket.getLength());
        } catch (Exception e) {
            e.printStackTrace();
            return "Error sending request: " + e.getMessage();
        }
    }
}
