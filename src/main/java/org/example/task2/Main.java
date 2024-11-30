package org.example.task2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner urlScanner = new Scanner(System.in);
        String url;

        while (true) {
            System.out.print("Enter URL: ");
            url = urlScanner.nextLine();

            try {
                readContent(url);
                break;
            } catch (MalformedURLException e) {
                System.err.println("The wrong URL. Please try again.");
            } catch (Exception e) {
                System.err.println("Unable to access the resource: " + e.getMessage() + ". Please try again.");
            }
        }
    }

    public static void readContent(String urlString) throws Exception {
        URL url = new URL(urlString);


        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int responseCode = connection.getResponseCode();

        if (responseCode == 200) { // HTTP OK
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                System.out.println("Content of the website:");
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } else {
            throw new Exception("HTTP Error: " + responseCode);
        }
    }
}
