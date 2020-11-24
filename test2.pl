:- dynamic illnesses/1.
:- dynamic symptoms/1.
:- dynamic serious_symptoms/1.
:- dynamic less_common_symptoms/1.
:- dynamic common_symptoms/1.

illnesses(none).
symptoms(none).
serious_symptoms(none).
less_common_symptoms(none).
common_symptoms(none).

%remember to convert temperature

performAnalysis(TotalUnderlyingIllnesses, Temperature, Age, Gender, DifficultyBreathing, RunnyNose, Aches, Dspv, LossOfSmell, LossOfTaste, DryCough, Fever, Rash, R):-
				%(Temperature is Temperature * 9/5 + 32), % convert temperature to Fahrenheit
				(Temperature > 100.3 -> FeverVal is 1; FeverVal is 0),

				% check serious symptoms severity
				(serious_symptoms(DifficultyBreathing) -> SeriousSymptomsVal is 3; SeriousSymptomsVal is 0),
				(serious_symptoms(RunnyNose) -> SeriousSymptomsVal is SeriousSymptomsVal + 3; SeriousSymptomsVal is SeriousSymptomsVal),
				(serious_symptoms(Aches) -> SeriousSymptomsVal is SeriousSymptomsVal + 3; SeriousSymptomsVal is SeriousSymptomsVal),
				(serious_symptoms(Dspv) -> SeriousSymptomsVal is SeriousSymptomsVal + 3; SeriousSymptomsVal is SeriousSymptomsVal),
				(serious_symptoms(LossOfSmell) -> SeriousSymptomsVal is SeriousSymptomsVal + 3; SeriousSymptomsVal is SeriousSymptomsVal),
				(serious_symptoms(LossOfTaste) -> SeriousSymptomsVal is SeriousSymptomsVal + 3; SeriousSymptomsVal is SeriousSymptomsVal),
				(serious_symptoms(DryCough) -> SeriousSymptomsVal is SeriousSymptomsVal + 3; SeriousSymptomsVal is SeriousSymptomsVal),
				(serious_symptoms(Fever) -> SeriousSymptomsVal is SeriousSymptomsVal + 3; SeriousSymptomsVal is SeriousSymptomsVal),
				(serious_symptoms(Rash) -> SeriousSymptomsVal is SeriousSymptomsVal + 3; SeriousSymptomsVal is SeriousSymptomsVal),

				% check less common symptoms
				(less_common_symptoms(DifficultyBreathing) -> LessCommonSymptomsVal is 1; LessCommonSymptomsVal is 0),
                (less_common_symptoms(RunnyNose) -> LessCommonSymptomsVal is LessCommonSymptomsVal + 1; LessCommonSymptomsVal is LessCommonSymptomsVal),
                (less_common_symptoms(Aches) -> LessCommonSymptomsVal is LessCommonSymptomsVal + 1; LessCommonSymptomsVal is LessCommonSymptomsVal),
                (less_common_symptoms(Dspv) -> LessCommonSymptomsVal is LessCommonSymptomsVal + 1; LessCommonSymptomsVal is LessCommonSymptomsVal),
                (less_common_symptoms(LossOfSmell) -> LessCommonSymptomsVal is LessCommonSymptomsVal + 1; LessCommonSymptomsVal is LessCommonSymptomsVal),
                (less_common_symptoms(LossOfTaste) -> LessCommonSymptomsVal is LessCommonSymptomsVal + 1; LessCommonSymptomsVal is LessCommonSymptomsVal),
                (less_common_symptoms(DryCough) -> LessCommonSymptomsVal is LessCommonSymptomsVal + 1; LessCommonSymptomsVal is LessCommonSymptomsVal),
                (less_common_symptoms(Fever) -> LessCommonSymptomsVal is LessCommonSymptomsVal + 1; LessCommonSymptomsVal is LessCommonSymptomsVal),
                (less_common_symptoms(Rash) -> LessCommonSymptomsVal is LessCommonSymptomsVal + 1; LessCommonSymptomsVal is LessCommonSymptomsVal),

                % check common symptoms
                (common_symptoms(DifficultyBreathing) -> CommonSymptomsVal is 2; CommonSymptomsVal is 0),
                (common_symptoms(RunnyNose) -> CommonSymptomsVal is CommonSymptomsVal + 2; CommonSymptomsVal is CommonSymptomsVal),
                (common_symptoms(Aches) -> CommonSymptomsVal is CommonSymptomsVal + 2; CommonSymptomsVal is CommonSymptomsVal),
                (common_symptoms(Dspv) -> CommonSymptomsVal is CommonSymptomsVal + 2; CommonSymptomsVal is CommonSymptomsVal),
                (common_symptoms(LossOfSmell) -> CommonSymptomsVal is CommonSymptomsVal + 2; CommonSymptomsVal is CommonSymptomsVal),
                (common_symptoms(LossOfTaste) -> CommonSymptomsVal is CommonSymptomsVal + 2; CommonSymptomsVal is CommonSymptomsVal),
                (common_symptoms(DryCough) -> CommonSymptomsVal is CommonSymptomsVal + 2; CommonSymptomsVal is CommonSymptomsVal),
                (common_symptoms(Fever) -> CommonSymptomsVal is CommonSymptomsVal + 2; CommonSymptomsVal is CommonSymptomsVal),
                (common_symptoms(Rash) -> CommonSymptomsVal is CommonSymptomsVal + 2; CommonSymptomsVal is CommonSymptomsVal),

                (RiskScore is SeriousSymptomsVal+LessCommonSymptomsVal+CommonSymptomsVal),

				determine_risk_val(FeverVal, RiskScore, SeriousSymptomsVal, LessCommonSymptomsVal, CommonSymptomsVal, Temperature, R).

