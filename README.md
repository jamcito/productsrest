# Products REST API

## Build & Deployment

Build relies on docker and maven. To build, navigate to the repository directory and run:
```
docker build --tag productsrest . 
```
This will fetch all the required images and dependencies, and then trigger the application build and tests.

To deploy the image, simply run the following comand:
```
docker run --publish 8080:8080 productsrest
```
This will run the application and expose the API on port `8080`.

## API

### GET /products

GET All products in the system.
```
curl --request GET \
  --url http://127.0.0.1:8080/products \
  --header 'User-Agent: insomnia/8.2.0'
```

### GET /products/{id}
GET product by ID.
```
curl --request GET \
  --url http://127.0.0.1:8080/products/3fdc495c-bd42-411f-86f6-10424e08c31e \
  --header 'User-Agent: insomnia/8.2.0'
```

### DELETE /products/{id}
DELETE a product by ID.
```
curl --request DELETE \
  --url http://127.0.0.1:8080/products/f6caf768-a5b0-4f6e-a228-bca49e49bb0a \
  --header 'User-Agent: insomnia/8.2.0'
```

### POST /products
POST a new product.
```
curl --request POST \
  --url http://127.0.0.1:8080/products \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/8.2.0' \
  --data '{
	"count": 17,
	"basePriceCents": 1323
}'
```

### PUT /products/{id}
PUT an update to an existing produt by ID.
```
curl --request PUT \
  --url http://127.0.0.1:8080/products/f6caf768-a5b0-4f6e-a228-bca49e49bb0a \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/8.2.0' \
  --data '{
	"count": 1234,
	"basePriceCents": 5678
}'
```

### GET /products/{id}/quote
GET a price quote of a product.
```
curl --request GET \
  --url 'http://127.0.0.1:8080/products/fc2f2e55-4295-42ef-934f-bd947d96b550/quote?count=2' \
  --header 'User-Agent: insomnia/8.2.0'
```

### PUT /products/{id}/percentage-discount
PUT a percentage discount on top of an existing product by ID.
```
curl --request PUT \
  --url http://127.0.0.1:8080/products/fc2f2e55-4295-42ef-934f-bd947d96b550/percentage-discount \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/8.2.0' \
  --data 15
```

### PUT /products/{id}/count-discount
PUT a count based discount on top of an existing product by ID.
```
curl --request PUT \
  --url http://127.0.0.1:8080/products/24907f68-0a8c-4d80-a585-4e23ff6b1311/count-discount \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/8.2.0' \
  --data 30
```