package com.zaicev.spring.security.models;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.zaicev.spring.wallet.models.Wallet;

public class UserTest {
	private User user;
	private Wallet wallet;

	@Before
	public void initialize() {
		user = new User();
		wallet = new Wallet();
	}
	
	@Test
	public void addWallet() {
		user.addWallet(wallet);
		
		assertTrue(user.getWallets().contains(wallet));
		assertEquals(user, wallet.getUser());
	}
	
	@Test
	public void removeWallet() {
		user.addWallet(wallet);
		
		user.removeWallet(wallet);
		
		assertFalse(user.getWallets().contains(wallet));
		assertNull(wallet.getUser());
	}
}
