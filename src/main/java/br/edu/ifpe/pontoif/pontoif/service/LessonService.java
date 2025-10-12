package br.edu.ifpe.pontoif.pontoif.service;

import br.edu.ifpe.pontoif.pontoif.dto.LessonDTO;
import br.edu.ifpe.pontoif.pontoif.mapper.LessonMapper;
import br.edu.ifpe.pontoif.pontoif.repository.LessonRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonService {
    //TODO: Implementar update

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    @Transactional
    public void insertLesson(final LessonDTO lessonDTO) {
        lessonRepository.save(lessonMapper.toEntity(lessonDTO));
    }

    public Optional<LessonDTO> getLessonById(final UUID uuid) {
        return lessonRepository.findById(uuid).map(lessonMapper::toDTO);
    }

    public List<LessonDTO> getAllLessons() {
        return lessonRepository.findAll()
                .stream()
                .map(lessonMapper::toDTO)
                .toList();
    }

    @Transactional
    public Optional<LessonDTO> updateLesson(UUID uuid, final LessonDTO lessonDTO) {
        return lessonRepository.findById(uuid).map(existing -> {
            existing.setDayOfWeek(lessonDTO.getDayOfWeek());
            existing.setStartTime(lessonDTO.getStartTime());
            existing.setEndTime(lessonDTO.getEndTime());
            return lessonMapper.toDTO(lessonRepository.save(existing));
        });
    }

    @Transactional
    public boolean deleteLesson(final UUID uuid) {
        return lessonRepository.findById(uuid).map(lesson -> {
            lessonRepository.delete(lesson);
            return true;
        }).orElse(false);
    }
}