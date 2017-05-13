Backend API proposal
======

Context
------

Willing to obtain a better comutincation API with the back-end, the middle-end team decided to make a proposal that aims to disambiguate by providing examples.

Back-end will expose an HTTP Server. For an Http **reqeust** with the header containing the "Content-Type" attribute set to "application/json" and an appropriate body a **response** with the header containing the "Content-Type" "application/json"and an json compliant response will be returned. The response will
always have the *_Status code_* : 200 OK.

API Methods
------

### Response format for every method
Every response will contain a field called _"error"_ that will be a non empty string if the opperation requested failed or an empty string (_""_) if the operation was successful
No guarantee is offerend for the other fields if the _"error"_ field is set. It is middle-ends responsability to first check the _"error"_ field.

####
**Method:** _getUserNotifications_

**Description:** _Obtain from the back-end only the notifications that were inserted by the user_

####
**Request body:**
```json
{
    "userId" : 23,
    "method": "getUserNotifications"
}
```

**Response body:**
```json
{
   "notificationsList" : [
         {
             "id": 23,
             "text": "Wake me up",
             "time" : 1231245,
             "repeatable" : 1,
             "interval" : 300
         },
         {
             "id": 24,
             "text": "Get the kid",
             "time" : 123245,
             "repeatable" : 0,
             "interval" : 400
         }
     ],
     "error" : "Whatever error string"
}
```


####
**Method:** _getNotifications_

**Description:** _Obtain from the back-end the notifications that should be displayed to the specified user_

####
**Request body:**
```json
{
    "userId" : 23,
    "method": "getNotifications"
}
```
####
**Response body:**

```json
{
	"userNotifications": [{
			"id": 23,
			"text": "Get the kid",
			"time": 213141,
			"repeatable": 1,
			"interval": 234
		},
		{
			"id": 24,
			"text": "Burn the house",
			"time": 54234,
			"repeatable": 1,
			"interval": 234
		}
	],

	"weatherNotificationsList": [{
			"location": {
				"city": "Iasi",
				"country": "Romania"
			},
			"text": "The weather is bad"
		},
		{
			"location": {
				"city": "Iasi",
				"country": "Romania"
			},
			"text": "The weather is bad"
		}
	],

	"earthquakesList": [{
		"magnitude": 2,
		"place": "Iasi, Romania",
		"time": "2017-05-06T00:21:40",
		"url": "http://link.ro",
		"title": "The amazing earthquake"
	}],

	"floodsList": [{
		"alertLevel": "BIG",
		"country": "Romania",
		"time": "2017-05-06T00:21:40",
		"url": "http://link.ro",
		"title": "The amazing earthquake",
		"description": "Description for the flood"
	}],
	"cyclonesList": [{
		"alertLevel": "BIG",
		"country": "Romania",
		"time": "2017-05-06T00:21:40",
		"url": "http://link.ro",
		"title": "The amazing cyclone",
		"description": "Description for the cyclone"
	}],
	"newsNotificationsList": [{
			"author": "Some guy",
			"title": "The weather is bad",
			"description": "The weaher is really bad",
			"url": "http://link.ro",
			"urlToImage": "http://...",
			"publishedAt": "some location"
		},
		{
			"author": "Some guy",
			"title": "The weather is bad",
			"description": "The weaher is really bad",
			"url": "http://link.ro",
			"urlToImage": "http://...",
			"publishedAt": "some location"
		}
	],
	"error": "Error message"
}
```
####
**Method:** addNotification


**Description:**  Register the notification for the specified user

####
**Request body:**
```json
{
    "userId" : 2,
    "method" : "addNotification",
    "notification" : {
        "text": "Get the kid",
        "time": 213141,
        "repeatable": 0,
        "interval" : 234
    }
}
```

####
**Response body:**
```json
{
    "notificationId" : 2,
    "error" : "" 
}
```

####
**Method:** addUser


**Description:** Register a new user

####
**Request body:**
```json
{   
    "method": "addUser",
    "data": {
        "country" : "Romania",
        "city" : "Iasi",
        "newsCrawler" : 0,
        "hazzardCrawler" : 1,
        "weatherCrawler" : 0,
        "email" : "example@gmail.com"
    }
}
```
####
**Response body:**
```json
{
    "userId" : 2,
    "error": "Error info"
}
```
####
**Method:** removeNotification

**Description:**  Removes the notification with the specified id

**Clarification required:** Should middle-end provide the userId for this aswell ?

####
**Request body:**
```json
{
    "notificationId": 23,
    "method": "removeNotification"
}
```

####
**Response body:**
```json
{
    "error": "Error info"
}
```
####
**Method:** removeUser

**Description:**  Removes the user with the specified id

####
**Request body:**
```json
{
    "userId": 2,
    "method": "removeUser"
}
```
####
**Response body:**
```json
{
    "error": "Error info"
}
```
