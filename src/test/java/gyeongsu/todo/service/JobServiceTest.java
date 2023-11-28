package gyeongsu.todo.service;

import gyeongsu.todo.domain.Job;
import gyeongsu.todo.domain.JobStatus;
import gyeongsu.todo.domain.Member;
import gyeongsu.todo.repository.JobSearch;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class JobServiceTest {
    @PersistenceContext
    EntityManager em;
    @Autowired JobService jobService;

    @Test
    void 할일등록() {
        //Given
        Member member = new Member("gyeongsu");
        em.persist(member);
        String name = "장 보기";
        String description = "두부 한 모, 포카칩 3봉지, 배추 한 포기";
        JobStatus status = JobStatus.NOT_STARTED;
        LocalDate expiryDate = LocalDate.of(3000, 01, 01);

        //When
        Long jobId = jobService.saveJob(member.getId(), name, description, status, expiryDate);

        //Then
        Job job = jobService.findOne(jobId);

        assertEquals(member, job.getMember());
        assertEquals(name, job.getName());
        assertEquals(description, job.getDescription());
        assertEquals(status, job.getStatus());
        assertEquals(expiryDate, job.getExpiryDate());
    }

    @Test
    void 할일조회() {
        //Given
        Member member = new Member("gyeongsu");
        em.persist(member);
        String name = "장 보기";
        String description = "두부 한 모, 포카칩 3봉지, 배추 한 포기";
        JobStatus status = JobStatus.NOT_STARTED;
        LocalDate[] expiryDate = new LocalDate[4];
        expiryDate[0] = LocalDate.of(3000, 01, 01);
        expiryDate[1] = LocalDate.of(3000, 01, 02);
        expiryDate[2] = LocalDate.of(3000, 01, 03);
        expiryDate[3] = LocalDate.of(3000, 01, 04);

        jobService.saveJob(member.getId(), name, description, status, expiryDate[1]);
        jobService.saveJob(member.getId(), name, description, status, expiryDate[0]);
        jobService.saveJob(member.getId(), name, description, status, expiryDate[3]);
        jobService.saveJob(member.getId(), name, description, status, expiryDate[2]);

        JobSearch jobSearch = new JobSearch();
        jobSearch.setMemberName(null);

        //When
        List<Job> jobList = jobService.findJobs(jobSearch);

        //Then
        assertEquals(4, jobList.size());

        //마감일 기준 오름차순 정렬
        int i = 0;
        for (Job job : jobList) {
            assertEquals(expiryDate[i], job.getExpiryDate());
            i++;
        }
    }

    @Test
    void 특정회원_할일조회() {
        //Given
        Member member1 = new Member("James");
        Member member2 = new Member("Thomas");
        em.persist(member1);
        em.persist(member2);
        String name = "장 보기";
        String description = "두부 한 모, 포카칩 3봉지, 배추 한 포기";
        JobStatus status = JobStatus.NOT_STARTED;
        LocalDate expiryDate = LocalDate.now();

        jobService.saveJob(member1.getId(), name, description, status, expiryDate);
        jobService.saveJob(member2.getId(), name, description, status, expiryDate);
        jobService.saveJob(member1.getId(), name, description, status, expiryDate);
        jobService.saveJob(member2.getId(), name, description, status, expiryDate);

        JobSearch jobSearch = new JobSearch();
        jobSearch.setMemberName(member1.getName());

        //When
        List<Job> jobList = jobService.findJobs(jobSearch);

        //Then
        assertEquals(2, jobList.size());

        for (Job job : jobList) {
            assertEquals(member1, job.getMember());
        }
    }
}
