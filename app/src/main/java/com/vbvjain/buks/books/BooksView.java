package com.vbvjain.buks.books;


import com.vbvjain.buks.repositories.Book;

import java.util.List;

public interface BooksView {
    void displayBooks(List<Book> bookList);
    void displayBooksWithNoBooksFound();

    void displayError();
}
