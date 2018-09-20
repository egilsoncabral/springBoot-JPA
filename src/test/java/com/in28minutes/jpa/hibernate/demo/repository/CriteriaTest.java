package com.in28minutes.jpa.hibernate.demo.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
public class CriteriaTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EntityManager em;
	
	@Test
	public void allCourse() {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class);
		Root<Course> rootCourse = cq.from(Course.class);
		TypedQuery<Course> query = em.createQuery(cq.select(rootCourse));
		List<Course> resultList = query.getResultList();
		logger.info("Typed Query -> {}", resultList);
	}
	
	@Test
	public void allCourseHavingOneHundredSteps() {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class);
		Root<Course> rootCourse = cq.from(Course.class);
		Predicate like100Steps = cb.like(rootCourse.get("name"), "%100 Steps");
		cq.where(like100Steps);
		TypedQuery<Course> query = em.createQuery(cq.select(rootCourse));
		List<Course> resultList = query.getResultList();
		logger.info("Typed Query -> {}", resultList);
	}
	
	@Test
	public void allCourseWithoutStudents() {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class);
		Root<Course> rootCourse = cq.from(Course.class);
		Predicate studentIsEmpty = cb.isEmpty(rootCourse.get("students"));
		cq.where(studentIsEmpty);
		TypedQuery<Course> query = em.createQuery(cq.select(rootCourse));
		List<Course> resultList = query.getResultList();
		logger.info("Typed Query -> {}", resultList);
	}
	
	@Test
	public void join() {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class);
		Root<Course> rootCourse = cq.from(Course.class);
		rootCourse.join("students");
		TypedQuery<Course> query = em.createQuery(cq.select(rootCourse));
		List<Course> resultList = query.getResultList();
		logger.info("Typed Query -> {}", resultList);
	}
	
	@Test
	public void leftJoin() {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class);
		Root<Course> rootCourse = cq.from(Course.class);
		rootCourse.join("students", JoinType.LEFT);
		TypedQuery<Course> query = em.createQuery(cq.select(rootCourse));
		List<Course> resultList = query.getResultList();
		logger.info("Typed Query -> {}", resultList);
	}
	
}
