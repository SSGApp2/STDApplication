package com.soft.app.entity.app;

import com.soft.app.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(of = {"id"})
public class ParameterHeader extends BaseEntity{

    @NotNull
    @Column(unique = true)
    private String code;

    private String parameterDescription;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parameterHeader")
    private List<ParameterDetail> parameterDetails;
}
