package br.edu.ifpe.pontoif.pontoif.mapper;

import br.edu.ifpe.pontoif.pontoif.dto.RecordDTO;
import org.mapstruct.Mapper;
import br.edu.ifpe.pontoif.pontoif.entity.Record;

@Mapper(componentModel = "spring")
public interface RecordMapper {
    //TODO: Verificar esse mapper

    Record toEntity(RecordDTO recordDTO);

    RecordDTO toDTO(Record record);
}


