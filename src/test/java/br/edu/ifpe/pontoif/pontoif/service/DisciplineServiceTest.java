//package br.edu.ifpe.pontoif.pontoif.service;
//
//import br.edu.ifpe.pontoif.pontoif.dto.DisciplineDTO;
//import br.edu.ifpe.pontoif.pontoif.entity.Discipline;
//import br.edu.ifpe.pontoif.pontoif.mapper.DisciplineMapper;
//import br.edu.ifpe.pontoif.pontoif.repository.DisciplineRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class DisciplineServiceTest {
//
//    @Mock
//    private DisciplineRepository disciplineRepository;
//
//    @Mock
//    private DisciplineMapper disciplineMapper;
//
//    @InjectMocks
//    private DisciplineService disciplineService;
//
//    @Test
//    void shouldInsertDiscipline() {
//        // Given
//        DisciplineDTO disciplineDTO = new DisciplineDTO();
//        disciplineDTO.setName("Matemática Discreta");
//        disciplineDTO.setWorkload(60);
//
//        Discipline discipline = new Discipline();
//        discipline.setName("Matemática Discreta");
//        discipline.setWorkload(60);
//
//        when(disciplineMapper.toEntity(disciplineDTO)).thenReturn(discipline);
//        when(disciplineRepository.save(discipline)).thenReturn(discipline);
//
//        // When
//        disciplineService.insertDiscipline(disciplineDTO);
//
//        // Then
//        verify(disciplineRepository, times(1)).save(discipline);
//        verify(disciplineMapper, times(1)).toEntity(disciplineDTO);
//    }
//
//    @Test
//    void shouldGetAllDisciplines() {
//        // Given
//        Discipline discipline1 = new Discipline();
//        discipline1.setName("Algoritmos");
//        discipline1.setWorkload(80);
//
//        Discipline discipline2 = new Discipline();
//        discipline2.setName("Estrutura de Dados");
//        discipline2.setWorkload(70);
//
//        DisciplineDTO dto1 = new DisciplineDTO();
//        dto1.setName("Algoritmos");
//        dto1.setWorkload(80);
//
//        DisciplineDTO dto2 = new DisciplineDTO();
//        dto2.setName("Estrutura de Dados");
//        dto2.setWorkload(70);
//
//        when(disciplineRepository.findAll()).thenReturn(List.of(discipline1, discipline2));
//        when(disciplineMapper.toDTO(discipline1)).thenReturn(dto1);
//        when(disciplineMapper.toDTO(discipline2)).thenReturn(dto2);
//
//        // When
//        List<DisciplineDTO> result = disciplineService.getAllDisciplines();
//
//        // Then
//        assertThat(result).hasSize(2);
//        assertThat(result.get(0).getName()).isEqualTo("Algoritmos");
//        assertThat(result.get(1).getName()).isEqualTo("Estrutura de Dados");
//    }
//
//    @Test
//    void shouldGetDisciplineById() {
//        // Given
//        UUID id = UUID.randomUUID();
//        Discipline discipline = new Discipline();
//        discipline.setName("Banco de Dados");
//        discipline.setWorkload(60);
//
//        DisciplineDTO dto = new DisciplineDTO();
//        dto.setName("Banco de Dados");
//        dto.setWorkload(60);
//
//        when(disciplineRepository.findById(id)).thenReturn(Optional.of(discipline));
//        when(disciplineMapper.toDTO(discipline)).thenReturn(dto);
//
//        // When
//        Optional<DisciplineDTO> result = disciplineService.getDisciplineById(id);
//
//        // Then
//        assertThat(result).isPresent();
//        assertThat(result.get().getName()).isEqualTo("Banco de Dados");
//    }
//
//    @Test
//    void shouldReturnEmptyWhenDisciplineNotFound() {
//        // Given
//        UUID id = UUID.randomUUID();
//        when(disciplineRepository.findById(id)).thenReturn(Optional.empty());
//
//        // When
//        Optional<DisciplineDTO> result = disciplineService.getDisciplineById(id);
//
//        // Then
//        assertThat(result).isEmpty();
//    }
//
//    @Test
//    void shouldUpdateDiscipline() {
//        // Given
//        UUID id = UUID.randomUUID();
//        Discipline existing = new Discipline();
//        existing.setName("Programação I");
//        existing.setWorkload(60);
//
//        DisciplineDTO updateDTO = new DisciplineDTO();
//        updateDTO.setCorrelationId(UUID.randomUUID());
//        updateDTO.setName("Programação II");
//        updateDTO.setWorkload(80);
//
//        Discipline updated = new Discipline();
//        updated.setCorrelationId(updateDTO.getCorrelationId());
//        updated.setName(updateDTO.getName());
//        updated.setWorkload(updateDTO.getWorkload());
//
//        DisciplineDTO resultDTO = new DisciplineDTO();
//        resultDTO.setName("Programação II");
//        resultDTO.setWorkload(80);
//
//        when(disciplineRepository.findById(id)).thenReturn(Optional.of(existing));
//        when(disciplineRepository.save(any(Discipline.class))).thenReturn(updated);
//        when(disciplineMapper.toDTO(updated)).thenReturn(resultDTO);
//
//        // When
//        Optional<DisciplineDTO> result = disciplineService.updateDiscipline(id, updateDTO);
//
//        // Then
//        assertThat(result).isPresent();
//        assertThat(result.get().getName()).isEqualTo("Programação II");
//        assertThat(result.get().getWorkload()).isEqualTo(80);
//    }
//
//    @Test
//    void shouldDeleteDiscipline() {
//        // Given
//        UUID id = UUID.randomUUID();
//        Discipline discipline = new Discipline();
//        discipline.setName("Redes de Computadores");
//
//        when(disciplineRepository.findById(id)).thenReturn(Optional.of(discipline));
//
//        // When
//        boolean result = disciplineService.deleteDiscipline(id);
//
//        // Then
//        assertThat(result).isTrue();
//        verify(disciplineRepository, times(1)).delete(discipline);
//    }
//
//    @Test
//    void shouldReturnFalseWhenDeletingNonExistentDiscipline() {
//        // Given
//        UUID id = UUID.randomUUID();
//        when(disciplineRepository.findById(id)).thenReturn(Optional.empty());
//
//        // When
//        boolean result = disciplineService.deleteDiscipline(id);
//
//        // Then
//        assertThat(result).isFalse();
//        verify(disciplineRepository, never()).delete(any());
//    }
//}
