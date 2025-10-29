package br.edu.ifpe.pontoif.pontoif.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListPresentDTO {
    private long percentPresent;
    private long percentAbsent;
    private String disciplineName;
    private List<UserPresentDTO> userPresentDTOs;
}
