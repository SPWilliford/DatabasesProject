package edu.utdallas.d_team.library.fileparser;

import edu.utdallas.d_team.library.entity.Borrower;
import edu.utdallas.d_team.library.service.BorrowerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class BorrowersFileParser {
    private static final Logger logger = LoggerFactory.getLogger(BorrowersFileParser.class);
    private final BorrowerService borrowerService;

    @Autowired
    public BorrowersFileParser(BorrowerService borrowerService){
        this.borrowerService = borrowerService;
    }

    public void parseBorrowersFile(String filepath) {
        ClassPathResource resource = new ClassPathResource(filepath);

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            List<String[]> data = reader.lines()
                    .skip(1) // first line is header
                    .map(line -> line.split(","))
                    .toList();
            for (String[] record : data) {
                processRecord(record);
            }
        } catch (IOException e) {
            logger.error("Error parsing borrowers file", e);
            throw new RuntimeException(e);
        }
    }

    private void processRecord(String[] record) {
        logger.info("Processing borrower record: {}", (Object) record);
        String ssn = record[1];
        String fullName = record[2].trim() + " " + record[3].trim();
        String address = record[5].trim() + ", " + record[6].trim() + ", " + record[7].trim();
        String phone = record[8];
        if (!borrowerService.existsBySsn(ssn)) {
            Borrower borrower = new Borrower();
            borrower.setSsn(ssn);
            borrower.setBname(fullName);
            borrower.setAddress(address);
            borrower.setPhone(phone);
            borrowerService.saveBorrower(borrower);
        } else {
            logger.info("Borrower with SSN {} already exists, skipping", ssn);
        }
    }
}
