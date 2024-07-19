import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class MenuItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private double price;
    private int quantity;

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
    private double discount;
    private double taxRate;

    public RestaurantBillingSystem() {
        menuItems = new ArrayList<>();
        discount = 0;
        taxRate = 0;
    }

    public void addItem(String name, double price, int quantity) {
        menuItems.add(new MenuItem(name, price, quantity));
    }

    public double calculateTotal() {
        double total = 0;
        for (MenuItem item : menuItems) {
            total += item.getTotalPrice();
        }
        total -= total * (discount / 100);
        total += total * (taxRate / 100);
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
            System.out.println("Error saving order: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadOrder() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("order.dat"))) {
            menuItems = (ArrayList<MenuItem>) ois.readObject();
            System.out.println("Order loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading order: " + e.getMessage());
        }
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
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
            System.out.println("6. Set Discount");
            System.out.println("7. Set Tax Rate");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int choice = -1;

            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter item name: ");
                    String name = scanner.nextLine();
                    double price = -1;
                    int quantity = -1;
                    try {
                        System.out.print("Enter item price: ");
                        price = scanner.nextDouble();
                        System.out.print("Enter item quantity: ");
                        quantity = scanner.nextInt();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Price and quantity should be numeric.");
                        scanner.next();
                        continue;
                    }
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
                    double discount = -1;
                    try {
                        System.out.print("Enter discount percentage: ");
                        discount = scanner.nextDouble();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Discount should be numeric.");
                        scanner.next();
                        continue;
                    }
                    system.setDiscount(discount);
                    break;
                case 7:
                    double taxRate = -1;
                    try {
                        System.out.print("Enter tax rate percentage: ");
                        taxRate = scanner.nextDouble();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Tax rate should be numeric.");
                        scanner.next();
                        continue;
                    }
                    system.setTaxRate(taxRate);
                    break;
                case 8:
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
