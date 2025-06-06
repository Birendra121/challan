package com.avis.trcModule;


public class ZoopRequest {
    private String mode = "sync";
    private Data data;
    private String task_id;

    public static class Data {
        private String vehicle_registration_number;
        private String consent = "Y";
        private String consent_text = "I hear by declare my consent agreement for fetching my information via ZOOP API.";
        // Getters and setters
		public String getVehicle_registration_number() {
			return vehicle_registration_number;
		}
		public void setVehicle_registration_number(String vehicle_registration_number) {
			this.vehicle_registration_number = vehicle_registration_number;
		}
		public String getConsent() {
			return consent;
		}
		public void setConsent(String consent) {
			this.consent = consent;
		}
		public String getConsent_text() {
			return consent_text;
		}
		public void setConsent_text(String consent_text) {
			this.consent_text = consent_text;
		}
        
        
    }

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}

    // Getters and setters
    
    
}
