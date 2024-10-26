import java.util.InputMismatchException;
import java.util.UUID;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Ecommerce {
    static Scanner input = new Scanner(System.in);

    private String name;
    private String address;
    private long phoneNumber;
    private final HashMap<String, Integer> cartItems = new HashMap<>();

    public void registerPage() {
        System.out.print("Enter your name: ");
        this.name = input.next();
        System.out.print("Enter your address: ");
        input.nextLine();
        this.address = input.nextLine();
        this.phoneNumber = getValidPhoneNumber();
        System.out.println("You are successfully registered!");
        displayUserDetails();
    }

    private long getValidPhoneNumber() {
        long number;

        while (true) {

            System.out.print("Enter your valid phone number: ");
            try {
                number = input.nextLong();
                if (String.valueOf(number).length() != 10) {
                    System.out.println("Invalid phone number. It must be a 10-digit number. Please try again.");
                    continue;
                }
                System.out.println("Your phone number is valid.");
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value for the phone number.");
                input.next(); // Clear the invalid input
            }
        }
        return number;
    }

    void loginPage() {
        System.out.print("Enter your name: ");
        this.name = input.next();
        this.phoneNumber = getValidPhoneNumber();
        System.out.println("You are successfully logged in!");
        displayUserDetails();
    }

    void displayUserDetails() {
        System.out.println("\n---------- User Details ----------");
        System.out.println("Name: " + this.name);
        System.out.println("Phone Number: " + this.phoneNumber);
        System.out.println("Address: " + this.address);
        System.out.println("-------------------------------");
        shoppingMenu();
    }

    void shoppingMenu() {
        while (true) {
            System.out.println("\n1. Add items to cart");
            System.out.println("2. View cart");
            System.out.println("3. Remove items from cart");
            System.out.println("4. Proceed to payment");
            System.out.println("5. Logout");
            System.out.print("Select an option: ");

            int choice = getUserChoice();

            switch (choice) {
                case 1: addItemsToCart();
                    break;
                case 2: viewCart();
                    break;
                case 3: removeItemsFromCart();
                    break;
                case 4: payment();
                    return;
                case 5: System.out.println("Logging out...");
                    return;
                default: System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private int getUserChoice() {
        int choice;

        while (true) {
            try {
                choice = input.nextInt();
                return choice;
            }
            catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                input.next();
            }
        }
    }

    void addItemsToCart() {
        System.out.println("Available Items:");

        System.out.println("1. Shoes");
        System.out.println("2. Bag");
        System.out.println("3. Shirt");
        System.out.println("4. Pant");
        System.out.println("5. Perfume");
        System.out.println("6. Watch");
        System.out.println("Select an item by name or number (type 'done' to finish): ");

        while (true) {
            String inputItem = input.next();

            if (inputItem.equalsIgnoreCase("done")) {
                break;
            }

            if (inputItem.matches("\\d+")) {
                int itemNumber = Integer.parseInt(inputItem);
                switch (itemNumber) {

                    case 1: inputItem = "Shoes";
                        break;
                    case 2: inputItem = "Bag";
                        break;
                    case 3: inputItem = "Shirt";
                        break;
                    case 4: inputItem = "Pant";
                        break;
                    case 5: inputItem = "Perfume";
                        break;
                    case 6: inputItem = "Watch";
                        break;
                    default:
                        System.out.println("Invalid item number. Please try again.");
                        continue;
                }
            }

            System.out.print("Enter the quantity of " + inputItem + ": ");
            int quantity = getPositiveInteger();
            if (quantity > 0) {
                cartItems.put(inputItem, cartItems.getOrDefault(inputItem, 0) + quantity);
                System.out.println(quantity + " " + inputItem + " item(s) have been added to your cart.");
            } else {
                System.out.println("Invalid quantity. Please enter a positive number.");
            }
            shoppingMenu();
        }
    }

    private int getPositiveInteger() {
        int quantity;
        while (true) {
            try {
                quantity = input.nextInt();
                if (quantity > 0) {
                    return quantity;
                } else {
                    System.out.println("Quantity must be a positive number. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value for quantity.");
                input.next(); // Clear the invalid input
            }
        }
    }

    void viewCart() {
        if (cartItems.isEmpty()) {
            System.out.println("\n----------------------");
            System.out.println("Your cart is empty.");
            System.out.println("----------------------");
        } else {
            System.out.println("\nItems in your cart:");
            for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
                System.out.println("- " + entry.getKey() + ": " + entry.getValue());
            }
            System.out.println("Total items in cart: " + getTotalItems());
            System.out.println("-------------------------------------------");
        }
        shoppingMenu();
    }

    void removeItemsFromCart() {
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty. Nothing to remove.");
            return;
        }

        System.out.print("Enter the name of the item to remove: ");
        String itemToRemove = input.next();

        if (cartItems.containsKey(itemToRemove)) {
            System.out.print("Enter the quantity to remove: ");
            int quantityToRemove = getPositiveInteger();
            if (quantityToRemove <= cartItems.get(itemToRemove)) {
                cartItems.put(itemToRemove, cartItems.get(itemToRemove) - quantityToRemove);
                if (cartItems.get(itemToRemove) == 0) {
                    cartItems.remove(itemToRemove);
                }
                System.out.println(quantityToRemove + " " + itemToRemove + "(s) have been removed from your cart.");
            } else {
                System.out.println("Invalid quantity. You can only remove up to " + cartItems.get(itemToRemove) + ".");
            }
        } else {
            System.out.println("Item not found in the cart.");
        }
    }

    private int getTotalItems() {
        return cartItems.values().stream().mapToInt(Integer::intValue).sum();
    }

    void payment() {
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty. Add items to cart before proceeding to payment.");
            return;
        }

        System.out.println("Proceeding to payment...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Payment process was interrupted.");
        }
        handleCreditCardPayment();
    }

    private void handleCreditCardPayment() {
        String cardNumber = getValidInput("Enter your credit card number (16 digits): ", this::validateCardNumber);
        String expiryDate = getValidInput("Enter the expiration date (MM/YY): ", this::validateExpiryDate);
        String cvv = getValidInput("Enter the CVV (3 digits): ", this::validateCVV);

        System.out.println("Processing payment...");
        boolean paymentSuccess = simulatePaymentGateway(cardNumber, expiryDate, cvv);

        if (paymentSuccess) {
            String transactionId = UUID.randomUUID().toString();
            System.out.println("Payment successful! Transaction ID: " + transactionId);
            logPaymentDetails(cardNumber, expiryDate, transactionId);
            System.out.println("Thank you for your purchase.");
            System.exit(0);
        } else {
            System.out.println("Payment failed. Please check your card details and try again.");
        }
    }

    private String getValidInput(String prompt, InputValidator validator) {
        String inputValue;
        while (true) {
            System.out.print(prompt);
            inputValue = input.next();
            if (validator.isValid(inputValue)) {
                return inputValue;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    private boolean validateCardNumber(String cardNumber) {
        return cardNumber.matches("\\d{16}");
    }

    private boolean validateExpiryDate(String expiryDate) {
        return expiryDate.matches("(0[1-9]|1[0-2])/[0-9]{2}");
    }

    private boolean validateCVV(String cvv) {
        return cvv.matches("\\d{3}");
    }

    private boolean simulatePaymentGateway(String cardNumber, String expiryDate, String cvv) {
        return Math.random() > 0.2; // Simulating a payment failure 20% of the time
    }

    private void logPaymentDetails(String cardNumber, String expiryDate, String transactionId) {
        System.out.println("Logging payment details...");
        System.out.println("Card Number: " + cardNumber.replaceAll("\\d(?=\\d{4})", "*"));
        System.out.println("Expiration Date: " + expiryDate);
        System.out.println("Transaction ID: " + transactionId);
    }

    public static void main(String[] args) {
        Ecommerce ecommerce = new Ecommerce();
        System.out.println("Welcome to Our Ecommerce Platform!");
        System.out.println("-----------------------------------------");
        System.out.print("Do you want to login or register? (Enter 'login' or 'register'): ");
        String yesOrNo = input.next();
        if (yesOrNo.equalsIgnoreCase("login")) {
            ecommerce.loginPage();
        } else if (yesOrNo.equalsIgnoreCase("register")) {
            ecommerce.registerPage();
        } else {
            System.out.println("Invalid input, try again!");
        }
    }

    interface InputValidator {
        boolean isValid(String input);
    }
}
