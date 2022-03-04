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
            member.addTeam(teamA);
            em.persist(member);

            Member member1 = new Member();
            member1.setUserName("회원2");
            member1.setAge(20);
            member1.addTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUserName("회원3");
            member2.setAge(27);
            member2.addTeam(teamB);
            em.persist(member2);
            System.out.println("======team add======");

            em.flush();
            em.clear();

            System.out.println("=============testing=============");
            // FLUSH 자동 호출
            int count = em.createQuery("update Member m set m.age = 20")
                            .executeUpdate();

            System.out.println(member.getAge());
            System.out.println(member1.getAge());
            System.out.println(member2.getAge());

            System.out.println(count);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

    }
}
