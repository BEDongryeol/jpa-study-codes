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

            List<Member> resultList = em.createQuery("select m from Member m left join m.team t on t.name = :name", Member.class)
                    .setParameter("name", "testName")
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            System.out.println(resultList.size());
            resultList.forEach(System.out::println);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

    }
}
