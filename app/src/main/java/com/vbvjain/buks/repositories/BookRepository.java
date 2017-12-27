package com.vbvjain.buks.repositories;


import java.util.List;

import io.reactivex.Single;


public interface BookRepository
{

    Single<List<Book>> getBooksReactively();
}
