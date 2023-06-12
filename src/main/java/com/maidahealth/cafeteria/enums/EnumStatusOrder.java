package com.maidahealth.cafeteria.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EnumStatusOrder {

    ABERTO (true),

    EM_PREPARO (true),
    FINALIZADO (false),
    CANCELADO (false);

    private boolean allowChangeStatus;

    public boolean isAllowChangeStatus() {
        return allowChangeStatus;
    }
}
