package com.tanthanh.bookservicev1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tanthanh.bookservicev1.data.Book;
import com.tanthanh.bookservicev1.data.BookRepository;
import com.tanthanh.bookservicev1.model.BookRequestModel;

@Service
public class BookService implements IBookService{

	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public BookRequestModel addBook(BookRequestModel model) {
		Book book = new Book();
		BeanUtils.copyProperties(model, book);
		book.setIsReady(true);
		book.setBookId(UUID.randomUUID().toString());
		BookRequestModel bookModel = new BookRequestModel();
		BeanUtils.copyProperties(bookRepository.save(book), bookModel);
		return bookModel;
	}

	@Override
	public BookRequestModel updateBook(BookRequestModel model) {
		Optional<Book> book = bookRepository.findById(model.getBookId());
		if(book.isPresent()) {
			book.get().setAuthor(model.getAuthor());
			book.get().setName(model.getName());
			book.get().setIsReady(model.getIsReady());
		}
		BookRequestModel bookModel = new BookRequestModel();
		BeanUtils.copyProperties(bookRepository.save(book.get()), bookModel);
		return bookModel;
	}

	@Override
	public Boolean deleteBook(String bookId) {
		try {
			bookRepository.deleteById(bookId);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	@Override
	public BookRequestModel getBooksDetail(String bookId) {
		BookRequestModel bookModel = new BookRequestModel();
		BeanUtils.copyProperties(bookRepository.findById(bookId).get(), bookModel);
		return bookModel;
	}

	@Override
	public Boolean updateBookStatus(BookRequestModel model) {
		Optional<Book> book = bookRepository.findById(model.getBookId());
		
		if(book.isPresent()) {
			book.get().setIsReady(model.getIsReady());
			bookRepository.save(book.get());
			return true;
		}else {
			return false;
		}
		
	}

	@Override
	public List<BookRequestModel> getAll() {
		List<Book> listEntity = bookRepository.findAll();
		List<BookRequestModel> listModel = new ArrayList<>();
		listEntity.stream().forEach(s ->{
			BookRequestModel model = new BookRequestModel();
			BeanUtils.copyProperties(s, model);
			listModel.add(model);
		});
		return listModel;
	}

}
