package br.edu.ifpe.pontoif.pontoif.mock;

import br.edu.ifpe.pontoif.pontoif.entity.*;
import br.edu.ifpe.pontoif.pontoif.entity.Record;
import br.edu.ifpe.pontoif.pontoif.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@Component
@Profile("dev") // executa apenas quando o perfil ativo for 'dev'
@RequiredArgsConstructor
public class DataMockLoader {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final DisciplineRepository disciplineRepository;
    private final ClassroomRepository classroomRepository;
    private final LessonRepository lessonRepository;
    private final BiometricRepository biometricRepository;
    private final RecordRepository recordRepository;

    @PostConstruct
    public void loadMockData() {
        log.info("Inserindo dados mockados (RabbitMQ desativado)...");


        lessonRepository.deleteAll();
        disciplineRepository.deleteAll();
        classroomRepository.deleteAll();
        courseRepository.deleteAll();
        userRepository.deleteAll();
        biometricRepository.deleteAll();


        User user = new User();
        user.setName("Maria dos Santos");
        user.setEmail("maria.santos@ifpe.edu.br");
        user.setRegistration("IFPE2025A01");
        user.setRole(Role.STUDENT);
        user.setIsActive(true);
        userRepository.save(user);

        Biometric biometric = new Biometric();
        biometric.setUser(user);
        biometricRepository.save(biometric);

        Course course = new Course();
        course.setName("Engenharia de Software");
        course.setAcronym("ESW");
        course.setStartTime(LocalDateTime.now().minusMonths(2));
        course.setEndTime(LocalDateTime.now().plusMonths(34));
        course.setDurationInMonths(36);
        courseRepository.save(course);

        Classroom classroom = new Classroom();
        classroom.setCode("IF-2025.1");
        classroom.setCourse(course);
        classroomRepository.save(classroom);

        Discipline discipline = new Discipline();
        discipline.setName("Programação Web II");
        discipline.setWorkload(80);
        discipline.setClassroom(classroom);
        disciplineRepository.save(discipline);

        Lesson lesson = new Lesson();
        lesson.setIsActive(true);
        lesson.setDayOfWeek(DayOfWeek.MONDAY);
        lesson.setStartTime(LocalTime.of(8, 0));          // 08:00
        lesson.setEndTime(LocalTime.of(2, 40));           // 09:40
        lesson.setDiscipline(discipline);
        lessonRepository.save(lesson);

        Record record = new Record();
        record.setLesson(lesson);
        record.setBiometric(biometric);
        record.setUser(user);
        recordRepository.save(record);

        log.info("Dados mockados inseridos com sucesso!");

        System.out.println("userID:" + user.toString());
    }
}
