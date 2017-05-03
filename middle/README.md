# Fisa Cerintelor - Proiect IP - Middle-End

## Membrii Echipei
* Vrabie Tudor
* Manole Catalin
* Bordeianu Razvan
* Tifrea George
* Cojocariu Florin
* Strainu Emanuela
* Damoc Valentin
	
## Descrierea task-ului

Crearea unei componente ce ofera doua API-uri de comunicare cu:
* front-end-ul
* back-end-ul

Middle-end-ul va:
* primi requesturi de la front-end, acestea vor fi validate iar componenta va incerca sa combata
posibile atacuri (ex: SQL Injection). In final va trimite requestul spre back-end daca datele furnizate au fost valide.
* primi requesturi de la back-end si le va forwarda spre front-end.

## Schita
```
                 / Send Message    <  (BMS)    <  (FMC)    <  (FMR)    < Receive Message  \
(Back-End) <--> |                                                                          | <--> (Front-End)
                 \ Receive Message >  (BMR)    >  (BMC)    >  (FMS)    > Send Message     /
```
## Actori
### Front-End 
O componenta a aplicatiei (interfata pentru utilizatori) ce va prezenta userilor informatiile despre notificarile 
aferente serviciului la care s-au abonat si le va oferi posibiltatea de a adauga memo-uri la cerere. 
    
### Back-End
Un RESTFul service care stocheaza datele utilizatorilor,
notificarile pe care acestia doresc sa le primeasca,
proceseaza datele primite trimitand utilizatorului
instiintarile dorite.

## Componente
### BMR (Back-End Message Receiver)
Se va ocupa de preluarea mesajului
trimis de Back-End si il va pasa mai departe catre modulul middle-end-ului ce
se va ocupa de procesarea acestuia (BMC).

### BMC (Back-End Message Controller)
Se va ocupa de prelucrarea mesajului trimis de BMR si il va 
pasa mai departe catre modulul middle-end-ului ce se va ocupa de trimiterea
acestuia catre Front-End (FMS).

### FMS (Front-End Message Sender)
Se va ocupa de preluarea mesajului formatat de BMC si il va pasa mai departe 
catre modulul middle-end-ului ce se va ocupa de trimiterea acestuia catre Front-End.

### FMR (Front-End Message Receiver)
Se va ocupa de preluarea mesajului
trimis de Front-End si il va pasa mai departe catre modulul middle-end-ului ce
se va ocupa de procesarea acestuia (FMC).

### FMC (Front-End Message Controller)
Se va ocupa de prelucrarea mesajului trimis de FMR si il va 
pasa mai departe catre modulul middle-end-ului ce
se va ocupa de trimiterea acestuia catre Back-End (BMS).

### BMS (Back-End Message Sender)
Se va ocupa de preluarea mesajului formatat de FMC si il va 
pasa mai departe catre modulul middle-end-ului ce
se va ocupa de trimiterea acestuia catre Back-End.
