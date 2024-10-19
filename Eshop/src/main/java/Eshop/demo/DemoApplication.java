package Eshop.demo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
				String uri = "mongodb://localhost:27017";

				try (MongoClient mongoClient = MongoClients.create(uri)) {
					MongoDatabase database = mongoClient.getDatabase("Eshop");
					// Optional: print a message to confirm connection
					System.out.println("Connected to MongoDB at " + uri);
				} catch (Exception e) {
					// Handle any exceptions
					System.err.println("Failed to connect to MongoDB: " + e.getMessage());
				}
			}
		}





