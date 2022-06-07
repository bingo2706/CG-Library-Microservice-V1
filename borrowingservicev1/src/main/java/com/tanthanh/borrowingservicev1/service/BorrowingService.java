package com.tanthanh.borrowingservicev1.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.tanthanh.borrowingservicev1.data.BorrowRepository;
import com.tanthanh.borrowingservicev1.data.Borrowing;
import com.tanthanh.borrowingservicev1.model.BookRequestModel;
import com.tanthanh.borrowingservicev1.model.BorrowRequestModel;

import reactor.core.publisher.Mono;

@Service
public class BorrowingService implements IBorrowingService{

	@Autowired
	private BorrowRepository borrowRepository;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Override
	public BorrowRequestModel addBookBorrow(BorrowRequestModel model) {
		BorrowRequestModel borrowDto = new BorrowRequestModel();
		Borrowing entity = new Borrowing();
		BeanUtils.copyProperties(model, entity);
		entity.setBorrowingDate(new Date());
		BeanUtils.copyProperties(borrowRepository.save(entity), borrowDto);
		
		BookRequestModel bookDTO = new BookRequestModel();
		bookDTO.setBookId(model.getBookId());
		bookDTO.setIsReady(false);
		
		Boolean isUpdate = webClientBuilder.build()
				.put()
				.uri("http://localhost:9001/api/v1/books/updateBookStatus")
				.body(Mono.just(bookDTO),BookRequestModel.class)
				.retrieve()
				.bodyToMono(Boolean.class)
				.block();
		
		
		return borrowDto;
	}

	@Override
	public BorrowRequestModel updateBookReturn(String bookId, String employeeId) {	
		BorrowRequestModel borrowDTO = new BorrowRequestModel();
		Borrowing entity = borrowRepository.findByEmployeeIdAndBookId(employeeId,bookId);
		entity.setReturnDate(new Date());
		BeanUtils.copyProperties(borrowRepository.save(entity), borrowDTO);
		
		BookRequestModel bookDTO = new BookRequestModel();
		bookDTO.setBookId(bookId);
		bookDTO.setIsReady(true);
		
		Boolean isUpdate = webClientBuilder.build()
		.put()
		.uri("http://localhost:9001/api/v1/books/updateBookStatus")
		.body(Mono.just(bookDTO),BookRequestModel.class)
		.retrieve()
		.bodyToMono(Boolean.class)
		.block();
		
		
		return borrowDTO;
	}

	@Override
	public List<BorrowRequestModel> getBorrowingByEmployee(String employeeId) {
		List<Borrowing> listEntity =  borrowRepository.findByEmployeeId(employeeId);
		List<BorrowRequestModel> listmodel = new ArrayList<>();	
		listEntity.stream().forEach(s ->{
			BorrowRequestModel model = new BorrowRequestModel();
			BeanUtils.copyProperties(s, model);
			listmodel.add(model);
		});
		return listmodel;
	}

}
