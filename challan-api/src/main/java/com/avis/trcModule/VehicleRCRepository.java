package com.avis.trcModule;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRCRepository extends JpaRepository<VehicleRCDetail, Long> {

	VehicleRCDetail findByRegistrationNumber(String registrationNumber);

}