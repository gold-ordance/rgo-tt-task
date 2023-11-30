### RestTasksBoardService

Methods:
* [GET ALL](#get-all-the-tasks-boards)
* [GET BY ENTITY ID](#get-tasks-board-by-entityid)
* [SAVE](#save-board)
* [DELETE BY ENTITY ID](#delete-board-by-entityid)

#### Get all the tasks boards

**Request**:
```http request
GET http://localhost:8080/tasks-boards
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

#### Get tasks board by entityId

**Request**:
```http request
GET http://localhost:8080/tasks-boards/{entityId}
```

**Query params**:
```
entityId:
  type: number
  valid-value: [1, 2^63 - 1]
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
```
content-type: application/json; charset=utf-8
```

Body:
```
{
    "status": {
        "statusCode": "NOT_FOUND"
    }
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

#### Save board

**Request**:
```http request
POST http://localhost:8080/tasks-boards
```

**Body**:
```
{
    "name": <string>
}
```

**Responses:**

* **CREATED (201):**

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
```
content-type: application/json; charset=utf-8
```

Bodies:
```
{
    "status": {
        "statusCode": "INVALID_RQ",
        "message": "The name is null."
    }
}
```
```
{
    "status": {
        "statusCode": "INVALID_RQ",
        "message": "The name is empty."
    }
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

#### Delete board by entityId

**Request**:
```http request
DELETE http://localhost:8080/tasks-boards/{entityId}
```

**Query params**:
```
entityId:
  type: number
  valid-value: [1, 2^63 - 1]
```

**Responses:**

* **OK (204)**

* **Bad request (400):**
    * The {entityId} is negative.

Headers:
```
content-type: application/json; charset=utf-8
```

Body:
```
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
```
content-type: application/json; charset=utf-8
```

Body:
```
{
    "status": {
        "statusCode": "NOT_FOUND"
    }
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
