# inventi-coding-challenge

### Installation

```sh
$ git clone https://github.com/Justas12/inventi-coding-challenge.git
$ cd inventi-coding-challenge
$ ./mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=8080
```
### API
<pre>
- POST /api/operations (form data payload with file attribute)
- GET /api/operations?from=to=
- GET /api/balances?accountNumber=from=to=
</pre>
