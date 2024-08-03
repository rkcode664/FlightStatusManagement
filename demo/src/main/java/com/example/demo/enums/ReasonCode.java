package com.example.demo.enums;

public enum ReasonCode {
        INVALID_REQUEST_DATA(40010, "Invalid request data"),
        AUTH_KEY_NULL(401, "Auth Key Absent"),
        BAD_ARGUMENT_ERROR(40030, "Bad Argument Error"),
        UNPROCESSABLE_ENTITY(42210, "Unprocessable Entity"),
        INVALID_POJO_REQUEST(42220, "Invalid POJO Request Payload"),
        INTERNAL_SERVER_ERROR(50010, "Internal Server Error "),
        NOT_FOUND(40410, "requested data does not exist"),
        UN_AUTHORIZED(40110, "token is not valid"),
        PACKAGE_SERVICE(40110,"Package Service Exception"),

        BASE_EXCEPTION(500, "Base Exception"),

        REQUIRED_PARAM_MISSING(40040, "required param missing ");



        private final Integer code;
        private final String description;

        private ReasonCode(Integer code, String description) {
            this.code = code;
            this.description = description;
        }

        public Integer getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
}
