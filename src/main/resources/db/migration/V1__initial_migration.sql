-- create_users_table.sql
CREATE TABLE IF NOT EXISTS omdb.app_user (
    id VARCHAR(255) PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL
);

-- create_watchlists_table.sql
CREATE TABLE IF NOT EXISTS omdb.watchlists (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES omdb.app_user(id) ON DELETE CASCADE
);
