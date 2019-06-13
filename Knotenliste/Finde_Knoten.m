% Einlesen des Lageplans der RUB als PDF
RUB=imread("RUB_nur_Knoten.png");
imshow(RUB);
[centers, radii]=imfindcircles(RUB, [10 20],'ObjectPolarity','dark');
viscircles(centers, radii,'Color','b');

% Die min und max Werte in x und y in dieser Darstellung
min_X=min(centers(:,1));
min_Y=min(centers(:,2));
max_X=max(centers(:,1));
max_Y=max(centers(:,2));

% Die Punkte in der 150DPI Abbildungliegen bei
% Xmin=128,Ymin=49, Xmax=1693 und Ymax=1047

%Vereinzelung der Koordinaten
centers_X=centers(:,1);
centers_Y=centers(:,2);

% Mappen der Koordianten auf das 150DPI Bild
x=[min_X max_X];
[Y1,PS1]=mapminmax(x,128,1693);
centers_X_150DPI = mapminmax('apply',centers_X,PS1);    
y=[min_Y max_Y];
[Y2,PS2]=mapminmax(y,49,1047);
centers_Y_150DPI= mapminmax('apply',centers_Y,PS2);

figure;
RUB150=imread("RUB-Lageplan_150dpi.png");
imshow(RUB150);hold on;
plot(centers_X_150DPI,centers_Y_150DPI, 'o','color','red');

% Es werden 273 Punkte gefunden
Punkte=(1:1:273);
% Eintragen der Zahlen in den Plot
Punkte_str=string(Punkte);
Punkte=Punkte(:);
labelpoints(centers_X_150DPI, centers_Y_150DPI, Punkte);
hold off

% Zusammenfügen der Daten in einer Matrix und Ausgabe der 
% gerundeten Mittelpunkte als CSV
centers_150DPI=[Punkte,round(centers_X_150DPI),round(centers_Y_150DPI)];
csvwrite('Knoten.txt',centers_150DPI);
