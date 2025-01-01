import java.util.*;


class Product {
    private String name;
    private double price;
    private int stock;

    public Product(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void reduceStock(int quantity) {
        this.stock -= quantity;
    }
}

// Cart Management - Singleton Pattern
class Cart {
    private static Cart instance;
    private List<Product> items = new ArrayList<>();

    private Cart() {}

    public static Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    public void addItem(Product product) {
        items.add(product);
        System.out.println(product.getName() + " added to cart.");
    }

    public void removeItem(Product product) {
        items.remove(product);
        System.out.println(product.getName() + " removed from cart.");
    }

    public List<Product> getItems() {
        return items;
    }

    public void viewCart() {
        System.out.println("Cart Contents:");
        for (Product product : items) {
            System.out.println(product.getName() + " - $" + product.getPrice());
        }
    }
}

// Payment Processor - Strategy Pattern
interface PaymentStrategy {
    void pay(double amount);
}

class CreditCardPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " using Credit Card.");
    }
}

class PayPalPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " using PayPal.");
    }
}

// Notification - Observer Pattern
interface Observer {
    void update(String message);
}

class Customer implements Observer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println(name + ", Notification: " + message);
    }
}

class NotificationService {
    private List<Observer> observers = new ArrayList<>();

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}

// Product Recommendation - Factory Pattern
class RecommendationEngine {
    public static List<Product> getRecommendations(Product purchasedProduct) {
        System.out.println("Recommendations based on " + purchasedProduct.getName() + ":");
        // Dummy recommendations
        return Arrays.asList(new Product("Accessory 1", 19.99, 100), new Product("Accessory 2", 29.99, 50));
    }
}

// Order Processing System
public class ECommerceSystem {
    public static void main(String[] args) {
        // Initialize Products
        Product product1 = new Product("Laptop", 999.99, 10);
        Product product2 = new Product("Mouse", 49.99, 20);

        // Cart Management
        Cart cart = Cart.getInstance();
        cart.addItem(product1);
        cart.addItem(product2);
        cart.viewCart();

        // Order Placement
        double totalAmount = 0;
        for (Product product : cart.getItems()) {
            if (product.getStock() > 0) {
                product.reduceStock(1); // Reducing stock by 1 for simplicity
                totalAmount += product.getPrice();
            } else {
                System.out.println(product.getName() + " is out of stock.");
            }
        }

        // Apply Payment Strategy
        PaymentStrategy paymentStrategy = new CreditCardPayment();
        paymentStrategy.pay(totalAmount);

        // Notifications
        NotificationService notificationService = new NotificationService();
        Customer customer = new Customer("John Doe");
        notificationService.registerObserver(customer);
        notificationService.notifyObservers("Your order has been placed successfully.");

        // Product Recommendation
        List<Product> recommendations = RecommendationEngine.getRecommendations(product1);
        for (Product recommendation : recommendations) {
            System.out.println(recommendation.getName() + " - $" + recommendation.getPrice());
        }
    }
}
