//package br.edu.ifpe.pontoif.pontoif.service;
//
//import br.edu.ifpe.pontoif.pontoif.dto.ClassroomDTO;
//import br.edu.ifpe.pontoif.pontoif.entity.Classroom;
//import br.edu.ifpe.pontoif.pontoif.mapper.ClassroomMapper;
//import br.edu.ifpe.pontoif.pontoif.repository.ClassroomRepository;
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
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class ClassroomServiceTest {
//
//    @Mock
//    private ClassroomRepository classroomRepository;
//
//    @Mock
//    private ClassroomMapper classroomMapper;
//
//    @InjectMocks
//    private ClassroomService classroomService;
//
//    @Test
//    void shouldInsertClassroom() {
//        // Given
//        ClassroomDTO classroomDTO = new ClassroomDTO();
//        classroomDTO.setCode("CC-2024-1");
//
//        Classroom classroom = new Classroom();
//        classroom.setCode("CC-2024-1");
//
//        when(classroomMapper.toEntity(classroomDTO)).thenReturn(classroom);
//        when(classroomRepository.save(classroom)).thenReturn(classroom);
//
//        // When
//        classroomService.insertClassroom(classroomDTO);
//
//        // Then
//        verify(classroomRepository, times(1)).save(classroom);
//        verify(classroomMapper, times(1)).toEntity(classroomDTO);
//    }
//
//    @Test
//    void shouldGetClassroomById() {
//        // Given
//        UUID id = UUID.randomUUID();
//        Classroom classroom = new Classroom();
//        classroom.setCode("SI-2024-2");
//
//        ClassroomDTO dto = new ClassroomDTO();
//        dto.setCode("SI-2024-2");
//
//        when(classroomRepository.findById(id)).thenReturn(Optional.of(classroom));
//        when(classroomMapper.toDTO(classroom)).thenReturn(dto);
//
//        // When
//        Optional<ClassroomDTO> result = classroomService.getClassroomById(id);
//
//        // Then
//        assertThat(result).isPresent();
//        assertThat(result.get().getCode()).isEqualTo("SI-2024-2");
//    }
//
//    @Test
//    void shouldGetAllClassrooms() {
//        // Given
//        Classroom classroom1 = new Classroom();
//        classroom1.setCode("CC-2024-1");
//
//        Classroom classroom2 = new Classroom();
//        classroom2.setCode("SI-2024-2");
//
//        ClassroomDTO dto1 = new ClassroomDTO();
//        dto1.setCode("CC-2024-1");
//
//        ClassroomDTO dto2 = new ClassroomDTO();
//        dto2.setCode("SI-2024-2");
//
//        when(classroomRepository.findAll()).thenReturn(List.of(classroom1, classroom2));
//        when(classroomMapper.toDTO(classroom1)).thenReturn(dto1);
//        when(classroomMapper.toDTO(classroom2)).thenReturn(dto2);
//
//        // When
//        List<ClassroomDTO> result = classroomService.getAllClassrooms();
//
//        // Then
//        assertThat(result).hasSize(2);
//        assertThat(result.get(0).getCode()).isEqualTo("CC-2024-1");
//        assertThat(result.get(1).getCode()).isEqualTo("SI-2024-2");
//    }
//
//    @Test
//    void shouldDeleteClassroom() {
//        // Given
//        UUID id = UUID.randomUUID();
//        Classroom classroom = new Classroom();
//        classroom.setCode("ES-2024-1");
//
//        when(classroomRepository.findById(id)).thenReturn(Optional.of(classroom));
//
//        // When
//        boolean result = classroomService.deleteClassroom(id);
//
//        // Then
//        assertThat(result).isTrue();
//        verify(classroomRepository, times(1)).delete(classroom);
//    }
//
//    @Test
//    void shouldReturnFalseWhenDeletingNonExistentClassroom() {
//        // Given
//        UUID id = UUID.randomUUID();
//        when(classroomRepository.findById(id)).thenReturn(Optional.empty());
//
//        // When
//        boolean result = classroomService.deleteClassroom(id);
//
//        // Then
//        assertThat(result).isFalse();
//        verify(classroomRepository, never()).delete(any());
//    }
//}
