//package br.edu.ifpe.pontoif.pontoif.service;
//
//import br.edu.ifpe.pontoif.pontoif.dto.BiometricSampleDTO;
//import br.edu.ifpe.pontoif.pontoif.dto.BiometricsDTO;
//import br.edu.ifpe.pontoif.pontoif.entity.Biometric;
//import br.edu.ifpe.pontoif.pontoif.entity.Lesson;
//import br.edu.ifpe.pontoif.pontoif.entity.Role;
//import br.edu.ifpe.pontoif.pontoif.entity.User;
//import br.edu.ifpe.pontoif.pontoif.mapper.BiometricMapper;
//import br.edu.ifpe.pontoif.pontoif.repository.BiometricRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.DayOfWeek;
//import java.time.LocalTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class BiometricServiceTest {
//
//    @Mock
//    private BiometricRepository biometricRepository;
//
//    @Mock
//    private BiometricMapper biometricMapper;
//
//    @Mock
//    private RecordService recordService;
//
//    @Mock
//    private LessonService lessonService;
//
//    @InjectMocks
//    private BiometricService biometricService;
//
//    @Test
//    void shouldInsertBiometric() {
//        // Given
////        BiometricSampleDTO biometricsDTO = new BiometricSampleDTO();
////        biometricsDTO.setImage(new byte[] {1, 2, 3});
////
////        Biometric biometric = new Biometric();
////        biometric.setId(123456789L);
////
////        when(biometricMapper.toEntity(biometricsDTO)).thenReturn(biometric);
////        when(biometricRepository.save(biometric)).thenReturn(biometric);
////
////        // When
////        biometricService.insertBiometric(biometricsDTO);
////
////        // Then
////        verify(biometricRepository, times(1)).save(biometric);
////        verify(biometricMapper, times(1)).toEntity(biometricsDTO);
//    }
//
//    @Test
//    void shouldGetBiometricById() {
//        // Given
//        Long id = 987654321L;
//        Biometric biometric = new Biometric();
//        biometric.setId(id);
//
//        BiometricsDTO dto = new BiometricsDTO();
//        dto.setId(id);
//
//        when(biometricRepository.findById(id)).thenReturn(Optional.of(biometric));
//        when(biometricMapper.toDTO(biometric)).thenReturn(dto);
//
//        // When
//        Optional<BiometricsDTO> result = biometricService.getBiometricById(id);
//
//        // Then
//        assertThat(result).isPresent();
//        assertThat(result.get().getId()).isEqualTo(id);
//    }
//
//    @Test
//    void shouldGetAllBiometrics() {
//        // Given
//        Biometric biometric1 = new Biometric();
//        biometric1.setId(111111111L);
//
//        Biometric biometric2 = new Biometric();
//        biometric2.setId(222222222L);
//
//        BiometricsDTO dto1 = new BiometricsDTO();
//        dto1.setId(111111111L);
//
//        BiometricsDTO dto2 = new BiometricsDTO();
//        dto2.setId(222222222L);
//
//        when(biometricRepository.findAll()).thenReturn(List.of(biometric1, biometric2));
//        when(biometricMapper.toDTO(biometric1)).thenReturn(dto1);
//        when(biometricMapper.toDTO(biometric2)).thenReturn(dto2);
//
//        // When
//        List<BiometricsDTO> result = biometricService.getAllBiometrics(0L);
//
//        // Then
//        assertThat(result).hasSize(2);
//        assertThat(result.get(0).getId()).isEqualTo(111111111L);
//        assertThat(result.get(1).getId()).isEqualTo(222222222L);
//    }
//
//    @Test
//    void shouldDeleteBiometric() {
//        // Given
//        Long id = 333333333L;
//        Biometric biometric = new Biometric();
//        biometric.setId(id);
//
//        when(biometricRepository.findById(id)).thenReturn(Optional.of(biometric));
//
//        // When
//        boolean result = biometricService.deleteBiometric(id);
//
//        // Then
//        assertThat(result).isTrue();
//        verify(biometricRepository, times(1)).delete(biometric);
//    }
//
//    @Test
//    void shouldMatchSampleAndCreateRecord() {
//        // Given
//        Long biometricId = 444444444L;
//        BiometricSampleDTO sampleDTO = new BiometricSampleDTO();
//        sampleDTO.setId(biometricId);
//
//        User user = new User();
//        user.setName("Test User");
//        user.setEmail("test@example.com");
//        user.setRegistration("12345");
//        user.setIsActive(true);
//        user.setType("STUDENT");
//        user.setRole(Role.STUDENT);
//
//        Biometric biometric = new Biometric();
//        biometric.setId(biometricId);
//        biometric.setUser(user);
//
//        Lesson lesson = new Lesson();
//        lesson.setDayOfWeek(DayOfWeek.MONDAY);
//        lesson.setStartTime(LocalTime.of(8, 0));
//        lesson.setEndTime(LocalTime.of(10, 0));
//
//        when(biometricRepository.findById(biometricId)).thenReturn(Optional.of(biometric));
//        when(lessonService.getCurrentLesson(user)).thenReturn(Optional.of(lesson));
//
//        // When
//        Optional<Biometric> result = biometricService.matchSample(sampleDTO);
//
//        // Then
//        assertThat(result).isPresent();
//        assertThat(result.get().getId()).isEqualTo(biometricId);
//        verify(recordService, times(1)).insertRecord(any());
//    }
//
//    @Test
//    void shouldMatchSampleButNotCreateRecordWhenNoCurrentLesson() {
//        // Given
//        Long biometricId = 555555555L;
//        BiometricSampleDTO sampleDTO = new BiometricSampleDTO();
//        sampleDTO.setId(biometricId);
//
//        User user = new User();
//        user.setName("Test User");
//        user.setEmail("test@example.com");
//        user.setRegistration("12345");
//        user.setIsActive(true);
//        user.setType("STUDENT");
//        user.setRole(Role.STUDENT);
//
//        Biometric biometric = new Biometric();
//        biometric.setId(biometricId);
//        biometric.setUser(user);
//
//        when(biometricRepository.findById(biometricId)).thenReturn(Optional.of(biometric));
//        when(lessonService.getCurrentLesson(user)).thenReturn(Optional.empty());
//
//        // When
//        Optional<Biometric> result = biometricService.matchSample(sampleDTO);
//
//        // Then
//        assertThat(result).isPresent();
//        assertThat(result.get().getId()).isEqualTo(biometricId);
//        verify(recordService, never()).insertRecord(any());
//    }
//}
