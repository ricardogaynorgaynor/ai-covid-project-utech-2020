package com.ai.covid.service;

import com.ai.covid.models.CovidInfo;
import com.ai.covid.repositories.CovidInfoRepository;
import com.sun.istack.ByteArrayDataSource;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class EmailService {

    @Autowired
    private CovidInfoRepository covidInfoRepository;

    @Value("${cron.email.enabled}")
    private boolean enableEmail;

    @Autowired
    private JavaMailSender javaMailSender;

//    Logger logger = java.util.logging.Logger.getLogger(EmailService.class);

    @Value("${email.trades.to}")
    private String to;
    @Value("${email.trades.from}")
    private String from;
    @Value("${email.trades.subject}")
    private String subject;
    @Value("${email.trade.message}")
    private String emailMessage;

    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Scheduled(cron = "${cron.email.expression}") // time interval to execute the email scheduler to send results to MOH
    public void buildExcelFileForCompletedOrder() {
        if (enableEmail) {
            final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            final Date currentDate = new Date();
            try {
                final String currentDateFormat = dateFormat.format(currentDate);
                String[] rowHeadArray = {"First Name", "Last Name", "Email", "Age", "Gender", "Mobile", "Temperature", "Illness", "Difficulty Breathing", "Runny Nose", "Aches", "DSPV", "Loss of Smell", "Loss of Taste", "Dry Cough", "Fever", "Skin Rash"};
                Workbook workbook = new XSSFWorkbook();
                final String fileName = "AI Project Covid Report (" + currentDateFormat + ").csv";
                Sheet excelSheet = workbook.createSheet(fileName);
                Row headingRow = excelSheet.createRow(0);
                for (int i = 0; i < rowHeadArray.length; i++) {
                    Cell cell = headingRow.createCell(i);
                    cell.setCellValue(rowHeadArray[i]);
                }
                final AtomicInteger index = new AtomicInteger(1);

                List<CovidInfo> results = covidInfoRepository.getAllBySent(false);
                results.forEach(t -> {
                    Row row = excelSheet.createRow(index.get());

                    Cell cell = row.createCell(0);
                    cell.setCellValue(t.getFirstName());

                    cell = row.createCell(1);
                    cell.setCellValue(t.getLastName());

                    cell = row.createCell(2);
                    cell.setCellValue(t.getEmail());

                    cell = row.createCell(3);
                    cell.setCellValue(t.getAge());

                    cell = row.createCell(4);
                    cell.setCellValue(t.getGender());

                    cell = row.createCell(5);
                    cell.setCellValue(t.getMobile());

                    cell = row.createCell(6);
                    cell.setCellValue(t.getTemperature());


//                    List<String> illnesses = new ArrayList<>();
//                    t.getIllnesses().forEach(i -> illnesses.add(i.getName()));
//                    String delimitedIllnesses = String.join(", ", illnesses);

                    cell = row.createCell(7);
                    cell.setCellValue("-");

                    cell = row.createCell(8);
                    cell.setCellValue(t.getDifficultyBreathing());

                    cell = row.createCell(9);
                    cell.setCellValue(t.getRunnyNose());

                    cell = row.createCell(10);
                    cell.setCellValue(t.getAches());

                    cell = row.createCell(11);
                    cell.setCellValue(t.getDspv());

                    cell = row.createCell(12);
                    cell.setCellValue(t.getLossOfSmell());

                    cell = row.createCell(13);
                    cell.setCellValue(t.getLossOfTaste());

                    cell = row.createCell(14);
                    cell.setCellValue(t.getDryCaugh());

                    cell = row.createCell(15);
                    cell.setCellValue(t.getFever());

                    cell = row.createCell(16);
                    cell.setCellValue(t.getSkinRash());

                    index.incrementAndGet();

                    t.setSent(Boolean.TRUE);
                });

                if (index.get() > 1) {
                    StringBuffer csvData = excelToCsv(excelSheet);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    DataOutputStream out = new DataOutputStream(byteArrayOutputStream);
                    try {
                        out.write(csvData.toString().getBytes());
                        byteArrayOutputStream.flush();
                        byteArrayOutputStream.close();
                    } catch (IOException e) {
                        logger.error(e.getLocalizedMessage(), e.getCause());
                    }
                    logger.debug("Finish generating document");
                    sendEmailToGroup(byteArrayOutputStream.toByteArray(), fileName);
                    // logger.debug("Finish sending mail");
                    // set status of records to sent
                    covidInfoRepository.saveAll(results); //persist all sent records
                }

            } catch (Throwable e) {
                logger.error(e.getLocalizedMessage(), e.getCause());
            }
        } else {
            logger.info("Email is disabled");
        }
    }

    private StringBuffer excelToCsv(Sheet sheet) {
        final StringBuffer data = new StringBuffer();
        sheet.forEach(s -> {
            s.forEach(cell -> {
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_BOOLEAN:
                        data.append(cell.getBooleanCellValue()).append(",");
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        data.append(cell.getNumericCellValue()).append(",");

                        break;
                    case Cell.CELL_TYPE_STRING:
                        data.append(cell.getStringCellValue()).append(",");
                        break;

                    case Cell.CELL_TYPE_BLANK:
                        data.append("" + ",");
                        break;
                    default:
                        data.append(cell).append(",");
                }
            });
            data.append('\n');
        });
        String dataStr = data.toString().substring(0, data.toString().length() - 2); // remove extra line and last comma
        StringBuffer csvTradeData = new StringBuffer(dataStr);
        logger.info(csvTradeData.toString());
        return csvTradeData;
    }

    private void sendEmailToGroup(final byte[] attachment, final String fileName) throws MessagingException {
        logger.info("Attempting to send email with covid results");
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(emailMessage, true);
            ByteArrayDataSource byteArrayDataSource = new ByteArrayDataSource(attachment, "text/csv");
            mimeMessageHelper.addAttachment(fileName, byteArrayDataSource);
            javaMailSender.send(message);
        } catch (Throwable e) {
            logger.error(e.getLocalizedMessage(), e.getCause());
        }
    }

}
