USE ecom;

-- MySQL dump for checkout orders table
-- Table structure for storing checkout orders

DROP TABLE IF EXISTS `checkout_orders`;

CREATE TABLE `checkout_orders` (
  `checkoutID` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `checkoutedItems` TEXT DEFAULT NULL,
  `status` varchar(50) NOT NULL DEFAULT 'pending',
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  `lastUpdated` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`checkoutID`),
  KEY `idx_email` (`email`),
  KEY `idx_status` (`status`),
  KEY `idx_email_status` (`email`, `status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

