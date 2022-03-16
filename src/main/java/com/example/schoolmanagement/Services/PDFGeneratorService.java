package com.example.schoolmanagement.Services;

import com.example.schoolmanagement.jpa.school.entity.Student;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;

import java.io.IOException;

import java.util.List;
import java.util.Map;

@Service
public class PDFGeneratorService {
    public Table createTable(List<Student> students, List<Map<String,String>>subjects) {
        Table table = new Table(4);
        table.setBorderWidth(1);
        table.setBorderColor(Color.BLACK);
        table.setPadding(1);
        table.setSpacing(0);

        return table;
    }
    public void reportToDocument(Document document,List<Student> students) throws IOException {
        BaseFont bf_helv = BaseFont.createFont(BaseFont.HELVETICA, "Cp1252", false);
        BaseFont bf_times = BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", false);
        BaseFont bf_courier = BaseFont.createFont(BaseFont.COURIER, "Cp1252", false);
        BaseFont bf_symbol = BaseFont.createFont(BaseFont.SYMBOL, "Cp1252", false);
        try {
            Image simple = Image.getInstance("/luban.png");
            simple.setAbsolutePosition(100, 100);
            simple.scalePercent(50);
            document.add(simple);
        } catch (Exception ex) {
            System.out.println("no image for you");
        }
        Table table = new Table(4);
        table.setBorderWidth(1);
        table.setBorderColor(Color.BLACK);
        table.setPadding(1);
        table.setSpacing(0);
        Cell idCell = new Cell("id");
        idCell.setWidth("15%");
        table.addCell(idCell);
        Cell firstNameCell = new Cell("firstName");
        firstNameCell.setWidth("20%");
        table.addCell(firstNameCell);
        Cell lastNameCell = new Cell("lastName");
        lastNameCell.setWidth("20%");
        table.addCell(lastNameCell);
        Cell emailCell = new Cell("email");
        emailCell.setWidth("45%");
        table.addCell(emailCell);
        students.forEach(student -> {
            table.addCell(new Cell(student.getId().toString()));
            table.addCell(new Cell(student.getFirstName()));
            table.addCell(new Cell(student.getLastName()));
            table.addCell(new Cell(student.getEmail()));
        });
        document.add(table);
    }

    public void export(HttpServletResponse response, List<Student> students) throws IOException {
        BaseFont bf_helv = BaseFont.createFont(BaseFont.HELVETICA, "Cp1252", false);
        BaseFont bf_times = BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", false);
        BaseFont bf_courier = BaseFont.createFont(BaseFont.COURIER, "Cp1252", false);
        BaseFont bf_symbol = BaseFont.createFont(BaseFont.SYMBOL, "Cp1252", false);

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        //Set Footer
        HeaderFooter footer = new HeaderFooter(
                new Phrase("page: ", new Font(bf_courier)), true);
        footer.setBorder(Rectangle.NO_BORDER);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.setFooter(footer);
        //set Header
        HeaderFooter header = new HeaderFooter(
                new Phrase("This is a header without a page number", new Font(bf_courier)), false);
        header.setAlignment(Element.ALIGN_CENTER);
        document.setHeader(header);



        // add an image, scale it down by half, and put at an absolute position
        try {
            Image simple = Image.getInstance("src/main/resources/luban.png");
//            simple.setAbsolutePosition(0, 0);
            simple.scalePercent(50);
            document.add(simple);
        } catch (Exception ex) {
            System.out.println("no image for you");
        }

        // start second page
//        document.newPage();

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA);
        fontTitle.setSize(18);
        fontTitle.setColor(Color.RED);
        Paragraph paragraph = new Paragraph("List All Student for School in PDF", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);
        Table table = new Table(4);
        table.setBorderWidth(1);
        table.setBorderColor(Color.BLACK);
        table.setPadding(1);
        table.setSpacing(0);
        Cell idCell = new Cell("id");
        idCell.setWidth("5%");
        table.addCell(idCell);
        Cell firstNameCell = new Cell("firstName");
        firstNameCell.setWidth("20%");
        table.addCell(firstNameCell);
        Cell lastNameCell = new Cell("lastName");
        lastNameCell.setWidth("20%");
        table.addCell(lastNameCell);
        Cell emailCell = new Cell("email");
        emailCell.setWidth("55%");
        table.addCell(emailCell);
        students.forEach(student -> {
            table.addCell(new Cell(student.getId().toString()));
            table.addCell(new Cell(student.getFirstName()));
            table.addCell(new Cell(student.getLastName()));
            table.addCell(new Cell(student.getEmail()));
        });
        document.add(table);
        document.close();
    }
}
