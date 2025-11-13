USE ecom;

-- Example: Add image filenames to rice products
-- Images should be placed in: ecommerce/src/main/resources/static/images/rice/
-- Replace the filenames with your actual image filenames

-- Update Basmati rice
UPDATE riceproduct_data 
SET riceImageFile = 'rice/basmati.jpg' 
WHERE riceName = 'Basmati (Jordan Farms)';

-- Update Jasmine rice
UPDATE riceproduct_data 
SET riceImageFile = 'rice/jasmine.png' 
WHERE riceName = 'Jasmine (Thai/Local)';

-- Update Sinandomeng rice
UPDATE riceproduct_data 
SET riceImageFile = 'rice/sinandomeng.jpg' 
WHERE riceName = 'Sinandomeng (Familia)';

-- Update Arborio rice
UPDATE riceproduct_data 
SET riceImageFile = 'rice/arborio.jpg' 
WHERE riceName = 'Arborio (imported)';

-- Update Sushi Rice
UPDATE riceproduct_data 
SET riceImageFile = 'rice/sushi-rice.jpg' 
WHERE riceName = 'Sushi Rice (Kokuho)';

-- Update Brown Rice
UPDATE riceproduct_data 
SET riceImageFile = 'rice/brown-rice.jpg' 
WHERE riceName = 'Brown Rice (any variety)';

-- Update Black Rice
UPDATE riceproduct_data 
SET riceImageFile = 'rice/black-rice.jpg' 
WHERE riceName = 'Black Rice (forbidden)';

-- Update Red Rice
UPDATE riceproduct_data 
SET riceImageFile = 'rice/red-rice.jpg' 
WHERE riceName = 'Red Rice (hearty)';

-- Update Dinorado rice
UPDATE riceproduct_data 
SET riceImageFile = 'rice/dinorado.jpg' 
WHERE riceName = 'Dinorado (Equal/Harvester''s)';

-- Update Malagkit rice
UPDATE riceproduct_data 
SET riceImageFile = 'rice/malagkit.jpg' 
WHERE riceName = 'Malagkit (glutinous)';

-- Update Jasmine Brown rice
UPDATE riceproduct_data 
SET riceImageFile = 'rice/jasmine-brown.jpg' 
WHERE riceName = 'Jasmine Brown';

-- Update NSIC Rc222 rice
UPDATE riceproduct_data 
SET riceImageFile = 'rice/nsic-rc222.jpg' 
WHERE riceName = 'NSIC Rc222 (PhilRice)';

-- Verify the updates
SELECT riceID, riceName, riceQuantity, riceImageFile 
FROM riceproduct_data 
ORDER BY riceName, riceQuantity;

