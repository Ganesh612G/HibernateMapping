package com.main;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.entity.Department;
import com.entity.Employee;

public class MainClass {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		cfg.addAnnotatedClass(Employee.class);
		cfg.addAnnotatedClass(Department.class); // Ensure Department class is registered
		SessionFactory sf = cfg.buildSessionFactory();
		while (true) {
			Session ss = sf.openSession();
			Transaction tr = ss.beginTransaction();

			System.out.println("Enter 1. for Department Details");
			System.out.println("Enter 2. for Employee Details");
			System.out.println("Enter 0. to Exit");
			int choice = sc.nextInt();

			switch (choice) {

			case 1:

				System.out.println("Enter Department ID");
				int deptId = sc.nextInt();
				System.out.println("Enter Department Name");
				sc.nextLine(); // Consume newline
				String deptName = sc.nextLine();

				Department dept = new Department();
				dept.setD_id(deptId);
				dept.setD_name(deptName);

				ss.persist(dept); // Save department to the database
				System.out.println("Department inserted successfully!");
				break;

			case 2:

				System.out.println("Enter Employee ID");
				int empId = sc.nextInt();
				System.out.println("Enter Employee Name");
				sc.nextLine(); // Consume newline
				String empName = sc.nextLine();

				System.out.println("Enter Department ID for the Employee");
				int empDeptId = sc.nextInt();

                // Fetch the Department from the database
                Department existingDept = ss.get(Department.class, empDeptId);
//
                if (existingDept == null) {
                    System.out.println("Department with ID " + empDeptId + " does not exist. Please add it first.");
                } else {
                    Employee emp = new Employee();
                    emp.setE_id(empId);
                    emp.setE_name(empName);
                    emp.setDepartment(existingDept);

                    ss.persist(emp); 
                    System.out.println("Employee inserted successfully!");
                }
				break;

			default:
				System.out.println("Invalid choice.");
				break;
			}

			tr.commit();
			ss.close();
		
		}
	}
}
