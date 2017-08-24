Allgemeine Anmerkungen
----------------------

Es scheint keine Entsprechung für AX_Post im Datenbank-Schema zu geben. Dementsprechend werden im Addresses-Mapping keine PostalDescriptors erzeugt. Fehlt dieser Typ ggf. noch im Datenbank-Schema oder wird diese Information hier nicht geführt? *(Berichtet am 14.12.2016)*

Update: Verwendung von `AX_Post` im Alignment ist nicht sinnvoll, das Original-Alignment muss entsprechend angepasst werden. Siehe https://services.interactive-instruments.de/xsaaasuite-gitlab/adv-inspire/inspire-alignements/issues/194


Notizen zu manuellen Anpassungen
--------------------------------

- Groovy-Skripte angepasst (geänderter Zugriff auf Quell-Eigenschaften)
- Mapping von `AX_Post` bzw. zu `PostalDescriptor` entfernt (es scheint keine Entsprechung zu `AX_Post` im Datenbank-Schema zu geben)
- Mappings aus `postleitzahl` zu `Address` deaktiviert um keine fehlerhaften Referenzen zu (nicht vorhandenen) `PostalDescriptor`s zu erstellen
- `FormattedString` mit Quelle `gml_id` auf geänderten Namen der Quelle angepasst
