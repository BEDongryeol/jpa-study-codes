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
            member.setUserName("관리자");
            member.setAge(60);
            em.persist(member);

            Member member1 = new Member();
            member1.setUserName("member2");
            member1.setAge(20);
            em.persist(member1);

            em.flush();
            em.clear();
            /*
               이름이 관리자이면 null을 반환한다.
               아니면 m.userName 반환
               -> 관리자 이름을 숨길 때 사용할 수 있다.
             */
            String query = "select nullif(m.userName, '관리자') from Member m";
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
