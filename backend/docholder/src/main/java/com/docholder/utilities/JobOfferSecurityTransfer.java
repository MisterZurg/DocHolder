package com.docholder.utilities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOfferSecurityTransfer {
    private String token;
    private UUID id;
}
