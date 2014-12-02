#### Branches overview:
- master - ready to checkout and start implementing
- example-solution-step-by-step - a kind of guide how to do this split into many small commits to show the order
- presentation - commits made during 90 minutes live coding presentation I gave at Gamesys

### Overview

The main task of this exercise is to implement a simple news feed system using proper TDD techniques.

Users, identified by a number, have a news feed visible in their mobile application. The news feed is user specific, every news feed may contain different messages. Messages are just text. The users see most recent messages on top of their news feed (similarly to Facebook's wall). The task is to implemented a server back-end, that mobile application will be calling to retrieve users' news feed. Also, another internal application is used by marketing department to add messages to the news feed. Marketing department has also requested to be able to add a message to the news feed at any time in the past or future.

For news feed persistance, third party's database will be used - all we know about it is ```thirdparty.DbReader``` interface with it's javadoc and we know that we can instantiate it by ```new thirdparty.PersistentDbReader()```. You should not be looking at this implementation - in real system you would probably not have its source.

For the simplicity of the task, we won't be creating RESTful endpoints but will focus on pure Java. In real system, we would also have endpoints calling the created API and serialising the responses to xml or json, all deployed in a web container. Also in this task parameters can be POJOs rather then single URL. So the method for adding message to the news feed can have a signature ```void post(int userId, long timestamp, String message)``` or ```void post(UserId userId, Message message)``` - it is entirely up to you.

### Task 1 - retrieve news feed

The simplest fully functional system is one that allows to retrieve the entire news feed (sorted by timestamp) for the given user and allows to post messages to the given user's news feed. Implement these two methods.

### Task 2 - add max size

Over time, users' news feed becomes much larger than displayed by the mobile application. Statistics have also shown that users almost never scroll down to the oldest messages. Create another method that allows to retrieve only last given number of recent messages for the given user.

### Task 3 - add pagination

When users scroll down the news feed, at some point it is necessary to request more messages from the news feed, however existing methods will return also those messages that are already cached by the mobile application, hence wasting bandwith and memory. We should add another method that will return a page of messages, given the page number and page size. For example, if entire news feed is [A, B, C, D, E, F, G], with G being the most recent message, the query of page 1 and page size 2 should return page [D, E]. Page numbers are 0 based so page 0 would be [F, G].

### TDD rules and hints

- **test behaviour, not implementation**
- don't write production code unless there is a failing test
  - refactoring is fine, but refrain from it if there are no tests (e.g. legacy code) and don't do this if tests are failing
- write minimal code to satisfy the test
- write minimal amount of tests which are necessary to fail
  - if you write a new test and it is passing, it means that it may not be needed (however you may still want to test some critical components, e.g. those that affect house edge)
  - if two tests are always failing together or always passing together, one of them is probably redundant
- always see the test fail and for the right reason
  - when writing tests for existing code, never change the test to see it fail, break implementation instead
  - when writing tests for existing code, consider removing all production code and keep adding it back piece by piece, only what is needed for the test to pass
- give a test a meaningful name, describe what it does (e.g. ```retrievesAllPostedMessages``` rather than ```testGet```)
- test should test only one thing - one logical assertion
  - it's worth to have only one assertion statement as it usually gives better diagnostics
- consider writing your assertion first and working backwards
- once feature is implemented and all tests are green, refactor production code
  - remove all smells (e.g. duplications)
  - maximise clarity (e.g. extract code to methods with meaningful names)
- maintain your tests - it's not a "second class citizen", it's as important as production code
- start writing tests from the simplest cases and keep adding more complex cases (triangulation)
- isolate the tests
  - test should not be using production code as expectation, the expected values should be hardcoded within the test
  - test should not be relying on another test
  - you should be able to run your tests (or any subset of them) in any order
  - you should be able to run your unit tests in parallel so avoid mutable static fields
  - tests should be cleaning after themselves, otherwise you may find other tests failing and it will be very difficult to find the reason
