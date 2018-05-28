package com.example.google.books.api;

public class CreateBookSearchQuery {

    public static String getSearchQuery(String searchType, String searchParam){

        try {

            String[] myQuery = new String[2];
            myQuery[0] = searchType;
            myQuery[1] = searchParam;

            if (myQuery.length == 0) {
                System.err.println("Usage: BooksSample [--author|--isbn|--title] \"<query>\"");
                System.exit(1);
            }
            // Parse command line parameters into a query.
            // Query format: "[<author|isbn|intitle>:]<query>"
            String prefix = null;
            String query = "";
            for (String it : myQuery) {
                if ("--author".equals(it)) {
                    prefix = "inauthor:";
                } else if ("--isbn".equals(it)) {
                    prefix = "isbn:";
                } else if ("--title".equals(it)) {
                    prefix = "intitle:";
                } else if (it.startsWith("--")) {
                    System.err.println("Unknown argument: " + it);
                    System.exit(1);
                } else {
                    query = it;
                }
            }
            if (prefix != null) {
                query = prefix + query;
            }
                return query;

        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.exit(0);
        return null;
    }
}
