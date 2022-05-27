package com.ngocnb20.travel.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {
    @JsonProperty("create_date")
    @Column(name = "create_date")
    @CreatedDate
    protected LocalDateTime createDate;

    @JsonProperty("update_time")
    @Column(name = "update_time")
    @LastModifiedDate
    protected LocalDateTime updateTime;

    @CreatedBy
    @Column(name = "create_by")
    protected Long createById;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    protected Long lastModifiedBy;
}
