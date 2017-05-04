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
    "newsCrawler" : false
    "hazzardCrawle" : false
    "weatherCrawler" : true
    "e-mail" : "example@gmail.com"
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
    "newsCrawler" : false
    "hazzardCrawle" : false
    "weatherCrawler" : true
    "e-mail" : "example@gmail.com"
}
```
 
* **Error Response:**

  * **Code:** 400 Bad Request <br />
    **Content:** `{ error : "Data for creating new user is invalid" }`


* **Notes:**
  _No aditional info available_
