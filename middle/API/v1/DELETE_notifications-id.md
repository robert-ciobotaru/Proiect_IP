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

  * **Code:** 200 Ok <br />
    **Content:** 

```javascript
 { 
      "id": 23
  }
```
 
* **Error Response:**

  * **Code:** 422 Unprocessable Entity <br />
    **Content:** `{ error : "Invalid notification id / Invalid Notification user" }`

* **Notes:**

  _No aditional info available_
