package views;

import java.time.LocalDate;

public class Employees {

	private int empNo;
	private String firstName;
	private String lastName;
	private char gender;
	private LocalDate hireDate;
	private LocalDate birthDate;

	public Employees() {
	}

	public Employees(int empNo, LocalDate birthDate, String firstName, String lastName, char gender, LocalDate hireDate) {
		this.empNo = empNo;
		this.birthDate = birthDate;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.hireDate = hireDate;
	}


	public int getEmpNo() {
		return this.empNo;
	}

	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}

	public LocalDate getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public char getGender() {
		return this.gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public LocalDate getHireDate() {
		return this.hireDate;
	}

	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}

}
