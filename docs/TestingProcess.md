# Processo di testing manuale finale dell'intero software
Situazione iniziale, database appena creato con la query CineRadar.sql, quindi con solo l'utente di amministrazione inserito, procedo ad effettuare l'accesso all'interfaccia di amministrazione.
- Accesso negato ✅
- Accesso autorizzato ✅
- Dimensionamento della finestra di amministrazione ✅
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
- Aggiunta membro cast ✅
- Aggiunta di un cast ✅
- Eliminazione di un membro del cast ✅
- View aggiunta di un cast ✅
- Creazione di un cast con i relativi membri ✅
- Aggiunta di una serie ✅
- Aggiunta di un cinema ✅
- Eliminazione di un cinema ✅
- Aggiunta di un registratore ✅
- Eliminazione di un registratore ✅

Creato un registratore, effettuo l'accesso come tale:
- Accesso come registratore ✅
- Tesseramento di un utente non esistente ✅
- Tesseramento di un utente esistente ✅

Terminate le funzionalità del registratore, riaccedo come amministratore per assegnare una promo
- Visualizzazione tessere ✅
- Assegnazione promo (Form) ✅
- Assegnazione promo non esistente ✅

## Testing sospeso per errore importante
