package gr.hua.dit.noc.core;

import gr.hua.dit.noc.core.model.LookupResult;

public interface LookupService {
    LookupResult lookupByHuaId(String huaId);
}