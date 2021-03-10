     
### Order book:        
3 modules 
* order book core - hold all the core logic of the order book
* model - the api models
* order book server - spring boot application 

2.1  for the first question u can see the order book in class OrderBookResult 

2.2 the consequences  of it will be wrong state of the bid and ask because they
    are sequence base calculation by the log
    
2.3 
   * each request will be sent to a kafka topic with partition key of symbol and exchange
     ( kafka data is prsisit and its impotent to keeo the order per symbol and exchange)
   * replace the in memory order book to disturbed in memory db ( like redis) , so i can scale
         the processing of the message and to save the concisest of the data ( redis is thread safe)
  * snapshot will be taken from redis when requested
    
2.4 don't see any different , send data directly to kafka and not via the rest
          


