package lk.test.student.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Student {

	@NotNull(message = "role no. not blank")
	@Min(value = 1, message = "Role number cannot be less than 1")
	@Max(value = 100, message = "Role number cannot be above than 100")
	private int roleNumber;

	@NotNull(message = "name not blank")
	private String name;

	public int getRoleNumber() {
		return roleNumber;
	}

	public void setRoleNumber(int roleNumber) {
		this.roleNumber = roleNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
