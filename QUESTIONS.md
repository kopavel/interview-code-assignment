# Questions

Here we have 3 questions related to the code base for you to answer. It is not about right or wrong, but more about what's the reasoning behind your decisions.

1. In this code base, we have some different implementation strategies when it comes to database access layer and manipulation. If you would maintain this code base, would you refactor any of those? Why?

**Answer:**
```txt
There are two different approaches used:
Repository pattern for Product and Warehouse, and Entity pattern for Store.

I would generally prefer the Repository pattern, because it centralizes database access and allows more complex queries to be defined in one place, making the code easier to maintain and test.

However, if the domain logic is very simple and mostly limited to basic CRUD operations, using the Entity pattern alone is sufficient and avoids unnecessary abstraction
```
----
2. When it comes to API spec and endpoints handlers, we have an Open API yaml file for the `Warehouse` API from which we generate code, but for the other endpoints - `Product` and `Store` - we just coded directly everything. What would be your thoughts about what are the pros and cons of each approach and what would be your choice?

**Answer:**
```txt
Those approaches are usually called schema-first and code-first.

Schema-first:
Good for external or cross-team APIs where the API is a contract.
API is defined before implementation, so it is clearer and more stable.
Works well with code generation and documentation.
Slower to change, more rigid.

Code-first:
Faster to implement, short feedback loop.
API follows business logic.
Easier to change and experiment.
Less stable, documentation may lag behind.
Best suited for internal APIs.

Choice:
Schema-first for Warehouse.
Code-first for Product and Store.
Often a hybrid works best: code-first early, schema-first once the API stabilizes.
```
----
3. Given the need to balance thorough testing with time and resource constraints, how would you prioritize and implement tests for this project? Which types of tests would you focus on, and how would you ensure test coverage remains effective over time?

**Answer:**
```txt
I would focus mainly on integration tests, since they validate multiple parts of the application working together and give high confidence in real behavior.

The drawback is that not all edge cases are easily covered this way. To improve coverage, I would regularly review code coverage reports and add targeted unit tests for critical or missed paths, especially for business logic and error handling.
```
