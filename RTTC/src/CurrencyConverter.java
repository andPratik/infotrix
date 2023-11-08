
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;


import org.json.JSONObject;

public class CurrencyConverter {
	private final String apiKey;
    private final String apiUrl;

    public CurrencyConverter(String apiKey) {
        this.apiKey = "7467cce9449f12402bb7a0ed";
        this.apiUrl = "https://v6.exchangerate-api.com/v6/7467cce9449f12402bb7a0ed/latest/USD";
    }

    public double convert(String fromCurrency, String toCurrency, double amount) {
        try {
            URL url = new URL(apiUrl + "?from=" + fromCurrency + "&to=" + toCurrency);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse the JSON response and extract the exchange rate
                double exchangeRate = parseExchangeRate(response.toString(),toCurrency);
                return amount * exchangeRate;
            } else {
                System.out.println("Error: Unable to fetch exchange rate data.");
                return -1.0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1.0;
        }
    }

    private double parseExchangeRate(String jsonResponse,String toCurrency) {
    	    try {
    	        // Print the JSON response for debugging purposes
    	        System.out.println("JSON Response:\n" + jsonResponse);

    	        // Parse the JSON response
    	        JSONObject json = new JSONObject(jsonResponse);

    	        // Extract the exchange rate from the JSON object
    	        if (json.has("conversion_rates")) {
    	            JSONObject conversionRates = json.getJSONObject("conversion_rates");

    	            if (conversionRates.has(toCurrency)) {
    	                double exchangeRate = conversionRates.getDouble(toCurrency);
    	                return exchangeRate;
    	            } else {
    	                System.out.println("Error: Exchange rate for USD not found in the JSON response.");
    	                return -1.0;
    	            }
    	        } else {
    	            System.out.println("Error: 'conversion_rates' key not found in the JSON response.");
    	            return -1.0;
    	        }
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	        return -1.0;
    	    }
    }   
    	    public static class app {

    	    	public static void main(String[] args) {
    	    		// TODO Auto-generated method stub
    	    		Scanner scanner = new Scanner(System.in);
    	            CurrencyConverter currencyConverter = new CurrencyConverter("7467cce9449f12402bb7a0ed");
    	            List<String> favoriteCurrencies = new ArrayList<>();

    	            System.out.println("Welcome to the Currency Converter!");

    	       
    	            while (true) {
    	                System.out.println("\nOptions:");
    	                System.out.println("1. Convert Currency");
    	                System.out.println("2. Add Favorite Currency");
    	                System.out.println("3. View Favorite Currencies");

    	                System.out.print("Enter your choice: ");
    	                int choice = scanner.nextInt();

    	                switch (choice) {
    	                    case 1:
    	                        System.out.print("Enter the source currency code (e.g., USD): ");
    	                        String fromCurrency = scanner.next();

    	                        System.out.print("Enter the target currency code (e.g., AED): ");
    	                        String toCurrency = scanner.next();

    	                        System.out.print("Enter the amount to convert: ");
    	                       
    	                        double amount = scanner.nextDouble();

    	                        double convertedAmount = currencyConverter.convert(fromCurrency, toCurrency, amount);

    	                        if (convertedAmount >= 0) {
    	                            System.out.println(amount + " " + fromCurrency + " is equivalent to " + convertedAmount + " " + toCurrency);
    	                        } else {
    	                            System.out.println("Currency conversion failed.");
    	                        }
    	                        break;

    	                       
    	                    case 2:
    	                        System.out.print("Enter the currency code you want to add to favorites (e.g., USD): ");
    	                        String favoriteCurrency = scanner.next();

    	                        // Add the favorite currency to the list (you'll need to initialize the list outside the loop)
    	                        favoriteCurrencies.add(favoriteCurrency);
    	                        
    	                        System.out.println(favoriteCurrency + " has been added to your favorite currencies..!");
    	                        break;
    	                    case 3:
    	              
    	                        if (favoriteCurrencies.isEmpty()) {
    	                            System.out.println("You have no favorite currencies yet.");
    	                        } else {
    	                            System.out.println("Your favorite currencies:");
    	                            for (String currency : favoriteCurrencies) {
    	                                System.out.println(currency);
    	                            }
    	                        }
    	                        break;

    	                    case 4:
    	                        System.out.println("Goodbye!");
    	                        scanner.close();
    	                        System.exit(0);
    	                        break;
    	                    default:
    	                        System.out.println("Invalid choice. Please try again.");
    	                }
    	            }
    	    	}
    	    }
    }
