package com.avis.trcModule;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ZoopResponse {
    private boolean success;
    private Result result;

    @Data
    public static class Result {
        @JsonProperty("rc_registration_number")
        private String rc_registration_number;

        @JsonProperty("rc_status")
        private String rc_status;

        @JsonProperty("rc_blacklist_status")
        private String rc_blacklist_status;

        @JsonProperty("user_name")
        private String user_name;

        @JsonProperty("rc_registration_date")
        private String rc_registration_date;

        @JsonProperty("rc_expiry_date")
        private String rc_expiry_date;

        @JsonProperty("vehicle_make_model")
        private String vehicle_make_model;

        @JsonProperty("vehicle_color")
        private String vehicle_color;

        @JsonProperty("vehicle_fuel_description")
        private String vehicle_fuel_description;

        @JsonProperty("vehicle_category_description")
        private String vehicle_category_description;

        @JsonProperty("vehicle_class_description")
        private String vehicle_class_description;

        @JsonProperty("user_present_address")
        private String user_present_address;

        @JsonProperty("body_type_description")
        private String bodyTypeDescription;

        @JsonProperty("rc_chassis_number")
        private String rc_chassis_number;

        @JsonProperty("rc_engine_number")
        private String rc_engine_number;

        @JsonProperty("norms_description")
        private String norms_description;

        @JsonProperty("rc_permit_number")
        private String rc_permit_number;

        @JsonProperty("rc_permit_type")
        private String rc_permit_type;

        @JsonProperty("rc_pucc_expiry_date")
        private String rc_pucc_expiry_date;

        @JsonProperty("rc_pucc_no")
        private String rc_pucc_no;

        @JsonProperty("rc_registration_location")
        private String rc_registration_location;

        @JsonProperty("rc_state_code")
        private String rc_state_code;

        @JsonProperty("rc_status_as_on")
        private String rc_status_as_on;

        @JsonProperty("rc_tax_upto")
        private String rc_tax_upto;

        @JsonProperty("vehicle_maker_description")
        private String vehicle_maker_description;

        @JsonProperty("vehicle_manufactured_date")
        private String vehicle_manufactured_date;

        @JsonProperty("vehicle_cubic_capacity")
        private String vehicle_cubic_capacity;

        @JsonProperty("vehicle_number_of_cylinders")
        private String vehicle_number_of_cylinders;

        @JsonProperty("vehicle_seating_capacity")
        private String vehicle_seating_capacity;

        @JsonProperty("rc_commercial_status")
        private String rc_commercial_status;

        @JsonProperty("rc_rto_code")
        private String rtoCode;

        @JsonProperty("vehicle_financed")
        private String financed;

        @JsonProperty("vehicle_type")
        private String vehicleType;

        @JsonProperty("vehicle_age")
        private String vehicleAge;

        private String city;
        private String state;

        @JsonProperty("rc_fit_upto")
        private String rc_fit_upto;

        private String financer;

        @JsonProperty("insurance")
        private Insurance insurance;


        @Data
        public static class Insurance {
            @JsonProperty("company")
            private String company;

            @JsonProperty("expiry_date")
            private String expiry_date;

            @JsonProperty("policy_number")
            private String policy_number;
        }
    }
}
