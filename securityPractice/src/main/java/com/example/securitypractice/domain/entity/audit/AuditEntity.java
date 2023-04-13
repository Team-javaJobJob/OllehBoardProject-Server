package com.example.securitypractice.domain.entity.audit;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditEntity extends UserAuditEntity {
    @CreatedBy
    @Column(nullable = false, updatable = false)
    protected String createdBy;

    @LastModifiedBy
    protected String updatedBy;

}