determine_risk_val(FeverVal, RiskScore, SeriousSymptomsVal, LessCommonSymptomsVal, CommonSymptomsVal, Temperature, R):-
                SymptomText = "You have " + (SeriousSymptomsVal > 0 -> SeriousSymptomsVal/3 ; 0) + " Serious Symptoms, "
                + LessCommonSymptomsVal + " less common symptom and " + (CommonSymptomsVal > 0 -> SeriousSymptomsVal/2; 0) + " Common Symptom",
                (FeverVal == 1, SeriousSymptomsVal > 0) -> R = "Your risk is very high. " + SymptomText;
				(FeverVal == 1, RiskScore < 1) -> R = "You're at risk. You don't have any common, serious or less common symptoms, however you have a fever as your temperature is " + Temperature + " degrees Fahrenheit";
				(RiskScore > 0) -> R = "You're at risk";
				R = "You're not at risk".

initializeIllness(I) :- (illnesses(I) -> nl,write('Illness already recorded');
                       assert(illnesses(I)), write('Illness added successfully')).
                       
initializeLessCommonSymptoms(I) :- (less_common_symptoms(I) -> nl,write('Less Symptom already recorded');
                       assert(less_common_symptoms(I)), write('Less Symptom added successfully')).

initializeSeriousSymptoms(I) :- (serious_symptoms(I) -> nl,write('Serious Symptom already recorded');
                       assert(serious_symptoms(I)), write('Serious Symptom added successfully')).

initializeCommonSymptoms(I) :- (common_symptoms(I) -> nl,write('Common Symptom already recorded');
                       assert(common_symptoms(I)), write('Common Symptom added successfully')).

getIllnesses(X) :-
    open('illnesses.txt', read, Str),
    read_file(Str,Lines),
    close(Str),
    X = Lines.
    
getSymptoms(X, FileName) :-
    open(FileName, read, Str),
    read_file(Str,Lines),
    close(Str),
    X = Lines.

read_file(Stream,[]) :-
    at_end_of_stream(Stream).

read_file(Stream,[X|L]) :-
    \+ at_end_of_stream(Stream),
    read(Stream,X),
    read_file(Stream,L).
    
addSymptom(S, FileName):- open(FileName,append,Stream),
		write(Stream,"'"),
		write(Stream,S),write(Stream,"'."),
		close(Stream).