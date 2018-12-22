library(tidyverse)
library(readxl)
library(janitor)
library(skimr)

data <- read_xlsx("data/telecom/telecom_data_benjamin.xlsx")
View(data)

# ----------------------------------------------------------
# Cleanup
# ----------------------------------------------------------

data_new <- data %>% clean_names(case = "snake")

data_new <- data_new %>%
  mutate(
    bel_minuten = bel_minuten %>%
      gsub("[^\\.\\d\\s]+", -1, ., perl = TRUE) %>%
      parse_number(),
    sms = sms %>%
      gsub("[^\\.\\d\\s]+", -1, ., perl = TRUE) %>%
      parse_number(),
    internet_gb = internet_gb %>%
      gsub("[^\\.\\d\\s]+", -1, ., perl = TRUE) %>%
      parse_number() %>%
      ifelse(. >= 500, . / 1000, .)
  ) %>%
  drop_na()

View(data_new)

write_rds(data_new, "data/telecom/telecom_clean.rds")

