package com.sgtech.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "LoanType")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class LoanType extends BaseEntity {

    @Column(name = "name", nullable = false, columnDefinition = "nvarchar(255)")
    private String name;

}
