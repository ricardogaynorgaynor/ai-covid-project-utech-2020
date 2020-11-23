package com.ai.covid.controllers;

import com.ai.covid.models.CovidInfo;
import com.ai.covid.models.KnownIllness;
import com.ai.covid.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

import javax.servlet.http.HttpServletRequest;

//@RequiredArgsConstructor
@Controller("/")
public class HomeController {

    @Autowired
    private AnalysisService analysisService = new AnalysisService();
 
    @GetMapping("/") 
    public String index(Model model, HttpServletRequest request){
        model.addAttribute("covidInfo", new CovidInfo());
        model.addAttribute("illnesses", analysisService.getKnownIllnesses());
        if(request.getSession().getAttribute("riskVal") != null) {
        	model.addAttribute("riskVal", (String) (request.getSession().getAttribute("riskVal")));
        }
        return "index";
    }

    @PostMapping(value = "/process/data", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String processData(@RequestParam MultiValueMap<String, Object> body, HttpServletRequest request){
        CovidInfo covidInfo = new CovidInfo();
        covidInfo.setGender( (String) body.getFirst("gender"));
        covidInfo.setDifficultyBreathing( (String) body.getFirst("difficultyBreathing"));
        covidInfo.setRunnyNose((String) body.getFirst("runnyNose"));
        covidInfo.setAches((String) body.getFirst("aches"));
        covidInfo.setDspv((String) body.getFirst("dspv"));
        covidInfo.setTemperature(Float.parseFloat(String.valueOf(body.getFirst("temperature"))));
        covidInfo.setFirstName((String) body.getFirst("firstName"));
        covidInfo.setLastName((String) body.getFirst("lastName"));
        covidInfo.setEmail((String) body.getFirst("email"));
        covidInfo.setMobile((String) body.getFirst("mobile"));
        covidInfo.setTerms((String) body.getFirst("terms"));
        Set<KnownIllness> underlyingConditions = new HashSet<>();
        body.get("illnesses").forEach(r -> underlyingConditions.add(
        		analysisService.getKnownIllnesses()
                        .stream().filter(f -> f.getId().equals(Long.parseLong(String.valueOf(r))))
                        .findFirst() 
                        .orElse(null)));
        covidInfo.setIllnesses(underlyingConditions);
        String result = analysisService.performAnalysis(covidInfo);
        request.getSession().setAttribute("riskVal", result); 
        return "redirect:/";
    }
    
    @PostMapping(value = "/save/symptom", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveSymptom(@RequestParam MultiValueMap<String, Object> body, HttpServletRequest request){
        String symptom = ((String) body.getFirst("symptom"));
        analysisService.addSymptom(symptom);
    	return "redirect:/";
    }
   

}
