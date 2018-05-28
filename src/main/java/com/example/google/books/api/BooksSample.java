/*
 * Copyright (c) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.google.books.api;

import com.example.model.Author;
import com.example.model.Book;
import com.example.model.BookInterface;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.Books.Volumes.List;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;

import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.LinkedList;

/**
 * A sample application that demonstrates how Google Books Client Library for
 * Java can be used to query Google Books. It accepts queries in the command
 * line, and prints the results to the console.
 *
 * $ java com.google.sample.books.BooksSample [--author|--isbn|--title] "<query>"
 *
 * Please start by reviewing the Google Books API documentation at:
 * http://code.google.com/apis/books/docs/getting_started.html
 */
public class BooksSample {

  /**
   * Be sure to specify the name of your application. If the application name is {@code null} or
   * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
   */
  private static final String APPLICATION_NAME = "bookrec";
  
  private static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance();
  private static final NumberFormat PERCENT_FORMATTER = NumberFormat.getPercentInstance();

  protected static java.util.List<BookInterface> queryGoogleBooks(JsonFactory jsonFactory, String query) throws Exception {
    ClientCredentials.errorIfNotSpecified();

    java.util.List<BookInterface> bookList = new LinkedList<>();
    
    // Set up Books client.
    final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
        .setApplicationName(APPLICATION_NAME)
        .setGoogleClientRequestInitializer(new BooksRequestInitializer(ClientCredentials.API_KEY))
        .build();
    // Set query string and filter only Google eBooks.
    System.out.println("Query: [" + query + "]");
    List volumesList = books.volumes().list(query);
    volumesList.setFilter("ebooks");

    // Execute the query.
    Volumes volumes = volumesList.execute();
    if (volumes.getTotalItems() == 0 || volumes.getItems() == null) {
      System.out.println("No matches found.");
      return null;
    }

    // Output results.
    for (Volume volume : volumes.getItems()) {
      Book book = new Book();
      Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();
      String imageLinks = volume.getVolumeInfo().getImageLinks().getThumbnail();
      book.setBookImage(imageLinks);
      System.out.println(imageLinks);

      Volume.SaleInfo saleInfo = volume.getSaleInfo();
      System.out.println("==========");
      // Title.
      System.out.println("Title: " + volumeInfo.getTitle());
      book.setTitle(volumeInfo.getTitle());
      // Author(s).
      java.util.List<String> authors = volumeInfo.getAuthors();
      if (authors != null && !authors.isEmpty()) {
        System.out.print("Author(s): ");
        for (int i = 0; i < authors.size(); ++i) {
          System.out.print(authors.get(i));
          if (i < authors.size() - 1) {
            System.out.print(", ");
          }
        }
        System.out.println();
      }

      // Description (if any).
      if (volumeInfo.getDescription() != null && volumeInfo.getDescription().length() > 0) {
        System.out.println("Description: " + volumeInfo.getDescription());
      }
      // Ratings (if any).
      if (volumeInfo.getRatingsCount() != null && volumeInfo.getRatingsCount() > 0) {
        int fullRating = (int) Math.round(volumeInfo.getAverageRating().doubleValue());
        System.out.print("User Rating: ");
        for (int i = 0; i < fullRating; ++i) {
          System.out.print("*");
        }
        System.out.println(" (" + volumeInfo.getRatingsCount() + " rating(s))");
      }
      // Price (if any).
      if (saleInfo != null && "FOR_SALE".equals(saleInfo.getSaleability())) {
        double save = saleInfo.getListPrice().getAmount() - saleInfo.getRetailPrice().getAmount();
        if (save > 0.0) {
          System.out.print("List: " + CURRENCY_FORMATTER.format(saleInfo.getListPrice().getAmount())
              + "  ");
        }
        System.out.print("Google eBooks Price: "
            + CURRENCY_FORMATTER.format(saleInfo.getRetailPrice().getAmount()));
        if (save > 0.0) {
          System.out.print("  You Save: " + CURRENCY_FORMATTER.format(save) + " ("
              + PERCENT_FORMATTER.format(save / saleInfo.getListPrice().getAmount()) + ")");
        }
        System.out.println();
      }
      // Access status.
      String accessViewStatus = volume.getAccessInfo().getAccessViewStatus();
      String message = "Additional information about this book is available from Google eBooks at:";
      if ("FULL_PUBLIC_DOMAIN".equals(accessViewStatus)) {
        message = "This public domain book is available for free from Google eBooks at:";
      } else if ("SAMPLE".equals(accessViewStatus)) {
        message = "A preview of this book is available from Google eBooks at:";
      }
      System.out.println(message);
      // Link to Google eBooks.
      System.out.println(volumeInfo.getInfoLink());
    }

    System.out.println("==========");
    System.out.println(
        volumes.getTotalItems() + " total results at http://books.google.com/ebooks?q="
        + URLEncoder.encode(query, "UTF-8"));

    return bookList;
  }
}
