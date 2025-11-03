package br.edu.ifpe.pontoif.pontoif.dto;

import br.edu.ifpe.pontoif.pontoif.entity.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserReportDTO {
    private String nome;
    private List<DisciplineReportDTO> disciplines;

    public static UserReportDTO from(User user, List<DisciplineReportDTO> disciplines) {
        return UserReportDTO.builder()
                .nome(user.getName())
                .disciplines(disciplines)
                .build();
    }
}

