# prerequisite
1. install docker locally
2.

## Local Test

## DB Setup
1. Install mySQL and mySQL workbench
2. login MySQL via mysql workbench by input your username and password
3. Create User table
~~~~
create user_table
~~~~
~~~
docker build -t quickimmi_webserver .
~~~

step 2: run the image with docker to start the server
~~~
docker run quickimmi_webserver .
~~~
Or running With AWS Configuration
~~~
docker run -e AWS_ACCESS_KEY_ID -e AWS_SECRET_ACCESS_KEY -e AWS_REGION -p 8080:8080 quickimmi_webserver
~~~
When starting the server, it will fetch docuSign private key from S3 bucket.
~~~
export AWS_ACCESS_KEY_ID='YOUR_AWS_ACCESS_KEY_ID'
export AWS_SECRET_ACCESS_KEY='YOUR_AWS_SECRET_ACCESS_KEY'
export AWS_REGION='us-west-1'
~~~

step 3: API test
...

# Test in Dev


# Other Helpful Command

## Kill local application with 8080
~~~
lsof -n -i4TCP:8080
~~~

PID is the second field. Then, kill that process:
~~~
kill -9 PID
~~~

## spotlessJavaCheck

1, Automatically Fix Formatting Issues
~~~
./gradlew spotlessApply
~~~
2, Rebuild the Project
~~~
./gradlew build
~~~
