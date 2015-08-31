package com.dealertrack.admiral.persistence.po;

import java.util.List;

public class ResourceChangePO {

    String type;
    String id;
    String operation;

    List<FieldChangePO> changes;


    public void setChanges(List<FieldChangePO> changes) {
        this.changes = changes;
    }

    public List<FieldChangePO> getChanges() {
        return changes;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getOperation(){
        return operation;
    }

    public void setOperation(final String operation){
        this.operation = operation;
    }
}
