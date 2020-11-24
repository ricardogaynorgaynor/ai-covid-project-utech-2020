package com.ai.covid.controllers;

import com.ai.covid.models.CovidInfo;
import com.ai.covid.models.KnownIllness;
import com.ai.covid.repositories.CovidInfoRepository;
import com.ai.covid.service.AnalysisService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

//@RequiredArgsConstructor
@Controller("/")
public class HomeController {

    @Autowired 
    private AnalysisService analysisService = new AnalysisService();

    @Autowired
    private CovidInfoRepository covidInfoRepository;
 
    @GetMapping("/") 
    public String index(Model model, HttpServletRequest request){
        model.addAttribute("covidInfo", new CovidInfo());
        model.addAttribute("illnesses", analysisService.getKnownIllnesses());
        if(request.getSession().getAttribute("riskVal") != null) {
        	model.addAttribute("riskVal", (String) (request.getSession().getAttribute("riskVal")));
        }
        return "index";
    }
    
     
    @GetMapping("/result") 
    public String result(Model model, HttpServletRequest request){
        model.addAttribute("illnesses", analysisService.getKnownIllnesses());
        CovidInfo covidInfo = (CovidInfo) request.getSession().getAttribute("covidInfo");
        if(covidInfo != null) {
            System.out.print(covidInfo.toString());  
        	model.addAttribute("covidInfo", covidInfo);
        	
        }
        return "result";
    }

    @PostMapping(value = "/process/data", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String processData(@RequestParam MultiValueMap<String, Object> body, HttpServletRequest request){
        CovidInfo covidInfo = new CovidInfo();
        covidInfo.setGender( (String) body.getFirst("gender"));
        covidInfo.setAge(body.getFirst("age") != null ? Integer.parseInt( (String) body.getFirst("age")) : 0);
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
        covidInfo.setLossOfTaste((String) body.getFirst("lass_of_taste"));
        covidInfo.setLossOfSmell((String) body.getFirst("lass_of_smell"));
        covidInfo.setSkinRash((String) body.getFirst("skin_rash"));
        covidInfo.setFever((String) body.getFirst("fever"));
        covidInfo.setDryCaugh((String) body.getFirst("dry_cough"));
        Set<KnownIllness> underlyingConditions = new HashSet<>();
        body.get("illnesses").forEach(r -> underlyingConditions.add(
        		analysisService.getKnownIllnesses()
                        .stream().filter(f -> f.getId().equals(Long.parseLong(String.valueOf(r))))
                        .findFirst() 
                        .orElse(null)));
        covidInfo.setIllnesses(underlyingConditions);
        CovidInfo covTemp = covidInfo;
        covTemp.setDifficultyBreathing(covidInfo.getDifficultyBreathing().equalsIgnoreCase("No") ? "No" : "Yes");
        covTemp.setRunnyNose(covidInfo.getRunnyNose().equalsIgnoreCase("No") ? "No" : "Yes");
        covTemp.setAches(covidInfo.getAches().equalsIgnoreCase("No") ? "No" : "Yes");
        covTemp.setDspv(covidInfo.getDspv().equalsIgnoreCase("No") ? "No" : "Yes");
        covTemp.setLossOfSmell(covidInfo.getLossOfTaste().equalsIgnoreCase("No") ? "No" : "Yes");
        covTemp.setLossOfTaste(covidInfo.getLossOfTaste().equalsIgnoreCase("No") ? "No" : "Yes");
        covTemp.setSkinRash(covidInfo.getSkinRash().equalsIgnoreCase("No") ? "No" : "Yes");
        covTemp.setFever(covidInfo.getFever().equalsIgnoreCase("No") ? "No" : "Yes");
        covTemp.setDryCaugh(covidInfo.getDryCaugh().equalsIgnoreCase("No") ? "No" : "Yes");
        String result = analysisService.performAnalysis(covidInfo);
        covTemp.setRiskMessage(result);
        System.out.println("Attempting to save test info");
        covidInfoRepository.save(covTemp);
        System.out.println("Save completed successfully");
        //request.getSession().setAttribute("riskVal", result); 
        covTemp.setRiskMessage(result); 
        request.getSession().setAttribute("covidInfo", covTemp);
        return "redirect:/result";
    } 
    
    @PostMapping(value = "/save/symptom", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveSymptom(@RequestParam MultiValueMap<String, Object> body, HttpServletRequest request){
        String symptom = ((String) body.getFirst("symptom"));
        String fileName = ((String) body.getFirst("file_name"));
        Assert.notNull(symptom, "Symptom cannot be null");
        Assert.notNull(fileName, "File name cannot be null");
        analysisService.addSymptom(symptom, fileName);
    	return "redirect:/";
    }

    @GetMapping(value = "/result/history")
    public String getResultHistory(Model model){
//       List<CovidInfo> covidInfoList = covidInfoRepository.findAll().stream().map(t -> {
//            t.setSent(false);
//            return t;
//        }).collect(Collectors.toList());
//       covidInfoRepository.saveAll(covidInfoList);
        model.addAttribute("histories", covidInfoRepository.findAll(Sort.by(Sort.Direction.DESC, "lastName")));
        return "history";
    }

    @GetMapping("/result/by/user/{id}")
    public String result(Model model, @PathVariable("id") Long id) throws NotFoundException {
        CovidInfo covidInfo = covidInfoRepository.findById(id).orElseThrow(() ->
               new NotFoundException(String.format("User with ID %s not found", String.valueOf(id))));
       model.addAttribute("covidInfo", covidInfo);
        return "result";
    }
    

}
