package gr.hua.dit.noc.web.rest;

import gr.hua.dit.noc.core.SmsService;
import gr.hua.dit.noc.core.model.SendSmsRequest;
import gr.hua.dit.noc.core.model.SendSmsResult;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// REST controller για αποστολή SMS
// Εκθέτει endpoint για αποστολή μηνυμάτων μέσω του SmsService
@RestController
@RequestMapping(
        value = "/api/v1/sms",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class SmsResource {

    // Service που υλοποιεί τη λογική αποστολής SMS
    private final SmsService smsService;

    // Constructor injection του SmsService
    public SmsResource(final SmsService smsService) {
        this.smsService = smsService;
    }

    // Endpoint για αποστολή SMS
    // Δέχεται JSON request με στοιχεία μηνύματος
    @PostMapping(
            value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SendSmsResult> sendSms(
            @RequestBody @Valid SendSmsRequest sendSmsRequest
    ) {

        // Εκτέλεση αποστολής SMS μέσω του service
        final SendSmsResult sendSmsResult =
                this.smsService.send(sendSmsRequest);

        // Επιστροφή αποτελέσματος αποστολής
        return ResponseEntity.ok(sendSmsResult);
    }
}