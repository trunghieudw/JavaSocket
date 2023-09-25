import java.net.*;
import java.util.Scanner;

public class UDPClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 9876;

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            DatagramSocket clientSocket = new DatagramSocket();

            while (true) {
                System.out.println("Menu:");
                System.out.println("1. Add Product");
                System.out.println("2. Update Product");
                System.out.println("3. Delete Product");
                System.out.println("Enter your choice (1-3): ");

                int choice = Integer.parseInt(scanner.nextLine());
                String request = "";

                switch (choice) {
                    case 1:
                        request = "add,Product1,ABC123,100.0";
                        break;

                    case 2:
                        request = "update,1,UpdatedProduct,XYZ789,200.0";
                        break;

                    case 3:
                        request = "delete,1";
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                        continue;
                }

                InetAddress serverAddress = InetAddress.getByName(SERVER_ADDRESS);
                byte[] sendData = request.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, SERVER_PORT);
                clientSocket.send(sendPacket);

                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);

                String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Response from server: " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
