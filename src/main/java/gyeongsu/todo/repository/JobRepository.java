package gyeongsu.todo.repository;

import gyeongsu.todo.domain.Job;
import gyeongsu.todo.dto.JobSearch;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class JobRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Job job) {
        em.persist(job);
    }

    public Job findOne(Long id) {
        return em.find(Job.class, id);
    }

    public List<Job> findAll(JobSearch jobSearch) {
        String jpql = "select j From Job j join j.member m";

        //회원 이름 검색
        if (StringUtils.hasText(jobSearch.getMemberName())) {
            jpql += " where m.name like :name";
        }
        jpql += " order by j.expiryDate";
        TypedQuery<Job> query = em.createQuery(jpql, Job.class);
        if (StringUtils.hasText(jobSearch.getMemberName())) {
            query = query.setParameter("name", jobSearch.getMemberName());
        }
        return query.getResultList();
    }
}
