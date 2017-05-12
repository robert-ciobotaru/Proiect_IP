**POST_users**
----
  

* **URL**

  _v1/users_

* **Method:**
  
  `POST` 
  
*  **URL Params**
_No Url params requested_
* **Data Params**

```json
 {
    "country" : "Romania",
    "city" : "Iasi",
    "newsCrawler" : false,
    "hazzardCrawler" : false,
    "weatherCrawler" : true,
    "email" : "example@gmail.com"
}
```

* **Success Response:** 

  * **Code:** 201 Created <br />
    **Content:** 
```json
{
    "id" : 23,
    "country" : "Romania",
    "city" : "Iasi",
    "newsCrawler" : false,
    "hazzardCrawler" : false,
    "weatherCrawler" : true,
    "email" : "example@gmail.com"
}
```
 
* **Error Response:**
  
  * **Code:** 429 Too Many Requests <br />
    **Content:** `{ error : "TOO MANY REQUESTS" }`
    
  * **Code:** 400 Bad Request <br />
    **Content:** `{ error : "Data for creating new user is invalid" }` <br />
    **Content:** `{ error : "The specified request is not readable" }`

  * **Code:** 500 Internal Server Error <br />
    **Content:** `{ error : "Internal server error" }`

  * **Code:** 503 Service Unavailable <br />
    **Content:** `{ error : "The server is currently unavailable" }`

* **Notes:**
  _No aditional info available_
