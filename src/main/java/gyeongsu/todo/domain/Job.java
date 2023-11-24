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

    private String description;

    @Enumerated(EnumType.STRING)
    private JobStatus status;

    private LocalDate expiryDate;

    public void setMember(Member member) {
        this.member = member;
        member.getJobs().add(this);
    }
}
