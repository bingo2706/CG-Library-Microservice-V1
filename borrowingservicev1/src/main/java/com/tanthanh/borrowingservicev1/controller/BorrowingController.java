package com.tanthanh.borrowingservicev1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanthanh.borrowingservicev1.model.BorrowRequestModel;
import com.tanthanh.borrowingservicev1.service.IBorrowingService;

@RestController
@RequestMapping("/api/v1/borrowing")
public class BorrowingController {

	@Autowired
	private IBorrowingService borrowingService;
	
	@PostMapping
	public BorrowRequestModel addBorrow(@RequestBody BorrowRequestModel model) {
		return borrowingService.addBookBorrow(model);
	}
	@PutMapping("/{employeeId}/{bookId}")
	public BorrowRequestModel updateBookReturn(@PathVariable String employeeId,@PathVariable String bookId) {
		return borrowingService.updateBookReturn(bookId, employeeId);
	}
	@GetMapping("/{employeeId}")
	public List<BorrowRequestModel> getBorrowingByEmployee(@PathVariable String employeeId){
		return borrowingService.getBorrowingByEmployee(employeeId);
	}
}
