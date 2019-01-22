require(RSelenium) 
library(rvest) 
library(stringr) 
library(tidyverse) 

remDr <- remoteDriver(remoteServerAddr = "167.99.217.70" 
                      , port = 4444 
                      , browserName = "chrome") 
remDr$open() 

scrape_css_attr <- function(css, group, html_page, attr) { 
  txt<-html_page %>% 
    html_nodes(group) %>% 
    lapply(.%>% html_nodes(css) %>% html_attr(attr) %>% ifelse(identical(.,character(0)),NA,.)) %>%
    unlist() 
  return(txt) 
} 

#function to scrape alle elements also missing elements 

scrape_css<-function(css,group,html_page){ 
  txt<-html_page %>% 
    html_nodes(group) %>% 
    lapply(.%>% html_nodes(css) %>% html_text() %>% ifelse(identical(.,character(0)),NA,.)) %>% 
    unlist() 
  return(txt) 
} 

#Get all elements from 1 page  

get_elements_from_url<-function(remDr, url){ 
  remDr$navigate(url) 

  #scroll down 5 times, waiting for the page to load at each time 

  for(i in 1:1){       
    remDr$executeScript(paste("scroll(0,",i*10000,");")) 
    Sys.sleep(1) 
    webElem <- remDr$findElement(using = 'css selector',".js-btn-show-more-results") 
    webElem$clickElement() 
  } 

  #get the page html 

  page_source<-remDr$getPageSource() 
  html_page <- html(page_source[[1]]) 

  #get all locations on this page, ".media" is the group (element in webscraper.io) 

  title <-scrape_css(".palm-zeta>.js-package-link",".desk-mh- .default-mb", html_page) 
  price <-scrape_css(".layout__item:nth-child(4) > div.c-spec",".desk-mh- .default-mb", html_page) 
  aansluitkosten <-scrape_css(".layout__item:nth-child(5) > div.c-spec",".desk-mh- .default-mb", html_page) 
  berekende_kosten <- scrape_css(".c-spec--amount",".desk-mh- .default-mb", html_page) 
  contractduur <- scrape_css(".palm-mb--:nth-child(1) .zeta.u-weight--bold",".desk-mh- .default-mb", html_page) 
  internetsnelheid <- scrape_css(".u-weight--bold.u-vertical-align--top",".desk-mh- .default-mb", html_page)  
  provider <- scrape_css_attr(".c-comparison-list__provider-logo",".desk-mh- .default-mb", html_page, "alt") 
  
  # Combine into a tibble and return 

  return(tibble( 
    title=title, 
    price=price, 
    aansluitkosten=aansluitkosten, 
    berekende_kosten=berekende_kosten, 
    contractduur=contractduur, 
    internetsnelheid=internetsnelheid, 
    provider=provider 
  )) 
} 

#main function to scrape all pages 
scrape_write_table<-function(remDr, url){ 

  # Generate the target URLs for all pages, in this case 2 pages 
  list_of_pages<-str_c(url) 
  # Apply the extraction and bind the individual results back into one table.  

  list_of_pages %>%  
    # Apply to all URLs 
    map(~ get_elements_from_url(remDr = remDr, url = .)) %>%   
    # Combine the tibbles into one tibble 
    bind_rows() 
} 

#call the function and write the returned tibble to friends 
belsimpel<-scrape_write_table(remDr, "https://www.bellen.com/vergelijken/sim-only") 

#show the data 
View(belsimpel) 

#save data 
write_rds(belsimpel,"belsimpel.rds")  
write_csv(belsimpel,"belsimpel.csv") 
