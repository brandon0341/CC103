import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class MenuItem {
    private double price;
    private int quantity;

    public MenuItem(double price, int quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

class Restaurant {
    private Map<String, MenuItem> menu;

    public Restaurant() {
        this.menu = new HashMap<>();
    }

    public void addToMenu(String itemName, double itemPrice, int totalQuantity) {
        MenuItem newItem = new MenuItem(itemPrice, totalQuantity);
        menu.put(itemName, newItem);
    }
    public MenuItem searchItem(String itemName) {
        return menu.get(itemName);
    }
    public void updateItem(String itemName, double newItemPrice, int newItemQuantity) {
        MenuItem existingItem = menu.get(itemName);
        if (existingItem != null) {
            existingItem.setPrice(newItemPrice);
            existingItem.setQuantity(newItemQuantity);
        }
    }

    public boolean deleteItem(String itemName) {
        return menu.remove(itemName) != null;
    }

    public void displayOrders() {
        // Implementation for displaying order history goes here
        System.out.println("Order history not implemented yet.");
    }
     public int getItemQuantity(String itemName) {
        MenuItem menuItem = menu.get(itemName);
        return (menuItem != null) ? menuItem.getQuantity() : 0;
    }

    public Map<String, MenuItem> getMenu() {
        return menu;
    }
}

class MenuView {
    public static void displayMenu(Restaurant restaurant) {
        System.out.println("\nCurrent Menu:");
        for (Map.Entry<String, MenuItem> entry : restaurant.getMenu().entrySet()) {
            System.out.println(entry.getKey() + "- Php " + entry.getValue().getPrice() +
                    " - Quantity: " + entry.getValue().getQuantity());
        }
    }
}

public class RestaurantManagementSystem {

    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Restaurant!");

        // Adding items to the menu with a quantity of 15
        restaurant.addToMenu("Adobo Chicken", 180.00, 15);
        restaurant.addToMenu("Sinigang na Baboy", 220.00, 15);
        restaurant.addToMenu("Lechon Kawali", 200.00, 15);
        restaurant.addToMenu("Kare-Kare", 250.00, 15);
        restaurant.addToMenu("Pancit Canton", 150.00, 15);
        restaurant.addToMenu("Bangus Belly Paksiw", 190.00, 15);
        restaurant.addToMenu("Chicken Adobo Flakes", 160.00, 15);
        restaurant.addToMenu("Laing", 180.00, 15);
        restaurant.addToMenu("Tapsilog", 190.00, 15);
        restaurant.addToMenu("Halo-Halo", 120.00, 15);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Item");
            System.out.println("2. Search Item");
            System.out.println("3. View Menu");
            System.out.println("4. Update Item");
            System.out.println("5. Order History");
            System.out.println("6. Delete Item");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character left by nextInt()

            switch (choice) {
                case 1:
                    addMultipleItemsToOrder(restaurant, scanner);
                    break;
                case 2:
                    searchItemInMenu(restaurant, scanner);
                    break;
                case 3:
                    MenuView.displayMenu(restaurant);
                    break;
                case 4:
                    updateMenuItem(restaurant, scanner);
                    break;
                case 5:
                    viewOrderHistory(restaurant);
                    break;
                case 6:
                    deleteItemFromMenu(restaurant, scanner);
                    break;
                case 7:
                    System.out.println("Exiting the system. Thank you!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

     private static void addMultipleItemsToOrder(Restaurant restaurant, Scanner scanner) {
        System.out.println("Food Items Available:");
        MenuView.displayMenu(restaurant);

        List<MenuItem> orderItems = new ArrayList<>();
        double totalAmount = 0;

        while (true) {
        System.out.print("Enter the name of the item to order (or type 'done' to finish): ");
        String itemName = scanner.nextLine();

        if (itemName.equalsIgnoreCase("done")) {
            break;
        }

        // Check if the entered item name is in the menu
        if (restaurant.getMenu().containsKey(itemName)) {
            int availableQuantity = restaurant.getItemQuantity(itemName);

            if (availableQuantity > 0) {
                System.out.print("Enter quantity for '" + itemName + "' (available: " + availableQuantity + "): ");
                int quantity = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character left by nextInt()

                if (quantity <= availableQuantity) {
                    // Reduce the quantity in the menu
                    double itemPrice = restaurant.searchItem(itemName).getPrice();
                    int updatedQuantity = availableQuantity - quantity;
                    restaurant.updateItem(itemName, itemPrice, updatedQuantity);

                    // Add the item to the order
                    MenuItem orderedItem = new MenuItem(restaurant.searchItem(itemName).getPrice(), quantity);
                    orderItems.add(orderedItem);

                    // Update the total amount
                    totalAmount += restaurant.searchItem(itemName).getPrice() * quantity;

                    System.out.println("Item added to the order.");
                } else {
                    System.out.println("Insufficient quantity in the menu. Please choose a smaller quantity.");
                }
            } else {
                System.out.println("Item is out of stock. Please choose a different item.");
            }
        } else {
            System.out.println("Invalid item name. Please enter a valid item name from the menu.");
        }
        }

        // Display the order summary
        System.out.println("\nOrder Summary:");
        for (MenuItem orderedItem : orderItems) {
            System.out.println("Item: Php" + orderedItem.getPrice() +
                    " - Quantity: " + orderedItem.getQuantity());
        }
        System.out.println("Total Amount: Php" + totalAmount);
    }


    private static void searchItemInMenu(Restaurant restaurant, Scanner scanner) {
        System.out.print("Enter item name to search: ");
        String itemName = scanner.nextLine();
        MenuItem item = restaurant.searchItem(itemName);
        if (item != null) {
            System.out.println("Item found: " + itemName + " - $" + item.getPrice() +
                    " - Quantity: " + item.getQuantity());
        } else {
            System.out.println("Item not found.");
        }
        }

    private static void updateMenuItem(Restaurant restaurant, Scanner scanner) {
        System.out.print("Enter item name to update: ");
        String itemName = scanner.nextLine();
        MenuItem existingItem = restaurant.searchItem(itemName);

        if (existingItem != null) {
            System.out.print("Enter new item price: ");
            double newItemPrice = scanner.nextDouble();
            System.out.print("Enter new item quantity: ");
            int newItemQuantity = scanner.nextInt();
            existingItem.setQuantity(newItemQuantity);
            existingItem.setPrice(newItemPrice);
            System.out.println(itemName + " updated successfully.");
        } else {
            System.out.println("Item not found.");
        }
    }

    private static void deleteItemFromMenu(Restaurant restaurant, Scanner scanner) {
        System.out.print("Enter item name to delete: ");
        String itemName = scanner.nextLine();
        boolean deleted = restaurant.deleteItem(itemName);

        if (deleted) {
            System.out.println(itemName + " deleted successfully.");
        } else {
            System.out.println("Item not found.");
        }
    }

    private static void viewOrderHistory(Restaurant restaurant) {
        restaurant.displayOrders();
    }
}
