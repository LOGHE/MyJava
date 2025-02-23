/**
 * 
 */
package com.hspdu.mhl.service;

import com.hspdu.mhl.dao.EmployeeDAO;
import com.hspdu.mhl.domain.Employee;

/**
 * @author 羅寿合(ラ　ジュゴウ)
 *该类完成对Employee表的各种操作（通过调用EmployeeDAO对像完成)
 */
public class EmployeeService {
	//定义一个EmployeeDAO属性
	private EmployeeDAO employeeDAO = new EmployeeDAO();
	//根据empId和pwd返回一个Employee对象
	public Employee getEmployeeByIdAndPwd(String empId,String pwd) {
		return employeeDAO.querySingle("select * from employee where empId=? and pwd=md5(?)",Employee.class,empId,pwd);
		
	}
}
