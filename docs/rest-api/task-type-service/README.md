### RestTaskTypeService

Methods:
* [GET ALL](#get-all-the-task-types)

#### Get all the task types

**Request**:
```http request
GET http://localhost:8080/types
```

**Responses:**

* **OK (200):**

Headers:
```
content-type: application/json; charset=utf-8
```

Body:
```
{
  "status": {
    "statusCode": "SUCCESS"
  },
  "types": [
    {
      "entityId": <number>,
      "name": <string>
    },
    ...
  ]
}
```

* **Too Many Requests (429):**
  * Too many requests.

Headers:
```
content-type: application/json; charset=utf-8
```

Body:
```
{
    "status": {
        "statusCode": "TOO_MANY_REQUESTS"
    }
}
```

* **Internal error (500):**
    * Internal unexpected error.

Headers:
```
content-type: application/json; charset=utf-8
```

Body:
```
{
    "status": {
        "statusCode": "ERROR",
        "message": <string>
    }
}
```