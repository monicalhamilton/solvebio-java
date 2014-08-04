This are my improvements on a Java client for the SolveBio REST API. See below for original requirements.  

I tackled the problem as follows:
1. Read the solvebio API documentation and played around with the solvebio python API to understand the data model.
2. Began retrieving data using the Java client, fixing immediate bugs as necessary (e.g. camelCaseToUnderscoreCase).
3. Fleshed out the model classes with missing fields. 
4. Implemented deserializations with gson annotations and custom deserializers.
5. Implemented skeleton methods and added test cases.

Some things that could be improved in the server-side API:
1. Standardize the datetime fields returned (some fields included milliseconds; others did not).
2. Standardize the error responses (most were JSON, but some were HTML).

How to use:
Add your API as an environment variable or pass to the JVM via -DapiKey.  

The original spec was as follows:
## SolveBio Java Client -- Test

### Overview
The goal of this project is to develop a simple Java client for the SolveBio REST API. To install, clone the repo locally and import it as a Maven project using your favorite Java IDE.

Documentation for the API can be found here: https://www.solvebio.com/docs/api/. You may find the Data Layout section particularly useful.

### Requirements
As is, this project contains a few issues (i.e. bugs), a few classes are incomplete and the test coverage is lacking. The existing tests `com.solvebio.SolveBioClientTest` will fail.

Candidates must:

1. ensure that existing tests pass
2. complete all **TODO**'s and add relevant tests
3. complete the implementation of all methods in `com.solvebio.model.DepositoryVersion` and `com.solvebio.model.Dataset` and  add relevant tests


### Bonus

1. complete all **BONUS**'es.
2. increase portability by refactoring com.solvebio.SolveBioClient to use configurable environment variables instead of static class members.
3. improve general code structure
