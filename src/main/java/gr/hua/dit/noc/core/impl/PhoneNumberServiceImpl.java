package gr.hua.dit.noc.core.impl;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import gr.hua.dit.noc.core.PhoneNumberService;
import gr.hua.dit.noc.core.model.PhoneNumberValidationResult;
import org.springframework.stereotype.Service;

import java.util.Locale;

// Υλοποίηση του PhoneNumberService
// Χρησιμοποιείται για έλεγχο εγκυρότητας και κανονικοποίηση αριθμών τηλεφώνου
@Service
public class PhoneNumberServiceImpl implements PhoneNumberService {

    // Utility της βιβλιοθήκης libphonenumber για parsing και validation
    private final PhoneNumberUtil phoneNumberUtil =
            PhoneNumberUtil.getInstance();

    // Προεπιλεγμένη περιοχή για parsing αριθμών τηλεφώνου
    private final String defaultRegion = "GR";

    // Έλεγχος και κανονικοποίηση αριθμού τηλεφώνου
    @Override
    public PhoneNumberValidationResult validatePhoneNumber(
            final String rawPhoneNumber
    ) {

        // Έλεγχος για null ή κενή τιμή
        if (rawPhoneNumber == null)
            return PhoneNumberValidationResult.invalid(rawPhoneNumber);

        if (rawPhoneNumber.isBlank())
            return PhoneNumberValidationResult.invalid(rawPhoneNumber);

        try {
            // Parsing του αριθμού τηλεφώνου με βάση την προεπιλεγμένη περιοχή
            final Phonenumber.PhoneNumber phoneNumber =
                    this.phoneNumberUtil.parse(
                            rawPhoneNumber,
                            this.defaultRegion
                    );

            // Επιστροφή έγκυρου αποτελέσματος με τύπο αριθμού και μορφή E.164
            return PhoneNumberValidationResult.valid(
                    rawPhoneNumber,
                    this.phoneNumberUtil
                            .getNumberType(phoneNumber)
                            .name()
                            .toLowerCase(Locale.ROOT),
                    this.phoneNumberUtil.format(
                            phoneNumber,
                            PhoneNumberUtil.PhoneNumberFormat.E164
                    )
            );

        } catch (NumberParseException e) {
            // Σε αποτυχία parsing, ο αριθμός θεωρείται μη έγκυρος
            return PhoneNumberValidationResult.invalid(rawPhoneNumber);
        }
    }
}