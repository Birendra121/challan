package com.avis.trcModule;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class VehicleRCDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String registrationNumber;
    private String status;
    private String ownerName;
    private String registrationDate;
    private String expiryDate;
    
  //  private String makeModel;
    private String vehicleMakeModel;
    private String color;
    private String fuel;
    private String category;
    private String vehicleClass;
    private String address;
    
    
    private String blacklistStatus;
    private String bodyTypeDescription;
    private String chassisNumber;
    
    private String financer;
    private String insuranceCompany;
    private String insuranceExpiryDate;
    private String insurancePolicyNumber;
    
    
    
    private String normsDescription;
    private String engineNumber;
    private String rcFitUpto;
    private String permitNumber;
    private String permitType;
    private String puccExpiryDate;
    private String puccNumber;
    private String registrationLocation;
    private String stateCode;
    private String statusAsOn;
    private String taxUpto;
    
    private String vehicleMakerDescription;
    private String manufacturedDate;
    private String cubicCapacity;
    private String numberOfCylinders;
    private String seatingCapacity;
    private String commercialStatus;
    private String rtoCode;
    private String financed;
    private String vehicleType;
    private String vehicleAge;
    private String city;
    private String state;
    private String categoryDescription;
    
   
  /*  
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getMakeModel() {
		return makeModel;
	}
	public void setMakeModel(String makeModel) {
		this.makeModel = makeModel;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getFuel() {
		return fuel;
	}
	public void setFuel(String fuel) {
		this.fuel = fuel;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getVehicleClass() {
		return vehicleClass;
	}
	public void setVehicleClass(String vehicleClass) {
		this.vehicleClass = vehicleClass;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBlacklistStatus() {
		return blacklistStatus;
	}
	public void setBlacklistStatus(String blacklistStatus) {
		this.blacklistStatus = blacklistStatus;
	}
	public String getBodyTypeDescription() {
		return bodyTypeDescription;
	}
	public void setBodyTypeDescription(String bodyTypeDescription) {
		this.bodyTypeDescription = bodyTypeDescription;
	}
	public String getChassisNumber() {
		return chassisNumber;
	}
	public void setChassisNumber(String chassisNumber) {
		this.chassisNumber = chassisNumber;
	}
	public String getFinancer() {
		return financer;
	}
	public void setFinancer(String financer) {
		this.financer = financer;
	}
	public String getInsuranceCompany() {
		return insuranceCompany;
	}
	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}
	public String getInsuranceExpiryDate() {
		return insuranceExpiryDate;
	}
	public void setInsuranceExpiryDate(String insuranceExpiryDate) {
		this.insuranceExpiryDate = insuranceExpiryDate;
	}
	public String getInsurancePolicyNumber() {
		return insurancePolicyNumber;
	}
	public void setInsurancePolicyNumber(String insurancePolicyNumber) {
		this.insurancePolicyNumber = insurancePolicyNumber;
	}
	public String getNormsDescription() {
		return normsDescription;
	}
	public void setNormsDescription(String normsDescription) {
		this.normsDescription = normsDescription;
	}
	public String getEngineNumber() {
		return engineNumber;
	}
	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}
	public String getFitUpto() {
		return getRcFitUpto();
	}
	public void setFitUpto(String rcFitUpto) {
		this.rcFitUpto = rcFitUpto;
	}
	public String getPermitNumber() {
		return permitNumber;
	}
	public void setPermitNumber(String permitNumber) {
		this.permitNumber = permitNumber;
	}
	public String getPermitType() {
		return permitType;
	}
	public void setPermitType(String permitType) {
		this.permitType = permitType;
	}
	public String getPuccExpiryDate() {
		return puccExpiryDate;
	}
	public void setPuccExpiryDate(String puccExpiryDate) {
		this.puccExpiryDate = puccExpiryDate;
	}
	public String getPuccNumber() {
		return puccNumber;
	}
	public void setPuccNumber(String puccNumber) {
		this.puccNumber = puccNumber;
	}
	public String getRegistrationLocation() {
		return registrationLocation;
	}
	public void setRegistrationLocation(String registrationLocation) {
		this.registrationLocation = registrationLocation;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getStatusAsOn() {
		return statusAsOn;
	}
	public void setStatusAsOn(String statusAsOn) {
		this.statusAsOn = statusAsOn;
	}
	public String getTaxUpto() {
		return taxUpto;
	}
	public void setTaxUpto(String taxUpto) {
		this.taxUpto = taxUpto;
	}
	public String getVehicleMakerDescription() {
		return vehicleMakerDescription;
	}
	public void setVehicleMakerDescription(String vehicleMakerDescription) {
		this.vehicleMakerDescription = vehicleMakerDescription;
	}
	public String getManufacturedDate() {
		return manufacturedDate;
	}
	public void setManufacturedDate(String manufacturedDate) {
		this.manufacturedDate = manufacturedDate;
	}
	public String getCubicCapacity() {
		return cubicCapacity;
	}
	public void setCubicCapacity(String cubicCapacity) {
		this.cubicCapacity = cubicCapacity;
	}
	public String getNumberOfCylinders() {
		return numberOfCylinders;
	}
	public void setNumberOfCylinders(String numberOfCylinders) {
		this.numberOfCylinders = numberOfCylinders;
	}
	public String getSeatingCapacity() {
		return seatingCapacity;
	}
	public void setSeatingCapacity(String seatingCapacity) {
		this.seatingCapacity = seatingCapacity;
	}
	public String getCommercialStatus() {
		return commercialStatus;
	}
	public void setCommercialStatus(String commercialStatus) {
		this.commercialStatus = commercialStatus;
	}
	public String getRtoCode() {
		return rtoCode;
	}
	public void setRtoCode(String rtoCode) {
		this.rtoCode = rtoCode;
	}
	public String getFinanced() {
		return financed;
	}
	public void setFinanced(String financed) {
		this.financed = financed;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getVehicleAge() {
		return vehicleAge;
	}
	public void setVehicleAge(String vehicleAge) {
		this.vehicleAge = vehicleAge;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCategoryDescription() {
		return categoryDescription;
	}
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
*/
    // Getters and setters
    
    
    
}
