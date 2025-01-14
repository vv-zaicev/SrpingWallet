package com.zaicev.spring.wallet.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.zaicev.spring.wallet.models.Wallet;

public class WalletDAO {

	private SessionFactory sessionFactory;

	public WalletDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public List<Wallet> getAllWallets() {
		return sessionFactory.getCurrentSession().createQuery("from Wallet", Wallet.class).list();
	}

	@Transactional
	public Wallet getWalletById(int id) {
		return sessionFactory.getCurrentSession().get(Wallet.class, id);
	}

	@Transactional
	public void createWallet(Wallet newWallet) {
		sessionFactory.getCurrentSession().persist(newWallet);
	}

	@Transactional
	public void updateWallet(Wallet updatedWallet) {
		sessionFactory.getCurrentSession().merge(updatedWallet);
	}

	@Transactional
	public void deleteWallet(int id) {
		Session session = sessionFactory.getCurrentSession();
		Wallet deletedWallet = session.get(Wallet.class, id);
		if (deletedWallet != null) {
			session.remove(deletedWallet);
		}
	}
}
