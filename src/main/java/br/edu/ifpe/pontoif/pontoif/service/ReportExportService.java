package br.edu.ifpe.pontoif.pontoif.service;

import br.edu.ifpe.pontoif.pontoif.dto.StudentAttendanceReportDTO;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportExportService {

    public byte[] exportToExcel(List<StudentAttendanceReportDTO> reportData) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Attendance Report");

        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Row headerRow = sheet.createRow(0);
        String[] headers = {
            "Matrícula", "Nome do Aluno", "Email", "Data da Aula",
            "Início", "Fim", "Status", "Presenças", "Total de Aulas",
            "Percentual de Presença (%)"
        };

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowNum = 1;
        for (StudentAttendanceReportDTO data : reportData) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(data.getStudentRegistration() != null ? data.getStudentRegistration() : "");
            row.createCell(1).setCellValue(data.getStudentName());
            row.createCell(2).setCellValue(data.getStudentEmail());
            row.createCell(3).setCellValue(data.getSessionDate());
            row.createCell(4).setCellValue(data.getSessionStart());
            row.createCell(5).setCellValue(data.getSessionEnd());
            row.createCell(6).setCellValue(data.getAttendanceStatus());
            row.createCell(7).setCellValue(data.getAttendedSessions());
            row.createCell(8).setCellValue(data.getTotalSessions());
            row.createCell(9).setCellValue(String.format("%.2f", data.getAttendancePercentage()));
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }

    public String exportToCsv(List<StudentAttendanceReportDTO> reportData) throws IOException {
        StringWriter stringWriter = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(stringWriter);

        String[] headers = {
            "Matrícula", "Nome do Aluno", "Email", "Data da Aula",
            "Início", "Fim", "Status", "Presenças", "Total de Aulas",
            "Percentual de Presença (%)"
        };
        csvWriter.writeNext(headers);

        for (StudentAttendanceReportDTO data : reportData) {
            String[] row = {
                data.getStudentRegistration() != null ? data.getStudentRegistration() : "",
                data.getStudentName(),
                data.getStudentEmail(),
                data.getSessionDate(),
                data.getSessionStart(),
                data.getSessionEnd(),
                data.getAttendanceStatus(),
                String.valueOf(data.getAttendedSessions()),
                String.valueOf(data.getTotalSessions()),
                String.format("%.2f", data.getAttendancePercentage())
            };
            csvWriter.writeNext(row);
        }

        csvWriter.close();
        return stringWriter.toString();
    }
}

