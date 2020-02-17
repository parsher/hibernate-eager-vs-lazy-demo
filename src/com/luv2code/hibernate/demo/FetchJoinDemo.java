package com.luv2code.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.luv2code.hibernate.demo.entity.Course;
import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;

public class FetchJoinDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class).addAnnotatedClass(Course.class).buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {
			
			// start a transaction
			session.beginTransaction();
			
			// get the instructor from db
			int theId = 1;
			
			// option2: Hibernate query with HQL
			Query<Instructor> query = session.createQuery("select i from Instructor i JOIN FETCH i.courses WHERE i.id=:theInstructorId", Instructor.class);
			
			query.setParameter("theInstructorId", theId);
			
			// execute query and get instructor
			Instructor tempInstructor = query.getSingleResult();
			
			System.out.println("instructor: " + tempInstructor);
			// commit transaction
			session.getTransaction().commit();
			
			session.close();

			System.out.println("courses: " + tempInstructor.getCourses());
			
			System.out.println("Done");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			session.close();
			factory.close();
		}

	}

}
