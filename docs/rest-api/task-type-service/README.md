### RestTaskTypeService

#### Get all the task types

**Request**:
```http request
GET http://localhost:8080/types
```

**Responses:**

* **OK (200):**

Headers:
```code
content-type: application/json; charset=utf-8
```

Body:
```code
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

* **Internal error (500):**
    * Internal unexpected error.

Headers:
```code
content-type: application/json; charset=utf-8
```

Body:
```code
{
    "status": {
        "statusCode": "ERROR",
        "message": <string>
    }
}
```