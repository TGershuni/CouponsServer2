package com.tehila.coupons.enums;

public enum ErrorType {

    GENERAL_ERROR(1000, "A general error has occurred, please try again later", 500),

    UNAUTHORIZED(1001, "Invalid user name or password", 401),

    INVALID_COUPON_DESCRIPTION(1002, "Invalid coupon description", 400),

    INVALID_CATEGORY_NAME(1003, "Invalid category name", 400),

    INVALID_COMPANY_NAME(1004, "Invalid company name", 400),

    INVALID_PHONE_NUMBER(1005, "Invalid phone number", 400),

    COUPON_AMOUNT_LESS_THAN_ZERO(1006, "Coupon amount should be more than 0", 400),

    COUPON_DESCRIPTION_TOO_LONG(1007, "Coupon description length can be up to 45 characters", 400),

    COUPON_PRICE_LESS_THAN_ZERO(1008, "Coupon price must be more than 0", 400),

    INVALID_COUPON_NAME(1009, "Invalid coupon name", 400),

    INVALID_COUPON_DATE(1010, "Invalid coupon end date or start date", 400),

    INVALID_COMPANY_ID(1011, "Invalid company ID", 400),

    INVALID_CATEGORY_ID(1012, "Invalid category ID", 400),

    INVALID_CUSTOMER_NAME(1013, "Invalid customer name", 400),

    INVALID_CUSTOMER_ID(1014, "Invalid customer ID", 400),

    INVALID_COUPON_ID(1015, "Invalid coupon ID", 400),

    AMOUNT_OF_PURCHASES_LESS_THAN_ZERO(1017, "Amount of purchases must be more than 0", 400),

    INVALID_USER_TYPE(1018, "Invalid user type", 400),

    USERNAME_TOO_LONG(1019, "User name length can be up to 45 characters", 400),

    COMPANY_NAME_TOO_LONG(1020, "Company name length can be up to 45 characters", 400),

    COMPANY_ADDRESS_TOO_LONG(1021, "Company address length can be up to 45 characters", 400),

    COUPON_NAME_TOO_LONG(1022, "Coupon name length can be up to 45 characters", 400),

    CUSTOMER_NAME_TOO_LONG(1023, "Customer name length can be up to 45 characters", 400),

    CUSTOMER_ADDRESS_TOO_LONG(1024, "Customer address length can be up to 45 characters", 400),

    PASSWORD_TOO_LONG(1025, "Password length can be up to 45 letters", 400),

    CATEGORY_NAME_TOO_LONG(1026, "Category name length can be up to 45 characters", 400),

    USER_NOT_EXIST(1027, "User does not exist", 404),

    INVALID_COUPON_TITLE(1028, "Invalid coupon title", 400),

    COUPON_TITLE_TOO_LONG(1028, "Coupon title length can be up to 45 characters", 400),

    INVALID_PURCHASE_TIMESTAMP(1029, "Purchase timestamp is invalid", 400),

    NOT_ENOUGH_COUPONS(1030, "There are not enough coupons in the pool for this purchase", 409),

    CATEGORY_NOT_EXIST(1031, "The category does not exist",404),

    COMPANY_NOT_EXIST(1032, "The company does not exist", 404),

    COMPANY_ALREADY_EXISTS(1033, "Company with the given name already exists", 409),

    CATEGORY_ALREADY_EXISTS(1035, "Category with the given name already exists", 409),

    USER_NAME_ALREADY_EXISTS(1036, "User with the given name already exists", 409),

    INVALID_PURCHASES_AMOUNT(1037, "Invalid amount of purchases", 400),

    URL_TOO_LONG(1038, "The given url is too long, url length is up to 100 characters", 400),

    INVALID_ACTION(1039, "User is not authorized for this action", 403);

    private int internalError;

    private String clientErrorMessage;

    private boolean isShowStackTrace;

    private int httpStatus;

    ErrorType(int internalError, String clientErrorMessage, int httpStatus) {
        this.internalError = internalError;
        this.clientErrorMessage = clientErrorMessage;
        this.httpStatus = httpStatus;
    }

    public int getInternalError() {
        return internalError;
    }

    public String getClientErrorMessage() {
        return clientErrorMessage;
    }

    public boolean isShowStackTrace() {
        return isShowStackTrace;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}
