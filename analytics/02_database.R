library(RMariaDB)
library(tidyverse)
library(DBI)

con <- DBI::dbConnect(
  drv = RMariaDB::MariaDB(),
  host = "localhost",
  username = "root",
  password = rstudioapi::askForPassword("Database password"),
  port = 3306,
  dbname = "openbanking"
)

dataset <- read_rds("data/transactions_master.rds")

dbWriteTable(con, "transactions", dataset, row.names = TRUE)

dbListTables(con)
