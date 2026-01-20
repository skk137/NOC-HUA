package gr.hua.dit.noc.web.rest;

import gr.hua.dit.noc.core.PhoneNumberService;
import gr.hua.dit.noc.core.model.PhoneNumberValidationResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// REST controller για έλεγχο εγκυρότητας αριθμών τηλεφώνου
// Εκθέτει endpoints για validation και κανονικοποίηση phone numbers
@RestController
@RequestMapping("/api/v1/phone-numbers")
public class PhoneNumberResource {

    // Service που υλοποιεί τη λογική ελέγχου αριθμών τηλεφώνου
    private final PhoneNumberService phoneNumberService;

    // Constructor  του PhoneNumberService
    public PhoneNumberResource(final PhoneNumberService phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
    }

    // Endpoint για έλεγχο εγκυρότητας αριθμού τηλεφώνου
    @GetMapping("/{phoneNumber}/validations")
    public ResponseEntity<PhoneNumberValidationResult> phoneNumberValidation(
            @PathVariable String phoneNumber
    ) {

        // Εκτέλεση validation μέσω του service
        final PhoneNumberValidationResult phoneNumberValidationResult =
                this.phoneNumberService.validatePhoneNumber(phoneNumber);

        // Επιστροφή αποτελέσματος ως JSON response
        return ResponseEntity.ok(phoneNumberValidationResult);
    }
}