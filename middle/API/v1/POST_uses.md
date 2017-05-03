****
----
  

* **URL**

  v1/users/{userId}/notifications/triggered-notifications

* **Method:**
  
  `GET` 
  
*  **URL Params**

   <_If URL params exist, specify them in accordance with name mentioned in URL section. Separate into optional and required. Document data constraints._> 

   **Required:**
 
   `userId=[integer]`


* **Data Params**

  _N/A for GET verb_

* **Success Response:**
  

  * **Code:** 200 <br />
    **Content:** 
```javascript
{
    "type" : 'News' or 'Hazzard' or 'Weather' or 'User_Notification'
    "data" :{json with data} or NOTIFICATION_DATA[NOTIFICATION_TEXT]
    error:(string) // field that will be completed should any problems occur
}
```
 
* **Error Response:**

  <_Most endpoints will have many ways they can fail. From unauthorized access, to wrongful parameters etc. All of those should be liste d here. It might seem repetitive, but it helps prevent assumptions from being made where they should be._>

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "Log in" }`

  OR

  * **Code:** 422 UNPROCESSABLE ENTRY <br />
    **Content:** `{ error : "Email Invalid" }`

* **Sample Call:**

  <_Just a sample call to your endpoint in a runnable format ($.ajax call or a curl request) - this makes life easier and more predictable._> 

* **Notes:**

  <_This is where all uncertainties, commentary, discussion etc. can go. I recommend timestamping and identifying oneself when leaving comments here._> 