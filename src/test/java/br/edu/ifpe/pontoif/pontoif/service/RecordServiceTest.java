//package br.edu.ifpe.pontoif.pontoif.service;
//
//import br.edu.ifpe.pontoif.pontoif.dto.RecordDTO;
//import br.edu.ifpe.pontoif.pontoif.entity.Record;
//import br.edu.ifpe.pontoif.pontoif.entity.Role;
//import br.edu.ifpe.pontoif.pontoif.entity.User;
//import br.edu.ifpe.pontoif.pontoif.mapper.RecordMapper;
//import br.edu.ifpe.pontoif.pontoif.repository.RecordRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class RecordServiceTest {
//
//    @Mock
//    private RecordRepository recordRepository;
//
//    @Mock
//    private RecordMapper recordMapper;
//
//    @InjectMocks
//    private RecordService recordService;
//
//    @Test
//    void shouldInsertRecord() {
//        // Given
//        RecordDTO recordDTO = new RecordDTO();
//        recordDTO.setDate(LocalDateTime.now());
//
//        Record record = new Record();
//        record.setDate(LocalDateTime.now());
//
//        when(recordMapper.toEntity(recordDTO)).thenReturn(record);
//        when(recordRepository.save(record)).thenReturn(record);
//
//        // When
//        recordService.insertRecord(recordDTO);
//
//        // Then
//        verify(recordRepository, times(1)).save(record);
//        verify(recordMapper, times(1)).toEntity(recordDTO);
//    }
//
//    @Test
//    void shouldGetRecordById() {
//        // Given
//        UUID id = UUID.randomUUID();
//        Record record = new Record();
//        record.setDate(LocalDateTime.now());
//
//        RecordDTO dto = new RecordDTO();
//        dto.setDate(LocalDateTime.now());
//
//        when(recordRepository.findById(id)).thenReturn(Optional.of(record));
//        when(recordMapper.toDTO(record)).thenReturn(dto);
//
//        // When
//        Optional<RecordDTO> result = recordService.getRecordById(id);
//
//        // Then
//        assertThat(result).isPresent();
//        assertThat(result.get().getDate()).isNotNull();
//    }
//
//    @Test
//    void shouldGetAllRecords() {
//        // Given
//        Record record1 = new Record();
//        record1.setDate(LocalDateTime.now().minusDays(1));
//
//        Record record2 = new Record();
//        record2.setDate(LocalDateTime.now());
//
//        RecordDTO dto1 = new RecordDTO();
//        dto1.setDate(LocalDateTime.now().minusDays(1));
//
//        RecordDTO dto2 = new RecordDTO();
//        dto2.setDate(LocalDateTime.now());
//
//        when(recordRepository.findAll()).thenReturn(List.of(record1, record2));
//        when(recordMapper.toDTO(record1)).thenReturn(dto1);
//        when(recordMapper.toDTO(record2)).thenReturn(dto2);
//
//        // When
//        List<RecordDTO> result = recordService.getAllRecords();
//
//        // Then
//        assertThat(result).hasSize(2);
//    }
//
//    @Test
//    void shouldUpdateRecord() {
//        // Given
//        UUID id = UUID.randomUUID();
//        Record existing = new Record();
//        existing.setDate(LocalDateTime.now().minusDays(1));
//
//        RecordDTO updateDTO = new RecordDTO();
//        updateDTO.setDate(LocalDateTime.now());
//
//        Record updated = new Record();
//        updated.setDate(LocalDateTime.now());
//
//        RecordDTO resultDTO = new RecordDTO();
//        resultDTO.setDate(LocalDateTime.now());
//
//        when(recordRepository.findById(id)).thenReturn(Optional.of(existing));
//        when(recordRepository.save(any(Record.class))).thenReturn(updated);
//        when(recordMapper.toDTO(updated)).thenReturn(resultDTO);
//
//        // When
//        Optional<RecordDTO> result = recordService.updateRecord(id, updateDTO);
//
//        // Then
//        assertThat(result).isPresent();
//        assertThat(result.get().getDate()).isAfter(LocalDateTime.now().minusMinutes(1));
//    }
//
//    @Test
//    void shouldDeleteRecord() {
//        // Given
//        UUID id = UUID.randomUUID();
//        Record record = new Record();
//        record.setDate(LocalDateTime.now());
//
//        when(recordRepository.findById(id)).thenReturn(Optional.of(record));
//
//        // When
//        boolean result = recordService.deleteRecord(id);
//
//        // Then
//        assertThat(result).isTrue();
//        verify(recordRepository, times(1)).delete(record);
//    }
//
//    @Test
//    void shouldGetRecordsByUser() {
//        // Given
//        User user = new User();
//        user.setName("Test User");
//        user.setEmail("test@example.com");
//        user.setRegistration("12345");
//        user.setIsActive(true);
//        user.setType("STUDENT");
//        user.setRole(Role.STUDENT);
//
//        Record record1 = new Record();
//        record1.setDate(LocalDateTime.now());
//
//        Record record2 = new Record();
//        record2.setDate(LocalDateTime.now().minusHours(1));
//
//        when(recordRepository.findAllByUserWithLessons(user)).thenReturn(List.of(record1, record2));
//
//        // When
//        List<Record> result = recordService.getRecordsByUser(user);
//
//        // Then
//        assertThat(result).hasSize(2);
//        verify(recordRepository, times(1)).findAllByUserWithLessons(user);
//    }
//}
