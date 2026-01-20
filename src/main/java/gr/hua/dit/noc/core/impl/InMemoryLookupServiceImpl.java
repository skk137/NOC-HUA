package gr.hua.dit.noc.core.impl;

import gr.hua.dit.noc.core.LookupService;
import gr.hua.dit.noc.core.model.LookupResult;
import gr.hua.dit.noc.core.model.PersonType;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Υλοποίηση του LookupService που χρησιμοποιεί in-memory αποθήκευση
// Κατάλληλη για απλές περιπτώσεις χωρίς εξωτερική βάση
@Service
public class InMemoryLookupServiceImpl implements LookupService {

    // In-memory "βάση δεδομένων" που χαρτογραφεί huaId σε PersonType
    // Χρησιμοποιείται ConcurrentHashMap για thread-safety
    private final Map<String, PersonType> inMemoryDatabase =
            new ConcurrentHashMap<>();

    // Αρχικοποίηση  δεδομένων μετά τη δημιουργία του bean
    @PostConstruct
    public void populateInitialData() {

        // Φοιτητές
        inMemoryDatabase.put("it2023001", PersonType.STUDENT);
        inMemoryDatabase.put("it2023002", PersonType.STUDENT);

        // Ανώνυμοι χρήστες
        inMemoryDatabase.put("t0001", PersonType.ANONYMOUS);
        inMemoryDatabase.put("t0002", PersonType.ANONYMOUS);

        // Χρήστες βιβλιοθήκης
        inMemoryDatabase.put("s0001", PersonType.LIBRARY);
        inMemoryDatabase.put("s0002", PersonType.LIBRARY);
    }

    // Αναζήτηση χρήστη με βάση το huaId
    @Override
    public LookupResult lookupByHuaId(final String huaId) {

        // Έλεγχος εγκυρότητας παραμέτρου
        if (huaId == null)
            throw new NullPointerException("huaId cannot be null");

        if (huaId.isBlank())
            throw new IllegalArgumentException("huaId cannot be blank");

        // Κανονικοποίηση του huaId (trim + lowercase)
        final String normalizedHuaId =
                huaId.strip().toLowerCase();

        // Αναζήτηση του τύπου χρήστη
        final PersonType type =
                this.inMemoryDatabase.get(normalizedHuaId);

        // Αν δεν βρεθεί, επιστρέφεται κενό αποτέλεσμα
        if (type == null) {
            return LookupResult.empty(huaId);
        }

        // Αν βρεθεί, επιστρέφεται πλήρες αποτέλεσμα αναζήτησης
        return new LookupResult(
                huaId,
                true,
                normalizedHuaId,
                type
        );
    }
}