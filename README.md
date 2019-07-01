# ing-sw-2019-02
**Gruppo 02**

Prova Finale di Ingegneria del Software, Politecnico di Milano, 2019

La prova consiste nell'implementazione in Java del gioco da tavolo Adrenalina di Cranio Creations.

**Funzionalità implementate:**
- Regole complete
- Connessione RMI (compatibilità con socket - ready to implement)
- GUI
- FA: Gestione partite multiple (multimatch)

Guida all' avvio dei Jar:

**AdrenalineClient**: 

Distribuito in 3 versioni differenti a seconda del SO (causa dipendenza JavaFX)

All'interno di ogni cartella di distribuzione si trovano:

- L'eseguibile **AdrenalineClient.jar**
- La cartella di **dipendenza JavaFX**: "javafx-sdk-12.0.1"
- Uno script **AdrenalineClientLauncher.sh** o **AdrenalineClientLauncher.bat** a seconda del SO in uso che permette di lanciare l'applicativo con il semplice comando 


    Linux/Mac command: ./AdrenalineClientLauncher.sh
    Windows   command: AdrenalineClientLauncher.bat
    
**AdrenalineServer**:

Nella cartella di distribuzione si trova:

- L'eseguibile **AdrenalineServer.jar**
- Il file di properties: **adrenaline.properties**

Per eseguire il jar è necessario lanciare da terminale uno dei due seguenti comandi:


    java -jar AdrenalineServer.jar portNumber
    java -jar AdrenalineServer.jar
    
Nel secondo caso il valore della porta su cui lanciare il server sarà caricato dal file di properties
    
