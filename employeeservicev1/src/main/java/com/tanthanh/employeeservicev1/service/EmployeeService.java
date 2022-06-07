package com.tanthanh.employeeservicev1.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tanthanh.employeeservicev1.data.Employee;
import com.tanthanh.employeeservicev1.data.EmployeeRepository;
import com.tanthanh.employeeservicev1.model.EmployeeRequestModel;

@Service
public class EmployeeService implements IEmployeeService{

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public EmployeeRequestModel addEmployee(EmployeeRequestModel model) {
		EmployeeRequestModel employeeModel = new EmployeeRequestModel();
		Employee entity = new Employee();
		BeanUtils.copyProperties(model, entity);
		entity.setIsDisciplined(false);
		entity.setEmployeeId(UUID.randomUUID().toString());
		BeanUtils.copyProperties(employeeRepository.save(entity), employeeModel);
		return employeeModel;
	}

	@Override
	public EmployeeRequestModel updateEmployee(EmployeeRequestModel model) {
		EmployeeRequestModel employeeModel = new EmployeeRequestModel();
		Optional<Employee> entity = employeeRepository.findById(model.getEmployeeId());
		if(entity.isPresent()) {
			entity.get().setFirstName(model.getFirstName());
			entity.get().setLastName(model.getLastName());
			entity.get().setKin(model.getKin());
			entity.get().setIsDisciplined(model.getIsDisciplined());
			employeeRepository.save(entity.get());
			BeanUtils.copyProperties(entity.get(), employeeModel);
		}
		
		
		return employeeModel;
	}

	@Override
	public Boolean deleteEmployee(String employeeId) {
		try {
			employeeRepository.deleteById(employeeId);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	@Override
	public EmployeeRequestModel getDetailEmployee(String employeeId) {
		EmployeeRequestModel model = new EmployeeRequestModel();
		BeanUtils.copyProperties(employeeRepository.findById(employeeId).get(),model);
		return model;
	}
	
}
