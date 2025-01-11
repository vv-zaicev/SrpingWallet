package com.zaicev.spring.wallet.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.zaicev.spring.wallet.models.Wallet;

public class WalletDAO {

	private SessionFactory sessionFactory;

	public WalletDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<Wallet> getAllWallets() {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("from Wallet", Wallet.class).list();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Wallet>();
		}
	}

	public Wallet getWalletById(int id) {
		try (Session session = sessionFactory.openSession()) {
			return session.get(Wallet.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void createWallet(Wallet newWallet) {
		try (Session session = sessionFactory.openSession()) {
			session.getTransaction().begin();
			session.persist(newWallet);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateWallet(Wallet updatedWallet) {
		try (Session session = sessionFactory.openSession()) {
			session.getTransaction().begin();
			session.merge(updatedWallet);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteWallet(int id) {
		try (Session session = sessionFactory.openSession()) {
			session.getTransaction().begin();
			Wallet deletedWallet = session.get(Wallet.class, id);
			if (deletedWallet != null) {
				session.remove(deletedWallet);
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
