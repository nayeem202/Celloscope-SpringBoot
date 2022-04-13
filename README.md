# CURL commands for testing api



  curl --location --request POST 'http://localhost:8080/save_user' \
  --header 'Content-Type: application/json' \
  --data-raw '{

      "userId" : "888",
      "password" : "4321",
      "mobile" : "5656556565"
      }'
      

      curl --location --request POST 'http://localhost:8080/login' \
  --header 'Content-Type: application/json' \
  --data-raw '{

      "userId" : "888",
      "password" : "4321"

      }'
     
      curl --location --request POST 'http://localhost:8080/reset_password' \
  --header 'Content-Type: application/json' \
  --data-raw '{

      "userId" : "888",
      "password" : "4321"

      }'
     
     
      curl --location --request PUT 'http://localhost:8080/update_password' \
  --header 'Content-Type: application/json' \
  --data-raw '{

      "userId" : "1",
      "password" : "123"

      }'
