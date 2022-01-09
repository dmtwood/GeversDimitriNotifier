# GeversDimitriNotifier
## Android project EhB - Dimitri Gevers

De Notifier is een applicatie waar gebruikers kunnen registreren en inloggen.

Ze kunnen vervolgens nieuwe notificaties bekijken en selecteren of een contact selecteren uit een contactenlijst. 
Daarna komen ze op een scherm waar de persoonlijke berichten met de geselecteerde gebruiker verschijnen en de mogelijkheid is om nieuwe notificaties te verzenden.

- Dit wordt bewerkstelligd met 5 activities waartussen informatie wordt uitgewisseld met behulp van Intents.

- De privacy statement kan optioneel getoond worden op de registratiepagina, dit is bewerkstelligd door het gebruik van Fragments in de RegisterActivity van de applicatie

- Qua layout is er meermaals gebruik gemaakt van een Lineair alsook van Recyclerviews met adapter. 

- Complexe layout van Recyclerviews werd vormgegeven m.b.v. de Groupie Library.

- Op niveau van individuele elementen is er nog gebruik gemaakt van de hdodenhof.circleimageview en drawables voor buttons, images, tekst.

- Na het inloggen wordt een supportActionbar gebruikt voor de navigatie.
 
- Er is ondersteuning van apparaten met minimum API 16 (Android versie 4.1).

- Tijdens ontwikkeling is uitvoerig getest op Pixel 4 en Nexus S, beide op API 16.

- De preview van de uploadimage bij registratie is wel niet op alle toestellen te zien, de functionaliteit blijft verder intact.

- Er is ondersteuning van hdpi, xhdpi en xxhdpi schermen. 

- Er is ondersteuning voor Nederlands en Engels. 

- Er is ondersteuning van landscape/portrait mode met verschillende layoutbestanden voor de NotifyActivity

- De users, afbeeldingen en notificaties worden opgeslagen m.b.v. respectievelijk Firestore Authentication, storage en een realtime-database.

- In de RegisterActivity wordt m.b.v. een coroutine een tweede thread gemaakt om de afbeelding van de gebruiker naar de Firestore storage te sturen.


