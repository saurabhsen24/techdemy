package com.techdemy.service;

import com.techdemy.entities.Enrollment;

import java.util.List;

public interface EnrollmentService {

    void saveEnrollment( Enrollment enrollment );

    Enrollment getEnrollment( Long enrollmentId );

    void updateEnrollment( Enrollment enrollment );

    void deleteEnrollment( Long enrollmentId );

    List<Enrollment> getEnrollments();

}
