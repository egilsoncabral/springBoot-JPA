package com.in28minutes.jpa.hibernate.demo.repository;

import static org.junit.Assert.assertEquals;

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
import org.springframework.transaction.annotation.Transactional;

import com.in28minutes.jpa.hibernate.demo.DemoApplication;
import com.in28minutes.jpa.hibernate.demo.entity.Course;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=DemoApplication.class)
public class NativeQueriesTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EntityManager em;
	
	@Test
	public void nativeQueriesBasic() {
		Query query = em.createNativeQuery("select * from course", Course.class);
		List resultList = query.getResultList();
		logger.info("select * from course -> {}", resultList);
	}
	
	@Test
	public void nativeQueriesWithParametersBasic() {
		Query query = em.createNativeQuery("select * from course where id = ?", Course.class);
		query.setParameter(1, 10001L);
		List resultList = query.getResultList();
		logger.info("select * from course where id = ? -> {}", resultList);
	}
	
	@Test
	public void nativeQueriesWithNamedParametersBasic() {
		Query query = em.createNativeQuery("select * from course where id = :id", Course.class);
		query.setParameter("id", 10001L);
		List resultList = query.getResultList();
		logger.info("select * from course where id = :id -> {}", resultList);
	}
	
	@Test
	@Transactional
	public void nativeQueriesToUpdate() {
		Query query = em.createNativeQuery("update course set last_updated_date = sysdate()", Course.class);
		int numRowsUpdated = query.executeUpdate();
		logger.info("Numbers of rows updated -> {}", numRowsUpdated);
	}
	
}
