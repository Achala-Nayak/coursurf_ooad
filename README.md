
# CourSurf


# CourSurf API Documentation

This documentation outlines the APIs available in the CourseSurf application.

## Base URL

All endpoints described below are relative to the base URL of your application.

```
http://your-domain.com
```

## Endpoints

### 1. Fetch Filtered Data

**Endpoint:** `GET /api/filter`

This endpoint fetches course data from the database based on optional filter parameters.

#### Parameters

- `searchQuery` (optional): A string to search for courses by title.
- `provider` (optional): A string to filter courses by provider.
- `rating` (optional): An integer to filter courses by minimum ratings.
- `limit` (optional, default: 10): An integer specifying the maximum number of results to return.

#### Example

```http
GET /api/filter?searchQuery=java&provider=Udemy&rating=4&limit=5
```

### 2. Get Trending Data

**Endpoint:** `GET /api/getTrending`

This endpoint retrieves trending courses based on click counts.

#### Parameters

- `limit` (optional, default: 10): An integer specifying the maximum number of trending courses to return.

#### Example

```http
GET /api/getTrending?limit=5
```

### 3. Increment Click Count

**Endpoint:** `GET /api/clicked`

This endpoint increments the click count for a specific course.

#### Parameters

- `title` (required): The title of the course for which the click count should be incremented.

#### Example

```http
GET /api/clicked?title=Java%20Masterclass
```

## Response Codes

- `200 OK`: Returned on success.
- `400 Bad Request`: Returned if the request is malformed.
- `404 Not Found`: Returned if the requested resource is not found.
- `500 Internal Server Error`: Returned if there is an internal server error.
