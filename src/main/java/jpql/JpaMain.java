package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Member member = new Member();
            member.setUserName(null);
            member.setAge(60);
            em.persist(member);

            Member member1 = new Member();
            member1.setUserName("member2");
            member1.setAge(20);
            em.persist(member1);

            em.flush();
            em.clear();

            String query = "select coalesce(m.userName, '이름없는 회원') from Member m";
            em.createQuery(query, String.class).getResultList().forEach(System.out::println);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

    }
}
