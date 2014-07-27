### Overview

The main task of this exercise is to implement a simple news feed system using proper TDD techniques.

Users, identified by a number, have a news feed visible in their mobile application. The news feed is user specific, every news feed may contain different messages. Messages are just text. The users see most recent messages on top of their news feed (similarly to Facebook's wall). The task is to implemented a server back-end, that mobile application will be calling to retrieve users' news feed. Also, another internal application is used by marketing department to add messages to the news feed. Marketing department has also requested to be able to add a message to the news feed at any time in the past or future.

For news feed persistance, third party's database will be used - all we know about it is ```thirdparty.DbReader``` interface with it's javadoc and we know that we can initialise it by ```new thirdparty.InMemoryDbReader()```. You should not be looking at this implementation - in real system you would probably not have its source.

//TODO maybe we should really persist the messages, e.g. to local file system? This would give interesting results when running tests multiple times and would require doing some clean up in "before" methods. As a disadvantage, this would require to use "delete" method (more code to write) or do some hacks like random user id.

For the simplicity of the task, we won't be creating RESTful endpoints but will focus on pure Java. In real system, we would also have endpoints calling the created API and serialising the responses to xml or json, all deployed in a web container. Also in this task parameters can be POJOs rather then single URL. So the method for adding message to the news feed can have a signature ```void post(int userId, long timestamp, String message)``` or ```void post(UserId userId, Message message)``` - it is entirely up to you.

### Task 1 - retrieve news feed

//TODO just retrieve all news feed in sorted by timestamp order

### Task 2 - add max size

//TODO add maximum number of items to retrieve (most recent items)

### Task 3 - add pagination

//TODO add page size and page number
