package com.vbvjain.buks.books;


import com.vbvjain.buks.repositories.Book;
import com.vbvjain.buks.repositories.BookRepository;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.realm.Realm;
import io.realm.RealmResults;

public class IBookRepo implements BookRepository {

    private Realm realm;

    public IBookRepo() {
        realm = Realm.getDefaultInstance();
        //dummy book insertion
        insertNewBook();
    }

    //dummy book
    private void insertNewBook() {
        Book book = new Book();
        book.setId(1);
        book.setName("Refactor Android App series overview");
        realm.beginTransaction();
        realm.copyToRealm(book);
        realm.commitTransaction();
    }

    public List<Book> getBooks() {
        RealmResults<Book> results = realm.where(Book.class).findAll();
        return realm.copyFromRealm(results);
    }

    @Override
    public Single<List<Book>> getBooksReactively() {
        return Single.fromCallable(new Callable<List<Book>>() {
            @Override
            public List<Book> call() throws Exception {
                return getBooks();
            }
        });
    }
}
