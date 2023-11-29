package gyeongsu.todo.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class Job {
    @Id @GeneratedValue
    @Column(name = "job_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private JobStatus status;

    private LocalDate expiryDate;

    protected Job() {}

    public void setMember(Member member) {
        this.member = member;
        member.getJobs().add(this);
    }

    public static Job createJob(Member member, String name, String description, JobStatus status, LocalDate expiryDate) {
        Job job = new Job();
        job.setMember(member);
        job.name = name;
        job.description = description;
        job.status = status;
        job.expiryDate = expiryDate;
        return job;
    }

    public void change(String name, String description, JobStatus status, LocalDate expiryDate) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.expiryDate = expiryDate;
    }
}
