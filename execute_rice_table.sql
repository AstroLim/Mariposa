USE ecom;

-- MySQL dump for rice product table
-- Table structure for storing rice product information

DROP TABLE IF EXISTS `riceproduct_data`;

CREATE TABLE `riceproduct_data` (
  `riceID` int NOT NULL AUTO_INCREMENT,
  `riceName` varchar(255) NOT NULL,
  `riceDescription` varchar(500) DEFAULT NULL,
  `riceQuantity` varchar(10) NOT NULL,
  `ricePrice` varchar(50) NOT NULL,
  PRIMARY KEY (`riceID`),
  CHECK (`riceQuantity` IN ('1kg', '5kg', '25kg'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `riceproduct_data`
--

LOCK TABLES `riceproduct_data` WRITE;
/*!40000 ALTER TABLE `riceproduct_data` DISABLE KEYS */;
INSERT INTO `riceproduct_data` (`riceName`, `riceDescription`, `riceQuantity`, `ricePrice`) VALUES
('Basmati 🌾 (Jordan Farms)', NULL, '1kg', '₱105'),
('Basmati 🌾 (Jordan Farms)', NULL, '5kg', '₱520'),
('Basmati 🌾 (Jordan Farms)', NULL, '25kg', '₱2,600'),
('Jasmine 🌸 (Thai/Local)', 'Fragrant, slightly sticky Thai rice. Your go-to for Thai fried rice or garlic rice.', '1kg', '₱72'),
('Jasmine 🌸 (Thai/Local)', 'Fragrant, slightly sticky Thai rice. Your go-to for Thai fried rice or garlic rice.', '5kg', '₱359'),
('Jasmine 🌸 (Thai/Local)', 'Fragrant, slightly sticky Thai rice. Your go-to for Thai fried rice or garlic rice.', '25kg', '₱1,599'),
('Sinandomeng 🇵🇭 (Familia)', 'The everyday white rice in every Filipino kitchen—soft, aromatic, budget-friendly.', '1kg', '₱68'),
('Sinandomeng 🇵🇭 (Familia)', 'The everyday white rice in every Filipino kitchen—soft, aromatic, budget-friendly.', '5kg', '₱339'),
('Sinandomeng 🇵🇭 (Familia)', 'The everyday white rice in every Filipino kitchen—soft, aromatic, budget-friendly.', '25kg', '₱1,595'),
('Arborio 🍚 (imported)', 'Creamy Italian short-grain. Turns into dreamy risotto in 20 minutes.', '1kg', '₱130'),
('Arborio 🍚 (imported)', 'Creamy Italian short-grain. Turns into dreamy risotto in 20 minutes.', '5kg', '₱645'),
('Arborio 🍚 (imported)', 'Creamy Italian short-grain. Turns into dreamy risotto in 20 minutes.', '25kg', '₱3,200'),
('Sushi Rice 🍣 (Kokuho)', 'Short, sticky Japanese rice. Roll it, shape it, love it.', '1kg', '₱115'),
('Sushi Rice 🍣 (Kokuho)', 'Short, sticky Japanese rice. Roll it, shape it, love it.', '5kg', '₱570'),
('Sushi Rice 🍣 (Kokuho)', 'Short, sticky Japanese rice. Roll it, shape it, love it.', '25kg', '₱2,850'),
('Brown Rice 🟤 (any variety)', 'Whole-grain version of any rice—chewy, nutty, extra fiber.', '1kg', '₱78'),
('Brown Rice 🟤 (any variety)', 'Whole-grain version of any rice—chewy, nutty, extra fiber.', '5kg', '₱389'),
('Brown Rice 🟤 (any variety)', 'Whole-grain version of any rice—chewy, nutty, extra fiber.', '25kg', '₱1,920'),
('Black Rice ⚫ (forbidden)', 'Purple when cooked, antioxidant bomb. Looks fancy in salads.', '1kg', '₱180'),
('Black Rice ⚫ (forbidden)', 'Purple when cooked, antioxidant bomb. Looks fancy in salads.', '5kg', '₱895'),
('Black Rice ⚫ (forbidden)', 'Purple when cooked, antioxidant bomb. Looks fancy in salads.', '25kg', '₱4,400'),
('Red Rice 🔴 (hearty)', 'Earthy, hearty cargo rice from Thailand or Camargue. Great with grilled fish.', '1kg', '₱140'),
('Red Rice 🔴 (hearty)', 'Earthy, hearty cargo rice from Thailand or Camargue. Great with grilled fish.', '5kg', '₱695'),
('Red Rice 🔴 (hearty)', 'Earthy, hearty cargo rice from Thailand or Camargue. Great with grilled fish.', '25kg', '₱3,450'),
('Dinorado 🇵🇭 (Equal/Harvester''s)', 'Premium, extra-fragrant local white rice—smells like pandan!', '1kg', '₱84'),
('Dinorado 🇵🇭 (Equal/Harvester''s)', 'Premium, extra-fragrant local white rice—smells like pandan!', '5kg', '₱414'),
('Dinorado 🇵🇭 (Equal/Harvester''s)', 'Premium, extra-fragrant local white rice—smells like pandan!', '25kg', '₱2,100'),
('Malagkit 🥮 (glutinous)', 'Super-sticky for champorado, biko, or mango sticky rice.', '1kg', '₱75'),
('Malagkit 🥮 (glutinous)', 'Super-sticky for champorado, biko, or mango sticky rice.', '5kg', '₱369'),
('Malagkit 🥮 (glutinous)', 'Super-sticky for champorado, biko, or mango sticky rice.', '25kg', '₱1,820'),
('Jasmine Brown 🌾🟤', 'Healthier take on jasmine—same floral scent, more chew.', '1kg', '₱82'),
('Jasmine Brown 🌾🟤', 'Healthier take on jasmine—same floral scent, more chew.', '5kg', '₱409'),
('Jasmine Brown 🌾🟤', 'Healthier take on jasmine—same floral scent, more chew.', '25kg', '₱2,020'),
('NSIC Rc222 🇵🇭 (PhilRice)', 'High-yield modern variety from PhilRice—farmers'' favorite for big harvests.', '1kg', '₱65'),
('NSIC Rc222 🇵🇭 (PhilRice)', 'High-yield modern variety from PhilRice—farmers'' favorite for big harvests.', '5kg', '₱324'),
('NSIC Rc222 🇵🇭 (PhilRice)', 'High-yield modern variety from PhilRice—farmers'' favorite for big harvests.', '25kg', '₱1,600');
/*!40000 ALTER TABLE `riceproduct_data` ENABLE KEYS */;
UNLOCK TABLES;
