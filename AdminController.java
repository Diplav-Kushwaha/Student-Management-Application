package com.diplav.StudentManagementApplication.controller;

import com.diplav.StudentManagementApplication.entity.Student;
import com.diplav.StudentManagementApplication.repository.StudentRepository;
import com.diplav.StudentManagementApplication.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {
    @Autowired
    private final StudentService studentService;
    @Autowired
    private final StudentRepository studentRepository;

    public AdminController(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        return "adminPage";
    }
    @PostMapping("/addStudent")
    public String addStudent(@ModelAttribute("student") Student student) {
        studentService.save(student);
        return "redirect:/admin";
    }
    @RequestMapping("/removeStudent/{id}")
    public String removeStudent(@PathVariable("id") Long id){
        studentService.deleteStudentById(id);
        return "redirect:/admin";
    }
    @GetMapping("/updateStudent/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Student student = studentRepository.findById(id).orElse(null);
        model.addAttribute("student", student);
        return "updateStudent";
    }
    @PostMapping("/updateStudent/{id}")
    public String updateStudent(@PathVariable("id") Long id, @ModelAttribute("student") Student student) {
        studentService.updateStudent(id, student);
        return "redirect:/admin";
    }


}
