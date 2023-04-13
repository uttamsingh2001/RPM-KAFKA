package com.rpm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Omron {
    private Long userId;
    private Long externalObstermId;
    private Double value;
    private String uomCode;
    private LocalDateTime effectiveDateTime;

}
