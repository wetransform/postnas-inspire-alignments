Notizen zu manuellen Anpassungen
--------------------------------

- Custom function *AX_Lagebezeichnung zu GeographicalName* angepasst um auf PostNAS-Schema operieren zu können
- Verwendung *AX_Lagebezeichnung zu GeographicalName* angepasst (für Flugverkehr zu `AerodromeArea`)
- Mapping für `uom` der Breite von `ElementWidth` entfernt (da keine solche Information in der Quelle vorhanden). Stattdessen wird von Metern als Einheit ausgegangen und dies wurde als Assign ergänzt
- Anpassung von Groovy Typ-Relationen mit korrigierten Typ- und Eigenschafts-Namen
- Explizite Behandlung von Codes als Strings in Groovy Typ-Relationen (um Logik kompatibel zu halten)
- Bedingungen mit `breiteDesObjekts` angepasst (zu `breitedesobjekts`)
- Mehrfach vorhandene Relationen zu Attributen von `AerodromeArea` gelöscht.

