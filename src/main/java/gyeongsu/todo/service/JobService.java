package gyeongsu.todo.service;

import gyeongsu.todo.domain.Job;
import gyeongsu.todo.domain.JobStatus;
import gyeongsu.todo.domain.Member;
import gyeongsu.todo.repository.JobRepository;
import gyeongsu.todo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class JobService {
    private final JobRepository jobRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public JobService(JobRepository jobRepository, MemberRepository memberRepository) {
        this.jobRepository = jobRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Long saveJob(Long memberId, String name, String description, JobStatus status, LocalDate expiryDate) {
        Member member = memberRepository.findOne(memberId);

        Job job = Job.createJob(member, name, description, status, expiryDate);
        jobRepository.save(job);

        return job.getId();
    }

    @Transactional
    public void deleteJob() {

    }
    public Job findOne(Long jobId) {
        return jobRepository.findOne(jobId);
    }

    public List<Job> findJobs() {
        return jobRepository.findAll();
    }
}
