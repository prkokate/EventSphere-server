package com.esphere.EventSphere.model;


import com.esphere.EventSphere.domain.VerificationType;
import lombok.Data;

@Data
public class TwoFactorAuth {
    private boolean isEnabled = false;

    private VerificationType sendTo;
}
