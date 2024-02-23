package restaurant.menu.management.system;

import java.util.Scanner;
import java.util.InputMismatchException;

class MenuItem {
    String name;
    double price;

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class BinarySearchTree {
    class TreeNode {
        MenuItem menuItem;
        TreeNode left, right;

        public TreeNode(MenuItem item) {
            this.menuItem = item;
            this.left = this.right = null;
        }
    }

    private TreeNode root;

    public BinarySearchTree() {
        this.root = null;
    }

    public void insert(MenuItem item) {
        root = insertRec(root, item);
    }

    private TreeNode insertRec(TreeNode root, MenuItem item) {
        if (root == null) {
            root = new TreeNode(item);
            return root;
        }

        if (item.name.compareToIgnoreCase(root.menuItem.name) < 0) {
            root.left = insertRec(root.left, item);
        } else if (item.name.compareToIgnoreCase(root.menuItem.name) > 0) {
            root.right = insertRec(root.right, item);
        } else {
            //if equal both items then it's displayed and ignore dish name and price storing
            System.out.println("This Dish " + "(" + item.name + ")" + " already in the menu");
        }

        return root;
    }

    // In-order traversal to get items in alphabetical order
    public void inorder() {
        inorderRec(root);
    }

    private void inorderRec(TreeNode root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.println("Name: " + root.menuItem.name + ", Price: " + root.menuItem.price);
            inorderRec(root.right);
        }
    }

    //update the price of the dish using item name(dish name)
    public void update(String itemName, double newPrice) {
        try {
            if (root == null) {
                throw new NullPointerException("Menu is empty. Cannot update.");
            }
            root = updateRec(root, itemName, newPrice);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private TreeNode updateRec(TreeNode root, String itemName, double newPrice) {
        if (root == null) {
            System.out.println("Dish " + "(" + itemName + ")" + " not found in the menu");
            return null;
        }

        int compareResult = itemName.compareToIgnoreCase(root.menuItem.name);

        if (compareResult < 0) {
            root.left = updateRec(root.left, itemName, newPrice);
        } else if (compareResult > 0) {
            root.right = updateRec(root.right, itemName, newPrice);
        } else {
            // Item found, update the price
            root.menuItem.price = newPrice;
            System.out.println("Dish " + "(" + itemName + ")" + " updated with new price: " + newPrice);
        }

        return root;
    }

    // Delete the dish with the specified name
    public void delete(String itemName) {
        root = deleteRec(root, itemName);
    }

    private TreeNode deleteRec(TreeNode root, String itemName) {
        if (root == null) {
            System.out.println("Dish " + "(" + itemName + ")" + " not found in the menu");
            return null;
        }

        int compareResult = itemName.compareToIgnoreCase(root.menuItem.name);

        if (compareResult < 0) {
            root.left = deleteRec(root.left, itemName);
        } else if (compareResult > 0) {
            root.right = deleteRec(root.right, itemName);
        } else {
            // Item found, delete the node
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            // Node with two children: Get the inorder successor (smallest in the right subtree)
            root.menuItem = minValue(root.right);

            // Delete the inorder successor
            root.right = deleteRec(root.right, root.menuItem.name);
        }

        return root;
    }

    private MenuItem minValue(TreeNode root) {
        MenuItem minValue = root.menuItem;
        while (root.left != null) {
            minValue = root.left.menuItem;
            root = root.left;
        }
        return minValue;
    }

    //Search the price with dish name
    public void searchAndPrintPrice(String itemName) {
        TreeNode foundNode = search(root, itemName);
        if (foundNode != null) {
            System.out.println("Price of " + itemName + ": " + foundNode.menuItem.price);
        } else {
            System.out.println("Dish " + "(" + itemName + ")" + " not found in the menu");
        }
    }

    private TreeNode search(TreeNode root, String itemName) {
        if (root == null || root.menuItem.name.equalsIgnoreCase(itemName)) {
            return root;
        }

        int compareResult = itemName.compareToIgnoreCase(root.menuItem.name);

        if (compareResult < 0) {
            return search(root.left, itemName);
        } else {
            return search(root.right, itemName);
        }
    }
}

//price look up class
class PriceBinarySearchTree {
    class TreeNode {
        MenuItem menuItem;
        TreeNode left, right;

        public TreeNode(MenuItem item) {
            this.menuItem = item;
            this.left = this.right = null;
        }
    }

    private TreeNode root;

    public PriceBinarySearchTree() {
        this.root = null;
    }

    public void insert(MenuItem item) {
        root = insertRec(root, item);
    }

    private TreeNode insertRec(TreeNode root, MenuItem item) {
        if (root == null) {
            root = new TreeNode(item);
            return root;
        }

        if (item.price < root.menuItem.price) {
            root.left = insertRec(root.left, item);
        } else if (item.price > root.menuItem.price) {
            root.right = insertRec(root.right, item);
        } else {
            if (item.name.compareTo(root.menuItem.name) < 0) {
                root.left = insertRec(root.left, item);
            } else if (item.name.compareTo(root.menuItem.name) > 0) {
                root.right = insertRec(root.right, item);
            } else {
                System.out.println("This Dish " + "(" + item.name + ")" + " already in the menu");
            }
        }

        return root;
    }

    // In-order traversal to get items sorted by low to high prices
    public void inorder() {
        inorderRec(root);
    }

    private void inorderRec(TreeNode root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.println("Name: " + root.menuItem.name + ", Price: " + root.menuItem.price);
            inorderRec(root.right);
        }
    }
}

public class RestaurantMenuManagementSystem {
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        BinarySearchTree lunch = new BinarySearchTree();
        BinarySearchTree dinner = new BinarySearchTree();
        BinarySearchTree specialpackages = new BinarySearchTree();
        
