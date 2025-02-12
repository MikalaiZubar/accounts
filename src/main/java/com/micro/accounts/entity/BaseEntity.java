package com.micro.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@MappedSuperclass //points that this is super class for some entities to be mapped as tables
@EntityListeners(AuditingEntityListener.class) // important to use correct class
@Getter
@Setter
@ToString
public class BaseEntity {

    @CreatedDate //to set value automatically
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy //to set value automatically
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedDate //to set value automatically
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    @LastModifiedBy //to set value automatically
    @Column(insertable = false)
    private String updatedBy;
}
