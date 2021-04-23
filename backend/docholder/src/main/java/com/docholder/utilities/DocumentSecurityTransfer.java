package com.docholder.utilities;

import com.docholder.model.DocumentDto;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentSecurityTransfer {
    private DocumentDto documentDto;
    private String token;
    private UUID id;
}
