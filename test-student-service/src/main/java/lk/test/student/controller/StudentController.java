package lk.test.student.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lk.test.student.model.Student;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

	@PostMapping(produces = "application/json")
	public Student addStudent(@Valid @RequestBody Student student) {
		//add student database
		return student;
	}
}
