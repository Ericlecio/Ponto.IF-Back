package br.edu.ifpe.pontoif.pontoif.mock;

import br.edu.ifpe.pontoif.pontoif.entity.*;
import br.edu.ifpe.pontoif.pontoif.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@Slf4j
@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataMockLoader {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final SubjectRepository subjectRepository;
    private final CourseSubjectRepository courseSubjectRepository;
    private final ClassroomRepository classroomRepository;
    private final SubjectOfferingRepository subjectOfferingRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ClassSessionRepository classSessionRepository;
    private final AttendanceRecordRepository attendanceRecordRepository;
    private final BiometricRepository biometricRepository;

    @PostConstruct
    public void loadMockData() {
        log.info("Inserindo dados mockados (perfil DEV)...");

        attendanceRecordRepository.deleteAll();
        classSessionRepository.deleteAll();
        enrollmentRepository.deleteAll();
        subjectOfferingRepository.deleteAll();
        classroomRepository.deleteAll();
        courseSubjectRepository.deleteAll();
        subjectRepository.deleteAll();
        courseRepository.deleteAll();
        biometricRepository.deleteAll();
        userRepository.deleteAll();

        // -------------------------
        // Usuário (Aluno)
        // -------------------------
        User student = User.builder()
                .name("Maria dos Santos")
                .email("maria.santos@ifpe.edu.br")
                .registration("IFPE2025A01")
                .role(Role.STUDENT)
                .build();
        userRepository.save(student);

        // Biometria do aluno
        Biometric biometric = new Biometric();
        biometric.setUser(student);
        biometric.setTemplate("mockTemplate".getBytes());
        biometricRepository.save(biometric);

        // Professor
        User teacher = User.builder()
                .name("João Professor")
                .email("joao.prof@ifpe.edu.br")
                .registration("IFPE-TCH-001")
                .role(Role.TEACHER)
                .build();
        userRepository.save(teacher);

        // -------------------------
        // Curso
        // -------------------------
        Course course = Course.builder()
                .name("Engenharia de Software")
                .code("ESW")
                .build();
        courseRepository.save(course);

        // -------------------------
        // Disciplina
        // -------------------------
        Subject subject = Subject.builder()
                .name("Programação Web II")
                .code("PW2")
                .description("Desenvolvimento de aplicações web avançadas.")
                .build();
        subjectRepository.save(subject);

        // -------------------------
        // Curso-Disciplina
        // -------------------------
        CourseSubject cs = CourseSubject.builder()
                .course(course)
                .subject(subject)
                .build();
        courseSubjectRepository.save(cs);

        // -------------------------
        // Sala
        // -------------------------
        Classroom classroom = Classroom.builder()
                .name("Sala 15 - Bloco A")
                .location("1º Andar")
                .build();
        classroomRepository.save(classroom);

        // -------------------------
        // Oferta de Disciplina
        // -------------------------
        SubjectOffering offering = SubjectOffering.builder()
                .courseSubject(cs)
                .classroom(classroom)
                .teacher(teacher)
                .term("2025.1")
                .schedule(Map.of("day", "MONDAY", "start", "08:00", "end", "10:00"))
                .build();
        subjectOfferingRepository.save(offering);

        // -------------------------
        // Matrícula
        // -------------------------
        Enrollment enrollment = Enrollment.builder()
                .offering(offering)
                .student(student)
                .status(EnrollmentStatus.ACTIVE)
                .build();
        enrollmentRepository.save(enrollment);

        // -------------------------
        // Sessão de aula
        // -------------------------
        ClassSession session = ClassSession.builder()
                .offering(offering)
                .sessionStart(Instant.now().minusSeconds(3600))
                .sessionEnd(Instant.now())
                .notes("Primeira aula do semestre")
                .build();
        classSessionRepository.save(session);

        // -------------------------
        // Registro de presença
        // -------------------------
        AttendanceRecord record = AttendanceRecord.builder()
                .session(session)
                .student(student)
                .biometricHash("hash123".getBytes())
                .status(AttendanceStatus.PRESENT)
                .confidence(0.97)
                .metadata(Map.of("source", "mock", "device", "test"))
                .build();
        attendanceRecordRepository.save(record);

        log.info("Mock carregado com sucesso!");
        log.info("Aluno criado: {}", student.getId());
        log.info("Professor criado: {}", teacher.getId());
        log.info("Matrícula: {}", enrollment.getId());
    }
}