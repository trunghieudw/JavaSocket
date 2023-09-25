import java.util.Scanner;

public class UDPClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 9876;
    private static ClientManager clientManager;

    public static void main(String[] args) {
        try {
            clientManager = new ClientManager(SERVER_ADDRESS, SERVER_PORT);
            Scanner scanner = new Scanner(System.in);

            while (true) {
                displayMenu();

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

                    case 4:
                        System.out.print("Enter product ID: ");
                        int searchId = Integer.parseInt(scanner.nextLine());
                        request = "searchById," + searchId;
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                        continue;
                }

                String response = clientManager.sendRequest(request);
                System.out.println("Response from server: " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void displayMenu() {
        System.out.println("Menu:");
        System.out.println("1. Add Product");
        System.out.println("2. Update Product");
        System.out.println("3. Delete Product");
        System.out.println("4. Search Product by ID");
        System.out.println("Enter your choice (1-4): ");
    }
}
