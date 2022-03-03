package jpql;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {

            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀A");
            em.persist(teamB);

            Member member = new Member();
            member.setUserName("회원1");
            member.setAge(60);
            em.persist(member);

            Member member1 = new Member();
            member1.setUserName("회원2");
            member1.setAge(20);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUserName("회원3");
            member2.setAge(27);
            em.persist(member2);

            member.addTeam(teamA);
            member1.addTeam(teamA);
            member2.addTeam(teamB);

            em.flush();
            em.clear();

            System.out.println("=============testing=============");

            String query = "select m from Member m";
            List<Member> result = em.createQuery(query, Member.class).getResultList();

            for (Member mem : result) {
                System.out.println(mem.getUserName() + " : " + mem.getTeam().getName());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

    }
}
