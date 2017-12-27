package com.vbvjain.buks.books;

import com.vbvjain.buks.repositories.Book;
import com.vbvjain.buks.repositories.BookRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class IBooksPresenterTest {
    @Mock
    BookRepository bookRepository;

    @Mock
    BooksView booksView;

    BooksPresenter booksPresenter;


    @Before
    public void setUp(){
        booksPresenter = new IBooksPresenter(booksView,bookRepository, Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(schedulers->Schedulers.trampoline());
    }

    @After
    public void cleanup(){
        RxJavaPlugins.reset();
    }

    @Test
    public void shouldHandleNoBooksFound(){
        Mockito.when(bookRepository.getBooksReactively()).thenReturn(Single.just(Collections.<Book>emptyList()));
        booksPresenter.getBooks();
        verify(booksView).displayBooksWithNoBooksFound();
    }

    @Test
    public void shouldHandleError(){
        Mockito.when(bookRepository.getBooksReactively()).thenReturn(Single.<List<Book>>error(new Throwable("d")));
        booksPresenter.getBooks();
        verify(booksView).displayError();
    }

    @Test
    public void shouldPassBookToViewReactively(){
        List<Book> bookList = Arrays.asList(new Book());
        Mockito.when(bookRepository.getBooksReactively()).thenReturn(Single.just(bookList));
        booksPresenter.getBooks();
        verify(booksView).displayBooks(bookList);
    }

}