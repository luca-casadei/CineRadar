# Processo di testing manuale finale dell'intero software
Situazione iniziale, database appena creato con la query CineRadar.sql, quindi con solo l'utente di amministrazione inserito, procedo ad effettuare l'accesso all'interfaccia di amministrazione.
- Accesso negato ✅
- Accesso autorizzato ✅
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
- Aggiunta di una serie ❌ (Vedi descrizione sottostante per l'errore)\
  L'aggiunta di una serie non dovrebbe prevedere l'inserimento delle ridondanze nei campi (NumeroEpisodi e DurataComplessiva), essi vanno calcolati tramite query e update.
- Visualizzazione della schermata di aggiunta di film ✅
- ...

## Testing sospeso per errore importante
