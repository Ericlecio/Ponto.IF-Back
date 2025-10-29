package br.edu.ifpe.pontoif.pontoif.dto;

import br.edu.ifpe.pontoif.pontoif.entity.Discipline;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisciplineReportDTO {
    private String disciplineName;
    private Integer totalClasses;
    private Integer attendedClasses;
    private Double percentageAssisted;

    public static DisciplineReportDTO from(Discipline discipline, Integer attendedClasses, Double percentageAssisted) {
        return DisciplineReportDTO.builder()
                .disciplineName(discipline.getName())
                .totalClasses(discipline.getWorkload())
                .attendedClasses(attendedClasses)
                .percentageAssisted(percentageAssisted)
                .build();
    }
}
