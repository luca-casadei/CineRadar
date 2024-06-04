# Processo di testing manuale finale dell'intero software
Situazione iniziale, database appena creato con la query CineRadar.sql, quindi con solo l'utente di amministrazione inserito, procedo ad effettuare l'accesso all'interfaccia di amministrazione.
- Accesso negato ✅
- Accesso autorizzato ✅
- Dimensionamento della finestra di amministrazione ❌ (Trovata origine) \
  Trovata la card che crea il problema, una possibile correzione è usare una scrollbar per ridurre la dimensione verticale, e complessivamente ridurre l'altezza delle righe, se non funziona, provare layout diverso.
- Pagina di profilo amministrazione ✅
- Scorrimento di tutte le sezioni senza errori in console ✅
- Disconnessione e chiusura App ✅

Ora tento la registrazione di un nuovo utente maggiorenne con username "luca" e password analoga allo username:
- Registrazione senza errori ✅
- Accesso diretto dopo registrazione ✅
- Scorrimento di tutte le sezioni vuote ✅
- Test sui bottoni inferiori e applicazione filtri vuoti senza errori ✅
- Richiesta di aggiunta di una serie ✅

Ora rieffettuo l'accesso come amministratore, per soddisfare la richiesta che ho appena creato:
- Visualizzazione della richiesta neocreata ✅
- Completamento della richiesta (numero valido) ✅
- Completamento della richiesta (numero invalido: mostra errore) ✅
- Aggiunta di una serie ✅
- Aggiunta di una stagione ✅
- Visualizzazione stagione inserita ✅
- Visualizzazione della schermata di aggiunta di film ✅
- Eliminazione di entrambe le serie aggiunte. ✅
- Aggiunta di un episodio ad una serie non esistente ✅
- Eliminazione Cast inesistente ✅
- Eliminazione membro cast inesistente ✅
- Aggiunta membro inesistente a cast inesistente ✅
- Aggiunta membro cast (errore grafico / ux) ❌ \
  - Usare dei campi di testo per dire se sono attori o registi con 1 o 0 è la cosa più terrificante che abbia mai visto, usare delle checkbox possibilmente.
  - Usare il datepicker che abbiamo usato nelle altre classi per inserire la data.
- Aggiunta di un cast (non capisco l'errore, non viene visualizzato nulla a schermo ) 
Dati inseriti per provocarlo (fa lo stesso anche con data debutto > data di nascita ):
  ![image](https://github.com/luca-casadei/CineRadar/assets/31739393/2e1235b0-4031-40a9-99ef-71795707865d)

## Testing sospeso per errore importante
