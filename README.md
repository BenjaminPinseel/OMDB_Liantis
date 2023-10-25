# MovieController

The MovieController class is responsible for handling HTTP requests related to movie data.

### Endpoints

    GET /movie/id/{id}: Retrieves movie information by its unique identifier.
    GET /movie/title/{title}: Retrieves movie information based on its title and optional parameters like type, year, plot, and return type.
    GET /movie/: Retrieves a list of movies based on specified parameters like title, type, year, and page number.

### MovieService

The MovieController class interacts with the MovieService to retrieve and manipulate movie data.

### Dependencies

    MovieService: Handles business logic related to movie data.

# UserController

The UserController class is responsible for managing user data through various HTTP requests.

### Endpoints

    GET /user: Retrieves a paginated list of users.
    GET /user/{id}: Retrieves a user by its unique identifier.
    POST /user: Creates a new user.
    PUT /user/{id}: Updates an existing user.
    DELETE /user/{id}: Deletes a user.

### UserService

The UserController class interacts with the UserService to perform CRUD operations on user data.

### Dependencies

    UserService: Manages user-related business logic and data operations.
    UserMapper: Facilitates the mapping between user-related data transfer objects (DTOs) and entities.
    EncryptionUtils: Provides utilities for encrypting sensitive user data.

# WatchlistController

The WatchlistController class handles HTTP requests related to managing user watchlists.

### Endpoints

    GET /watchlist/user/{id}: Retrieves all watchlists associated with a specific user.
    GET /watchlist/{id}: Retrieves a specific watchlist by its unique identifier.
    POST /watchlist: Creates a new watchlist.
    PUT /watchlist: Updates an existing watchlist.
    POST /watchlist/{watchlistId}/movie/{movieId}: Adds a movie to a specific watchlist.
    DELETE /watchlist/{watchlistId}/movie/{movieId}: Removes a movie from a specific watchlist.
    DELETE /watchlist/{id}: Deletes a watchlist.

### WatchlistService

The WatchlistController class interacts with the WatchlistService to manage watchlist-related operations.

### Dependencies

    WatchlistService: Handles business logic related to watchlists.
    WatchlistMapper: Facilitates the mapping between watchlist-related DTOs and entities.

# Future Functionality

In the future, consider implementing the following features to enhance the application:

### 1.User Authentication and Authorization

Implement user authentication and authorization using OAuth or JWT tokens to ensure secure access to user-specific data and functionalities.

### 2.User Profile Customization

Allow users to customize their profiles by adding personal details, profile pictures, and preferences, providing a more personalized experience.

### 3.User Reviews and Ratings

Enable users to rate and review movies, creating a community-driven platform where users can share their opinions and experiences.

### 4.Recommendation System

Develop a recommendation system that suggests movies to users based on their watch history, preferences, and behavior, providing personalized movie recommendations.

### 5.Multi-Language Support

Provide multi-language support to cater to a diverse user base, allowing users to access the application in their preferred languages.

### 6.Watchlist Sharing
Enable users to share their watchlists with others, fostering a collaborative movie-watching experience among friends and family.

# Extra information

1. Security.crypto has been used to encrypt the API key. It is then decrypted every time an API call needs to be sent to omdbAPI.
Currently the encrypt method is still in place, for a possibility to re-encrypt the APIkey periodically using  new password / salt.
2. Using security.crypto enables a plethora of protection settings. This is why the ApplicationNoSecurity disabled this security for this project.
Eventually when user authentication is added, this would be removed.
3. Currently the userId is added in the header of requests, to have some form of security at the moment.
4. WatchlistControllerIntegrationTest was not added because of lack of time.
