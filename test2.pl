:- dynamic illnesses/1.
:- dynamic symptoms/1.

illnesses(none).
symptoms(none).

performAnalysis(Illness, Temperature, Age, Gender, DifficultyBreathing, RunnyNose, Aches, Dspv, R):-
				(DifficultyBreathing == 'yes' -> DifficultyBreathingVal is 1; DifficultyBreathingVal is 0),
				(RunnyNose == 'yes' -> RunnyNoseVal is 1; RunnyNoseVal is 0),
				(Aches == 'yes' -> AchesVal is 1; AchesVal is 0),
				(Temperature > 100.3 -> FeverVal is 1; FeverVal is 0),
				(Dspv == 'yes' -> DspvVal is 1; DspvVal is 0),
				(RiskScore is DspvVal+AchesVal+RunnyNoseVal+DifficultyBreathingVal),
				determine_risk_val(FeverVal, RiskScore, R).

determine_risk_val(FeverVal, RiskScore, R):- (FeverVal == 1, RiskScore > 0) -> R = "Your risk is very high";
				(FeverVal == 1, RiskScore < 1) -> R = "Your are at risk";
				(RiskScore > 0) -> R = "You are at risk";
				R = "You're not at risk".

%get the user illnesses

%split_string(illnesses, "%", "", IllnessesValList),


initializeIllness(I) :- (illnesses(I) -> nl,write('Illness already recorded');
                       assert(illnesses(I)), write('Illness added successfully')).
                       
initializeSymptoms(I) :- (symptoms(I) -> nl,write('Symptom already recorded');
                       assert(symptoms(I)), write('Symptom added successfully')).

getIllnesses(X) :-
    open('illnesses.txt', read, Str),
    read_file(Str,Lines),
    close(Str),
    X = Lines.
    
getSymptoms(X) :-
    open('symptoms.txt', read, Str),
    read_file(Str,Lines),
    close(Str),
    X = Lines.

read_file(Stream,[]) :-
    at_end_of_stream(Stream).

read_file(Stream,[X|L]) :-
    \+ at_end_of_stream(Stream),
    read(Stream,X),
    read_file(Stream,L).
    
addSymptom(S):- open('symptoms.txt',append,Stream),
		write(Stream,"'"),
		write(Stream,S),write(Stream,"'."),
		close(Stream).
		
		
		