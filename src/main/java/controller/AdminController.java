package controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import manager.EmployeeManager;
import manager.LabourGradeManager;
import model.Employee;
import model.Labgrd;

@RequestScoped
@Named("Admin")
public class AdminController {
	@Inject private EmployeeManager employeeManager;

	@Inject private Employee newEmployee;
	
	@Inject private EmployeeController employeeController;
	
	@Inject private LabourGradeManager labourGradeManager;
	
	public EmployeeController getEmployeeController() {
		return employeeController;
	}
	
	public void setEmployeeController(EmployeeController employeeController) {
		this.employeeController = employeeController;
	}
	
	public Employee getNewEmployee() {
    	return newEmployee;
    }
    
    public void setNewEmployee(Employee employee) {
    	newEmployee = employee;
    }
    
    public String addEmployee() {
    	newEmployee.setEmpFlxTm(BigDecimal.ZERO);
    	employeeManager.persist(newEmployee);
    	employeeController.refreshList();
    	return "admin";
    }
    
    public List<Labgrd> getLabourGrades() {
    	return labourGradeManager.getAll();
    }
}