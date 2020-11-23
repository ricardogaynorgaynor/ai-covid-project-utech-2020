child_of(joe, ralf). 
child_of(mary, joe). 
child_of(steve, joe).
descendent_of(X, Y) :- 
    child_of(X, Y). 
descendent_of(X, Y) :- 
    child_of(Z, Y), 
    descendent_of(X, Z).



% Ricardo Gaynor (1605048) - Assignment #1 AI Lab
%************ QUESTION 1 - 15 MRKS *******************%
taxable :- write('Enater name: '), nl, read(Name),
    				nl, write('Enter tax revenue number'), nl, read(Number),
    				nl, write('Enter income amount'), nl, read(Income),
    				nl, write('Are you Marry y/n ?'), nl, read(Married),
                    nl, write('Do you have children y/n ?'), nl, read(Children),
    				calcalculate_tax(Income, Tax),
    				get_refund(Married, Refund, Children),
    				get_disposable_income(Income, Tax, Refund, DisposableAmt),
					print_result(Name, Number, Income, Tax, Refund, DisposableAmt, Children).

calcalculate_tax(Income, Tax) :- (Income >= 1, Income =< 15461.99) -> Tax is 0;
    							(Income >= 15462.00, Income =< 28893.99) -> Tax is Income * 0.10;
    							(Income >= 28894.00, Income =< 59499.99) -> Tax is Income * 0.15;
    							(Income >= 59500.00, Income =< 65899.99) -> Tax is Income * 0.20;
    							(Income > 65900.00) -> Tax is Income * 0.25;
    							Tax is 0.

get_refund(Married, Refund, Children) :- ((Married = 'y'; Married = 'Y'), (Children = 'y'; Children = 'Y')) ->  Refund is 50;
    							(Children = 'y'; Children = 'Y') -> Refund is 60;
    							Refund is 0.
 
get_disposable_income(Income, Tax, Refund, DisposableAmt) :- DisposableAmt is Income - Tax + Refund.

print_result(Name, Number, Income, Tax, Refund, DisposableAmt, Children) :-
					write('*****Tax payable Report***'), nl,
					write('Name '), write(Name), nl,
					write('Number '), write(Number), nl,nl,
					write('Taxable Income '), write(Income), nl,
					write('Taxes '), write(Tax), nl,
					write('Refund '), write(Refund), nl,
					write('Disposable Income '), write(DisposableAmt), nl,
                    write('Do you wish to run the program again y/n ?'), nl, read(Continue),
    				continue_program(Continue).

continue_program(Continue) :- (Continue = 'y'; Continue = 'Y') -> taxable ; nl, write('Program terminated').

%************ QUESTION 2 - 15 MRKS *******************%

/*

Formalize the following problem by implementing the necessary Prolog facts and rules.
Destinations may be travelled to via various airlines. Panam and Stauline offer first class travel, Mercury offers
only business class. Panam’s origin is in Canada, Mercury is in Norway and Stauline is in Korea. Panam stops
over in Norway, Korea and Canada. Mercury stops over in Korea and Germany and Stauline stops over in
Germany and Canada. Stauline and Panam fly to Europe and Asia, Mercury flies South America.


Michael wants to travel to South America via an airline who will stop over in Korea but doesn’t care about the
class.

Dorine wants to travel first class to Europe or Asia via an airline that does not originate in Korea and will
stop over in Germany or Canada.

Lisa wants to travel first class to Europe or Asia via an airline who will stop
over in Germany.

Write a knowledge-base in PROLOG which encodes the above knowledge in as general a form as possible, and
a predicate, which, when executed, generates a sequence of statements of the form:

<traveler> can travel to <destination> via <airline>

*/


classes('First Class').
classes('Business Class').

airline('Panam').
airline('Stauline').
airline('Mercury').

orign('Canada').
orign('Norway').
orign('Korea').

destination('Europe').
destination('Asia').
destination('South America').

offered('Panam', 'First Class').
offered('Stauline', 'First Class').
offered('Mercury', 'Business Class').

orign_in('Panam', 'Canada').
orign_in('Mercury', 'Norway').
orign_in('Stauline', 'Korea').

stops_over('Panam', 'Norway').
stops_over('Panam', 'Korea').
stops_over('Panam', 'Canada').

stops_over('Mercury', 'Korea').
stops_over('Mercury', 'Germany').

stops_over('Stauline', 'Canada').
stops_over('Stauline', 'Germany').

fly_to('Stauline', 'Europe').
fly_to('Stauline', 'Asia').

fly_to('Panam', 'Europe').
fly_to('Panam', 'Asia').

fly_to('Mercury', 'South America').

michael(Destination, Airline) :- fly_to(Airline, 'South America'), stops_over(Airline, 'Korea'),
                destination(Destination), display_travel_details('Michael', Airline, Destination).

doraine(Destination, Airline) :- (fly_to(Airline, 'Europe');fly_to(Airline, 'Asia')), offered(Airline, 'First Class'),
    not(stops_over(Airline, 'Germany');stops_over(Airline, 'Canada')), destination(Destination), (Destination = 'Europe';Destination = 'Asia'), not(orign('Korea')), display_travel_details('Lisa', Airline, Destination).

lisa(Destination, Airline) :- (fly_to(Airline, 'Europe');fly_to(Airline, 'Asia')), offered(Airline, 'First Class'),
    not(stops_over(Airline, 'Germany')), destination(Destination), (Destination = 'Europe';Destination = 'Asia'),  display_travel_details('Lisa', Airline, Destination).

display_travel_details(Traveller, Airline, Destination) :- write(Traveller), write(' can travel to '), write(Destination), write(' via '), write(Airline), nl.