package com.tanthanh.employeeservicev1.service;

import com.tanthanh.employeeservicev1.model.EmployeeRequestModel;

public interface IEmployeeService {
	EmployeeRequestModel addEmployee(EmployeeRequestModel model);
	EmployeeRequestModel updateEmployee(EmployeeRequestModel model);
	Boolean deleteEmployee(String employeeId);
	EmployeeRequestModel getDetailEmployee(String employeeId);
}
