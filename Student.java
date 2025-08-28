package com.diplav.StudentManagementApplication.entity;

import lombok.Data;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int studentClass;
    private int rollNumber;
    private String studentName;
    private String fatherName;
    private String Address;
    private String contactNumber;
    private int totalFee;
    private int submittedFee;
}
