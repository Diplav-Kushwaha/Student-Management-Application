package com.diplav.StudentManagementApplication.controller;

import com.diplav.StudentManagementApplication.entity.Student;
import com.diplav.StudentManagementApplication.repository.StudentRepository;
import com.diplav.StudentManagementApplication.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private final StudentService studentService;
    @Autowired
    private final StudentRepository studentRepository;

    public StudentController(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    @GetMapping("/")
    public String home(){ return "home";}

    @GetMapping("/allStudent")
    public String showAllStudent(Model model){
        model.addAttribute("students", studentRepository.findAll());
        return "student";

    }
    @GetMapping("/searchStudent")
    public String searchStudent(){
        return "searchResult";
    }
    @GetMapping("/doSearch")
    public String doSearch(@RequestParam("studentName") String studentName, Model model) {
        List<Student> studentList = studentService.findByName(studentName);
        model.addAttribute("student", studentList);
        return "searchResult";
    }
}
