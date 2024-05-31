# Processo di testing manuale finale dell'intero software
Situazione iniziale, database appena creato con la query CineRadar.sql, quindi con solo l'utente di amministrazione inserito, procedo ad effettuare l'accesso all'interfaccia di amministrazione.
- Accesso negato ✅
- Accesso autorizzato ✅
- Dimensionamento della finestra di amministrazione ❌ \
  Troppo grande, va fuori dallo schermo, ridurre la dimensione di partenza della finestra.
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
- Aggiunta di una stagione ❌ (Vedi errore sottostante) \
  Eccezione in console se non si riempiono tutti i campi, implementare un dialog di errore.
- Visualizzazione stagione inserita ❌ (Vedi errore sottostante) \
  Eccezione in console se si visualizza la stagione appena inserita (probabile errore a catena).
- Visualizzazione della schermata di aggiunta di film ✅
- ...

## Testing sospeso per errore importante
