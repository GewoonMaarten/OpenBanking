library(h2o)
library(janitor)
library(tidyverse)
library(skimr)
library(stringr)

localH2O = h2o.init()

gbm_model <- h2o.gbm(
  model_id = "categorie_classifier_1",
  y = "categorie",
  training_frame = data_hex_new_split[[1]],
  validation_frame = data_hex_new_split[[2]]  
)

head(as.data.frame(data_hex_new_split[[2]])$naam)

h2o.predict(gbm_model, data_hex_new_split[[2]])

h2o.confusionMatrix(gbm_model)

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


best_gbm2 <- h2o.getModel(gbm_gridperf1@model_ids[[1]])


best_gbm2
