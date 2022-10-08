package com.techdemy.service.impl;

import com.techdemy.entities.Enrollment;
import com.techdemy.exception.ResourceNotFoundException;
import com.techdemy.repository.EnrollmentRepository;
import com.techdemy.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public void saveEnrollment(Enrollment enrollment) {
        enrollmentRepository.save(enrollment);
    }

    @Override
    public Enrollment getEnrollment(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById( enrollmentId ).orElseThrow(() ->
                new ResourceNotFoundException("Enrollment noy found"));
        return enrollment;
    }

    @Override
    public void updateEnrollment(Enrollment enrollment) {
        enrollmentRepository.save(enrollment);
    }

    @Override
    public void deleteEnrollment(Long enrollmentId) {
        enrollmentRepository.deleteById( enrollmentId );
    }

    @Override
    public List<Enrollment> getEnrollments() {
        return enrollmentRepository.findAll();
    }
}
