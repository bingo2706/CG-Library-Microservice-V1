package com.tanthanh.bookservicev1.service;

import java.util.List;

import com.tanthanh.bookservicev1.model.BookRequestModel;


public interface IBookService {
	BookRequestModel addBook(BookRequestModel model);
	BookRequestModel updateBook(BookRequestModel model);
	Boolean deleteBook(String bookId);
	BookRequestModel getBooksDetail(String bookId);
	Boolean updateBookStatus(BookRequestModel model);
	List<BookRequestModel> getAll();
}
