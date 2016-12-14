Notizen zu manuellen Anpassungen
--------------------------------

- Groovy-Skripte angepasst (geänderter Zugriff auf Quell-Eigenschaften)
- Mapping von `AX_Post` bzw. zu `PostalDescriptor` entfernt (es scheint keine Entsprechung zu `AX_Post` im Datenbank-Schema zu geben)
- Mappings aus `postleitzahl` zu `Address` deaktiviert um keine fehlerhaften Referenzen zu (nicht vorhandenen) `PostalDescriptor`s zu erstellen
