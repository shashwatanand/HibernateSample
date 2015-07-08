package com.shashwat.hibernate.sample.datalayer;

import java.io.File;
import java.sql.Blob;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.query.spi.sql.NativeSQLQueryReturn;

import com.shashwat.hibernate.sample.model.Users;

public class DataLayer {
	private static SessionFactory sessionFactory = null;

	@SuppressWarnings("deprecation")
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				sessionFactory = new Configuration().configure(
						new File("resources/hibernate.cfg.xml"))
						.buildSessionFactory();
			} catch (Throwable ex) {
				throw new ExceptionInInitializerError(ex);
			}
		}
		return sessionFactory;
	}

	public static void saveUser(Users user) {
		SessionFactory factory;
		Session session;
		Transaction transaction = null;
		if ((factory = getSessionFactory()) != null
				&& (session = factory.openSession()) != null) {
			try {
				transaction = session.beginTransaction();
				user = processUserForImage(user, session);
				session.save(user);
				transaction.commit();
			} catch (HibernateException e) {
				if (transaction != null) {
					transaction.rollback();
				}
				e.printStackTrace();
			} finally {
				session.close();
			}
		}
	}

	private static Users processUserForImage(Users user, Session session) {
		byte[] data;
		if ((data = user.getBitmap()) != null) {
			Blob blob = Hibernate.getLobCreator(session).createBlob(data);
			user.setImage(blob);
		}
		return user;
	}

	/* Method to READ all the users */
	@SuppressWarnings("rawtypes")
	public static void listUsers() {
		SessionFactory factory;
		Session session;
		if ((factory = getSessionFactory()) != null
				&& (session = factory.openSession()) != null) {
			Transaction transaction = null;
			try {
				transaction = session.beginTransaction();
				//List users = session.createQuery("from USERS").list();
				List users = session.createSQLQuery("Select * from USERS").addEntity(Users.class).list();
				//List<NativeSQLQueryReturn> queryReturns = users.getQueryReturns();
				for (Iterator iterator = users.iterator(); iterator.hasNext();) {
					Users user = (Users) iterator.next();
					System.out.println("==================================");
					System.out.println("Name : " + user.getName());
					System.out.println("Address : " + user.getAddress());
					System.out.println("Email : " + user.getEmail());
					System.out.println("Phone : " + user.getPhone());
					System.out.println("Mobile : " + user.getMobile());
					System.out.println("==================================");
				}
				transaction.commit();
			} catch (HibernateException e) {
				if (transaction != null) {
					transaction.rollback();
				}
				e.printStackTrace();
			} finally {
				session.close();
			}
		}
	}

}
