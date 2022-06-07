package com.tanthanh.bookservicev1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanthanh.bookservicev1.model.BookRequestModel;
import com.tanthanh.bookservicev1.service.IBookService;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

	@Autowired
	private IBookService iBookService;
	
	@PostMapping
	public BookRequestModel addBook(@RequestBody BookRequestModel model) {
	 
		return iBookService.addBook(model);
	}
	@PutMapping
	public BookRequestModel updateBook(@RequestBody BookRequestModel model) {
		return iBookService.updateBook(model);
	}
	@DeleteMapping("/{bookId}")
	public Boolean deleteBook(@PathVariable String bookId) {
		return iBookService.deleteBook(bookId);
	}
	@GetMapping("/{bookId}")
	public BookRequestModel getDetailBook(@PathVariable String bookId) {
		return iBookService.getBooksDetail(bookId);
	}
	@PutMapping("/updateBookStatus")
	public Boolean updateBookStatus(@RequestBody BookRequestModel model) {
		return iBookService.updateBookStatus(model);
	}
	@GetMapping
	public List<BookRequestModel> getAllBook(){
		return iBookService.getAll();
	}
}
