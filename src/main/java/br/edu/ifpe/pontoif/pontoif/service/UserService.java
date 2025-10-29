package br.edu.ifpe.pontoif.pontoif.service;

import br.edu.ifpe.pontoif.pontoif.dto.*;
import br.edu.ifpe.pontoif.pontoif.entity.*;
import br.edu.ifpe.pontoif.pontoif.mapper.DisciplineMapper;
import br.edu.ifpe.pontoif.pontoif.mapper.UserMapper;
import br.edu.ifpe.pontoif.pontoif.repository.DisciplineRepository;
import br.edu.ifpe.pontoif.pontoif.repository.LessonRepository;
import br.edu.ifpe.pontoif.pontoif.repository.RecordRepository;
import br.edu.ifpe.pontoif.pontoif.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final DisciplineRepository disciplineRepository;
    private final LessonRepository lessonRepository;
    private final RecordRepository recordRepository;
    private final UserMapper userMapper;
    private final DisciplineMapper disciplineMapper;

    public List<UserDTO> getTeachers() {
        return userRepository
                .findByRole(Role.PROFESSOR)
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    public User getTeacherById(UUID id) {
        return userRepository
                .findByIdAndRole(id, Role.PROFESSOR)
                .orElse(null);
    }

    public List<DisciplineDTO> getDisciplineByTeacher(UUID id) {
        return disciplineRepository.findByUserId(id)
                .stream()
                .map(disciplineMapper::toDTO)
                .toList();
    }

    public ListPresentDTO getReportByDiscipline(UUID disciplineId) {
        var discipline = disciplineRepository.findById(disciplineId).orElseThrow();
        var records = recordRepository.findByDateBetweenAndLessonDisciplineId(
                LocalDateTime.now().plusHours(4),
                LocalDateTime.now(),
                disciplineId
        );
        var users = userRepository.findByDisciplineId(disciplineId);
        var totalUsers = users.size();
        var listPresent = users
                .stream()
                .map(x -> getUserPresentDTO(x, records))
                .toList();

        Map<Status, Long> summary = listPresent.stream()
                .collect(
                    Collectors.groupingBy(
                        UserPresentDTO::getStatus,
                        Collectors.counting()
                    )
                );

        return ListPresentDTO
                .builder()
                .userPresentDTOs(listPresent)
                .disciplineName(discipline.getName())
                .percentAbsent((summary.getOrDefault(Status.ABSENT, 0L) * 100) / totalUsers)
                .percentPresent((summary.getOrDefault(Status.PRESENT, 0L) * 100) / totalUsers)
                .build();
    }

    private UserPresentDTO getUserPresentDTO(User user, List<br.edu.ifpe.pontoif.pontoif.entity.Record> records) {
        var currentDate = LocalDate.now();

        var recordsByUser = records.stream()
                .filter(record -> record.getUser().getId().equals(user.getId()))
                .findFirst()
                .orElse(null);

        if (recordsByUser == null) {
            return UserPresentDTO
                    .builder()
                    .status(Status.ABSENT)
                    .name(user.getName())
                    .date(currentDate)
                    .build();
        }

        var recordDate = recordsByUser.getDate();

        return UserPresentDTO
                .builder()
                .status(Status.PRESENT)
                .name(user.getName())
                .timestamp(recordDate.toLocalTime())
                .date(recordDate.toLocalDate())
                .build();
    }

    public UserReportDTO getReportByUser(User user) {
        var disciplines = disciplineRepository.findByUserId(user.getId());
        var lesson = lessonRepository.findAllById(disciplines
                .stream()
                .map(Discipline::getId)
                .toList());

        return UserReportDTO.from(user, getDisciplinesByUser(disciplines, lesson, user.getId()));
    }

    private List<DisciplineReportDTO> getDisciplinesByUser(List<Discipline> disciplines, List<Lesson> lessons, UUID userId) {
        return disciplines.stream().map(discipline -> {
            var lessonsByDiscipline = lessons.stream()
                    .filter(lesson -> lesson.getDiscipline().getId().equals(discipline.getId()))
                    .findFirst()
                    .orElse(null);

            var attendedClasses = lessonsByDiscipline == null ? 0 : lessonsByDiscipline
                    .getRecords()
                    .stream()
                    .filter(x -> x.getUser().getId().equals(userId))
                    .toList()
                    .size();

            var percentageAssisted = lessonsByDiscipline == null ? 0 : (attendedClasses / discipline.getWorkload().doubleValue()) * 100;

            return DisciplineReportDTO.from(discipline, attendedClasses, percentageAssisted);
        }).toList();
    }

}
