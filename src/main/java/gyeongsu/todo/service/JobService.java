package gyeongsu.todo.service;

import gyeongsu.todo.domain.Job;
import gyeongsu.todo.domain.JobStatus;
import gyeongsu.todo.domain.Member;
import gyeongsu.todo.dto.JobDto;
import gyeongsu.todo.repository.JobRepository;
import gyeongsu.todo.dto.JobSearch;
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
    public Long saveJob(JobDto jobDto) {
        Member member = memberRepository.findOne(jobDto.getMemberId());

        Job job = Job.createJob(member, jobDto.getName(), jobDto.getDescription(), jobDto.getStatus(), jobDto.getExpiryDate());
        jobRepository.save(job);

        return job.getId();
    }

    @Transactional
    public void updateJob(JobDto jobDto) {
        Job job = jobRepository.findOne(jobDto.getId());
        job.change(jobDto.getName(), jobDto.getDescription(), jobDto.getStatus(), jobDto.getExpiryDate());
    }

    public Job findOne(Long jobId) {
        return jobRepository.findOne(jobId);
    }

    public List<Job> findJobs(JobSearch jobSearch) {
        return jobRepository.findAll(jobSearch);
    }
}
