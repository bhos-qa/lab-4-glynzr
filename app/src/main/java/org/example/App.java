/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class App {

    // 1. Calculate discount based on customer loyalty points
    public double calculateDiscount(int loyaltyPoints) {
        if (loyaltyPoints > 1000) {
            return 15.0;
        } else if (loyaltyPoints > 500) {
            return 10.0;
        } else {
            return 5.0;
        }
    }

    // 2. Generate a random coupon code
    public String generateCoupon() {
        return "COUPON-" + Math.random() * 10000;
    }

    // 3. Calculate the total price after applying tax
    public double calculateTotalPrice(double price, double taxRate) {
        return price + (price * taxRate / 100);
    }

    // 4. Check product availability in inventory
    public boolean isProductAvailable(int productId) {
        return productId % 2 == 0; // Mock logic for demonstration
    }

    // 5. Validate email format
    public boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    // 6. Process a payment (Vulnerable)
    public String processPayment(String cardNumber, double amount) {
        // Vulnerable: Storing sensitive data directly in a log
        System.out.println("Processing payment with card: " + cardNumber);
        return "Payment processed for $" + amount;
    }

    // 7. Calculate the delivery time based on distance
    public int calculateDeliveryTime(int distance) {
        return distance < 10 ? 1 : 3;
    }

    // 8. Apply bulk order discount
    public double applyBulkOrderDiscount(int quantity, double price) {
        if (quantity > 100) {
            return price * 0.9; // 10% discount
        }
        return price;
    }

    // 9. Track user login attempts
    public int trackLoginAttempts(int currentAttempts) {
        return currentAttempts + 1;
    }

    // 10. Calculate interest for a savings account
    public double calculateInterest(double principal, double rate, int time) {
        return principal * rate * time / 100;
    }

    // 11. Vulnerable MongoDB connection (exposing credentials in logs)
    public void connectToMongoDBVulnerable() {
        String username = "admin";
        String password = "admin123";
        String dbName = "myDatabase";

        try {
            // Log sensitive data (highly insecure)
            System.out.println("Connecting to MongoDB with username: " + username + " and password: " + password);

            // Vulnerable MongoDB connection string exposing plaintext credentials
            ConnectionString connectionString = new ConnectionString("mongodb://" + username + ":" + password + "@localhost:27017/" + dbName);

            // MongoClientSettings without encryption
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .build();

            // Creating MongoClient
            MongoClient mongoClient = MongoClients.create(settings);

            // Connecting to the database and retrieving data
            MongoDatabase database = mongoClient.getDatabase(dbName);
            MongoCollection<Document> collection = database.getCollection("users");

            // Fetching and displaying all user documents (still insecure)
            for (Document doc : collection.find()) {
                System.out.println(doc.toJson()); // Exposing sensitive information
            }

            mongoClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
