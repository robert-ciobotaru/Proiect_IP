**GET_triggered-notifications**
----
  

* **URL**

  _v1/users/{userId}/notifications/triggered-notifications_

* **Method:**
  
  `GET` 
  
*  **URL Params**

   **Required:**
 
   `userId=[integer]`


* **Data Params**

  _N/A for GET verb_

* **Success Response:** 

  * **Code:** 200 OK <br />
    **Content:** 

```json
{
    "type" : "User_Notification",
    "data" : {
        "id": 23,
        "text": "Wake me up",
        "time" : 1231245,
        "repeatable" : true,
        "interval" : 300
    },
    "error": "Error whatever"
}
```
 
* **Error Response:**

  * **Code:** 422 Unprocessable Entity <br />
    **Content:** `{ error : "Invalid User" }`

  * **Code:** 400 Bad Request <br />
    **Content:** `{ error : "Input criteria not correct" }`

  * **Code:** 500 Internal Server Error <br />
    **Content:** `{ error : "Internal server error" }`

  * **Code:** 503 Service Unavailable <br />
    **Content:** `{ error : "The server is currently unavailable" }`

* **Notes:**

    _No known format for some specific types other than 'User_Notification'_
 
