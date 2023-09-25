import java.net.*;

public class UDPServer {
    private static final int PORT = 9876;
    private static ServiceProductsServer serviceProductsServer;

    public static void main(String[] args) {
        try {
            serviceProductsServer = new ServiceProductsServer();
            DatagramSocket serverSocket = new DatagramSocket(PORT);

            byte[] receiveData = new byte[1024];

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                String request = new String(receivePacket.getData(), 0, receivePacket.getLength());

                String response = handleRequest(request);

                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                byte[] sendData = response.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                serverSocket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String handleRequest(String request) {
        try {
            String[] parts = request.split(",");
            String action = parts[0];

            switch (action) {
                case "add":
                    String productName = parts[1];
                    String productCode = parts[2];
                    double productPrice = Double.parseDouble(parts[3]);
                    serviceProductsServer.addProduct(productName, productCode, productPrice);
                    return "Product added successfully.";

                case "update":
                    int productId = Integer.parseInt(parts[1]);
                    productName = parts[2];
                    productCode = parts[3];
                    productPrice = Double.parseDouble(parts[4]);
                    serviceProductsServer.updateProduct(productId, productName, productCode, productPrice);
                    return "Product updated successfully.";

                case "delete":
                    productId = Integer.parseInt(parts[1]);
                    serviceProductsServer.deleteProduct(productId);
                    return "Product deleted successfully.";

                case "searchById":
                    int searchId = Integer.parseInt(parts[1]);
                    return serviceProductsServer.searchProductById(searchId);

                default:
                    return "Invalid request.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing request: " + e.getMessage();
        }
    }
}
