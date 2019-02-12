# Android

Proiectul este realizat impreuna cu o colega de facultate.
Aplicatia nu are si un server cu, care sa comunice de aceea unele functionalitati sunt mimate.(am create prin intermediul siteului myjson.com cateva fisiere json pe care sa le pot accesa de la o adresa din retea)

# Q/A Response Game/Audience Feedback app

# Functionalitati

1. Aplicatia suporta 2 tipuri de utiizatori: profesori si studenti.
2. Aplicatia permite crearea de intrebari de tip grila cu numar limitati de optiuni, timp maxim de raspuns, punctaj, feedback pentru raspuns gesit.
3. Aplicatia permite conectarea studentilor la test numai pe baza unui cod de acces
4. Aplicatia permite crearea de teste publice avand urmatoarele caracteristici: amestecarea intrebarilor si a raspunsurilor, numarul de incercari de rulare a testului, furnizarea de feedback, afisarea scorului final, alocare timp pe test, alocare punctaj pentru fiecare intrebare, prosibiliatea de a parcurge testul intr-un singur sens sau in ambele sensuri.
5. Aplicatia permite gestiunea testelor existente.
6. Aplicatia permite partajarea testelor intre profesori.


# Descriere functionalitate pentru utilizatorul de tip student

Activitati: `HomeStudent`, `ViewProfilStudent`, `StartTest`, 'RezultateTeste', `Chart`

1.In activitatea `HomeStudent` studentul are la dispozitie 3 butoane care duc catre activitatile:`ViewProfilStudent`, `StartTest`, `RezultateTeste`.

2.In activitatea 'ViewProfilStudent' utilizatorul is poate modifica datele pe care le-a folosit la crearea contului sau poate
da logout.

3.In activitatea `StartTest` studentul poate incepe un test introducand un pin dat de profesor.

4.In activitatea 'RezultateTeste' sunt afisate sub forma unul ListView reultatele obtinute de student la testele sustinute. Acesta
are posibilitatea sa salveze rezultatele intr-un fisier text sau csv sau sa vada reprezentarea grafica a rezultatelor.


# Descriere functionalitate pentru utilizatorul de tip profesor:

Activitati: `HomeProfesfor`, `ViewProfilProfesor`, `Teste`,`RapoarteProfesor`, `RaportPeTest`, `CreareTest`, `CreareIntrebare`,
`SetariTest`, `ShareTest`, `StartIndividual`, `StartEchipe`

1.In activitatea `HomeProfesor` profesorul are la dispozitie 3 butoane care duc catre activitatile:`ViewProfilProfesor`, `Teste`, 'RapoarteProfesor'.

2.In activitatea `ViewProfilProfesor` utilizatorul iss poate modifica datele pe care le-a folosit la crearea contului sau poate iesi 
din cont.

3.In activitatea `RapoarteProfesor` sunt afisate sub forma unul ListView testele pe care profesorul le-a dat. Prin apasarea pe un test acesta este dus in activitatea `RaportPeTest` une poate vedea studentii care au dat testul si punctajele luate de fiecare. Profesorul
mai are posibilitatea de a salva rezultatele intr-un fisier test sau csv.

4.In activitatea `Teste` sunt afisate testele pe care profesorul le-a creat. Acesta le poate modifica, sterge sau partaja cu alti profesori. De asemenea are posibilitatea de a crea un test nou apasand pe butonul Add Test. Acesta il duce in activitatea `CreateTest` in care se seteaza optiunile pentru test si de aici poate adauga si intrebari la test. Dupa ce a selectat un test din cele create profesorul poate apasa pe butonul 'GO' care-l va duce in activitatea `StarteIndiviual`. Aici profesorul primeste un pin pe care il va da studentilor pentru a se conecta a test. Studentii conectati apar intr-un GridView tot in aceasta activitate. Dupa conectarea studentilor profesorul poate alete sa inceapa testul sau poate apasa pe butonul 'Pe echipe'. Acesta ii deschide o fereastra in care trebuie sa introduca numarul de echipe dupa care este trimis in activitatea 'StarteEchipe' in care sunt afisate echipe formate si de aici poate incepe testul.
 

