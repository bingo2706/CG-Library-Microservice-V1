package com.tanthanh.employeeservicev1.controller;

import java.util.ArrayList;
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
import org.springframework.web.reactive.function.client.WebClient;

import com.tanthanh.employeeservicev1.model.BookRequestModel;
import com.tanthanh.employeeservicev1.model.BorrowRequestModel;
import com.tanthanh.employeeservicev1.model.EmployeeRequestModel;
import com.tanthanh.employeeservicev1.service.IEmployeeService;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Autowired
	private IEmployeeService employeeService;
	
	@PostMapping
	public EmployeeRequestModel addEmployee(@RequestBody EmployeeRequestModel model) {
		return employeeService.addEmployee(model);
	}
	@PutMapping
	public EmployeeRequestModel updateEmployee(@RequestBody EmployeeRequestModel model) {
		return employeeService.updateEmployee(model);
	}
	@DeleteMapping("/{employeeId}")
	public Boolean deleteEmployee(@PathVariable String employeeId) {
		return employeeService.deleteEmployee(employeeId);
	}
	@GetMapping("/{employeeId}")
	public EmployeeRequestModel getDetailEmployee(@PathVariable String employeeId) {
		return employeeService.getDetailEmployee(employeeId);
	}
	@GetMapping("/{employeeId}/books")
	public List<BookRequestModel> getEmployeeBorrowed(@PathVariable String employeeId){
		
		
		List<BorrowRequestModel> listBorrow = webClientBuilder.build()
				.get()
				.uri("http://localhost:9003/api/v1/borrowing/"+employeeId)
				.retrieve()
				.bodyToFlux(BorrowRequestModel.class)
				.collectList()
				.block();
		List<BookRequestModel> listBook = webClientBuilder.build()
				.get()
				.uri("http://localhost:9001/api/v1/books")
				.retrieve()
				.bodyToFlux(BookRequestModel.class)
				.collectList()
				.block();
		List<BookRequestModel> listTemp = new ArrayList<>();
		for(int i = 0;i <listBook.size();i++) {
			for(int j = 0 ; j< listBorrow.size();j++) {
				if(listBook.get(i).getBookId().equals(listBorrow.get(j).getBookId())) {
					listTemp.add(listBook.get(i));
					continue;
				}
			}
		}
		return listTemp;
	}
	
}
