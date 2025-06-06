package com.avis.trcModule;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.avis.exception.NoRecordFoundException;

@Service
public class VehicleRCAPIRetryService {

    @Value("${zoop.advance.api.url}")
    private String ZOOP_API_URL;

    @Value("${zoop.advance.api.key}")
    private String API_KEY;

    @Value("${zoop.advance.app.id}")
    private String APP_ID;

    @Retryable(
        value = { Exception.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public VehicleRCDetail fetchRCDetail(String vehicleNumber) throws Exception {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("api-key", API_KEY);
            headers.set("app-id", APP_ID);

            ZoopRequest.Data data = new ZoopRequest.Data();
            data.setVehicle_registration_number(vehicleNumber);

            ZoopRequest request = new ZoopRequest();
            request.setTask_id(UUID.randomUUID().toString());
            request.setData(data);

            HttpEntity<ZoopRequest> entity = new HttpEntity<>(request, headers);
            ResponseEntity<ZoopResponse> response = restTemplate.postForEntity(ZOOP_API_URL, entity, ZoopResponse.class);

          /*  if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null && response.getBody().isSuccess()) {*/
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                ZoopResponse responseBody = response.getBody();
                
                if (!responseBody.isSuccess()) {
                    throw new NoRecordFoundException("No RC details found for vehicle number: " + vehicleNumber);
                }
                
                ZoopResponse.Result r = response.getBody().getResult();
                VehicleRCDetail rc = new VehicleRCDetail();

                rc.setRegistrationNumber(r.getRc_registration_number());
                rc.setStatus(r.getRc_status());
                rc.setOwnerName(r.getUser_name());
                rc.setRegistrationDate(r.getRc_registration_date());
                rc.setExpiryDate(r.getRc_expiry_date());
                rc.setVehicleMakeModel(r.getVehicle_make_model());
                rc.setColor(r.getVehicle_color());
                rc.setFuel(r.getVehicle_fuel_description());
                rc.setCategory(r.getVehicle_category_description());
                rc.setVehicleClass(r.getVehicle_class_description());
                rc.setAddress(r.getUser_present_address());

                rc.setBlacklistStatus(r.getRc_blacklist_status());
                rc.setBodyTypeDescription(r.getBodyTypeDescription());
                rc.setChassisNumber(r.getRc_chassis_number());
                rc.setFinancer(r.getFinancer());

                if (r.getInsurance() != null) {
                    rc.setInsuranceCompany(r.getInsurance().getCompany());
                    rc.setInsuranceExpiryDate(r.getInsurance().getExpiry_date());
                    rc.setInsurancePolicyNumber(r.getInsurance().getPolicy_number());
                }

                rc.setNormsDescription(r.getNorms_description());
                rc.setEngineNumber(r.getRc_engine_number());
                rc.setRcFitUpto(r.getRc_fit_upto());
                rc.setPermitNumber(r.getRc_permit_number());
                rc.setPermitType(r.getRc_permit_type());
                rc.setPuccExpiryDate(r.getRc_pucc_expiry_date());
                rc.setPuccNumber(r.getRc_pucc_no());
                rc.setRegistrationLocation(r.getRc_registration_location());
                rc.setStateCode(r.getRc_state_code());
                rc.setStatusAsOn(r.getRc_status_as_on());
                rc.setTaxUpto(r.getRc_tax_upto());
                rc.setVehicleMakerDescription(r.getVehicle_maker_description());
                rc.setManufacturedDate(r.getVehicle_manufactured_date());
                rc.setCubicCapacity(r.getVehicle_cubic_capacity());
                rc.setNumberOfCylinders(r.getVehicle_number_of_cylinders());
                rc.setSeatingCapacity(r.getVehicle_seating_capacity());
                rc.setCommercialStatus(r.getRc_commercial_status());
                rc.setRtoCode(r.getRtoCode());
                rc.setFinanced(r.getFinanced());
                rc.setVehicleType(r.getVehicleType());
                rc.setVehicleAge(r.getVehicleAge());
                rc.setCity(r.getCity());
                rc.setState(r.getState());
                rc.setCategoryDescription(r.getVehicle_category_description());

                return rc;
            }

           // throw new RuntimeException("Failed to fetch RC details from Zoop API");
            throw new RuntimeException("Failed to fetch RC details from Zoop API. Response code: " + response.getStatusCode());

        } catch (Exception e) {
            System.out.println("API failed for vehicle number: " + vehicleNumber + " -> " + e.getMessage());
            throw e; // important for retry to work
        }
    }
}
