USE ecom;

-- Add image column to riceproduct_data table
ALTER TABLE `riceproduct_data` 
ADD COLUMN `riceImageFile` VARCHAR(255) NULL AFTER `ricePrice`;

-- Verify the column was added
DESCRIBE `riceproduct_data`;

