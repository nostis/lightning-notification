# lightning-notification
Application to send notifications about storm in given area

* API available at: https://nostis.heliohost.org/lightning/
* If you want API access (gathering token, and sending queries) mail to me at **lightning.notification@gmail.com**. Send me your name/nickname and I will create account for you.
* API description:
  * /api/authenticate - used to obtain token (175 characters) and expiration time(token expires after 5 hours, to count how much time you have: get epoch time in seconds and substract expiration - you will get remaining time in seconds)
  * /api/register - used to register new API clients - you need token, and you need to be admin to do that
  * /api/delete/{name/login} - used to delete API clients - you need token, and you need to be admin to do that
 
  ---
 
  * /lightning/addcustomer - used to add new customers (people who will get notifications); floats in location **must** have one digit after point - you need token to do that
  * /lightning/removecustomer - used to remove customer - you need token to do that

  ---
  * Info: In Authorization: Bearer <token> - after Bearer **must** be one space | **Only** https will work

* REST API access:
  * /**api**
    * /**authenticate** - method: **POST** | Headers: **Content-Type: application/json** | Body: **name:<name/login>, password:\<password>** 
    
    * /**register** - method: **POST** | Headers: **Content-Type: application/json, Authorization: Bearer \<token>** | Body: **name:<name/login>, password:\<password>, (optional) isadmin=\<true or false>**
    
    * /**delete**/{name/login} - method: **DELETE** | Headers: **Content-Type: application/json, Authorization: Bearer \<token>**
  
  * /**lightning**
    * /**addcustomer** - method: **POST** | Headers: **Content-Type: application/json, Authorization: Bearer \<token>** | Body: **email:\<email>, location:[\<float>, \<float>]**
    
    * /**removecustomer**/{email} - method: **DELETE** | Header: **Content-Type: application/json, Authorization: Bearer \<token>**


* Lightnings API used from: https://burze.dzis.net/
* Soap classes - getting data from wsdl service - code used from: https://stackoverflow.com/questions/15940234/how-to-do-a-soap-web-service-call-from-java-class
* JWT service - code used from: https://www.javainuse.com/spring/boot-jwt-mysql
