     
### Order book:        
3 modules 
* order book core - hold all the core logic of the order book
* model - the api models
* order book server - spring boot application 

2.1  for the first question u can see the order book in class OrderBookResult 

2.2 the consequences  of it will be wrong state of the bid and ask because they
    are sequence base calculation by the log
    
2.3 
   * each request will be sent to a kafka topic with partition key of symbol and exchange for every request 
     ( kafka data is persist, and it's important to keep the order per symbol and exchange)
   * calculate the order book in memory , and update in memory db ( redis for example )  for each transaction,
     ( can be done by one to many for each message , in case of error on message presist)
     in case of service restart , load the state of the order book from db , and continue processing from topic
  * snapshot will be service from db by multi instance of the service
    
2.4 don't see any different , send data directly to kafka and not via the rest
          


