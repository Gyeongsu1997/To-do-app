package gyeongsu.todo.repository;

import gyeongsu.todo.domain.Job;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JobRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Job job) {
        if (job.getId() == null) {
            em.persist(job);
        } else {
            em.merge(job);
        }
    }

    public Job findOne(Long id) {
        return em.find(Job.class, id);
    }

    public List<Job> findAll() {
        return em.createQuery("select j from Job j order by j.expiryDate", Job.class)
                .getResultList();
    }


}
