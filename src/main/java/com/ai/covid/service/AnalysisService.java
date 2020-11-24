package com.ai.covid.service;

import com.ai.covid.models.CovidInfo;
import com.ai.covid.models.KnownIllness;
import com.ai.covid.models.Statistics;

import com.ai.covid.repositories.CovidInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.fli.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
//@RequiredArgsConstructor
//@Slf4j
//https://jpl7.org/ReleaseNotes701
public class AnalysisService {

    public AnalysisService() {
    }  

    public void queryKnowledgeBase(){
        Query query = new Query("consult", new Term[] {new Atom("test2.pl")});
        System.out.println( "consult " + (query.hasSolution() ? "succeeded" : "failed"));
       System.out.print(Query.oneSolution("mean(X,Y)").get("Y").intValue());
       System.out.print(Query.oneSolution("calcalculate_tax(40000,Y)").get("Y").intValue());

    } 
    
    public List<KnownIllness> getKnownIllnesses(){
    	List<KnownIllness> illnessesListFinal = new ArrayList<>(); 
    	 Query query = new Query("consult", new Term[] {new Atom("test2.pl")});
         System.out.println( "consult " + (query.hasSolution() ? "succeeded" : "failed"));
         String illnesses = Query.oneSolution("getIllnesses(X)").get("X").toString(); 
         String[] illnessList = illnesses.replace("[", "").replace("]", "")
        		 .split(",");
         for(int i = 0; i < illnessList.length; i++) {
        	 illnessesListFinal.add(new KnownIllness(Long.parseLong(String.valueOf((i+1))), illnessList[i].replace("'", "")));
         }
       return illnessesListFinal; 
    }
    
    public List<String> getSymptoms(final String fileName){
    	List<String> symptomsListFinal = new ArrayList<>(); 
    	 Query query = new Query("consult", new Term[] {new Atom("test2.pl")});
         System.out.println( "consult " + (query.hasSolution() ? "succeeded" : "failed"));
         String symptoms = Query.oneSolution("getSymptoms(X,'"+fileName+"')").get("X").toString(); 
         String[] symptomsList = symptoms.replace("[", "").replace("]", "")
        		 .split(",");
         for(int i = 0; i < symptomsList.length; i++) {
        	 symptomsListFinal.add(symptomsList[i].replace("'", ""));
         }
       return symptomsListFinal;  
    }
    
    public void initializeIllnesses() {
    	 Query query = new Query("consult", new Term[] {new Atom("test2.pl")});
    	 System.out.println( "consult " + (query.hasSolution() ? "succeeded" : "failed"));
    	this.getKnownIllnesses().forEach(i -> {
    		Query.oneSolution("initializeIllness('"+i.getName()+"')"); 
    	});
    }
    
    public void initializeSymptoms(final String fileName, final String function) {
   	 Query query = new Query("consult", new Term[] {new Atom("test2.pl")});
   	 System.out.println( "consult " + (query.hasSolution() ? "succeeded" : "failed"));
   	this.getSymptoms(fileName).forEach(s -> {
   		Query.oneSolution(function+"('"+s+"')");
   	});
   } 
    
    public void addSymptom(final String symptom, final String fileName) {
    	 Query query = new Query("consult", new Term[] {new Atom("test2.pl")});
         System.out.println( "consult " + (query.hasSolution() ? "succeeded" : "failed"));
    	 Query.oneSolution("addSymptom('"+symptom+ "','" + fileName + "')"); 
    } 
    
    public List<Statistics> getStatistics(){
    	List<Statistics> statistics = new ArrayList<>();
    	// read stats from prolog txt file and populate the list
    	return statistics; 
    }

    public String performAnalysis(final CovidInfo covidInfo) {
    	this.initializeIllnesses(); // initialize the illness facts
    	this.initializeSymptoms("common_symptoms.txt", "initializeCommonSymptoms"); // initialize common symptoms
    	this.initializeSymptoms("less_common_symptoms.txt", "initializeLessCommonSymptoms"); // initialize less common symptoms
    	this.initializeSymptoms("serious_symptoms.txt", "initializeSeriousSymptoms"); // initialize serious symptoms
        List<String> illnesses = new ArrayList<>();
        covidInfo.getIllnesses().forEach(i -> illnesses.add(i.getName()));
        String delimitedIllnesses = String.join("%", illnesses);
        int totalIllnesses = covidInfo.getIllnesses().size();
        //String queryString = "performAnalysis("+"'"+illnessPlaceHolder+ "','" +covidInfo.getTemperature()+ "','" +covidInfo.getAge()+ "','" +covidInfo.getGender()+ "','" +covidInfo.getDifficultyBreathing()+ "','" +covidInfo.getRunnyNose()+ "','" +covidInfo.getAches()+ "','" +covidInfo.getDspv()+ "'," +"R)";
        //System.out.println(queryString);
        Query query = new Query("consult", new Term[] {new Atom("test2.pl")});
        System.out.println( "consult " + (query.hasSolution() ? "succeeded" : "failed"));
        final String result = Query.oneSolution("performAnalysis("+""+totalIllnesses+ "," +covidInfo.getTemperature()+ "," +covidInfo.getAge()+ ",'" +covidInfo.getGender()+ "','" +covidInfo.getDifficultyBreathing()+ "','" +covidInfo.getRunnyNose()+ "','" +covidInfo.getAches()+ "','" +covidInfo.getDspv()+ "','" +covidInfo.getLossOfSmell()+ "','" +covidInfo.getLossOfTaste()+ "','" +covidInfo.getDryCaugh()+ "','" +covidInfo.getFever()+ "','" +covidInfo.getSkinRash()+ "'," +"R)").get("R").name();
        System.out.println("Result: " + result.toLowerCase());
        return result;
    }
}