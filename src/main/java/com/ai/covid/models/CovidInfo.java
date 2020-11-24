package com.ai.covid.models;

import javax.persistence.*;
import java.util.Set;


@Table(name = "covid_info")
@Entity
public class CovidInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String email;
    @Column
    private String mobile;
    @Column
    private String terms;
    @Column
    private float temperature;
    @Column
    private int age;
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "illess_id")
    private Set<KnownIllness> illnesses;
    @Column
    private String gender;
    @Column
    private String difficultyBreathing;
    @Column
    private String runnyNose;
    @Column
    private String aches;
    @Column
    private String dspv;
    @Column
    private String riskMessage;
    @Column
    private String lossOfTaste;
    @Column
    private String lossOfSmell;
    @Column
    private String skinRash;
    @Column
    private String fever;
    @Column
    private String dryCaugh;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean sent;

    @Transient
    private String imageHolder;
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
	
	

	public String getLossOfTaste() {
		return lossOfTaste;
	}

	public void setLossOfTaste(String lossOfTaste) {
		this.lossOfTaste = lossOfTaste;
	}

	public String getLossOfSmell() {
		return lossOfSmell;
	}

	public void setLossOfSmell(String lossOfSmell) {
		this.lossOfSmell = lossOfSmell;
	}

	public String getSkinRash() {
		return skinRash;
	}

	public void setSkinRash(String skinRash) {
		this.skinRash = skinRash;
	}

	public String getFever() {
		return fever;
	}

	public void setFever(String fever) {
		this.fever = fever;
	}

	public String getDryCaugh() {
		return dryCaugh;
	}

	public void setDryCaugh(String dryCaugh) {
		this.dryCaugh = dryCaugh;
	}

	public String getFullName(){
        return String.format("%s %s", this.getFirstName(), this.getLastName());
    }
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImageHolder() {
		return this.gender.equalsIgnoreCase("Male") ? "male.png" : "feamle.png";
	}

	public void setImageHolder(String imageHolder) {
		this.imageHolder = imageHolder;
	}

    public boolean getSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    @Override
	public String toString() {
		return "CovidInfo [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", mobile="
				+ mobile + ", terms=" + terms + ", temperature=" + temperature + ", age=" + age + ", illnesses="
				+ illnesses + ", gender=" + gender + ", difficultyBreathing=" + difficultyBreathing + ", runnyNose="
				+ runnyNose + ", aches=" + aches + ", dspv=" + dspv + ", riskMessage=" + riskMessage + ", lossOfTaste="
				+ lossOfTaste + ", lossOfSmell=" + lossOfSmell + ", skinRash=" + skinRash + ", fever=" + fever
				+ ", dryCaugh=" + dryCaugh + ", getFirstName()=" + getFirstName() + ", getLastName()=" + getLastName()
				+ ", getEmail()=" + getEmail() + ", getMobile()=" + getMobile() + ", getTerms()=" + getTerms()
				+ ", getTemperature()=" + getTemperature() + ", getAge()=" + getAge() + ", getIllnesses()="
				+ getIllnesses() + ", getGender()=" + getGender() + ", getDifficultyBreathing()="
				+ getDifficultyBreathing() + ", getRunnyNose()=" + getRunnyNose() + ", getAches()=" + getAches()
				+ ", getDspv()=" + getDspv() + ", getRiskMessage()=" + getRiskMessage() + ", getLossOfTaste()="
				+ getLossOfTaste() + ", getLossOfSmell()=" + getLossOfSmell() + ", getSkinRash()=" + getSkinRash()
				+ ", getFever()=" + getFever() + ", getDryCaugh()=" + getDryCaugh() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	
    
}
