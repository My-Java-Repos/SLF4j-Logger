# APPLICATION_NAME

## SLF4J  (MDC)

You should consider using the SLF4J MDC (mapped diagnostic context) whenever
there is some common information that should be logged with every message.  To
add data to the MDC, use:

```java
org.slf4j.MDC.put("sample-key", "sample value");
```

To make the MDC values appear in the generated logs, adjust logback-spring.xml.
Add the following to the format statement for the JSON logs:

```
   "sample-key": "%mdc{sample-key}"
```

To see the generated MDC context in a JSON log message, enable the `cloud`
profile.

## Protected Endpoints

For the Clinical Platform, our intent is to use JWT tokens to authenticate all
API calls.  This is implemented with the standard [spring-jwt-library].
This library standardizes web security behavior so it's easy to protect any
sort of Spring Boot-based API.  Since Camunda is based on Spring Boot, it
drops right in.

### Protecting Endpoints with a Pattern

By default, this example code does not protect any endpoints.  You can
change configuration in `application.yml` to match your needs.

Each URL requires a `type` (either [`ANT`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/util/AntPathMatcher.html), [`MVC`](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/servlet/util/matcher/MvcRequestMatcher.html), or [`REGEX`](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/util/matcher/RegexRequestMatcher.html)), a `pattern` string, and a list of one or more `roles`.  For any URL that matches the pattern, the caller is required to be in at least one of the provided roles.

Role names are taken from the demo claims in the JWT token.  Role names are
always in UPPER CASE and always start with `ROLE_`.  For example, if the demo
claims include `one` as a role, then the Spring Security context will include
`ROLE_ONE`.

For Camunda, the simplest example looks like this:

```yaml
demo.jwt:
  protectedUrls:
    - type: ANT
      pattern: /engine-rest/**
      roles: [ ROLE_ONE ]
```

Here's a slightly more complicated example, where portions of the API
are identified in different ways and have different protections:

```yaml
demo.jwt:
  protectedUrls:
    - type: REGEX
      pattern: .*process-definition.*
      roles: [ ROLE_ONE, ROLE_TWO ]
    - type: ANT
      pattern: /engine-rest/history/process-instance/**
      roles: [ ROLE_THREE ]
```

Note that the pattern must match _the endpoint configured on the controller
itself_, disregarding any context path that is set for the application.  Even
if you were running this application with context path `/demo` (meaning that
the API would be invoked at `/demo/engine-rest/process-definition` or whatever)
you still only pattern match on `/engine-rest/**`.

How you protect these Camunda endpoints depends a lot on your use case and who
you are expecting to invoke the [Camunda API](https://docs.camunda.org/manual/7.7/reference/rest/).  In 
some cases, you might protect all endpoints with a single role.  In other
cases, you may have one role that is allowed to start a workflow and retrieve
results, but other roles that are allowed to change system configuration.
Think this through carefully.  For instance, if browser end users are going to
call the Camunda API directly, you probably don't want them to be able to do
dangerous things like remove or modify rulesets through the API.

## Retrieving a Token

To invoke a protected endpoint, a caller first gets an access token from the
Clinical Platform authorization service.

In the Clinical Platform user interface, this is taken care of by standard
functionality.  The auth library sends the user to the correct login page and
then uses the password grant to generate an access token for the logged-in
user.  The demo access token can be found in local storage.

Backend callers need to get their own access token using the client credentials
grant instead of the password grant.  This requires a client id and secret.


In the dev environment, the simplest way for developers and testers to retrieve
an access token is to use the `demo_engineer` client id with the client
credentials grant:

```
curl --request POST --url 'https://dev-demo-api.org.com/auth/oauth/token?grant_type=client_credentials&client_id=demo_engineer&client_secret=demo_engineer'
```
You can look at the resulting JWT claims using the debugger at [jwt.io](https://jwt.io).

## Invoking Protected Endpoints

Once the caller has an access token, they provide it as a `Bearer` token when
making API calls, using the `Authorization` header:

```
Authorization: Bearer eyJraWQiOiI0MDU3ZDZkNS1jMjYzLTRj...Rze-NxpO89BStpkIGSFb1FQ
```

When that API call arrives at the application, Spring uses the functionality in
[spring-jwt-library](https://github.com/demo/spring-jwt-library) to
extract the `Bearer` token, verify its signature, and extract granted authorities
based on the claims in the token.

## Pre-requisities
- Download and install Docker for Mac[https://docs.docker.com/docker-for-mac/install/] or Windows [https://docs.docker.com/docker-for-windows/install/]
- Java 11 SDK
- Apache Maven

## Docker Compose for Postgres

In the IntelliJ run configurations, Add New Configuration, and select Docker-Compose as shown below and then select the docker compose file path as shown
in the second screenshot

- Screen1


![image](screen-1.png)

- Screen2


![image](screen-2.png)

## To run locally

In the IntelliJ run configurations, Add New Configuration, and select Spring Boot Configuration and then provide the main class

## BDD Tests

Implement the BDD Tests using Cucumber


