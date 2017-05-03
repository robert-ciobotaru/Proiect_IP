**POST_notifications**
----
  

* **URL**

  _v1/users/{userId}/notifications_

* **Method:**
  
  `POST` 
  
*  **URL Params**

   **Required:**
 
   `userId=[integer]`


* **Data Params**

  ```javascript
  {
      "text": "Wake me up",
      "time" : 1231245,
      "repeatable" : true,
      "interval" : 300
  }
  ```

* **Success Response:** 

  * **Code:** 200 <br />
    **Content:** 
```javascript
 { 
      "id": 23,
      "text": "Wake me up",
      "time" : 1231245,
      "repeatable" : true,
      "interval" : 300
  }
```
 
* **Error Response:**

  * **Code:** 422 Unprocessable Entity <br />
    **Content:** `{ error : "Invalid User" }`

  * **Code:** 400 Bad Request <br />
    **Content:** `{ error : "Input criteria not correct" }`

* **Notes:**

  _No aditional info available_

