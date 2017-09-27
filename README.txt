-Launch Eclipse neon
-import the project textvertx 
-Run the project
-Test using the command :   curl -D- http://localhost:8080/analyze -d "{\"text\":\"word\"}"



Exemple of testing:

-The first time will be print a message :
That the first request the two field not exist


-Next Time will show the closest value and lexical on the list and all the data of the list.

Exemple : list :"{word=60, words=79}"
          parameter: "wor" ,
          the value of wor is 56 and the most closest value is 60.
          the most closest can be word or words so that will return one of them.

if the next parameter is "y" 
The field value: 56
The field lexical we will found no word so we will search the closest length word and the field lexical will contain "wor" on our exemple.
