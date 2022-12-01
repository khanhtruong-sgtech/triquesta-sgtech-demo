package com.sgtech.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Applicant")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Applicant extends BaseEntity {

    @Column(name = "FirstName", nullable = false, columnDefinition = "nvarchar(255)")
    private String firstName;

    @Column(name = "LastName", nullable = false, columnDefinition = "nvarchar(255)")
    private String lastName;

    @Column(name = "Address", columnDefinition = "nvarchar(255)")
    private String address;

    @Column(name = "Email",columnDefinition = "nvarchar(255)")
    private String email;

    @OneToMany(mappedBy = "applicant")
    private List<CreditFacility> creditFacilities;

}
