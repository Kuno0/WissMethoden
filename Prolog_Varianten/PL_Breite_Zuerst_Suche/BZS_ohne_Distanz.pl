% Modul zum Finden von Pfaden mit Hilfe der Breite-Zuerst-Suche auf
% Basis von Lunze: Künstliche Intelligenz für Ingenieure
%
:-module(find_path,[go/2,insertStart/3]).
:-[edges].
:-use_module(library(clpfd)).

path(From,To,Dist) :- edge(To,From,Dist).
path(From,To,Dist) :- edge(From,To,Dist).

% Definition: go(Startknoten, Zielknoten, Ergebnispfad, Distanz)

go(From,To):-
         findpath(From,To,[],[],[],[],0).
        % write('Pfad: '),
        % write(Path).

% Definition: findpath(Start, Ziel, [Liste markierter Knoten], [Liste aktiver Knoten], Pfad, Distanz).
% Abbruchbedingung: Abbrechen, sobald der Endknoten in der Liste enthalten ist
%findpath(Start,To,Visited,Path,Dist):-
%          Start=To,
%          reverse(Visited,Path). %  Die Startliste enthält den Pfad.

insertStart(Start,[],[]).
insertStart(Start,[K1|Rest],[[Start|K1]|K2]):-insertStart(Start,Rest,K2).

/* Ist true, sofern es vom atuellen Start aus eine Kante gibt, mit der
 der Weg zum Ziel kürzer ist.*/
% compareList([[_|T]|T2],[[_|B]|B2],AktDist,UpdatedActiveList):- NewDist
% is B+AktDist,
%         compare(<,T,NewDist)->
%         compareList(T2,B2,AktDist,Xs).

findpath(Start,To,Found,Active,EdgeList,Path,AktDist):-
         % Aufbau der Kantenliste mit allen gefunden Knoten
         findall([Knot,Dist],(path(Start,Knot,Dist),not(member(Knot,Found))),NoSEdgeList),
         % Noch den Start zu den jeweiligen Kanten hinzufügen
         insertStart(Start,NoSEdgeList,_edgeList),
%         write(SKnotList),nl,

         /*Liste der gefundenen Kanten: Es ist kürzer, noch mal alle neuen Kanten zu suchen, als immer wieder eine
         ellenlange Liste aufzubauen*/
         findall(Knot,(path(Start,Knot,_),not(member(Knot,Found))),FoundList),
         append(Found,Foundlist, NewFoundList),
%         write(NewFoundlist),
%
         /* Liste der aktiven Kanten setzt sich zusammen aus NoSEdgeList mit angepassten Distanzen.
         Dazu müssen zunächst alle Kanten gefunden werden, die von Start ausgehen und deren Enden
         mit den Endknoten aus der FoundList zusammenfallen.*/
         findall([Knot,Dist],(path(Start,Knot,Dist),member(Knot,FoundList)),Updated),
%         compareList(NoSEdgeList,Updated,AktDist,UpdatedActiveList),
         write(Updated),nl,
        write(NoSEdgeList).


/*        _edgeList=[FirstPath|RestEdge],
         % Die neue aktuelle Distanz nach der Bewegung muss festgesetzt werden
         FirstPath=[_,_,D],
         RestEdge=[Start,_,_],
%         write(D).

         NewAktDist is AktDist + D.
         % Die Liste der aktiven Knoten muss in jedem Schritt hinsichtlich der Distanz aktualisiert werden

%      findpath(Start,To,NewFoundList,RestActive,RestEdge,[FirstPath|Path],NewAktDist).*/

