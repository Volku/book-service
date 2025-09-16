# book-service
Requirement
1. Docker
2. Java 21
3. Gradle (optional)


For this project to run
- In case wanted to run on intelliJ
  1. open project on intelliJ
  2. right click on BookServiceApplication
  3. click run and server start then test with provided curl
     
- In case wanted to run from terminal
  1. cd to project folder
  2. using ./gradlew clean bootRun command for running

In case of integrations test and unit test I provided in the project itself:
  from IntelliJ 
    - click on run test from the folder
      <img width="437" height="848" alt="image" src="https://github.com/user-attachments/assets/a03c9222-3a1f-4675-9a88-c94455486ba0" />

  from command line
    - change directory to project folder and use ./gradlew clean test command


**Try chmod 777 ./gradlew first if command line doesn't work**
**All resource will be created on docker using docker compose if you needed to connect to resource read compose.yml**


API Spec:
  GET: /books
    description: Get book by author name
    request parameter:
      author(String)(Mandatory): author name of book
    
    response:
      bookList(Array of Object):
        id (String): id of books
        title (String): book's title name
        author (String): book's author name
        publishDate (String): book publish date in Bhuddist calendar with format yyyy-mm-dd (1970-01-01) 

  curl example:
        --url 'http://localhost:8080/books?author=%E0%B9%80%E0%B8%AD%E0%B9%8A%E0%B8%B0%20%E0%B8%88%E0%B8%B4%E0%B8%A3%E0%B8%B2%E0%B8%81%E0%B8%A3%E0%B8%93%E0%B9%8C'


  POST: /books
    description: Create book from request body
    request body:
      author(String)(Mandatory): book's author name
      title(String)(Mandatory): book's title name
      publishDate (String): book publish date in Bhuddist calendar with format yyyy-mm-dd (1970-01-01) 

    response:
      Nothing only HTTP Status

  curl example:
          curl --request POST \
        --url http://localhost:8080/books \
        --header 'Content-Type: application/json' \
        --data '{
        "author": "เอ๊ะ จิรากรณ์",
        "title": "บ่ฮู้เรื่องดอก",
        "publishedDate": "2558-09-15"
      }'
  
    
