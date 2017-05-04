**DELETE_users-id**
----
  

* **URL**

  _v1/users/{userId}_

* **Method:**
  
  `DELETE` 
  
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
     "id": 23
}
```
 
* **Error Response:**

  * **Code:** 422 Unprocessable Entity <br />
    **Content:** `{ error : "Invalid User" }`

* **Notes:**

  _No aditional info available_
