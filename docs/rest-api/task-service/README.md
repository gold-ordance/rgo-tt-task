### RestTaskService

Methods:
* [GET ALL](#get-all-the-tasks-of-the-specified-board)
* [GET BY ENTITY ID](#get-task-by-entityid)
* [GET BY NAME](#get-task-by-name)
* [SAVE](#save-task)
* [UPDATE](#update-task)
* [DELETE BY ENTITY ID](#delete-task-by-entityid)

#### Get all the tasks of the specified board

**Request**:
```http request
GET http://localhost:8080/tasks?boardId={boardId}
```

**Query params**:
```
boardId:
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
    "tasks": [
        {
            "entityId": <number>,
            "name": <string>,
            "createdDate": <date>,
            "lastModifiedDate": <date>,
            "description": <string>:optional,
            "number": <number>,
            "board": {
                "entityId": <number>,
                "name": <string>,
                "shortName": <string>
            },
            "type": {
                "entityId": <number>,
                "name": <string>
            },
            "status": {
                "entityId": <number>,
                "name": <string>
            }
        },
        ...
    ]
}
```

* **Bad request (400):**
    * The {boardId} parameter is negative.

Headers:
```
content-type: application/json; charset=utf-8
```

Body:
```
{
    "status": {
        "statusCode": "INVALID_RQ",
        "message": "The boardId is negative."
    }
}
```

* **Unprocessable Entity (422):**
    * The {boardId} not found in the storage.

Headers:
```
content-type: application/json; charset=utf-8
```

Body:
```
{
    "status": {
        "statusCode": "INVALID_ENTITY",
        "message": "The boardId not found in the storage."
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

#### Get task by entityId

**Request**:
```http request
GET http://localhost:8080/tasks/{entityId}
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
    "task": {
        "entityId": <number>,
        "name": <string>,
        "createdDate": <date>,
        "lastModifiedDate": <date>,
        "description": <string>,
        "number": <number>,
        "board": {
            "entityId": <number>,
            "name": <string>,
            "shortName": <string>
        },
        "type": {
            "entityId": <number>,
            "name": <string>
        },
        "status": {
            "entityId": <number>,
            "name": <string>
        }
    }
}
```

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

Body:
```
{
    "status": {
        "statusCode": "ERROR",
        "message": <string>
    }
}
```

#### Get task by name

**Request**:
```http request
GET http://localhost:8080/tasks?name={name}&boardId={boardId}
```

**Query params**:
```
boardId:
  type: number
  valid-value: [1, 2^63 - 1]
name:
  type: string
  valid-value: non-empty string
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
    "tasks": [
        {
            "entityId": <number>,
            "name": <string>,
            "createdDate": <date>,
            "lastModifiedDate": <date>,
            "description": <string>,
            "number": <number>,
            "board": {
                "entityId": <number>,
                "name": <string>,
                "shortName": <string>
            },
            "type": {
                "entityId": <number>,
                "name": <string>
            },
            "status": {
                "entityId": <number>,
                "name": <string>
            }
        },
        ...
    ]
}
```

* **Bad request (400):**
    * The {name} is empty.
    * The {boardId} is negative.

Headers:
```
content-type: application/json; charset=utf-8
```

Bodies:
```
{
    "status": {
        "statusCode": "INVALID_RQ",
        "message": "The name is empty."
    }
}
```
```
{
    "status": {
        "statusCode": "INVALID_RQ",
        "message": "The entityId is negative."
    }
}
```

* **Unprocessable Entity (422):**
    * The {boardId} not found in the storage.

Headers:
```
content-type: application/json; charset=utf-8
```

Body:
```
{
    "status": {
        "statusCode": "INVALID_ENTITY",
        "message": "The boardId not found in the storage."
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

#### Save task

**Request**:
```http request
POST http://localhost:8080/tasks
```

**Body**:
```
{
    "name": <string>,
    "boardId": <number>,
    "typeId": <number>,
    "statusId": <number>:optional,
    "description": <string>:optional
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
        "statusCode": "STORED"
    },
    "task": {
        "entityId": <number>,
        "name": <string>,
        "createdDate": <date>,
        "lastModifiedDate": <date>,
        "description": <string>,
        "number": <number>,
        "board": {
            "entityId": <number>,
            "name": <string>,
            "shortName": <string>
        },
        "type": {
            "entityId": <number>,
            "name": <string>
        },
        "status": {
            "entityId": <number>,
            "name": <string>
        }
    }
}
```

* **Bad request (400):**
    * The {name} is [null, empty].
    * The {boardId} is [null, negative].
    * The {typeId} is [null, negative].

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
```
{
    "status": {
        "statusCode": "INVALID_RQ",
        "message": "The boardId is null."
    }
}
```
```
{
    "status": {
        "statusCode": "INVALID_RQ",
        "message": "The boardId is negative."
    }
}
```
```
{
    "status": {
        "statusCode": "INVALID_RQ",
        "message": "The typeId is null."
    }
}
```
```
{
    "status": {
        "statusCode": "INVALID_RQ",
        "message": "The typeId is negative."
    }
}
```

* **Unprocessable Entity (422):**
    * The {boardId} not found in the storage.
    * The {typeId} not found in the storage.

Headers:
```
content-type: application/json; charset=utf-8
```

Bodies:
```
{
    "status": {
        "statusCode": "INVALID_ENTITY",
        "message": "The boardId not found in the storage."
    }
}
```
```
{
    "status": {
        "statusCode": "INVALID_ENTITY",
        "message": "The typeId not found in the storage."
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

#### Update task

**Request**:
```http request
PUT http://localhost:8080/tasks
```

**Body**:
```
{
    "entityId": <number>,
    "name": <string>,
    "statusId": <number>,
    "typeId": <number>,
    "description": <string>:optional
}
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
    "task": {
        "entityId": <number>,
        "name": <string>,
        "createdDate": <date>,
        "lastModifiedDate": <date>,
        "description": <string>,
        "number": <number>,
        "board": {
            "entityId": <number>,
            "name": <string>,
            "shortName": <string>
        },
        "type": {
            "entityId": <number>,
            "name": <string>
        },
        "status": {
            "entityId": <number>,
            "name": <string>
        }
    }
}
```

* **Bad request (400):**
    * The {entityId} is [null, negative].
    * The {name} is [null, empty].
    * The {statusId} is [null, negative].
    * The {typeId} is [null, negative].

Headers:
```
content-type: application/json; charset=utf-8
```

Bodies:
```
{
    "status": {
        "statusCode": "INVALID_RQ",
        "message": "The entityId is null."
    }
}
```
```
{
    "status": {
        "statusCode": "INVALID_RQ",
        "message": "The entityId is negative."
    }
}
```
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
```
{
    "status": {
        "statusCode": "INVALID_RQ",
        "message": "The statusId is null."
    }
}
```
```
{
    "status": {
        "statusCode": "INVALID_RQ",
        "message": "The statusId is negative."
    }
}
```
```
{
    "status": {
        "statusCode": "INVALID_RQ",
        "message": "The typeId is null."
    }
}
```
```
{
    "status": {
        "statusCode": "INVALID_RQ",
        "message": "The typeId is negative."
    }
}
```

* **Unprocessable Entity (422):**
    * The {entityId} not found in the storage.
    * The {statusId} not found in the storage.
    * The {typeId} not found in the storage.

Headers:
```
content-type: application/json; charset=utf-8
```

Bodies:
```
{
    "status": {
        "statusCode": "INVALID_ENTITY",
        "message": "The entityId not found in the storage."
    }
}
```
```
{
    "status": {
        "statusCode": "INVALID_ENTITY",
        "message": "The statusId not found in the storage."
    }
}
```
```
{
    "status": {
        "statusCode": "INVALID_ENTITY",
        "message": "The typeId not found in the storage."
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

#### Delete task by entityId

**Request**:
```http request
DELETE http://localhost:8080/tasks/{entityId}
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
