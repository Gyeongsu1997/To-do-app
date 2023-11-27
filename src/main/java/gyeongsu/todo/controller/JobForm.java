package gyeongsu.todo.controller;

import gyeongsu.todo.domain.JobStatus;
import gyeongsu.todo.domain.Member;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class JobForm {
    private Long id;

    private Long memberId;

    @NotEmpty(message = "이름을 입력해 주십시오.")
    private String name;

    private String description;
    
    @Enumerated(EnumType.STRING)
    private JobStatus status;

    private LocalDate expiryDate;
}
