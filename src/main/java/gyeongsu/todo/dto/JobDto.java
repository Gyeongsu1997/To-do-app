package gyeongsu.todo.dto;

import gyeongsu.todo.domain.JobStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * use between controller and service layer
 */
@Getter @Setter
public class JobDto {
    private Long id;

    private Long memberId;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private JobStatus status;

    private LocalDate expiryDate;

    public JobDto() {}

    public JobDto(Long memberId, String name, String description, JobStatus status, LocalDate expiryDate) {
        this.memberId = memberId;
        this.name = name;
        this.description = description;
        this.status = status;
        this.expiryDate = expiryDate;
    }
}
