package com.rpm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    private Long patientId;
    private String obstermId;
    private Double value;
    private String uomCode;
    private LocalDateTime effectiveDateTime;

}
