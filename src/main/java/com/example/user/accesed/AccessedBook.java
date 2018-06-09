package com.example.user.accesed;

import org.springframework.stereotype.Component;

@Component
public class AccessedBook {

    public String bookUrl;

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public String getBookUrl() {
        return bookUrl;
    }
}
