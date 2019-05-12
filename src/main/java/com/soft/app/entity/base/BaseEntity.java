package com.soft.app.entity.base;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(name = "version")
    private Integer version;

    /**
     */
    @JsonIgnore
    private String createdBy;

    /**
     */
    @JsonIgnore
    private String updatedBy;

    /**
     */
    private String status;

    /**
     */
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date createdDate;

    /**
     */
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date updatedDate;

    protected Boolean isActive;
}
