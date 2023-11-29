package gyeongsu.todo.service;

import gyeongsu.todo.domain.Job;
import gyeongsu.todo.domain.JobStatus;
import gyeongsu.todo.domain.Member;
import gyeongsu.todo.dto.JobDto;
import gyeongsu.todo.dto.JobSearch;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

        JobDto jobDto = new JobDto(member.getId(), name, description, status, expiryDate);

        //When
        Long jobId = jobService.saveJob(jobDto);

        //Then
        Job job = jobService.findOne(jobId);

        assertEquals(member, job.getMember());
        assertEquals(name, job.getName());
        assertEquals(description, job.getDescription());
        assertEquals(status, job.getStatus());
        assertEquals(expiryDate, job.getExpiryDate());
    }

    @Test
    void 할일수정() {
        //Given
        Member member = new Member("gyeongsu");
        em.persist(member);
        String name = "장 보기";
        String description = "두부 한 모, 포카칩 3봉지, 배추 한 포기";
        JobStatus status = JobStatus.NOT_STARTED;
        LocalDate expiryDate = LocalDate.of(3000, 01, 01);

        JobDto jobDto = new JobDto(member.getId(), name, description, status, expiryDate);

        Long jobId = jobService.saveJob(jobDto);

        //When
        jobDto.setId(jobId);
        jobDto.setName("약 먹기");
        jobDto.setDescription("식후 30분 3정 복용");
        jobDto.setStatus(JobStatus.DONE);
        jobDto.setExpiryDate(LocalDate.of(3000, 01, 02));
        jobService.updateJob(jobDto);

        //Then
        Job job = jobService.findOne(jobId);
        assertEquals("약 먹기", job.getName());
        assertEquals("식후 30분 3정 복용", job.getDescription());
        assertEquals(JobStatus.DONE, job.getStatus());
        assertEquals(LocalDate.of(3000, 01, 02), job.getExpiryDate());
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

        JobDto[] jobDto = new JobDto[4];
        for (int i = 0; i < 4; i++) {
            jobDto[i] = new JobDto(member.getId(), name, description, status, expiryDate[i]);
        }

        jobService.saveJob(jobDto[1]);
        jobService.saveJob(jobDto[0]);
        jobService.saveJob(jobDto[3]);
        jobService.saveJob(jobDto[2]);

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

        JobDto jobDto1 = new JobDto(member1.getId(), name, description, status, expiryDate);
        JobDto jobDto2 = new JobDto(member2.getId(), name, description, status, expiryDate);

        jobService.saveJob(jobDto1);
        jobService.saveJob(jobDto2);
        jobService.saveJob(jobDto1);
        jobService.saveJob(jobDto2);

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
