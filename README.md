### Overview

The main task of this exercise is to implement a simple news feed system using proper TDD techniques.

Users, identified by a number, have a news feed visible in their mobile application. The news feed is user specific, every news feed may contain different messages. Messages are just text. The users see most recent messages on top of their news feed (similarly to Facebook's wall). The task is to implemented a server back-end, that mobile application will be calling to retrieve users' news feed. Also, another internal application is used by marketing department to add messages to the news feed. Marketing department has also requested to be able to add a message to the news feed at any time in the past or future.

For news feed persistance, third party's database will be used - all we know about it is ```thirdparty.DbReader``` interface with it's javadoc and we know that we can initialise it by ```new thirdparty.InMemoryDbReader()```. You should not be looking at this implementation - in real system you would probably not have its source.

//TODO maybe we should really persist the messages, e.g. to local file system? This would give interesting results when running tests multiple times and would require doing some clean up in "before" methods. As a disadvantage, this would require to use "delete" method (more code to write) or do some hacks like random user id.

For the simplicity of the task, we won't be creating RESTful endpoints but will focus on pure Java. In real system, we would also have endpoints calling the created API and serialising the responses to xml or json, all deployed in a web container. Also in this task parameters can be POJOs rather then single URL. So the method for adding message to the news feed can have a signature ```void post(int userId, long timestamp, String message)``` or ```void post(UserId userId, Message message)``` - it is entirely up to you.

### Task 1 - retrieve news feed

The simplest fully functional system is a one that allows to retrieve the entire news feed (sorted by timestamp) for the given user and allows to post messages to the given user's news feed. Implement these two methods.

### Task 2 - add max size

Over time, users' news feed becomes much larger than displayed by the mobile application. Statistics have also shown that users almost never scroll down to the oldest messages. Create another method that allows to retrieve only last given number of recent messages for the given user.

### Task 3 - add pagination

When users scroll down the news feed, at some point it is necessary to request more messages from the news feed, however to existing methods will return also those messages that are already cached by the mobile application, hence wasting bandwith and memory. We should add another method that will return a page of messages, given the page number and page size. For example, if entire news feed is [A, B, C, D, E, F, G], with G being the most recent message, the query of page 1 and page size 2 should return page [D, E].
