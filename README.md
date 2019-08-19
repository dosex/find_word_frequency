## What is this?
This is a small test project to get familiar with writing RESTful APIs with spring boot.
The goal was to provide an endpoint that can find the frequency of a given word and any 
similar words in a provided text. A word is considered "similar" to another word if the 
Levenshtein distance is not more than 1.

## What it contains
The project consists of an application class which is the contains the main method.
There is one controller class with one endpoint in it that does what is described above.
Two POJOs for the JSON communication and a SearchUtils class that has methods that
implement the algorithm for finding a words frequency and similar words. There is also
a very simple test class that contains a bounce of basic test for the endpoint.

Furthermore there is a POC ui for playing around a bit with the endpoint. It is very
hacky but it does it's job and is way more convenient than using `curl | jq` all the
time.

## What can be improved
First of all the overall structure of the project is pretty flat. Basically the whole
logic resides in the controller and the utils classes. Normally I'd like to have some
separate validation class rather than doing these checks in the controller, so I would
be able to reuse them at another place. Put since this is such a small task I wanted
to keep it simple.

The second improvement could be made in performance. The computational expensive
calculation of the Levenshtein distance has to be done for almost every word in the
provided text. I tried to compensate that by taking into account the upper bounds
for the Levenshtein distance as described on [Wikipedia][1]. But I opted to implement
a slightly improved version of the [Wagner–Fischer algorithm][2] which is easy to 
understand but not the most efficient one.

I also think that this kind of task would heavily benefit from parallel computing,
since the text could be split up into smaller chunks and then a number of workers could
run the algorithm in parallel.

__Sources used:__
https://spring.io/guides/gs/rest-service/
https://en.wikipedia.org/wiki/Levenshtein_distance
https://en.wikipedia.org/wiki/Wagner%E2%80%93Fischer_algorithm


[1]: https://en.wikipedia.org/wiki/Levenshtein_distance#Upper_and_lower_bounds           
[2]: https://en.wikipedia.org/wiki/Wagner%E2%80%93Fischer_algorithm#Calculating_distance