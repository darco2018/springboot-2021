package com.example.demo3jpa.service;

import com.example.demo3jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// define types for JpaRepository
public interface UserRepository extends JpaRepository<User, Long> {

    // you can add your own custom queries using contains, and, or, etc. (methods of query creation)
    User findByAgeAndName(int age, String name);
}
/*
Compare with SPRING DATA REST:

@RepositoryRestResource(collectionResourceRel = "todos", path = "todos")
public interface TodoRepository extends PagingAndSortingRepository<Todo, Long> {
        // custom methods possible:
        List<Todo> findByUser(@Param("user") String user);
 }
-----------------------
        POST
URL : http://localhost:8080/todos
Use Header : Content-Type:application/json
Request Content

{
  "user": "Jill",
  "desc": "Learn Hibernate",
  "done": false
}

-----------------
GET
URI - http://localhost:8080/todos
Response

{
  "_embedded" : {
    "todos" : [ {
      "user" : "Jill",
      "desc" : "Learn Hibernate",
      "done" : false,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/todos/1"
        },
        "todo" : {
          "href" : "http://localhost:8080/todos/1"
        }
      }
    } ]
  },
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/todos"
    },
    "profile" : {
      "href" : "http://localhost:8080/profile/todos"
    },
    "search" : {
      "href" : "http://localhost:8080/todos/search"
    }
  },
  "page" : {
    "size" : 20,
    "totalElements" : 1,
    "totalPages" : 1,
    "number" : 0
  }
}
*/
