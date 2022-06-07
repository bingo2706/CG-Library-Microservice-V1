package com.tanthanh.borrowingservicev1.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRepository extends JpaRepository<Borrowing, String>{
	Borrowing findByEmployeeIdAndBookId(String employeeId,String bookId);
	List<Borrowing> findByEmployeeId(String employeeId);
}
