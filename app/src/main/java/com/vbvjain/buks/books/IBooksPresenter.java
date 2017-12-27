package com.vbvjain.buks.books;


import com.vbvjain.buks.repositories.Book;
import com.vbvjain.buks.repositories.BookRepository;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class IBooksPresenter implements BooksPresenter {

    private BooksView booksView;
    private BookRepository bookRepository;
    private Scheduler mainScheduler;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    public IBooksPresenter(BooksView booksView, BookRepository bookRepository,Scheduler mainScheduler) {
        this.booksView = booksView;
        this.bookRepository = bookRepository;
        this.mainScheduler = mainScheduler;
    }

    @Override
    public void getBooks() {
        compositeDisposable.add(bookRepository.getBooksReactively()
                .subscribeOn(Schedulers.io())
                .observeOn(mainScheduler)
                .subscribeWith(new DisposableSingleObserver<List<Book>>() {
            @Override
            public void onSuccess(List<Book> books) {
                if (books.isEmpty()) booksView.displayBooksWithNoBooksFound();
                else booksView.displayBooks(books);
            }

            @Override
            public void onError(Throwable e) {
                booksView.displayError();
            }
        }));
    }

    /**
     * Method to call by the view when the view is getting destroyed to avoid memory leak
     */
    @Override
    public void unsubscribe(){
        compositeDisposable.clear();
    }


}
