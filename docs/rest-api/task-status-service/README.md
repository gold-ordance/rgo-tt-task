### RestTaskStatusService

Methods:
* [GET ALL](#get-all-the-task-statuses)

#### Get all the task statuses

**Request**:
```http request
GET http://localhost:8080/statuses
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
  "taskStatuses": [
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