package br.edu.ifpe.pontoif.pontoif.service;

import br.edu.ifpe.pontoif.pontoif.dto.SubjectOfferingDTO;
import br.edu.ifpe.pontoif.pontoif.entity.*;
import br.edu.ifpe.pontoif.pontoif.exception.NotFoundException;
import br.edu.ifpe.pontoif.pontoif.mapper.SubjectOfferingMapper;
import br.edu.ifpe.pontoif.pontoif.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SubjectOfferingService {

    private final SubjectOfferingRepository offeringRepository;
    private final CourseSubjectRepository courseSubjectRepository;
    private final ClassroomRepository classroomRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ClassSessionRepository classSessionRepository;
    private final AttendanceRecordRepository attendanceRecordRepository;

    private final SubjectOfferingMapper mapper;

    public void create(SubjectOfferingDTO dto) {

        CourseSubject cs = courseSubjectRepository.findById(dto.getCourseSubjectId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid courseSubjectId"));

        Classroom classroom = classroomRepository.findById(dto.getClassroomId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid classroomId"));

        User teacher = userRepository.findById((dto.getTeacherId()))
                .orElseThrow(() -> new IllegalArgumentException("Invalid teacherId"));

        SubjectOffering entity = new SubjectOffering();
        entity.setCourseSubject(cs);
        entity.setClassroom(classroom);
        entity.setTeacher(teacher);
        entity.setTerm(dto.getTerm());
        entity.setSchedule(dto.getSchedule());

        offeringRepository.save(entity);
        mapper.toDTO(entity);
    }

    @Transactional
    public Optional<SubjectOfferingDTO> update(Long id, SubjectOfferingDTO dto) {

        return offeringRepository.findById(id).map(existing -> {

            CourseSubject cs = courseSubjectRepository.findById(dto.getCourseSubjectId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid courseSubjectId"));

            Classroom classroom = classroomRepository.findById(dto.getClassroomId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid classroomId"));

            User teacher = userRepository.findById((dto.getTeacherId()))
                    .orElseThrow(() -> new IllegalArgumentException("Invalid teacherId"));

            existing.setCourseSubject(cs);
            existing.setClassroom(classroom);
            existing.setTeacher(teacher);
            existing.setTerm(dto.getTerm());
            existing.setSchedule(dto.getSchedule());

            offeringRepository.save(existing);

            return mapper.toDTO(existing);
        });
    }

    public List<SubjectOfferingDTO> getAll() {
        return offeringRepository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    public Optional<SubjectOfferingDTO> getById(Long id) {
        return offeringRepository.findById(id)
                .map(mapper::toDTO);
    }

    @Transactional
    public boolean delete(Long id) {
        return offeringRepository.findById(id)
                .map(subjectOffering -> {
                    offeringRepository.delete(subjectOffering);
                    return true;
                })
                .orElse(false);
    }

    public List<Enrollment> getEnrollments(Long offeringId) {
        return enrollmentRepository.findAllByOffering_Id(offeringId);
    }

    public List<AttendanceRecord> getAttendance(Long offeringId) {

        List<ClassSession> sessions =
                classSessionRepository.findAllByOffering_Id(offeringId);

        return sessions.stream()
                .flatMap(session ->
                        attendanceRecordRepository
                                .findAllBySession_Id(session.getId())
                                .stream()
                )
                .toList();
    }

    @Transactional
    public void startClassSession(Long offeringId, UUID teacherId) {
        SubjectOffering offering = offeringRepository.findById(offeringId)
                .orElseThrow(() -> new NotFoundException("Offering not found"));

        if (!offering.getTeacher().getId().equals(teacherId)) {
            throw new SecurityException("Teacher not authorized for this offering");
        }

        List<ClassSession> sessions = classSessionRepository.findAllByOffering_Id(offeringId);
        boolean hasActive = sessions.stream().anyMatch(s -> s.getSessionEnd() == null);
        if (hasActive) {
            throw new IllegalStateException("There is already an active session for this offering");
        }

        ClassSession session = new ClassSession();
        session.setOffering(offering);
        session.setSessionStart(Instant.now());
        session.setCreatedAt(Instant.now());

        classSessionRepository.save(session);
    }

    @Transactional
    public void endClassSession(Long offeringId, UUID teacherId) {
        SubjectOffering offering = offeringRepository.findById(offeringId)
                .orElseThrow(() -> new NoSuchElementException("Offering not found"));

        if (!offering.getTeacher().getId().equals(teacherId)) {
            throw new SecurityException("Teacher not authorized for this offering");
        }

        List<ClassSession> sessions = classSessionRepository.findAllByOffering_Id(offeringId);
        ClassSession active = sessions.stream()
                .filter(s -> s.getSessionEnd() == null)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No active session to end"));

        active.setSessionEnd(Instant.now());

        classSessionRepository.save(active);
    }
}