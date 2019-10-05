# NetflixProject
Distributed Web Object project


##Section1
Netflix would like to expose an API through which clients can build up client applications to see available movies to watch.
Movies can belong to multiple categories. Netflix provides an endpoint for clients to suggest new movies that Netflix
can look in to adding to their directory. A movie suggested by a client is marked as “suggested” while a Netflix original
is marked as an “original”. When a client system queries for available movies,it specifies the category id as a path 
variable and a type(original or suggested) as a request parameter and the server responds with a list of movies matching 
the request.To be able to do all this Netflix exposes an API that the client uses to register themselves by basically 
providing their name and identification number to which it saves and uses this information to tag requests such as updating,
creating and deleting.Create a simple Netflix API server to represent the above description.(20 marks)

Marking area to earn marks:
●Correct identification of models
●Correct modelling of relationships between the models that you come up with.
●Enforcing that update, delete only apply to one owns’ content
●Implementation of SQL persistence layer
●Good error handling.

##Section2
Model a simple Netflix client to consume your server.(10 marks)

