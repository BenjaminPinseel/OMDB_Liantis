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