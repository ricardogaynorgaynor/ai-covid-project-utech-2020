package com.ai.covid.models;

import java.util.Set;

public class CovidInfo {

    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String terms;
    private float temperature;
    private int age;
    private Set<KnownIllness> illnesses;
    private String gender;
    private String difficultyBreathing;
    private String runnyNose;
    private String aches;
    private String dspv;
    private String riskMessage;
//    private String shortnessOfBreath;
//    private String chestPainOrPressure;
//    private String lossOfMovement;
//    private String lossOfSpeeach;


    public CovidInfo() {
    }

    public CovidInfo(String firstName, String lastName, String email, String mobile, String terms, float temperature, int age, Set<KnownIllness> illnesses, String gender, String difficultyBreathing, String runnyNose, String aches, String dspv) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.terms = terms;
        this.temperature = temperature;
        this.age = age;
        this.illnesses = illnesses;
        this.gender = gender;
        this.difficultyBreathing = difficultyBreathing;
        this.runnyNose = runnyNose;
        this.aches = aches;
        this.dspv = dspv;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<KnownIllness> getIllnesses() {
        return illnesses;
    }

    public void setIllnesses(Set<KnownIllness> illnesses) {
        this.illnesses = illnesses;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDifficultyBreathing() {
        return difficultyBreathing;
    }

    public void setDifficultyBreathing(String difficultyBreathing) {
        this.difficultyBreathing = difficultyBreathing;
    }

    public String getRunnyNose() {
        return runnyNose;
    }

    public void setRunnyNose(String runnyNose) {
        this.runnyNose = runnyNose;
    }

    public String getAches() {
        return aches;
    }

    public void setAches(String aches) {
        this.aches = aches;
    }

    public String getDspv() {
        return dspv;
    }

    public void setDspv(String dspv) {
        this.dspv = dspv;
    }
    
    

	public String getRiskMessage() {
		return riskMessage;
	}

	public void setRiskMessage(String riskMessage) {
		this.riskMessage = riskMessage;
	}

	@Override
	public String toString() {
		return "CovidInfo [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", mobile="
				+ mobile + ", terms=" + terms + ", temperature=" + temperature + ", age=" + age + ", illnesses="
				+ illnesses + ", gender=" + gender + ", difficultyBreathing=" + difficultyBreathing + ", runnyNose="
				+ runnyNose + ", aches=" + aches + ", dspv=" + dspv + ", getFirstName()=" + getFirstName()
				+ ", getLastName()=" + getLastName() + ", getEmail()=" + getEmail() + ", getMobile()=" + getMobile()
				+ ", getTerms()=" + getTerms() + ", getTemperature()=" + getTemperature() + ", getAge()=" + getAge()
				+ ", getIllnesses()=" + getIllnesses() + ", getGender()=" + getGender() + ", getDifficultyBreathing()="
				+ getDifficultyBreathing() + ", getRunnyNose()=" + getRunnyNose() + ", getAches()=" + getAches()
				+ ", getDspv()=" + getDspv() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
    
    
}
