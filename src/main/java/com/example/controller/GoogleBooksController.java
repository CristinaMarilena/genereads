package com.example.controller;

import com.example.google.books.api.ClientCredentials;
import com.example.google.books.api.CreateBookSearchQuery;
import com.example.model.Author;
import com.example.model.Book;
import com.example.model.BookGenre;
import com.example.model.BookInterface;
import com.example.service.AuthorService;
import com.example.service.BookGenreService;
import com.example.service.BookService;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("api/v1/explore")
public class GoogleBooksController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookGenreService bookGenreService;

    /**
     * Be sure to specify the name of your application. If the application name is {@code null} or
     * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
     */
    private static final String APPLICATION_NAME = "bookrec";

    private static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance();
    private static final NumberFormat PERCENT_FORMATTER = NumberFormat.getPercentInstance();

    public List<Book> getBooks(String query){
        ClientCredentials.errorIfNotSpecified();

        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        java.util.List<Book> bookList = new LinkedList<>();

        // Set up Books client.
        try {
            final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
                    .setApplicationName(APPLICATION_NAME)
                    .setGoogleClientRequestInitializer(new BooksRequestInitializer(ClientCredentials.API_KEY))
                    .build();

            // Set query string and filter only Google eBooks.
            System.out.println("Query: [" + query + "]");
            Books.Volumes.List volumesList = books.volumes().list(query);
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

                // Title.
                System.out.println("Title: " + volumeInfo.getTitle());
                book.setTitle(volumeInfo.getTitle());

                //Authors
                java.util.List<String> authors = volumeInfo.getAuthors();
                if (authors != null && !authors.isEmpty()) {
                    System.out.print("Author(s): ");
                    for (int i = 0; i < authors.size(); ++i) {
                        System.out.print(authors.get(i));
                        if (i < authors.size() - 1) {
                            System.out.print(", ");
                        }

                        Author author = new Author();
                        author.setName(authors.get(i));

                        authorService.addAuthor(author);

                    }
                    System.out.println();
                }

                // Description (if any).
                if (volumeInfo.getDescription() != null && volumeInfo.getDescription().length() > 0) {
                    System.out.println("Description: " + volumeInfo.getDescription());
                    book.setDescription(volumeInfo.getDescription());
                }

                book.setApiUrl(volume.getSelfLink());

                List<String> genres = volumeInfo.getCategories();
                for(String it:genres){
                    BookGenre bookGenre = new BookGenre();
                    bookGenre.setGenreName(it);
                    bookGenreService.addBookGenre(bookGenre);
                }

                bookService.addBook(book);
            }

        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bookList;
    }

    @RequestMapping(value = "byisbn/{isbn}", method = RequestMethod.GET)
    public List<Book> get(@PathVariable String isbn){
        List<Book> bookList = new LinkedList<Book>();
        String query = CreateBookSearchQuery.getSearchQuery("--isbn", isbn);
        return this.getBooks(query);
    }
}
