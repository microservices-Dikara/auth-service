package com.dikara.cruds.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Operation {

    CREATED,
    DRAFTED,
    SUBMITTED,
    UPDATED,
    ACTIVATED,
    APPROVED,
    DELETED,
    EXECUTED,
    FAILED

}
