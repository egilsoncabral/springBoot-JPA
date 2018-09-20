package com.in28minutes.jpa.hibernate.demo.repository;

import static org.junit.Assert.*;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.in28minutes.jpa.hibernate.demo.DemoApplication;
import com.in28minutes.jpa.hibernate.demo.entity.Course;
import com.in28minutes.jpa.hibernate.demo.entity.Review;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=DemoApplication.class)
public class CourseSpringDataRepositoryTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CourseSpringDataRepository repository;
	
	@Autowired
	EntityManager em;
	
	@Test
	public void findByIdCoursePresent() {
		Optional<Course> courseOptional = repository.findById(10001L);
		assertTrue(courseOptional.isPresent());
	}
	
	@Test
	public void findByIdCourseNotPresent() {
		Optional<Course> courseOptional = repository.findById(20001L);
		assertFalse(courseOptional.isPresent());
	}
	
	@Test
	public void playAroundWithSpringDataRepository() {
//		Course course = new Course("Microservices in 100 Steps");
//		repository.save(course);
//		course.setName("Microservices in 100 Steps -  Updated");
//		repository.save(course);
		logger.info("Courses -> {}", repository.findAll());
		logger.info("Courses count -> {}", repository.count());
	}
	
	@Test
	public void sort() {
//		Course course = new Course("Microservices in 100 Steps");
//		repository.save(course);
//		course.setName("Microservices in 100 Steps -  Updated");
//		repository.save(course);
		Sort sort = new Sort(Sort.Direction.ASC, "name");
		logger.info("Courses -> {}", repository.findAll(sort));
		logger.info("Courses count -> {}", repository.count());
	}
	
	@Test
	public void pagination() {
		PageRequest pageRequest = PageRequest.of(0, 3);
		Page<Course> firstPage = repository.findAll(pageRequest);
		logger.info("First Page -> {}", firstPage.getContent());
		
		Pageable secondPageable = firstPage.nextPageable();
		Page<Course> secondPage = repository.findAll(secondPageable);
		logger.info("Second Page -> {}", secondPage.getContent());
	}
	
}
