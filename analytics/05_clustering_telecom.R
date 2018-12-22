library(h2o)
library(tidyverse)
library(ggplot2)
library(factoextra)

h2o.init()

data <- read_rds("data/telecom/telecom_clean.rds")


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

# Word2vec
words <- tokenize(data_hex$provider)
words
w2v_model <- h2o.word2vec(
  words,
  word_model = "Skipgram"
)
# Add new vectors to rows
tekst_vec <- h2o.transform(w2v_model, words, aggregate_method = "AVERAGE")
valid <- !is.na(tekst_vec$C1)
data_hex_new <- h2o.cbind(data_hex[valid,], tekst_vec[valid,])

#find optimal k
fviz_nbclust(data_df[,c(2:10, 12:111)], kmeans, method = "wss") + 
  geom_vline( xintercept = 3, linetype = 2)


#Split the dataset
data_hex_new_split <- h2o.splitFrame(
  data_hex_new, 
  ratios = 0.8,
  seed = 4043
)

kmeans_model <- h2o.kmeans(
  init = "PlusPlus",
  training_frame = data_hex_new_split[[1]],
  validation_frame = data_hex_new_split[[2]],
  k = 5
)


data_hex_new$predict <- h2o.predict(kmeans_model, data_hex_new)
data_hex_new

data_df <- as.data.frame(data_hex_new)
names(data_df)
ggplot(data_df, aes(x = prijs , y = internet_gb, color = as.factor(predict))) +
  geom_point()


get_similar <- function(row, data_set) {
  data_set <- data_set %>%
    filter(
      prijs <= row$prijs
      ,predict == row$predict
    )
  
  (data_set[,c(1:10, 112)])
}



closest<-function(xv,sv){ xv[which(abs(xv-sv)==min(abs(xv-sv)))] }

closest(data_df[,4], data_df[23,4])
View(data_df[,c(2:10, 12:111)])

get_similar(data_df[23,c(1:10, 112)], data_df)
