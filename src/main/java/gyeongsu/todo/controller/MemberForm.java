package gyeongsu.todo.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {
    @NotEmpty(message = "이름을 입력해 주세요.")
    private String name;
}
