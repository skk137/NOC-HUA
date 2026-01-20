package gr.hua.dit.noc.core;

import gr.hua.dit.noc.core.model.LookupResult;

// Interface που ορίζει λειτουργία αναζήτησης χρήστη με βάση το huaId
// Χρησιμοποιείται από υπηρεσίες που χρειάζονται πληροφορίες ταυτοποίησης
public interface LookupService {

    // Αναζητά χρήστη με βάση το huaId
    // Επιστρέφει LookupResult με πληροφορία αν βρέθηκε και τον τύπο χρήστη
    LookupResult lookupByHuaId(String huaId);
}