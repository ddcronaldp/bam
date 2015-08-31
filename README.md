admiral-svc
===========
```
                                _,,,--~~~~~~~~--,_
                            ,-' : : : :::: :::: :: : : : : :º '-,
                         ,-' :: : : :::: :::: :::: :::: : : :o : '-,
                      ,-' :: ::: :: : : :: :::: :::: :: : : : : :O '-,
                   ,-' : :: :: :: :: :: : : : : : , : : :º :::: :::: ::';
                 ,-' / / : :: :: :: :: : : :::: :::-, ;; ;; ;; ;; ;; ;; ;\
                /,-',' :: : : : : : : : : :: :: :: : '-, ;; ;; ;; ;; ;; ;;|
              /,',-' :: :: :: :: :: :: :: : ::_,-~~,_'-, ;; ;; ;; ;; |
          _/ :,' :/ :: :: :: : : :: :: _,-'/ : ,-';'-'''''~-, ;; ;; ;;,'
      ,-' / : : : : : : ,-''' : : :,--'' :|| /,-'-'--'''__,''' \ ;; ;,-'/
      \ :/,, : : : _,-' --,,_ : : \ :\ ||/ /,-'-'x### ::\ \ ;;/
        \/ /---'''' : \ #\ : :\ : : \ :\ \| | : (O##º : :/ /-''
        /,'____ : :\ '-#\ : \, : :\ :\ \ \ : '-,___,-',-`-,,
        ' ) : : : :''''--,,--,,,,,,¯ \ \ :: ::--,,_''-,,'''¯ :'- :'-,
         ) : : : : : : ,, : ''''~~~~' \ :: :: :: :'''''¯ :: ,-' :,/\
         \,/ /|\\| | :/ / : : : : : : : ,'-, :: :: :: :: ::,--'' :,-' \ \
         \\'|\\ \|/ '/ / :: :_--,, : , | )'; :: :: :: :,-'' : ,-' : : :\ \,
       /¯ :| \ |\ : |/\ :: ::----, :\/ :|/ :: :: ,-'' : :,-' : : : : : : ''-,,
      | : : :/ ''-(, :: :: :: '''''~,,,,,'' :: ,-'' : :,-' : : : : : : : : :,-'''\\
  ,-' : : : | : : '') : : :¯''''~-,: : ,--''' : :,-'' : : : : : : : : : ,-' :¯'''''-,_
 / : : : : :'-, :: | :: :: :: _,,-''''¯ : ,--'' : : : : : : : : : : : / : : : : : : :''-,
/ : : : : : -, :¯'''''''''''¯ : : _,,-~'' : : : : : : : : : : : : : :| : : : : : : : : :
: : : : : : : :¯''~~~~~~''' : : : : : : : : : : : : : : : : : : | : : : : : : : : :)
```
Back End repository for Operation Tooling

# System Config
- You need to set up your environment for remote config w/ Java 8. <a href="http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html">Download the policies</a>, then add them to the correct location (follow the normal remote config instructions.


Starting The Admiral from the command line:
<ol>
	<li>From the root directory do a mvn clean install.</li>
	<li>cd service</li>
	<li>mvn spring-boot:run</li>
</ol>

Setting up intellij:
<ol>
	<li>Use a Spring Boot Runner</li>
	<li>main class: com.dealertrack.admiral.Application</li>
	<li>VM options: -Dapp.name=admiral-services -Djetty.port=9092 -javaagent:/Users/{USERNAME}/.m2/repository/org/springframework/spring-instrument/4.1.6.RELEASE/spring-instrument-4.1.6.RELEASE.jar</li>
	<li>
		Override params:
		<ul>
			<li>spring.velocity.checkTemplateLocation --- false</li>
		</ul>
	</li>
</ol>

Setting up for localhost:

<ol>
	<li>Install JCE: From your terminal, run: install-jce. Choose the option for Java 1.8</li>
    <li>Start your local mongodb instance (mongod)</li>
    <li>Run your mongo shell and add the user "advertising" with the password "advertising" to the database "advertising" with read-write permissions (use either the db.createUser() command or db.addUser() command).
        <ul>
            <li>See: http://docs.mongodb.org/manual/tutorial/add-user-to-database/ (Mongo 3.x) or http://docs.mongodb.org/v2.2/reference/method/db.addUser/ (Mongo 2.x)
            <li>E.g.: db.createUser({ user: "advertising", pwd:"advertising", roles: [ {role: "readWrite", db: "advertising" } ] }) (mongo 3.x syntax)</li>
        </ul>
    </li>
    <li>You can verify this user from the terminal via the command: mongo -u advertising -p advertising --authenticationDatabase advertising</li>
</ol>


RAML Documentation:
<ol>
	<li><a href="http://json-schema.org/latest/json-schema-validation.html#anchor61">Documentation on json schema standard which defines the json objects.</a></li>
	<li><a href="https://github.com/joelittlejohn/jsonschema2pojo/wiki/Reference">Documentation for the generator of the model objects.</a></li>
	<li><a href="http://raml.org/spec.html">RAML spec(was out of date when this was written.)</a></li>
</ol>


Making authenticated requests
=========================


Step 1 - Authenticate
--------------------
Auth Request:
```
POST http://127.0.0.1:9092/admiral-services/api/v1/oauth
  headers:
     Content-type:application/x-www-form-urlencoded;charset=UTF-8
  form fields:  
     grant_type=password
     client_id=ddc.mobile
     username=<username>
     password=password
```

Response:
```
{"expires_in":"21600","refresh_token":"TGT-506-iRtyDhovELvWtkkLqUcjmHDE1euRdTOJd9wUc4ZvgbdDCOEaGo-cas","access_token":"ST-878-3Rh1cYygoaycDpLH6Ocd-cas"}
```

Step 2 - Use the auth_token to make a request
--------------------

Include the Authorization header with every request

`Authorization: Bearer <access_token>`


Example:
 GET       http://127.0.0.1:9092/admiral-services/api/v1/accounts/1
 HEADER    Authorization: Bearer <access_token>



