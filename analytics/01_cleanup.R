library(tidyverse)
library(janitor)

data_RABO_1 = read_csv("data/bank_account_transactions_RABO_1.csv")
data_RABO_2 = read_csv("data/bank_account_transactions_RABO_2.csv")
data_RABO_3 = read_csv("data/bank_account_transactions_RABO_3.csv")
data_INGB_1 = read_csv("data/bank_account_transactions_INGB_1.csv")
names(clean_names(data_INGB_1, case = "snake"))

parse_RABO <- function(dataset) {

  dataset <- clean_names(dataset, case = "snake")
  
  dataset %>%
    mutate(
      bedrag = parse_number(bedrag)
      ,datum = as.Date(datum, tryFormats = c("%m/%d/%Y", "%d-%m-%y", "%d/%m/%Y"))
      ,dag = parse_number(lubridate::day(datum))
      ,maand = parse_number(lubridate::month(datum))
      ,is_vaste_last = parse_factor(is_vaste_last, levels = c(0,1))
      ,categorie = categorie %>%
        trimws() %>%
        tolower() %>%
        replace(categorie %in% c("uitgaven", "overige", "ovee"), "overige uitgaven") %>%
        as_factor()
      ,naam = parse_character(naam_tegenpartij)
      ,iban = iban_bban
    ) %>%
    select(
      bedrag
      ,naam
      ,dag
      ,maand
      ,is_vaste_last
      ,categorie
      ,iban
    )
}

parse_INGB <- function(dataset) {
  
  dataset <- clean_names(dataset, case = "snake")

  dataset %>%
    mutate(
      bedrag = parse_number(ifelse(kolom6 =="Af",kolom7 * -1, kolom7))
      ,datum = parse_date(kolom1,"%Y%m%d")
      ,dag = parse_number(lubridate::day(datum))
      ,maand = parse_number(lubridate::month(datum))
      ,is_vaste_last = parse_factor(is_vaste_last, levels = c(0,1))
      ,categorie = categorie %>%
        trimws() %>%
        tolower() %>%
        replace(categorie %in% c("uitgaven", "overige", "ovee"), "overige uitgaven") %>%
        replace(categorie %in% c("medisch"), "medische kosten") %>%
        replace(categorie == "uitgaven", "overige uitgaven") %>%
        as_factor()
      ,naam = parse_character(kolom2)
      ,iban = kolom3
    ) %>%
    select(
      bedrag
      ,naam
      ,dag
      ,maand
      ,is_vaste_last
      ,categorie
    )
    
}


data_RABO_1_clean <- parse_RABO(data_RABO_1)
data_RABO_2_clean <- parse_RABO(data_RABO_2)
data_RABO_3_clean <- parse_RABO(data_RABO_3)
data_INGB_1_clean <- parse_INGB(data_INGB_1)


table(data_RABO_1_clean$categorie)

write_csv(data_RABO_1_clean, "data/bank_account_transactions_RABO_1_clean.csv")
write_csv(data_RABO_2_clean, "data/bank_account_transactions_RABO_2_clean.csv")
write_csv(data_RABO_3_clean, "data/bank_account_transactions_RABO_3_clean.csv")
write_csv(data_INGB_1_clean, "data/bank_account_transactions_INGB_1_clean.csv")

write_rds(data_RABO_1_clean, "data/bank_account_transactions_RABO_1_clean.rds")
write_rds(data_RABO_2_clean, "data/bank_account_transactions_RABO_2_clean.rds")
write_rds(data_RABO_3_clean, "data/bank_account_transactions_RABO_3_clean.rds")
write_rds(data_INGB_1_clean, "data/bank_account_transactions_INGB_1_clean.rds")


master <- rbind(
    data_RABO_1_clean
    ,data_RABO_2_clean
    ,data_RABO_3_clean
    ,data_INGB_1_clean
  ) %>%
  drop_na()



write_csv(master, "data/transactions_master.csv")
write_rds(master, "data/transactions_master.rds")



