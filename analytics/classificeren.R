library(janitor)
library(kernlab)
library(caret)
library(e1071)
library(skimr)
library(tidyverse)

data <- read.csv("data/rabo_new.csv", stringsAsFactors = FALSE) %>%
  clean_names(case = "snake")

data <- data %>%
  mutate(
    bedrag = parse_number(bedrag),
    datum_num = parse_number(datum),
    code = as_factor(code),
    is_vaste_last = as.factor(is_vaste_last)
  )

training_index <- createDataPartition(y=data$is_vaste_last, p=0.75, list=FALSE)
training_set <- data[training_index,]
test_set <- data[-training_index,]

set.seed(1043)

model <- train(is_vaste_last ~bedrag+ code + datum_num, data=training_set, method="lm", metric="RMSE")
model

test_set$predictions <- predict(model, newdata=test_set)
confusionMatrix(test_set$predictions, test_set$is_vaste_last)

