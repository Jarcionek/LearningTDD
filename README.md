### Overview

//TODO some overview of the task, explain thirdparty db, define interface for Application

For the simplicity of the task, we won't be creating RESTful endpoints but will focus on pure Java. In real system, we would also have endpoints calling the created API and serialising the responses to xml or json, all deployed in a web container. Also parameters can be POJOs rather then single URL string.

So the "post" method can have a signature ```void post(int userId, long timestamp, String message)``` or ```void post(UserId userId, Message message)``` - it is entirely up to you.

### Task 1 - retrieve news feed

//TODO just retrieve all news feed in sorted by timestamp order

### Task 2 - add max size

//TODO add maximum number of items to retrieve (most recent items)

### Task 3 - add pagination

//TODO add page size and page number
