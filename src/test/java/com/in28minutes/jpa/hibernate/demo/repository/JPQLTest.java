package com.in28minutes.jpa.hibernate.demo.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.in28minutes.jpa.hibernate.demo.DemoApplication;
import com.in28minutes.jpa.hibernate.demo.entity.Course;
import com.in28minutes.jpa.hibernate.demo.entity.Student;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=DemoApplication.class)
public class JPQLTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EntityManager em;
	
	@Test
	public void jpqlBasic() {
		Query query = em.createNamedQuery("query_get_all_courses");
		List resultList = query.getResultList();
		logger.info("select c from Course c -> {}", resultList);
	}
	
	@Test
	public void jpqlTyped() {
		TypedQuery<Course> query = em.createNamedQuery("query_get_all_courses", Course.class);
		List<Course> resultList = query.getResultList();
		logger.info("select c from Course c -> {}", resultList);
	}
	
	@Test
	public void jpqlWhere() {
		TypedQuery<Course> query = em.createNamedQuery("query_get_100_Steps_courses", Course.class);
		List<Course> resultList = query.getResultList();
		logger.info("select c from Course c Where name like '%100 Steps' -> {}", resultList);
	}
	
	@Test
	public void jpqlCoursesWithoutStudents() {
		TypedQuery<Course> query = em.createQuery("select c from Course c where c.students is empty", Course.class);
		List<Course> resultList = query.getResultList();
		logger.info("Results -> {}", resultList);
	}
	
	@Test
	public void jpqlCoursesWithAtLeastTwoStudents() {
		TypedQuery<Course> query = em.createQuery("select c from Course c where size(c.students) >= 2", Course.class);
		List<Course> resultList = query.getResultList();
		logger.info("Results -> {}", resultList);
	}
	
	@Test
	public void jpqlCoursesOrderedByStudents() {
		TypedQuery<Course> query = em.createQuery("select c from Course c order by size(c.students)", Course.class);
		List<Course> resultList = query.getResultList();
		logger.info("Results -> {}", resultList);
	}
	
	@Test
	public void jpqlStudentsWithPassportInCertainPattern() {
		TypedQuery<Student> query = em.createQuery("select s from Student s where s.passport.number like '%1234%'", Student.class);
		List<Student> resultList = query.getResultList();
		logger.info("Results -> {}", resultList);
	}
	
	@Test
	public void join(){
		Query query = em.createQuery("select c, s from Course c JOIN c.students s");
		List<Object[]> resultList = query.getResultList();
		logger.info("Results Size -> {}", resultList.size());
		for (Object[] result : resultList) {
			logger.info("Course{} Student{}", result[0],  result[1]);
		}
	}
	
	@Test
	public void leftJoin(){
		Query query = em.createQuery("select c, s from Course c LEFT JOIN c.students s");
		List<Object[]> resultList = query.getResultList();
		logger.info("Results Size -> {}", resultList.size());
		for (Object[] result : resultList) {
			logger.info("Course{} Student{}", result[0],  result[1]);
		}
	}
	
	@Test
	public void crossJoin(){
		Query query = em.createQuery("select c, s from Course c, Student s");
		List<Object[]> resultList = query.getResultList();
		logger.info("Results Size -> {}", resultList.size());
		for (Object[] result : resultList) {
			logger.info("Course{} Student{}", result[0],  result[1]);
		}
	}
	
}
