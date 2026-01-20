package gr.hua.dit.noc.core;

import gr.hua.dit.noc.core.model.PhoneNumberValidationResult;

// Interface που ορίζει λειτουργία ελέγχου εγκυρότητας αριθμού τηλεφώνου
// Χρησιμοποιείται πριν την αποστολή SMS
public interface PhoneNumberService {

    // Ελέγχει αν ένας αριθμός τηλεφώνου είναι έγκυρος
    // Επιστρέφει αποτέλεσμα με πληροφορίες εγκυρότητας και κανονικοποιημένη μορφή
    PhoneNumberValidationResult validatePhoneNumber(String rawPhoneNumber);
}