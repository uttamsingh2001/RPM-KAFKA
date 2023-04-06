package com.rpm.integrationdataservice.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "external_patient_map")
public class ExternalPatientMapEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "external_patient_map_id")
    private Long externalPatientMapId;

    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "external_patient_id")
    private Long externalPatientId;
}

