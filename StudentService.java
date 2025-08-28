package com.diplav.StudentManagementApplication.service;

import com.diplav.StudentManagementApplication.entity.Student;
import com.diplav.StudentManagementApplication.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private final StudentRepository studentRepository;
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public List<Student> showAllStudent() {
        return studentRepository.findAllByOrderByStudentClassAscRollNumberAsc();
    }
    public List<Student> findByName(String studentName) {
        return studentRepository.findByStudentNameContainingIgnoreCase(studentName);
    }
    public void save(Student student) {
        studentRepository.save(student);
        List<Student> studentsInClass = studentRepository.findByStudentClass(student.getStudentClass());
        studentsInClass.sort(Comparator.comparing(Student::getStudentName));
        int roll = 1;
        for (Student s : studentsInClass) {
            s.setRollNumber(roll++);
        }
        studentRepository.saveAll(studentsInClass);
    }
    public void deleteStudentById(Long id) {
        Student student = getStudentById(id);
        if (student != null) {
            studentRepository.deleteById(id);
            List<Student> studentsInClass = studentRepository.findByStudentClass(student.getStudentClass());
            studentsInClass.sort(Comparator.comparing(Student::getStudentName));

            int roll = 1;
            for (Student s : studentsInClass) {
                s.setRollNumber(roll++);
            }
            studentRepository.saveAll(studentsInClass);
        }
    }
    public void updateStudent(Long id, Student updatedStudent) {
        Student existing = studentRepository.findById(id).orElse(null);
        if (existing != null) {

            int oldClass = existing.getStudentClass();
            existing.setStudentClass(updatedStudent.getStudentClass());
            existing.setStudentName(updatedStudent.getStudentName());
            existing.setFatherName(updatedStudent.getFatherName());
            existing.setAddress(updatedStudent.getAddress());
            existing.setContactNumber(updatedStudent.getContactNumber());
            existing.setTotalFee(updatedStudent.getTotalFee());
            existing.setSubmittedFee(updatedStudent.getSubmittedFee());

            studentRepository.save(existing);
            if (oldClass != updatedStudent.getStudentClass()) {
                recalculateRollNumbers(oldClass);
            }
            recalculateRollNumbers(updatedStudent.getStudentClass());
        }
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id " + id));
    }
    private void recalculateRollNumbers(int studentClass) {
        List<Student> students = studentRepository.findByStudentClass(studentClass);
        students.sort(Comparator.comparing(Student::getStudentName));

        int roll = 1;
        for (Student s : students) {
            s.setRollNumber(roll++);
        }
        studentRepository.saveAll(students);
    }
}
