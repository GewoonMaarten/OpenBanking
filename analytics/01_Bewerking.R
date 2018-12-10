library(tidyverse)

#dataset Rabobank
dataRabobank<-read_csv("casRABOBANK.csv")
View(dataRabobank)

#dataset ING
dataING<-read_csv2("benjaminING.csv")
View(dataING)

#Dataset Rabobank parse
Rabobankparse<-dataRabobank%>%
  mutate(
    Datum=parse_date(Datum, format="%Y-%m-%d"),
    Bedrag=col_integer()
  )
View(Rabobankparse)

#Dataset Rabobank bewerken
RabobankClean<-Rabobankparse[,c("Datum","Naam tegenpartij","Categorie","is_vaste_last","IBAN/BBAN","Tegenrekening IBAN/BBAN","Bedrag","Omschrijving-1","Code")]
View(RabobankClean)

#Dataset ING parse
INGparse<-dataING%>%
  mutate(
    Bedrag = ifelse(`Bij/af` =="Af",Bedrag * -1,Bedrag),
    Datum=parse_date(Datum, format="%Y%m%d")
  )
View(INGparse)

#Dataset ING bewerken
INGClean<-INGparse[,c("Datum","Naam tegenpartij","Categorie","is_vaste_last","IBAN/BBAN","Tegenrekening IBAN/BBAN","Bedrag","Omschrijving-1","Code")]
View(INGClean)

#Dataset ING exporteren
write_csv(INGClean, "benjamin-ING-Clean.csv")

#Dataset Rabobank exporteren
write_csv(RabobankClean, "cas-Rabobank-Clean.csv")
