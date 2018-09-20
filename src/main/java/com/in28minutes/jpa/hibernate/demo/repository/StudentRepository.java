package com.in28minutes.jpa.hibernate.demo.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.in28minutes.jpa.hibernate.demo.entity.Course;
import com.in28minutes.jpa.hibernate.demo.entity.Passport;
import com.in28minutes.jpa.hibernate.demo.entity.Student;

@Repository
@Transactional
public class StudentRepository {

	@Autowired
	EntityManager em;
	
	public Student findById(Long id){
		return em.find(Student.class, id);
	}
	
	public void deleteByID(Long id){
		Student student = findById(id);
		em.remove(student);
	}
	
	public Student save(Student student){
		if (student.getId() == null) {
			em.persist(student);
		}else{
			em.merge(student);
		}
		return student;
	}
	
	public void saveStudentWithPassport(){
		Passport passport = new Passport("Z123456");
		em.persist(passport);
		Student student = new Student("Mike");
		student.setPassport(passport);
		em.persist(student);
	}
	
	public void insertHardCodedStudentAndCourse(){
		Student student = new Student("Jack");
		Course course = new Course("Microservices in 100 Steps");
		em.persist(student);
		em.persist(course);
		student.addCourse(course);
		course.addStudent(student);
		em.persist(student);
	}
	
	public void insertStudentAndCourse(Student student, Course course){
		student.addCourse(course);
		course.addStudent(student);
		em.persist(student);
		em.persist(course);
	}
	
}
