package com.zaicev.spring.security.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.zaicev.spring.config.TestDataConfig;
import com.zaicev.spring.security.models.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDataConfig.class})
public class UserDAOTest {
	@Autowired
	private SessionFactory sessionFactory;
	
	private UserDAO userDAO;
	
	@Before
	public void initialize() {
		userDAO = new UserDAO(sessionFactory);
	}
	
	@After
	public void tearDown() {
		sessionFactory.getCurrentSession().clear();
	}
	
	@Test
	@Transactional
	public void testCreateAndGetUser() {
		User user = new User();
		user.setUsername("testUser");
		user.setPassword("password");
		
		userDAO.createUser(user);
		Optional<User> receivedUser = userDAO.getUserByName("testUser");
		
		assertTrue(receivedUser.isPresent());
		assertEquals("testUser", receivedUser.get().getUsername());
	}
	
	@Test
	@Transactional
	public void testUserNotFount() {
		Optional<User> user = userDAO.getUserByName("unknown");
		assertFalse(user.isPresent());
	}
	
	@Test
	@Transactional
    public void testUpdateUser() {
        User user = new User();
        user.setUsername("bob");
        user.setPassword("old_password");
        userDAO.createUser(user);

        user.setPassword("new_password");
        userDAO.updateUser(user);

        Optional<User> updatedUser = userDAO.getUserByName("bob");
        assertTrue(updatedUser.isPresent());
        assertEquals("new_password", updatedUser.get().getPassword());
    }
	
	

}
