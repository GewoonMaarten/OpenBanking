library(h2o)
library(janitor)
library(tidyverse)
library(skimr)
library(stringr)

localH2O = h2o.init()

data <- read_rds("data/master-clean.rds")
skim(data)

data_hex = as.h2o(data, destination_frame = "data_hex")

tokenize <- function(words) {
  tokenized <- h2o.tokenize(words, "\\\\W+")
  
  #convert to lower
  tokenized_lower <- h2o.tolower(tokenized)
  
  #remove short words
  tokenized_lengths <- h2o.nchar(tokenized_lower)
  tokenized_filtered <- tokenized_lower[is.na(tokenized_lengths) || tokenized_lengths >= 2,]
  
  #remove numbers
  tokenized_words <- tokenized_filtered[h2o.grep("[0-9]", tokenized_filtered, invert = TRUE, output.logical = TRUE),]
}


words <- tokenize(data_hex$naam_tegenpartij)
words
w2v_model <- h2o.word2vec(
  words,
  word_model = "Skipgram"
)

h2o.findSynonyms(w2v_model, "ditzo", count = 1)

tekst_vec <- h2o.transform(w2v_model, words, aggregate_method = "AVERAGE")
valid <- !is.na(tekst_vec$C1)
data_hex_new <- h2o.cbind(data_hex[valid,], tekst_vec[valid,])
data_hex_new_split <- h2o.splitFrame(
  data_hex_new, 
  ratios = 0.8,
  seed = 4043
)


skim(as.data.frame(data_hex_new))

glm_model <- h2o.glm(
  y = "is_vaste_last",
  family = "binomial", 
  nfolds = 10, 
  alpha = 0.5,
  training_frame = data_hex_new_split[[1]],
  validation_frame = data_hex_new_split[[2]]
)

h2o.confusionMatrix(glm_model)

gbm_model <- h2o.gbm(
  y = "categorie",
  training_frame = data_hex_new_split[[1]],
  validation_frame = data_hex_new_split[[2]]  
)

head(as.data.frame(data_hex_new_split[[2]])$naam_tegenpartij)

h2o.predict(gbm_model, data_hex_new_split[[2]])

h2o.confusionMatrix(gbm_model)
