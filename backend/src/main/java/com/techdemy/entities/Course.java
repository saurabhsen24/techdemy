package com.techdemy.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "courses")
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    @Column(name = "author", nullable = false)
    private String author;
    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Column(name = "course_description", nullable = false)
    private String courseDescription;

    @ColumnDefault("0.0")
    @Column(name = "course_price", nullable = false)
    private Double coursePrice;

    @ColumnDefault("0.0")
    @Column(name = "course_rating", nullable = false)
    private Double courseRating;

    @Column(name = "created_on")
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    @UpdateTimestamp
    private LocalDateTime updatedOn;

    public String toString() {
        return "Course { courseId = " + courseId + " , courseName = " + courseName +
                ", courseDesc = " + courseDescription + " , createdOn = " + createdOn + " , updatedOn = "
                + updatedOn + " }";
    }

}
