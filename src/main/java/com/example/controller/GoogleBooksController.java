package com.example.controller;

import com.example.google.books.api.ClientCredentials;
import com.example.google.books.api.CreateBookSearchQuery;
import com.example.model.Author;
import com.example.model.Book;
import com.example.model.BookCategory;
import com.example.service.AuthorService;
import com.example.service.BookCategoryService;
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
import java.util.*;

@RestController
@RequestMapping("api/v1/explore")
public class GoogleBooksController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookCategoryService bookCategoryService;

    /**
     * Be sure to specify the name of your application. If the application name is {@code null} or
     * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
     */
    private static final String APPLICATION_NAME = "bookrec";

    private static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance();
    private static final NumberFormat PERCENT_FORMATTER = NumberFormat.getPercentInstance();

    public List<Book> getBooks(String queryTitle, String queryAuthor) {
        ClientCredentials.errorIfNotSpecified();

        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        java.util.List<Book> bookList = new LinkedList<>();


        // Set up Books client.
        try {
            final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
                    .setApplicationName(APPLICATION_NAME)
                    .setGoogleClientRequestInitializer(new BooksRequestInitializer(ClientCredentials.API_KEY))
                    .build();

            String query = "";

            if (queryAuthor != null && queryTitle != null) {

                java.util.List<Book> bookListAuthor = new LinkedList<>();
                java.util.List<Book> bookListTitle = new LinkedList<>();

                bookListAuthor = search(queryAuthor, books);
                bookListTitle = search(queryTitle, books);

                bookList.addAll(bookListAuthor.subList(0, bookListAuthor.size()/2));
                bookList.addAll(bookListTitle.subList(0, bookListTitle.size()/2));


            } else {
                query = (queryAuthor != null) ? queryAuthor : queryTitle;
                bookList = search(query, books);
            }

        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bookList;
    }


    public List<Book> search(String query, Books books) throws IOException {

        java.util.List<Book> bookList = new LinkedList<>();

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
        boolean enoughelements = false;

        for (Volume volume : volumes.getItems()) {
            if (!enoughelements) {
                Book book = new Book();
                Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();

                Book bookToBefound = bookService.getBookByUrl(volume.getSelfLink());

                if (bookToBefound == null) {
                    book.setApiUrl(volume.getSelfLink());

                    if(volume.getVolumeInfo().getImageLinks()!= null) {
                        if (volume.getVolumeInfo().getImageLinks().getThumbnail() != null) {
                            String imageLinks = volume.getVolumeInfo().getImageLinks().getThumbnail();
                            book.setBookImage(imageLinks);
                            System.out.println(imageLinks);
                        }
                    }

                    // Title.
                    System.out.println("Title: " + volumeInfo.getTitle());
                    book.setTitle(volumeInfo.getTitle());

                    //Authors
                    Set<Author> bookAuthors = new LinkedHashSet<>();
                    java.util.List<String> authors = volumeInfo.getAuthors();
                    if (authors != null && !authors.isEmpty()) {
                        System.out.print("Author(s): ");
                        for (int i = 0; i < authors.size(); ++i) {
                            System.out.print(authors.get(i));
                            if (i < authors.size() - 1) {
                                System.out.print(", ");
                            }

                            Author authorToBeFound = authorService.getAuthorByName(authors.get(i));
                            if (authorToBeFound == null) {

                                Author author = new Author();
                                author.setName(authors.get(i));

                                authorService.addAuthor(author);
                                Author authorToBeAddedToBook = authorService.getAuthorByName(authors.get(i));
                                bookAuthors.add(authorToBeAddedToBook);
                            } else {
                                bookAuthors.add(authorToBeFound);
                            }

                        }
                        System.out.println();
                    }

                    // Description (if any).
                    if (volumeInfo.getDescription() != null && volumeInfo.getDescription().length() > 0) {
                        System.out.println("Description: " + volumeInfo.getDescription());
                        book.setDescription(volumeInfo.getDescription());
                    }


                    List<String> categories = volumeInfo.getCategories();
                    Set<BookCategory> bookCategories = new LinkedHashSet<>();
                    if (categories != null) {
                        for (String it : categories) {
                            BookCategory categoryToBeFound = bookCategoryService.getCategoryByName(it);

                            if (categoryToBeFound == null) {
                                BookCategory bookCategory = new BookCategory();
                                bookCategory.setCategoryName(it);
                                bookCategoryService.addBookCategory(bookCategory);
                                bookCategories.add(bookCategory);
                            } else
                                bookCategories.add(categoryToBeFound);

                        }
                        book.setCategories(bookCategories);
                    }

                    book.setAuthors(bookAuthors);
                    bookService.addBook(book);
                    System.out.println("Book added");
                    bookList.add(book);

                    if (bookList.size() > 15) enoughelements = true;
                } else {
                    book.setApiUrl(volume.getSelfLink());
                    System.out.println("Book already exists");

                    if(volume.getVolumeInfo().getImageLinks()!= null) {
                        if (volume.getVolumeInfo().getImageLinks().getThumbnail() != null) {
                            String imageLinks = volume.getVolumeInfo().getImageLinks().getThumbnail();
                            book.setBookImage(imageLinks);
                            System.out.println(imageLinks);
                        }
                    }

                    // Title.
                    System.out.println("Title: " + volumeInfo.getTitle());
                    book.setTitle(volumeInfo.getTitle());

                    //Authors
                    Set<Author> bookAuthors = new LinkedHashSet<>();
                    java.util.List<String> authors = volumeInfo.getAuthors();
                    if (authors != null && !authors.isEmpty()) {
                        System.out.print("Author(s): ");
                        for (int i = 0; i < authors.size(); ++i) {
                            System.out.print(authors.get(i));
                            if (i < authors.size() - 1) {
                                System.out.print(", ");
                            }

                            Author authorToBeFound = authorService.getAuthorByName(authors.get(i));
                            bookAuthors.add(authorToBeFound);
                        }
                        System.out.println();
                    }

                    // Description (if any).
                    if (volumeInfo.getDescription() != null && volumeInfo.getDescription().length() > 0) {
                        System.out.println("Description: " + volumeInfo.getDescription());
                        book.setDescription(volumeInfo.getDescription());
                    }


                    List<String> categories = volumeInfo.getCategories();
                    Set<BookCategory> bookCategories = new LinkedHashSet<>();
                    if (categories != null) {
                        for (String it : categories) {
                            BookCategory categoryToBeFound = bookCategoryService.getCategoryByName(it);
                            bookCategories.add(categoryToBeFound);

                        }
                        book.setCategories(bookCategories);
                    }

                    book.setAuthors(bookAuthors);
                    System.out.println("Book already in the database");
                    System.out.println(new Date());
                    bookList.add(book);

                    if (bookList.size() > 12) enoughelements = true;

                }
            }
        }

        return bookList;
    }

/*    @RequestMapping(value = "byisbn/{isbn}", method = RequestMethod.GET)
    public List<Book> getBooksByISBN(@PathVariable String isbn) {
        List<Book> bookList = new LinkedList<Book>();
        String query = CreateBookSearchQuery.getSearchQuery("--isbn", isbn);
        return this.getBooks(query);
    }*/

    @RequestMapping(value = "bytitle/{title}", method = RequestMethod.GET)
    public List<Book> getBooksByTitle(@PathVariable String title) {
        String query = CreateBookSearchQuery.getSearchQuery("--title", title);
        return this.getBooks(query, null);
    }

    @RequestMapping(value = "byauthor/{author}", method = RequestMethod.GET)
    public List<Book> getBooksByAuthor(@PathVariable String author) {
        String query = CreateBookSearchQuery.getSearchQuery("--author", author);
        return this.getBooks(null, query);
    }

    @RequestMapping(value = "by/{searchParam}", method = RequestMethod.GET)
    public List<Book> getAllBooks(@PathVariable String searchParam) {
        String queryTitle = CreateBookSearchQuery.getSearchQuery("--author", searchParam);
        String queryAuthor = CreateBookSearchQuery.getSearchQuery("--title", searchParam);
        return this.getBooks(queryTitle, queryAuthor);
    }
}
