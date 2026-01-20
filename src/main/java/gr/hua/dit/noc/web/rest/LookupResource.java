package gr.hua.dit.noc.web.rest;

import gr.hua.dit.noc.core.LookupService;
import gr.hua.dit.noc.core.model.LookupResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// REST controller για αναζήτηση χρηστών με βάση το huaId
// Εκθέτει endpoints για lookup πληροφοριών ταυτοποίησης
@RestController
@RequestMapping("/api/v1/lookups")
public class LookupResource {

    // Service που υλοποιεί τη λογική αναζήτησης
    private final LookupService lookupService;

    // Constructor του LookupService
    public LookupResource(final LookupService lookupService) {
        this.lookupService = lookupService;
    }

    // Endpoint για αναζήτηση χρήστη με βάση το huaId
    @GetMapping("/{huaId}")
    public ResponseEntity<LookupResult> lookups(
            @PathVariable String huaId
    ) {

        // Εκτέλεση lookup μέσω του service
        final LookupResult lookupResult =
                this.lookupService.lookupByHuaId(huaId);

        // Επιστροφή αποτελέσματος ως JSON response
        return ResponseEntity.ok(lookupResult);
    }
}