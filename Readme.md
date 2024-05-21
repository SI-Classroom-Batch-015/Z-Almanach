1. Gradle eingerichtet
2. Packages nach MVVM erstellt
2. Datenklassen erstellt 
3. Fragmente Definiert
4. Überall ViewBinding eingerichtet
4. nav_graph erstellt und eingerichtet
5. bottom_nav_bar erstellt und eingerichtet
6. Vector Assest und paar Dummie Fotos geladen, App Icon gesetzt
7. DbzFragment und DbzDetailFragment Layout provisorisch gestaltet, damit die Attribute feststehen
8. list_item_dbz erstellt , damit wird die Recyclerview befüllt
9. DbzAdapter Konfigurieren :-)
10. Room Database eingerichtet: Dies ermöglicht die effiziente Speicherung und Abfrage von Daten
11. CharacterDao definiert: Ein Data Access Object (DAO) für die Character-Tabelle wurde erstellt, zwecks Datenbankoperationen 
12. Repository implementiert Datenzugriffsschicht: Im Repository wurden Methoden implementiert, um Daten aus der lokalen Datenbank abzurufen und bei Bedarf zu aktualisieren. Klare Trennung zwischen Datenzugriff und Geschäftslogik
13. ViewModel erstellt und mit dem Repository verbunden. Ermöglicht eine saubere Trennung von UI-Logik und Datenverarbeitung
14. DbzFragment mit ViewModel verbunden, um Daten zwischen der UI und der Datenzugriffsschicht auszutauschen
15. RecyclerView im DbzFragment konfiguriert, um Charakterdaten aus der Datenbank mithilfe des DbzAdapters anzuzeigen?
16. Im DbzDetailFragment mithilfe von SafeArgs die Daten geladen um detaillierte Informationen zu einem ausgewählten Charakter anzuzeigen
17. Endlich mal die Internetberechtigungen hinzugefügt
18. Datenbank wird angezeigt, aber LEER, und alles andere, immerhin stürzt sie nicht mehr ab
19. fragment_dbz.xml ENNDLICH den app:layoutmanager gestzt
20. Characters Datenklasse ENDLICH die Postmananwort richtig interpretiert und die @Json ano für nam=items gesetzt
21. DbzFragment.kt den LinearLayoutManager erstellen und der RecyclerView zuweisen
22. CHECK Daten werden von der API richtig geladen und jeweils in den Fragmenten angezeigt
