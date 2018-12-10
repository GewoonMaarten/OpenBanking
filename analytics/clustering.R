library(tidyverse)
library(psych)
library(factoextra)
library(skimr)
library(janitor)
library(dummies)
library(ggplot2)

data = read_csv("data/rabo.csv") %>%
  clean_names(case = "snake")

data_raw <- data

normalize<-function(x){
  (x -min(x))/(max(x)-min(x))
}

data <- data %>%
  mutate(
    bedrag = parse_number(bedrag, locale = locale(decimal_mark = ",")),
    saldo_na_trn = parse_number(saldo_na_trn, locale = locale(decimal_mark = ",")),
    code = as.factor(code),
    datum = as.numeric(datum)
  ) %>%
  mutate_if(is.numeric, normalize)

data <- cbind(data, dummy(data$code, sep = "_"))


pairs.panels(select(data, bedrag, saldo_na_trn, code, datum, rentedatum, oorspr_bedrag, starts_with("data")))


cluster_set <- select(data, bedrag, datum, starts_with("data")) %>% 
  drop_na()

set.seed(123)

fviz_nbclust(cluster_set, kmeans, method = "wss") + 
  geom_vline( xintercept = 3, linetype = 2)


kcluster = kmeans(cluster_set, 6, nstart = 25 )

cluster_set$cluster <- as.factor(kcluster$cluster)
data_raw$cluster <- as.factor(kcluster$cluster)

ggplot(cluster_set, aes(x = bedrag, y = datum)) +
  geom_point(color = cluster_set$cluster) +
  theme_minimal()

write_csv(data_raw, "data/rabo2.csv")
