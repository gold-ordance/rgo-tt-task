### RestTasksBoardService

#### Get all the tasks boards

**Request**:
```http request
GET http://localhost:8080/tasks-boards
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
  "boards": [
    {
      "entityId": <number>,
      "name": <string>,
      "shortName": <string>
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

#### Get tasks board by entityId

**Request**:
```http request
GET http://localhost:8080/tasks-boards/{entityId}
```

**Query params**:
```code
entityId:
  type: number
  valid-value: [1, 2^63 - 1]
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
  "board": {
    "entityId": <number>,
    "name": <string>,
    "shortName": <string>
  }
}

```

* **Not found (404):**
    * Not found by {entityId}.

Headers:
```code
content-type: application/json; charset=utf-8
```

Body:
```code
{
    "status": {
        "statusCode": "NOT_FOUND"
    }
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

#### Save task

**Request**:
```http request
POST http://localhost:8080/tasks-boards
```

**Body**:
```code
{
    "name": <string>
}
```

**Responses:**

* **CREATED (201):**

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
  "board": {
    "entityId": <number>,
    "name": <string>,
    "shortName": <string>
  }
}

```

* **Bad request (400):**
    * The {name} is [null, empty].

Headers:
```code
content-type: application/json; charset=utf-8
```

Bodies:
```code
{
    "status": {
        "statusCode": "INVALID_RQ",
        "message": "The name is null."
    }
}
```
```code
{
    "status": {
        "statusCode": "INVALID_RQ",
        "message": "The name is empty."
    }
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

#### Delete task by entityId

**Request**:
```http request
DELETE http://localhost:8080/tasks-boards/{entityId}
```

**Query params**:
```code
entityId:
  type: number
  valid-value: [1, 2^63 - 1]
```

**Responses:**

* **OK (204)**

* **Bad request (400):**
    * The {entityId} is negative.

Headers:
```code
content-type: application/json; charset=utf-8
```

Body:
```code
{
    "status": {
        "statusCode": "INVALID_RQ",
        "message": "The entityId is negative."
    }
}
```

* **Not found (404):**
    * Not found by {entityId}.

Headers:
```code
content-type: application/json; charset=utf-8
```

Body:
```code
{
    "status": {
        "statusCode": "NOT_FOUND"
    }
}
```

* **Internal error (500):**
    * Internal unexpected error.

Headers:
```code
content-type: application/json; charset=utf-8
```