        PriceBinarySearchTree priceBST = new PriceBinarySearchTree();
        PriceBinarySearchTree pricelunchBST = new PriceBinarySearchTree();
        PriceBinarySearchTree pricedinnerBST = new PriceBinarySearchTree();
        PriceBinarySearchTree price_SP_BST = new PriceBinarySearchTree();
        
        Scanner scanner = new Scanner(System.in);

        int userChoice,menuTypeChoice,customerChoice,menuOperationTypeChoice,customerOperations;
        String menuName;
        do{
            // Display options for user selection
            System.out.println("\nSelect User Type:");
            System.out.println("1. Restaurant Staff");
            System.out.println("2. Customer");
            System.out.println("3. Exit");
            System.out.println();
            
            userChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
            
            if(userChoice==1){
            do{
                // Display options for selecting menu type
            System.out.println("\nSelect Menu Type:");
            System.out.println("1. Breakfast");
            System.out.println("2. Lunch");
            System.out.println("3. Dinner");
            System.out.println("4. Special Packages");
            System.out.println("5. Exit");
            
            menuTypeChoice = scanner.nextInt();
            scanner.nextLine();
            
            if(menuTypeChoice==1){
                menuName="Breakfast";
                do{
                // Display options for menu operations
            System.out.println("\n" + menuName + " Menu Operations:");
            System.out.println("1. Add " + menuName.toLowerCase() + " dishes");
            System.out.println("2. Search dishes");
            System.out.println("3. Update dishes");
            System.out.println("4. Delete dishes");
            System.out.println("5. Exit");
            
            menuOperationTypeChoice=scanner.nextInt();
            scanner.nextLine();
            
            switch(menuOperationTypeChoice){
                case 1:
                    // Getting menu items from the user
        System.out.println("Enter menu items (Dish Name and Price). "
                + "Ex:- Pizza 890.00 "
                + " To finish for adding items type 'done' .");
        String input;
        while (!(input = scanner.nextLine()).equalsIgnoreCase("done")) {
            String[] inputParts = input.split(" ");
            if (inputParts.length == 2) {
                String name = inputParts[0];
                try {
                    double price = Double.parseDouble(inputParts[1]);

                    // Inserting into both BSTs
                    bst.insert(new MenuItem(name, price));
                    priceBST.insert(new MenuItem(name, price));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid price format. Please enter a valid number.");
                }
            } else {
                System.out.println("Invalid input. Please enter Dish Name and Price separated by space."
                        + "Ex:- Pizza 890.00 ");
            }
        }

        // Displaying menu items in alphabetical order with prices
        System.out.println("\nMenu Items in Alphabetical Order:");
        bst.inorder();

                    break;
                case 2:
                    // Getting user input for searching a menu item
                        System.out.println("\nEnter the dish name to search in the menu:");
                        String dishToSearch = scanner.nextLine();

                        // Searching for the specified dish and printing its price
                        bst.searchAndPrintPrice(dishToSearch);
                        break;
                case 3:
                    // Getting user input for updating dish price
                        System.out.println("\nEnter the dish name to update the price:");
                        String dishToUpdate = scanner.nextLine();

                        // Set new price
                        System.out.println("Enter the new price for " + dishToUpdate + ":");
                        double newPrice = scanner.nextDouble();
                        scanner.nextLine();

                        // Update the price of the specified dish
                        bst.update(dishToUpdate, newPrice);

                        // Displaying updated menu items in alphabetical order with prices
                        System.out.println("\nUpdated Menu Items in Alphabetical Order:");
                        bst.inorder();
                        break;
                        
                case 4:
                    // Deleting a menu item
                        System.out.println("\nEnter the dish name to delete from the menu:");
                        String dishToDelete = scanner.nextLine();

                        // Delete specified dish
                        bst.delete(dishToDelete);

                        // Display updated menu items in alphabetical order with prices after deletion
                        System.out.println("\nUpdated Menu Items in Alphabetical Order (after deletion):");
                        bst.inorder();
                        break;
                        
                case 5:
                        System.out.println("Exiting the program.");
                        break;

                default:
                        System.out.println("Invalid choice. Please enter a valid option.");
            }
            } while (menuOperationTypeChoice != 5);
            }
            else if(menuTypeChoice==2){
                menuName="Lunch";
                do{
                // Display options for menu operations
            System.out.println("\n" + menuName + " Menu Operations:");
            System.out.println("1. Add " + menuName.toLowerCase() + " dishes");
            System.out.println("2. Search dishes");
            System.out.println("3. Update dishes");
            System.out.println("4. Delete dishes");
            System.out.println("5. Exit");
            
            menuOperationTypeChoice=scanner.nextInt();
            scanner.nextLine();
            
            switch(menuOperationTypeChoice){
                case 1:
                    // Getting menu items from the user
        System.out.println("Enter menu items (Dish Name and Price). "
                + "Ex:- Pizza 890.00 "
                + " To finish for adding items type 'done' .");
        String input;
        while (!(input = scanner.nextLine()).equalsIgnoreCase("done")) {
            String[] inputParts = input.split(" ");
            if (inputParts.length == 2) {
                String name = inputParts[0];
                try {
                    double price = Double.parseDouble(inputParts[1]);

                    // Inserting into both BSTs
                    lunch.insert(new MenuItem(name, price));
                    pricelunchBST.insert(new MenuItem(name, price));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid price format. Please enter a valid number.");
                }
            } else {
                System.out.println("Invalid input. Please enter Dish Name and Price separated by space."
                        + "Ex:- Pizza 890.00 ");
            }
        }

        // Displaying menu items in alphabetical order with prices
        System.out.println("\nMenu Items in Alphabetical Order:");
        lunch.inorder();

                    break;
                case 2:
                    // Getting user input for searching a menu item
                        System.out.println("\nEnter the dish name to search in the menu:");
                        String dishToSearch = scanner.nextLine();

                        // Searching for the specified dish and printing its price
                        lunch.searchAndPrintPrice(dishToSearch);
                        break;
                case 3:
                    // Getting user input for updating dish price
                        System.out.println("\nEnter the dish name to update the price:");
                        String dishToUpdate = scanner.nextLine();

                        // Set new price
                        System.out.println("Enter the new price for " + dishToUpdate + ":");
                        double newPrice = scanner.nextDouble();
                        scanner.nextLine();

                        // Update the price of the specified dish
                        lunch.update(dishToUpdate, newPrice);

                        // Displaying updated menu items in alphabetical order with prices
                        System.out.println("\nUpdated Menu Items in Alphabetical Order:");
                        lunch.inorder();
                        break;
                        
                case 4:
                    // Deleting a menu item
                        System.out.println("\nEnter the dish name to delete from the menu:");
                        String dishToDelete = scanner.nextLine();

                        // Delete specified dish
                        lunch.delete(dishToDelete);

                        // Display updated menu items in alphabetical order with prices after deletion
                        System.out.println("\nUpdated Menu Items in Alphabetical Order (after deletion):");
                        lunch.inorder();
                        break;
                        
                case 5:
                        System.out.println("Exiting the program.");
                        break;

                default:
                        System.out.println("Invalid choice. Please enter a valid option.");
            }
                }
            while (menuOperationTypeChoice != 5);
                }
            
            else if(menuTypeChoice==3){
                menuName="Dinner";
            do{
                // Display options for menu operations
            System.out.println("\n" + menuName + " Menu Operations:");
            System.out.println("1. Add " + menuName.toLowerCase() + " dishes");
            System.out.println("2. Search dishes");
            System.out.println("3. Update dishes");
            System.out.println("4. Delete dishes");
            System.out.println("5. Exit");
            
            menuOperationTypeChoice=scanner.nextInt();
            scanner.nextLine();
            
            switch(menuOperationTypeChoice){
                case 1:
                    // Getting menu items from the user
        System.out.println("Enter menu items (Dish Name and Price). "
                + "Ex:- Pizza 890.00 "
                + " To finish for adding items type 'done' .");
        String input;
        while (!(input = scanner.nextLine()).equalsIgnoreCase("done")) {
            String[] inputParts = input.split(" ");
            if (inputParts.length == 2) {
                String name = inputParts[0];
                try {
                    double price = Double.parseDouble(inputParts[1]);

                    // Inserting into both BSTs
                    dinner.insert(new MenuItem(name, price));
                    pricedinnerBST.insert(new MenuItem(name, price));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid price format. Please enter a valid number.");
                }
            } else {
                System.out.println("Invalid input. Please enter Dish Name and Price separated by space."
                        + "Ex:- Pizza 890.00 ");
            }
        }

        // Displaying menu items in alphabetical order with prices
        System.out.println("\nMenu Items in Alphabetical Order:");
        dinner.inorder();

                    break;
                case 2:
                    // Getting user input for searching a menu item
                        System.out.println("\nEnter the dish name to search in the menu:");
                        String dishToSearch = scanner.nextLine();

                        // Searching for the specified dish and printing its price
                        dinner.searchAndPrintPrice(dishToSearch);
                        break;
                case 3:
                    // Getting user input for updating dish price
                        System.out.println("\nEnter the dish name to update the price:");
                        String dishToUpdate = scanner.nextLine();

                        // Set new price
                        System.out.println("Enter the new price for " + dishToUpdate + ":");
                        double newPrice = scanner.nextDouble();
                        scanner.nextLine();

                        // Update the price of the specified dish
                        dinner.update(dishToUpdate, newPrice);

                        // Displaying updated menu items in alphabetical order with prices
                        System.out.println("\nUpdated Menu Items in Alphabetical Order:");
                        dinner.inorder();
                        break;
                        
                case 4:
                    // Deleting a menu item
                        System.out.println("\nEnter the dish name to delete from the menu:");
                        String dishToDelete = scanner.nextLine();

                        // Delete specified dish
                        dinner.delete(dishToDelete);

                        // Display updated menu items in alphabetical order with prices after deletion
                        System.out.println("\nUpdated Menu Items in Alphabetical Order (after deletion):");
                        dinner.inorder();
                        break;
                        
                case 5:
                        System.out.println("Exiting the program.");
                        break;

                default:
                        System.out.println("Invalid choice. Please enter a valid option.");
            }
            }
            while (menuOperationTypeChoice != 5);
            }
            else if(menuTypeChoice==4){
                do{
                menuName="Special Package";
                // Display options for menu operations
            System.out.println("\n" + menuName + " Menu Operations:");
            System.out.println("1. Add " + menuName.toLowerCase() + " dishes");
            System.out.println("2. Search dishes");
            System.out.println("3. Update dishes");
            System.out.println("4. Delete dishes");
            System.out.println("5. Exit");
            
            menuOperationTypeChoice=scanner.nextInt();
            scanner.nextLine();
            
            switch(menuOperationTypeChoice){
                case 1:
                    // Getting menu items from the user
        System.out.println("Enter menu items (Dish Name and Price). "
                + "Ex:- Pizza 890.00 "
                + " To finish for adding items type 'done' .");
        String input;
        while (!(input = scanner.nextLine()).equalsIgnoreCase("done")) {
            String[] inputParts = input.split(" ");
            if (inputParts.length == 2) {
                String name = inputParts[0];
                try {
                    double price = Double.parseDouble(inputParts[1]);

                    // Inserting into both BSTs
                    specialpackages.insert(new MenuItem(name, price));
                    price_SP_BST.insert(new MenuItem(name, price));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid price format. Please enter a valid number.");
                }
            } else {
                System.out.println("Invalid input. Please enter Dish Name and Price separated by space."
                        + "Ex:- Pizza 890.00 ");
            }
        }

        // Displaying menu items in alphabetical order with prices
        System.out.println("\nMenu Items in Alphabetical Order:");
        specialpackages.inorder();

                    break;
                case 2:
                    // Getting user input for searching a menu item
                        System.out.println("\nEnter the dish name to search in the menu:");
                        String dishToSearch = scanner.nextLine();

                        // Searching for the specified dish and printing its price
                        specialpackages.searchAndPrintPrice(dishToSearch);
                        break;
                case 3:
                    // Getting user input for updating dish price
                        System.out.println("\nEnter the dish name to update the price:");
                        String dishToUpdate = scanner.nextLine();

                        // Set new price
                        System.out.println("Enter the new price for " + dishToUpdate + ":");
                        double newPrice = scanner.nextDouble();
                        scanner.nextLine();

                        // Update the price of the specified dish
                        specialpackages.update(dishToUpdate, newPrice);

                        // Displaying updated menu items in alphabetical order with prices
                        System.out.println("\nUpdated Menu Items in Alphabetical Order:");
                        specialpackages.inorder();
                        break;
                        
                case 4:
                    // Deleting a menu item
                        System.out.println("\nEnter the dish name to delete from the menu:");
                        String dishToDelete = scanner.nextLine();

                        // Delete specified dish
                        specialpackages.delete(dishToDelete);

                        // Display updated menu items in alphabetical order with prices after deletion
                        System.out.println("\nUpdated Menu Items in Alphabetical Order (after deletion):");
                        specialpackages.inorder();
                        break;
                        
                case 5:
                        System.out.println("Exit");
                        break;

                default:
                        System.out.println("Invalid choice. Please enter a valid option.");
            }
                }
            while (menuOperationTypeChoice != 5);
            }
            else {
                        System.out.println("Invalid choice. Please enter a valid option.");
                    }
            }
            while (menuTypeChoice != 5);
            }
            
        else if(userChoice==2){
                do{
                // Display options for customer operations
            System.out.println("\nCustomer Operations:");
            System.out.println("1. Breakfast menu list");
            System.out.println("2. Lunch menu list");
            System.out.println("3. Dinner menu list");
            System.out.println("4. Special Packages menu list");
            System.out.println("5. Price look up Breakfast menu");
            System.out.println("6. Price look up Lunch menu");
            System.out.println("7. Price look up Dinner menu");
            System.out.println("8. Price look up Special Packages menu");
            System.out.println("9. Exit");
            
            customerOperations = scanner.nextInt();
            scanner.nextLine();
    
            switch(customerOperations){
                case 1:
                    // Displaying menu items in alphabetical order with prices
        System.out.println("\nMenu Items in Alphabetical Order:");
        bst.inorder();
        break;
                case 2:
                    // Displaying menu items in alphabetical order with prices
        System.out.println("\nMenu Items in Alphabetical Order:");
        lunch.inorder();
        break;
                case 3:
                    // Displaying menu items in alphabetical order with prices
        System.out.println("\nMenu Items in Alphabetical Order:");
        dinner.inorder();
        break;
                case 4:
                    // Displaying menu items in alphabetical order with prices
        System.out.println("\nMenu Items in Alphabetical Order:");
        specialpackages.inorder();
        break;
                case 5:
                    // Displaying menu items arcorrding to price 
        System.out.println("\nMenu Items from low price to high price:");
        priceBST.inorder();
        break;
        case 6:
                    // Displaying menu items arcorrding to price 
        System.out.println("\nMenu Items from low price to high price:");
        pricelunchBST.inorder();
        break;
        case 7:
                    // Displaying menu items arcorrding to price 
        System.out.println("\nMenu Items from low price to high price:");
        pricedinnerBST.inorder();break;
        case 8:
                    // Displaying menu items arcorrding to price 
        System.out.println("\nMenu Items from low price to high price:");
        price_SP_BST.inorder();break;
        
        case 9:
                        System.out.println("Exit.");
                        break;

        default:
                        System.out.println("Invalid choice. Please enter a valid option.");
            }
                } while (customerOperations != 9);}
        else {
                //System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (userChoice != 3);

        scanner.close();
        
        if(userChoice == 3){
            System.out.println("Exit from the program.");
            scanner.close();
        }
    }
}
