:- use_module(library(pce)).


:-dynamic stats/2.
:-dynamic atrisk/1.



menu:-
    new(M,dialog('MOH Covid System-  Main Menu')),send(M,append,new(label)),
    send(M,append,button(diagnose_patient, message(@prolog, mainmenu))),
    send(M,open).

mainmenu:-
    new(D,dialog('MOH Health Diagnosis System')),send(D,append,new(label)),
    send(D,append,new(Name, text_item(name))),
    send(D,append,new(Age, text_item(age))),
    send(D,append,new(Sex, menu(sex,marked))),
    send(D,append,new(Temperature, text_item(temp))),
    send(D,append,new(UC, menu('Patient has underlying conditions',unmarked))),
    send(D,append,new(Symptoms, menu('Patient has symptoms',unmarked))),

    send(Sex, append, female), send(Sex, append, male),
    send(UC, append, yes), send(UC, append, no),
    send(Symptoms, append, yes),send(Symptoms, append, no),

    send(Age, type, int),
    send(Temperature, type, int),

    send(D,append,button(accept,message(@prolog,savemain,
                                        Name?selection,
                                        Temperature?selection,
                                        UC?selection
                                       ,Symptoms?selection))),
    send(D,open).

savemain(Name,Temperature,UC,Symptoms):-
    new(A,dialog('Dianosis of Results')),
    send(A,append,new(Lbl1234,label)),send(Lbl1234,append,'Name :'),
    send(A,append,new(Lbl1,label)),   send(Lbl1,append,Name),

    TempF is (temp*2)+30,
    send(A,append,new(Lbl1235,label)),send(Lbl1235,append,'Temperature :'),
    send(A,append,new(Lbl1,label)),   send(Lbl1,append,Temperature),

    (UC == 'yes' -> UCval is 1;UCval is 0),

    (Symptoms == 'yes' -> Symval is 1;Symval is 0),

    Hyperrisk is UCval+Symval+(TempF>100.4),

    send(A,append,new(Lbl15,label)),
    (Hyperrisk  >= 2 -> send(Lbl15,append,'You are at risk.'),Rval is 1;
    send(Lbl15,append,'You are not at risk'),Rval is 0),
    send(A,open).