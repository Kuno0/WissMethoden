:-module(bzs_mit_distanz,[go/3]).

:-use_module(library(clpfd)).

:-[edges].
%:-[small_edges].

connected(From,To):-edge(From,To,_).
connected(From,To):-edge(To,From,_).

dist(From,To,Dist):-edge(To,From,Dist).
dist(From,To,Dist):-edge(From,To,Dist).


/* Die neue Distanz zum Knoten N ergibt sich aus der absoluten Distanz
 *  aus den Kanten und der bis zum bisherigen Knoten zurückgelegten
 *  Distanz */
rateByDistance(KnotA,Dist_A,KnotN,Dist_N):-
                                         dist(KnotA,KnotN,Edge_Dist),
                                         Dist_N is Dist_A + Edge_Dist.


/* Sortieren der Pfade:
 *  Die Liste neu gefundener Pfade entsprechen dem ersten Argument. Bei
 *  Aufruf ist die Liste 3 erst einmal leer. List1 enthält die Liste
 *  der Pfade, die bereits bekannt waren (quasi alte Pfade).
 *  H entspricht der ersten Unterliste d, folglich dem ersten Eintrag in
 *   der Liste der Pfade. T enthält den Rest und wird rekursiv aufgerufen.
 *   List1 enthält die Liste der Pfade, die bereits bekannt waren.
*/
sort_getPaths([],List,List).
sort_getPaths([H|T],List1,List3):-
                                         sort_Path(H,List1,List2),
                                         sort_getPaths(T,List2,List3).

/* Initialisierung: Beim ersten Aufruf ist List1 leer (mittlere Variable).
 *  Daher wird der Kopf der Liste (erste Kante) als Liste in die dritte
 *  Variable (List2) gegeben. */
sort_Path(Path,[],[Path]).
/* Prüfung, ob Distanz von Path1 kleiner ist als Distanz von Path2.
 *  Wenn das wahr ist, wird Path1 in die neue Liste List3 vor Path2
 *  gesetzt. */
sort_Path(Path1,[Path2|Rest],[Path1,Path2|Rest]):-
                                         Path1=[[_],K1],Path2=[[_],K2],K1=<K2,!.

/* für den Fall, dass das obere Prädikat false liefert, folglich K1>K2,
 *  wird rekursiv sort_Path mit den übrigen Werten aufgerufen. */
sort_Path(Path1,[Path2|Rest1],[Path2|Rest2]):-
                                         sort_Path(Path1,Rest1,Rest2).




/* Initialisierung:
 *  ruft Prädikat findPath/4 auf; Startpunkt wird an feste Variable path
 *  übergeben mit aktueller Distanz 0; die leere Liste an zweiter Position
 *  enthält später weitere Pfade zum Ziel; Übergibt Ziel und Lösung; */

go(From, To, Solution):- findPath([[From],0],[],To,Solution).


/* Abbruchbedingung für findpath:
 *  Wenn der erste Eintrag der ersten Unterliste der festen Variable
 *  path das Ziel enthält, wird die Lösung Solution an die Path-Variable
 *  übergeben. */

findPath(Path, MorePaths,To,Solution):-Path=[[To|_],_],
                                         Path=Solution,!.

/* Hier folgt der eigentliche Ablauf des Programmes:
 *  Path wird mit pfad belegt, wobei dieser aufgeteilt wid in den
 *  ersten Knoten A und den Restweg, der alle bisher gefundenen
 *  Knoten enthält, zusammen mit der entsprechend zurückgelegten Distanz bis A */

findPath(Path,MorePaths,To,Solution):-
                                         Path=path([KnotA|RestpathToA],Dist_A),
/* Finde alle Verbindungen von Knoten A zum neuen Knoten N,für die gilt,
 *  dass N nicht in der Liste von bisher gefundenen Knoten (in
 *  RestpathToA ) enthalten ist. Das Ergebnis wird noch im Goal sortiert
 *  und als Liste FoundPaths ausgegeben.
 *  Die Liste sieht beispielsweise folgendermaßen aus:
 *  [[path([12,5|4,2,1],12)],[path([13,5|4,2,1],14)]. */

                                         findall([[KnotN,KnotA|RestpathToA],Dist_N],
                                                 (connected(KnotA, KnotN),
                                                  not(member(KnotN,[KnotA|RestpathToA])),
                                                  rateByDistance(KnotA,Dist_A,KnotN,Dist_N)),
                                                 FoundPaths),
                                         sort_getPaths(FoundPaths,MorePaths,NewPaths),
     /* Aufsplitten der Liste mit neuen Pfaden */
     NewPaths=[PathN|RestPaths],
     /* Rekursionsschritt */
     findPath(PathN,RestPaths,To,Solution).

