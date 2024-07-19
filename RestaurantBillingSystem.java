import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class MenuItem implements Serializable {
    String name;
    double price;
    int quantity;

    MenuItem(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    double getTotalPrice() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return name + " - $" + price + " x " + quantity;
    }
}

public class RestaurantBillingSystem {
    private ArrayList<MenuItem> menuItems;

    public RestaurantBillingSystem() {
        menuItems = new ArrayList<>();
    }

    public void addItem(String name, double price, int quantity) {
        menuItems.add(new MenuItem(name, price, quantity));
    }

    public double calculateTotal() {
        double total = 0;
        for (MenuItem item : menuItems) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public void displayMenu() {
        System.out.println("Menu:");
        for (MenuItem item : menuItems) {
            System.out.println(item);
        }
    }

    public void saveOrder() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("order.dat"))) {
            oos.writeObject(menuItems);
            System.out.println("Order saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadOrder() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("order.dat"))) {
            menuItems = (ArrayList<MenuItem>) ois.readObject();
            System.out.println("Order loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        RestaurantBillingSystem system = new RestaurantBillingSystem();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Restaurant Billing System");
        while (true) {
            System.out.println("1. Add Item");
            System.out.println("2. Display Menu");
            System.out.println("3. Calculate Total");
            System.out.println("4. Save Order");
            System.out.println("5. Load Order");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter item name: ");
                    String name = scanner.next();
                    System.out.print("Enter item price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter item quantity: ");
                    int quantity = scanner.nextInt();
                    system.addItem(name, price, quantity);
                    break;
                case 2:
                    system.displayMenu();
                    break;
                case 3:
                    double total = system.calculateTotal();
                    System.out.println("Total bill: $" + total);
                    break;
                case 4:
                    system.saveOrder();
                    break;
                case 5:
                    system.loadOrder();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
