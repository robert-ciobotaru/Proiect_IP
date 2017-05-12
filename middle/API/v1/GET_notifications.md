**GET_notifications**
----
  

* **URL**

  _v1/users/{userId}/notifications_

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
    "userNotificationsList" : [
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
    ],
    "weatherNotificationsList" : [
        {
            "location" : {
                       "city" : "Iasi",
                       "country" : "Romania"
                      },
           "text": "The weather is bad"
        },
         {
            "location" : {
                       "city" : "Iasi",
                       "country" : "Romania"
                      },
           "text": "The weather is bad"
        }
    ],
    "hazzardNotifications" : {
        "earthquakesList" : [
            {
                "magnitude" : 2,
                "location" : {
                        "city" : "Iasi",
                        "country" : "Romania"
                },
                "time" : "2017-05-06T00:21:40",
                "url" :  "http://link.ro",
                "title" : "The amaizing earthquake"
            }
        ],
        "hazzard" : {

            "floodsList" : [
                {
                    "alert-level" : "BIG",
                    "location" : {
                            "country" : "Romania"
                    },
                    "time" : "2017-05-06T00:21:40",
                    "url" :  "http://link.ro",
                    "title" : "The amaizing earthquake",
                    "description" : "Description for the flood"
                }
            ],
            "cyclonesList" : [
                 {
                    "alert-level" : "BIG",
                    "location" : {
                            "country" : "Romania"
                    },
                    "time" : "2017-05-06T00:21:40",
                    "url" :  "http://link.ro",
                    "title" : "The amaizing earthquake",
                    "description" : "Description for the cyclone"
                }
            ]
        }
    },
    "newsNotificationsList" : [
        {
            "author": "Some guy",
            "title" : "The weather is bad",
            "description" : "The weaher is really bad",
            "url" : "http://link.ro",
            "urlToImage" : "http://...",
            "publishedAt" : "some location"
        },
        {
            "author": "Some guy",
            "title" : "The weather is bad",
            "description" : "The weaher is really bad",
            "url" : "http://link.ro",
            "urlToImage" : "http://...",
            "publishedAt" : "some location"
        }
    ]   
}
```
 
* **Error Response:**
  
  * **Code:** 429 Too Many Requests <br />
    **Content:** `{ error : "TOO MANY REQUESTS" }`
    
  * **Code:** 422 Unprocessable Entity <br />
    **Content:** `{ error : "Internal server error" }`

  * **Code:** 400 Bad Request <br />
    **Content:** `{ error : "The specified request is not readable" }`

  * **Code:** 500 Internal Server Error <br />
    **Content:** `{ error : "Internal server error" }`

  * **Code:** 503 Service Unavailable <br />
    **Content:** `{ error : "The server is currently unavailable" }`

* **Notes:**

 
