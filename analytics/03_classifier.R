library(h2o)
library(janitor)
library(tidyverse)
library(skimr)
library(stringr)

localH2O = h2o.init()

data <- read_rds("data/transactions_master.rds")
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


words <- tokenize(data_hex$naam)
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

gbm_params1 <- list(learn_rate = c(0.01, 0.1),
                    max_depth = c(3, 5, 9),
                    sample_rate = c(0.8, 1.0),
                    col_sample_rate = c(0.2, 0.5, 1.0))

gbm_grid1 <- h2o.grid("gbm", y = "categorie",
                      grid_id = "gbm_grid1",
                      training_frame = data_hex_new_split[[1]],
                      validation_frame = data_hex_new_split[[2]],
                      ntrees = 100,
                      seed = 1,
                      hyper_params = gbm_params1)

# Get the grid results, sorted by validation AUC
gbm_gridperf1 <- h2o.getGrid(grid_id = "gbm_grid1",
                             sort_by = "accuracy",
                             decreasing = TRUE)
print(gbm_gridperf1)

gbm_model <- h2o.gbm(
  y = "categorie",
  training_frame = data_hex_new_split[[1]],
  validation_frame = data_hex_new_split[[2]]  
)

head(as.data.frame(data_hex_new_split[[2]])$naam)

h2o.predict(gbm_model, data_hex_new_split[[2]])

h2o.confusionMatrix(gbm_model)
