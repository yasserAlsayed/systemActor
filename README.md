# Actor System Project 

1- I build this using Java Threading  , I defined one thread for every actor and using on the thread Blocking Queue 
2- I implemented Observer Pattern to notify every actor register on the observer with new message.
3- config.properties -> settings file to define all constants for application it contains only default path for messages files (default.path=/home/yasser/messages/).
4- to start application add default path , then got to ActorSystem class and just call main method.
5- pushMessage method i create dummy file with message and push it to consumers
6- Consumer start by FileScanner with receiving the file , then pass to FileParser to parse the message and divide it to 3 events startEvent,new Line Event ,End Event.
7. With each event it parser notify Aggregator to make specific business for each event , at the end it print count of words on the file .
8. i added Map for each file and Its words count for information.
9. Between Actor pass MessageDTo as object carries data between Actors.

Sorry I didn't work With Akka Frame Work, so i implement my own one 
Using Java 8 ,streams,threading,Obsever pattern
 