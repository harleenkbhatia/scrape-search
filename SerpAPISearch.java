package org.example;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SerpAPISearch {

    private static final String API_KEY = "80dd6ee54149fad6c28d0ec66af4261a63daf6742438cbb236602d5af0b56886";  // Replace with your API key

    public static void main(String[] args) throws IOException {
        // Take user input for search query
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter search query: ");
        String query = scanner.nextLine();
        scanner.close();

        // Perform Google search via SerpAPI
        String apiUrl = "https://serpapi.com/search.json?q=" + query + "&google_domain=google.com&num=10&api_key=" + API_KEY;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(apiUrl).build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            String jsonData = response.body().string();
            JSONObject jsonObject = new JSONObject(jsonData);

            // Parse the search results
            JSONArray results = jsonObject.getJSONArray("organic_results");

            // Writing search results to output.txt
            FileWriter fw = new FileWriter("output.txt", true);

            System.out.println("Search results:");
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                String link = result.getString("link");
                String title = result.getString("title");
                System.out.println(title + ": " + link);
                fw.write(title + ": " + link + "\n");
            }
            fw.close();
            System.out.println("Results saved to output.txt");
        } else {
            System.out.println("Failed to fetch results from SerpAPI");
        }
    }
}
