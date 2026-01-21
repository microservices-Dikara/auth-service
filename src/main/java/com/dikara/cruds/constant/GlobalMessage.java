package com.dikara.cruds.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GlobalMessage {

    SUCCESS("00", "SUCCESS"),

    FAILED("01", "FAILED"),
    DATA_ALREADY_EXISTS("02", "DATA_ALREADY_EXISTS"),
    DATA_NOT_FOUND("03", "DATA_NOT_FOUND"),
    SAVE_FAILED("04", "SAVE_FAILED"),
    REQUEST_DATE_NOT_VALID("30", "REQUEST_DATE_NOT_VALID"),
    INVALID_REQUEST("36", "INVALID_REQUEST"),
    INVALID_URL_PATH("38", "INVALID_URL_PATH"),
    TRANSACTION_NOT_FOUND("45", "TRANSACTION_NOT_FOUND"),

    INVALID_DATA("80", "INVALID_DATA"),
    DB_ERROR("81", "DB_ERROR"),
    EXTERNAL_SERVER_ERROR("98", "EXTERNAL_SERVER_ERROR"),
    ERROR("99", "ERROR");

    public final String code;
    public final String message;

}
