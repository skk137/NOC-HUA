package gr.hua.dit.noc.core.model;

public record LookupResult(String raw, boolean exists, String huaId, PersonType type) {
    public static LookupResult empty(final String raw) {
        return new LookupResult(raw, false, null, null);
    }
}