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

            Team team = new Team();
            team.setName("OneTeam");
            em.persist(team);

            Member member = new Member();
            member.setUserName("관리자");
            member.setAge(60);
            em.persist(member);
            Member member1 = new Member();
            member1.setUserName("member2");
            member1.setAge(20);
            em.persist(member1);

            member.addTeam(team);
            member1.addTeam(team);

            em.flush();
            em.clear();

            String query = "select function('group_concat', m.userName) From Member m";
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
