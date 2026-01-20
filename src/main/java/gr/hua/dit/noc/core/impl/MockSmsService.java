package gr.hua.dit.noc.core.impl;

import gr.hua.dit.noc.core.SmsService;
import gr.hua.dit.noc.core.model.SendSmsRequest;
import gr.hua.dit.noc.core.model.SendSmsResult;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

// Mock υλοποίηση του SmsService
// Χρησιμοποιείται για development χωρίς πραγματική αποστολή SMS
@Service
public class MockSmsService implements SmsService {

    // Logger για καταγραφή των mock αποστολών
    private static final Logger LOGGER =
            LoggerFactory.getLogger(MockSmsService.class);

    // Προσομοίωση αποστολής SMS
    @Override
    public SendSmsResult send(@Valid final SendSmsRequest sendSmsRequest) {

        // Έλεγχος εγκυρότητας του request
        if (sendSmsRequest == null)
            throw new NullPointerException();

        final String e164 = sendSmsRequest.e164();
        final String content = sendSmsRequest.content();

        // Έλεγχος αριθμού τηλεφώνου
        if (e164 == null)
            throw new NullPointerException();

        if (e164.isBlank())
            throw new IllegalArgumentException();

        // Έλεγχος περιεχομένου SMS
        if (content == null)
            throw new NullPointerException();

        if (content.isBlank())
            throw new IllegalArgumentException();

        // Καταγραφή της αποστολής αντί για πραγματική επικοινωνία με SMS provider
        LOGGER.info("SENDING SMS {} {}", e164, content);

        // Επιστροφή αποτελέσματος αποστολής (mock)
        return new SendSmsResult(false);
    }
}