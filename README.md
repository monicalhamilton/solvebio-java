## SolveBio Java Client -- Test

### Overview
The goal of this project is to develop a simple Java client for the SolveBio REST API. To install, clone the repo locally and import it into as a Maven project using your favorite Java IDE.

Documentation for the API can be found here: https://www.solvebio.com/docs/api/. You may find the Data Layout section particularly useful.

### Requirements
As is, this project contains a few issues (i.e. bugs), a few classes are incomplete and the test coverage is lacking. The existing tests `com.solvebio.SolveBioClientTest` will fail.

Candidates must:
1. ensure that existing tests pass
2. complete all **TODO**'s and add relevant tests
3. complete the implementation of all methods in `com.solvebio.model.DepositoryVersion` and `com.solvebio.model.Dataset` and  add relevant tests


### Bonus
1. Complete all **BONUS**'es.
2. Increase portability by refactoring com.solvebio.SolveBioClient use configurable environment variables instead of static class members.
