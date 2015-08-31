package com.dealertrack.bam.model.errorcodes;

public class BadRequestError {

    public static final String BAD_REQUEST         = "BAD_REQUEST";
    public static final String MISSING_PARAM       = BAD_REQUEST + ".MISSING_PARAM";
    public static final String INCOMPATIBLE_PARAMS = BAD_REQUEST + ".INCOMPATIBLE_PARAMS";

    public static String getMissingParamErrorMessage(String missingParam){

       return String.format("paramater [%s] is missing", missingParam);
    }

    public static String getIncompatibleParamsErrorMessage(String param1, String param2){

        return String.format("paramater [%s] and [%s] are incomptabile and cannot be used in the same request", param1, param2);
    }
}
