**GET_reminders**
----
  

* **URL**

  _v1/users/{userId}/reminders_

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
    "remindersList" : [
        {
            "id": 23,
            "text": "Wake me up",
            "time" : 1231245,
            "repeatable" : true,
            "interval" : 300
        },
        {
            "id": 24,
            "text": "Get the kid",
            "time" : 123245,
            "repeatable" : false,
            "interval" : 400
        }
    ]
}
```
 
* **Error Response:**
  
  * **Code:** 429 Too Many Requests <br />
    **Content:** `{ error : "TOO MANY REQUESTS" }`
    
  * **Code:** 422 Unprocessable Entity <br />
    **Content:** `{ error : "Invalid User" }`

  * **Code:** 400 Bad Request <br />
    **Content:** `{ error : "Input criteria not correct" }` <br />
    **Content:** `{ error : "The specified request is not readable" }`

  * **Code:** 500 Internal Server Error <br />
    **Content:** `{ error : "Internal server error" }`

  * **Code:** 503 Service Unavailable <br />
    **Content:** `{ error : "The server is currently unavailable" }`

* **Notes:**

 
