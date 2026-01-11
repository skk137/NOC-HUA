package gr.hua.dit.noc.core.impl;

import gr.hua.dit.noc.core.LookupService;
import gr.hua.dit.noc.core.model.LookupResult;
import gr.hua.dit.noc.core.model.PersonType;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryLookupServiceImpl implements LookupService {
    private final Map<String, PersonType> inMemoryDatabase = new ConcurrentHashMap<>();

    @PostConstruct
    public void populateInitialData() {
        inMemoryDatabase.put("it2023001", PersonType.STUDENT);
        inMemoryDatabase.put("it2023002", PersonType.STUDENT);
        inMemoryDatabase.put("t0001", PersonType.ANONYMOUS);
        inMemoryDatabase.put("t0002", PersonType.ANONYMOUS);
        inMemoryDatabase.put("s0001", PersonType.LIBRARY);
        inMemoryDatabase.put("s0002", PersonType.LIBRARY);
    }

    @Override
    public LookupResult lookupByHuaId(final String huaId) {
        if (huaId == null) throw new NullPointerException("huaId cannot be null");
        if (huaId.isBlank()) throw new IllegalArgumentException("huaId cannot be blank");
        final String normalizedHuaId = huaId.strip().toLowerCase();
        final PersonType type = this.inMemoryDatabase.get(normalizedHuaId);
        if (type == null) {
            return LookupResult.empty(huaId);
        } else {
            return new LookupResult(huaId, true, normalizedHuaId, type);
        }
    }
}