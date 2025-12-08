package br.edu.ifpe.pontoif.pontoif.mapper;


import br.edu.ifpe.pontoif.pontoif.dto.EnrollmentDTO;
import br.edu.ifpe.pontoif.pontoif.entity.Enrollment;
import br.edu.ifpe.pontoif.pontoif.entity.SubjectOffering;
import br.edu.ifpe.pontoif.pontoif.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.UUID;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Enrollment toEntity(EnrollmentDTO dto);

    EnrollmentDTO toDTO(Enrollment entity);



    public default void updateEntityFromDTO(
            EnrollmentDTO dto, Enrollment entity,
            SubjectOffering offering, User student) {

        entity.setOffering(offering);
        entity.setStudent(student);
        entity.setStatus(dto.getStatus());
    }
}
