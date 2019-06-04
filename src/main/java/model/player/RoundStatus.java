package model.player;

import java.io.Serializable;

public enum RoundStatus implements Serializable{
    LOBBY,			//il giocatore si trova nella lobby in attesa che si connettano altri giocatori e che inizi la partita
    LOBBY_MASTER,	//il giocatore è nella lobby in attesa, è il PRIMO che si è connesso, quello che diventa master
    MASTER,			// master è il giocatore che sceglie la mappa della partita, è per forza il lobby master
                    // in tutta la partita ci sarà solo un giocatore con questo stato una volta sola in tutto il match!
    SPAWN,          // in questo stato il giocatore sceglie dove spawnare, in seguito esegue turno normale (NB viene eseguito solo al primo turno oppure dopo le morti)
    WAIT_TURN,		// attesa del proprio turno, in questo stato i permessi sono limitatissimi (solo uso della tagback grenade)
    WAIT_FIRST_TURN, //ATTESA DEL PRIMO TURNO!
    FIRST_ACTION,	// il giocatore deve eseguire la prima azione
    SECOND_ACTION, 	// il giocatore deve eseguire la seconda azione
    RELOADING,		// il giocatore ha terminato la sua seconda azione e deve ricaricare le armi (se può); //TODO chiedere a riccky di implementare ill metodo per 					il reload delle armi da esporre sul matchController.
    END_TURN,		// serve per sapere che il giocatore ha finito il turno, centra con quello che diceva ricky sulla gestione dei punti e dei morti !!
    DISCONNECTED,	// stato in cui il giocatore si è disconnesso dal server, significa che ha iniziato la partita ma ha staccato, può riconnettersi con lo stesso 						Nickname ed essere messo in waitTurn
    RESPAWN         //il giocatore deve respawnare, significa che è morto. Hai permessi per scegliere un nuovo punto di respawn (avrà la vita piena).
}