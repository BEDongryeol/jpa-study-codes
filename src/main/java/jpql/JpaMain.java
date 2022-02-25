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
            member.setUserName("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            List<Member> members  = em.createQuery("select m from Member m", Member.class)
                    .getResultList();

            members.get(0).setAge(20);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

    }
}
