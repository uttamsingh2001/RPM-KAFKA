package com.rpm.integrationdataservice.repository;

import com.rpm.integrationdataservice.entity.ExternalPatientMapEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExternalPatienMapRepository extends CrudRepository<ExternalPatientMapEntity,Long> {
    @Query(value = "select e.patient_id from external_patient_map e where e.external_patient_id = ?1", nativeQuery = true)
    Long findPatientIdByExternalPatientId(Long externalPatientId);


}
