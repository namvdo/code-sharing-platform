### A simple REST service application using Spring Boot for sharing your code!

### Services available:
* Post code:
  * (`application/json` response): ```POST /api/code/new```
* Get code with UUID: 
  * (`application/json` response): ```GET /api/code/{UUID}```
  * (`text/html` response): ```GET /code/{UUID}```
### Features:
  * Post code from Json, HTML form
  * Limit number of views, time restriction (in second)
  * View code by UUID 
  * Get 10 newest code snippets

![](https://github.com/namvdo/code-sharing-platform/blob/master/src/resources/static/img/demo.png)
