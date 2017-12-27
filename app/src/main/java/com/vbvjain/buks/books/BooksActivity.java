package com.vbvjain.buks.books;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.vbvjain.buks.R;
import com.vbvjain.buks.repositories.Book;
import com.vbvjain.buks.repositories.BookRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class BooksActivity extends AppCompatActivity implements BooksView {
    BooksPresenter booksPresenter;
    BookRepository bookRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        bookRepository = new IBookRepo();
        booksPresenter = new IBooksPresenter(this,bookRepository, AndroidSchedulers.mainThread());
        booksPresenter.getBooks();
    }


    @Override
    public void displayBooks(List<Book> bookList) {
        TextView  t = (TextView) findViewById(R.id.txtView);
        t.setText(bookList.toString());

    }

    @Override
    public void displayBooksWithNoBooksFound() {
        ((TextView)findViewById(R.id.txtView)).setText("No Books Found");

    }

    @Override
    public void displayError() {
        Toast.makeText(this, "Cant access DB", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        booksPresenter.unsubscribe();
        super.onStop();

    }
}
