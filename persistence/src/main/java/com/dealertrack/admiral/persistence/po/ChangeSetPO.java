package com.dealertrack.admiral.persistence.po;

import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Document(collection = "changeset")
public class ChangeSetPO {
    @Id
    @Indexed(unique=true)
    private String id;

    private String description;

    private List<ResourceChangeStatusPO> changes = new ArrayList<>();

    @CreatedDate
    private Date created;

    @CreatedBy // This won't be set automatically without some additional work
    private String createdBy;

    @LastModifiedDate
    private Date modified;

    @LastModifiedBy // This won't be set automatically without some additional work
    private String modifiedBy;

    @Version
    private Integer version;

    private String status;

    boolean indeterminate;

    double progress;

    public List<ResourceChangeStatusPO> getChanges() {
        return changes;
    }

    public void setChanges(List<ResourceChangeStatusPO> changes) {
        this.changes = changes;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreated() {
        return created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Date getModified() {
        return modified;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public Integer getVersion() {
        return version;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void setCreatedBy(String createdBy){
        this.createdBy = createdBy;
    }

    public void setDescription(final String description){
        this.description = description;
    }

    public void setCreated(final Date created){
        this.created = created;
    }

    public void setModified(final Date modified){
        this.modified = modified;
    }

    public void setModifiedBy(final String modifiedBy){
        this.modifiedBy = modifiedBy;
    }

    public void setVersion(final Integer version){
        this.version = version;
    }

    public boolean isIndeterminate(){
        return indeterminate;
    }

    public void setIndeterminate(final boolean indeterminate){
        this.indeterminate = indeterminate;
    }

    public double getProgress(){
        return progress;
    }

    public void setProgress(final double progress){
        this.progress = progress;
    }
}

