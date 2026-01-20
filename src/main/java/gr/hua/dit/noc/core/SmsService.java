package gr.hua.dit.noc.core;

import gr.hua.dit.noc.core.model.SendSmsRequest;
import gr.hua.dit.noc.core.model.SendSmsResult;

// Interface που ορίζει λειτουργία αποστολής SMS
// Υλοποιείται είτε από πραγματική υπηρεσία είτε από mock υλοποίηση
public interface SmsService {

    // Αποστέλλει SMS με βάση το request
    // Επιστρέφει αποτέλεσμα που δηλώνει αν η αποστολή ήταν επιτυχής
    SendSmsResult send(SendSmsRequest sendSmsRequest);
}