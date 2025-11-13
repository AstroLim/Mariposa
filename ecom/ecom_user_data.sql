-- MySQL dump for user accounts table
-- Table structure for storing registered users

DROP TABLE IF EXISTS `user_data`;

CREATE TABLE `user_data` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL UNIQUE,
  `email` varchar(255) NOT NULL UNIQUE,
  `password` varchar(255) NOT NULL,
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_updated` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Sample test user (password: test123)
-- In production, passwords should be hashed
INSERT INTO `user_data` (`username`, `email`, `password`) VALUES
('testuser', 'test@example.com', 'test123');

