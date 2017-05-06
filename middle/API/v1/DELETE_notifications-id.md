**DELETE_users-id**
----
  

* **URL**

  _v1/users/{userId}/notifications/{notificationId}_

* **Method:**
  
  `DELETE` 
  
*  **URL Params**

   **Required:**
 
   `userId=[integer]`
   `notificationId=[integer]`


* **Data Params**
  _N/A for GET verb_

* **Success Response:** 

  * **Code:** 200 OK <br />
    **Content:** 

```json
 { 
      "id": 23
  }
```
 
* **Error Response:**

  * **Code:** 422 Unprocessable Entity <br />
    **Content:** `{ error : "Invalid notification id / Invalid Notification user" }`

  * **Code:** 500 Internal Server Error <br />
    **Content:** `{ error : "Internal server error" }`

  * **Code:** 503 Service Unavailable <br />
    **Content:** `{ error : "The server is currently unavailable" }`

* **Notes:**

  _No aditional info available_
