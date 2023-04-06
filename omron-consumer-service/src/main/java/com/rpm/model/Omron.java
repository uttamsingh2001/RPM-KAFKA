package com.rpm.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Omron {
    private Long userId;
    private String externalObstermId;
    private Double value;
    private String uomCode;
    private LocalDateTime effectiveDateTime;
}
