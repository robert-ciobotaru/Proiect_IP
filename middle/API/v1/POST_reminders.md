**POST_reminders**
----
  

* **URL**

  _v1/users/{userId}/reminders_

* **Method:**
  
  `POST` 
  
*  **URL Params**

   **Required:**
 
   `userId=[integer]`


* **Data Params**

  ```json
  {
      "text": "Wake me up",
      "time" : 1231245,
      "repeatable" : true,
      "interval" : 300
  }
  ```

* **Success Response:** 

  * **Code:** 201 Created <br />
    **Content:** 
```json
 { 
      "id": 23,
      "text": "Wake me up",
      "time" : 1231245,
      "repeatable" : true,
      "interval" : 300
  }
```
 
* **Error Response:**
  
  * **Code:** 429 Too Many Requests <br />
    **Content:** `{ error : "TOO MANY REQUESTS" }`
    

  * **Code:** 422 Unprocessable Entity <br />
    **Content:** `{ error : "Invalid User" }`

  * **Code:** 400 Bad Request <br />
    **Content:** `{ error : "Input criteria not correct" }` <br />
    **Content:** `{ error : "The specified request is not readable" }` <br />
    **Content:** `{ error : "Input provided does not meet the requirements" }`

  * **Code:** 500 Internal Server Error <br />
    **Content:** `{ error : "Internal server error" }`

  * **Code:** 503 Service Unavailable <br />
    **Content:** `{ error : "The server is currently unavailable" }`

* **Notes:**

  _No aditional info available_

