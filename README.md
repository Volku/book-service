### book-service
***
### Requirement
 1. Docker
 2. Java 21
3. Gradle (optional)

***
### For this project to run
* **In case wanted to run on intelliJ**
  1. open project on intelliJ
  2. right click on BookServiceApplication
  3. click run and server start then test with provided curl
     
* **In case wanted to run from terminal**
  1. cd to project folder
  2. using ./gradlew clean bootRun command for running

### In case of integrations test and unit test I provided in the project itself:
* **from IntelliJ** 
  - click on run test from the folder </br>
    <img width="437" height="848" alt="image" src="https://github.com/user-attachments/assets/a03c9222-3a1f-4675-9a88-c94455486ba0" />

* **from command line**
  - change directory to project folder and use ./gradlew clean test command


**Try chmod 777 ./gradlew first if command line doesn't work** </br>
**All resource will be created on docker using docker compose if you needed to connect to resource read compose.yml**

### API Specification 📚

***

### GET /books

* **Description**: Get a book by its author's name.
* **Request Parameter**:
    * `author` (`String`, **Mandatory**): The author's name.
* **Response**:
    * `bookList` (`Array of Objects`): A list of book objects.
        * `id` (`String`): The unique ID of the book.
        * `title` (`String`): The book's title.
        * `author` (`String`): The author's name.
        * `publishDate` (`String`): The book's publish date in the Buddhist calendar, formatted as `YYYY-MM-DD` (e.g., 2513-01-01).

* **cURL Example**:

```bash
curl --url 'http://localhost:8080/books?author=%E0%B9%80%E0%B8%AD%E0%B9%8A%E0%B8%B0%20%E0%B8%88%E0%B8%B4%E0%B8%A3%E0%B8%B2%E0%B8%81%E0%B8%A3%E0%B8%93%E0%B9%8C'
```

***

### POST: /books
* **Description**: Create book from request body
* **Request Body**:
  * `author` (`String`, **Mandatory**): The author's name.
  * `title` (`String`, **Mandatory**): The book's title.
  * `publishDate` (`String`): The book's publish date in the Buddhist calendar, formatted as `YYYY-MM-DD` (e.g., 2513-01-01).

* **Response**:
  * Nothing only HTTP Status

* **cURL Example**:

```bash
curl --request POST \
      --url http://localhost:8080/books \
      --header 'Content-Type: application/json' \
      --data '{
      "author": "เอ๊ะ จิรากรณ์",
      "title": "บ่ฮู้เรื่องดอก",
      "publishedDate": "2558-09-15"
    }'
```
  
