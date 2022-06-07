package com.tanthanh.borrowingservicev1.service;

import java.util.List;

import com.tanthanh.borrowingservicev1.model.BorrowRequestModel;

public interface IBorrowingService {
	BorrowRequestModel addBookBorrow(BorrowRequestModel model);
	BorrowRequestModel updateBookReturn(String bookId, String employeeId);
	List<BorrowRequestModel> getBorrowingByEmployee(String employeeId);
}
