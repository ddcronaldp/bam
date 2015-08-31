package com.dealertrack.admiral.persistence.po;

import java.util.List;

public class ResourceChangeStatusPO{

    public String id;
    public String status;
    public List<ProviderStatusPO> providerStatus;
    public ResourceChangePO resourceChange;
}
