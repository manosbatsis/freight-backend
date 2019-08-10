INSERT INTO `BulkType` (`displayName`, `type`)
VALUES
('Aluminium', 'ALUMINIUM'),
('Construction Sand', 'CONSTRUCTION_SAND'),
('Quartz Sand', 'QUARTZ_SAND'),
('Asphalt', 'ASPHALT'),
('Bauxite', 'BAUXITE'),
('Sulphur', 'SULPHUR'),
('Iron Ore', 'IRON_ORE'),
('Nickel Ore', 'NICKEL_ORE'),
('Cement', 'CEMENT'),
('Gypsum', 'GYPSUM'),
('Granite', 'GRANITE'),
('Boulder Stone', 'BOULDER_STONE'),
('Gravel', 'GRAVEL'),
('Split Stone', 'SPLIT_STONE'),
('Limestone', 'LIMESTONE'),
('Manganese', 'MANGANESE'),
('Copper', 'COPPER'),
('Bronze', 'BRONZE'),
('Tin', 'TIN'),
('Clay', 'CLAY'),
('Timber', 'TIMBER'),
('Construction Equipment', 'CONSTRUCTION_EQUIPMENT'),
('Gold Ore', 'GOLD_ORE'),
('Silver Ore', 'SILVER_ORE'),
('Granite (Boulder)', 'GRANITE_BOULDER'),
('Palm Kernel Shell', 'PALM_KERNEL_SHELL'),
('Fertilizer', 'FERTILIZER'),
('Container', 'CONTAINER'),
('Heavy Machinery', 'HEAVY_MACHINERY'),
('Trucks', 'TRUCKS'),
('Cranes', 'CRANES'),
('Foundation Pile', 'FOUNDATION_PILE'),
('Precast Construction', 'PRECAST_CONSTRUCTION');

INSERT INTO `ShipType` (`displayName`, `type`)
VALUES
('TB', 'Tug Boat'),
('BG', 'Barge'),
('LCT', 'LCT'),
('SPOB', 'Self Propelled Oil Barge'),
('SPB', 'Self Propelled Barge'),
('TANKER', 'Tanker'),
('CS', 'Container Ship'),
('MV', 'Bulk Carrier Mother Vessel'),
('DCS', 'Dry Cargo Ship'),
('RORO', 'Roll On Roll Off Ferry');

INSERT INTO `Incoterms` (`displayName`, `type`)
VALUES
('FOB', 'Free On Board'),
('CFR', 'Cost And Freight'),
('CIF', 'Cost, Insurance and Freight'),
('FAS', 'Free Alongside Ship'),
('EXW', 'Ex Works'),
('FCA', 'Free Carrier'),
('CPT', 'Carriage Paid To'),
('CIP', 'Carriage And Insurance Paid To'),
('DAT', 'Delivered At Terminal'),
('DAP', 'Delivered At Place'),
('DDP', 'Delivered Duty Paid');

INSERT INTO `Authentication` (`guid`, `email`, `phone`, `password`, `verificationCode`, `verificationExpiry`, `status`, `token`, `created`, `lastModified`)
VALUES
	('03b3dab9-3bde-4e3b-a1e8-4573aad4665b', 'toshikijahja@gmail.com', NULL, '$2a$12$3RejsWRN97Ow6PEBEwpId.ZoL8qYLWIMBLv8YL1VN64SB8OL268Za', '1846', '2019-07-08 02:45:17', 'VERIFIED', 'b86937ab-1a3d-4ff8-9b71-c994ca50bd73', '2019-07-08 01:45:17', '2019-07-07 18:46:58');

INSERT INTO `User` (`id`, `guid`, `username`, `email`, `phone`, `companyId`, `type`, `status`, `created`, `lastModified`)
VALUES
	(1, '03b3dab9-3bde-4e3b-a1e8-4573aad4665b', NULL, 'toshikijahja@gmail.com', NULL, NULL, 'CUSTOMER', 'ACTIVE', '2019-07-08 01:46:59', '2019-07-08 01:46:59');

INSERT INTO `User` (`id`, `guid`, `username`, `email`, `phone`, `companyId`, `type`, `status`, `created`, `lastModified`)
VALUES
	(2, '03b3dab5-3bd3-4e3b-a1e8-4573fad4665b', NULL, 'toshikijahja@gmail.com', NULL, 1, 'TRANSPORTER', 'ACTIVE', '2019-07-15 21:31:51', '2019-07-15 21:32:22');

INSERT INTO `Company` (`id`, `name`, `type`, `status`, `created`, `lastModified`)
VALUES
	(1, 'PT Perahu Abadi', 'TRANSPORTER', 'UNVERIFIED', '2019-07-15 21:31:38', '2019-07-15 21:31:41'),
	(2, 'PT Laut Shipper', 'TRANSPORTER', 'UNVERIFIED', '2019-07-15 21:31:38', '2019-08-09 22:28:05');

INSERT INTO `Ship` (`id`, `name`, `companyId`, `shipTypeId`, `yearBuilt`, `grossTonnage`, `status`, `created`, `lastModified`)
VALUES
	(1, 'Perahu Maju Mundur', 1, 2, 1935, 1000, 'ACTIVE', '2019-07-15 21:33:39', '2019-08-09 22:27:45'),
	(2, 'Anchor Sea', 2, 1, 1927, 1500, 'ACTIVE', '2019-07-15 21:33:39', '2019-08-09 22:28:45'),
	(3, 'Golden Liverpool', 2, 2, 1980, 15000, 'ACTIVE', '2019-07-15 21:33:39', '2019-08-09 22:35:08'),
	(4, 'Ombak Pancasila', 1, 1, 1945, 8600, 'ACTIVE', '2019-07-15 21:33:39', '2019-08-09 22:38:31'),
	(5, 'Batavia', 2, 2, 1999, 6500, 'ACTIVE', '2019-07-15 21:33:39', '2019-08-09 22:41:20'),
	(6, 'Sunda Kelapa', 1, 1, 1955, 4000, 'ACTIVE', '2019-07-15 21:33:39', '2019-08-09 22:44:06');

INSERT INTO `ShipCargoType` (`shipId`, `cargoTypeId`, `created`, `lastModified`)
VALUES
	(6, 3, '2019-08-09 22:49:02', '2019-08-09 22:49:02'),
	(5, 3, '2019-08-09 22:49:02', '2019-08-09 22:49:02'),
	(4, 3, '2019-08-09 22:49:02', '2019-08-09 22:49:02'),
	(3, 3, '2019-08-09 22:49:02', '2019-08-09 22:49:02'),
	(2, 3, '2019-08-09 22:49:02', '2019-08-09 22:49:02'),
	(1, 3, '2019-08-09 22:49:02', '2019-08-09 22:49:02');


INSERT INTO `Location` (`id`, `externalId`, `mainName`, `secondaryName`, `lat`, `lon`, `route`, `locality`, `village`, `subdistrict`, `city`, `province`, `country`, `created`, `lastModified`)
VALUES
	(1, 'ca9de7f3845c17bb46c20f6217e01bc01bd14123', 'Pelabuhan Tanjung Priok', 'Tanjung Priok, Kota Jakarta Utara, Daerah Khusus Ibukota Jakarta, Indonesia', -6.09681900, 106.88245200, NULL, NULL, 'Tanjung Priok', 'Tanjung Priok', 'Kota Jakarta Utara', 'Daerah Khusus Ibukota Jakarta', 'Indonesia', '2019-08-10 05:13:43', '2019-08-10 05:13:43'),
	(2, 'a1da6429f99a6e5125fac74d986172186b2034d9', 'Pelabuhan Labuan Bajo - Flores NTT', 'Jalan Soekarno Hatta, Labuan Bajo, Kabupaten Manggarai Barat, Nusa Tenggara Timur, Indonesia', -8.49296520, 119.87688520, 'Jalan Soekarno Hatta', NULL, 'Labuan Bajo', 'Komodo', 'Kabupaten Manggarai Barat', 'Nusa Tenggara Timur', 'Indonesia', '2019-08-10 05:13:43', '2019-08-10 05:13:43');

INSERT INTO `Cargo` (`id`, `contractId`, `shipmentId`, `userId`, `status`, `cargoTypeId`, `quantity`, `originLocationId`, `destinationLocationId`, `departure`, `weight`, `weightUnit`, `volume`, `volumeUnit`, `length`, `width`, `height`, `dimensionUnit`, `containerTypeId`, `bulkTypeId`, `expiry`, `created`, `lastModified`)
VALUES
	(1, NULL, NULL, 1, 'INQUIRY', 3, 1, 1, 2, '2019-10-20', 3000, 'TON', 3041, 'M3', NULL, NULL, NULL, 'NOT_USED', NULL, 4, NULL, '2019-07-15 22:08:05', '2019-08-09 22:29:08'),
	(2, 6, 1, 1, 'RESERVED', 3, 1, 1, 2, '2019-08-15', 7000, 'TON', 5020, 'M3', NULL, NULL, NULL, 'NOT_USED', NULL, 3, NULL, '2019-07-15 22:08:05', '2019-08-09 22:23:03'),
	(3, 9, 2, 1, 'RESERVED', 3, 1, 1, 2, '2019-07-01', 5000, 'TON', NULL, 'NOT_USED', NULL, NULL, NULL, 'NOT_USED', NULL, 10, NULL, '2019-07-15 22:08:05', '2019-08-09 22:29:34'),
	(4, 10, 3, 1, 'RESERVED', 3, 1, 1, 2, '2019-07-15', NULL, 'NOT_USED', 10000, 'M3', NULL, NULL, NULL, 'NOT_USED', NULL, 7, NULL, '2019-07-15 22:08:05', '2019-08-09 22:33:17'),
	(5, 11, 4, 1, 'RESERVED', 3, 1, 1, 2, '2019-07-01', 3500, 'TON', 2000, 'M3', NULL, NULL, NULL, 'NOT_USED', NULL, 5, NULL, '2019-07-15 22:08:05', '2019-08-09 22:36:09'),
	(6, 12, 5, 1, 'RESERVED', 3, 1, 1, 2, '2019-06-12', 5000, 'TON', NULL, 'NOT_USED', NULL, NULL, NULL, 'NOT_USED', NULL, 11, NULL, '2019-07-15 22:08:05', '2019-08-09 22:39:24'),
	(7, 13, 6, 1, 'RESERVED', 3, 1, 1, 2, '2019-06-12', NULL, 'NOT_USED', 5000, 'M3', NULL, NULL, NULL, 'NOT_USED', NULL, 14, NULL, '2019-07-15 22:08:05', '2019-08-09 22:42:18');

INSERT INTO `CargoContract` (`id`, `cargoId`, `contractId`, `customerId`, `transporterId`, `status`, `expiry`, `created`, `lastModified`)
VALUES
	(1, 1, 1, 1, 2, 'TRANSPORTER_OFFERED', NULL, '2019-07-15 22:08:48', '2019-07-15 22:08:48'),
	(2, 1, 2, 1, 2, 'CUSTOMER_NEGOTIATE', NULL, '2019-07-15 22:08:48', '2019-08-09 22:18:03'),
	(3, 1, 3, 1, 2, 'CUSTOMER_DECLINED', NULL, '2019-07-15 22:08:48', '2019-08-09 22:18:16'),
	(4, 1, 4, 1, 2, 'CUSTOMER_EXPIRED', NULL, '2019-07-15 22:08:48', '2019-08-09 22:18:32'),
	(5, 1, 5, 1, 2, 'TRANSPORTER_EXPIRED', NULL, '2019-07-15 22:08:48', '2019-08-09 22:18:41'),
	(6, 2, 6, 1, 2, 'CUSTOMER_ACCEPTED', NULL, '2019-07-15 22:08:48', '2019-08-09 22:23:22'),
	(7, 2, 7, 1, 2, 'CUSTOMER_DECLINED', NULL, '2019-07-15 22:08:48', '2019-08-09 22:24:20'),
	(8, 2, 8, 1, 2, 'CUSTOMER_EXPIRED', NULL, '2019-07-15 22:08:48', '2019-08-09 22:26:19'),
	(9, 3, 9, 1, 2, 'CUSTOMER_ACCEPTED', NULL, '2019-08-09 22:29:42', '2019-08-09 22:29:50'),
	(10, 4, 10, 1, 2, 'CUSTOMER_ACCEPTED', NULL, '2019-08-09 22:29:42', '2019-08-09 22:33:36'),
	(11, 5, 11, 1, 2, 'CUSTOMER_ACCEPTED', NULL, '2019-08-09 22:29:42', '2019-08-09 22:36:19'),
	(12, 6, 12, 1, 2, 'CUSTOMER_ACCEPTED', NULL, '2019-08-09 22:29:42', '2019-08-09 22:39:31'),
	(13, 7, 13, 1, 2, 'CUSTOMER_ACCEPTED', NULL, '2019-08-09 22:29:42', '2019-08-09 22:42:26');

INSERT INTO `CargoShipment` (`cargoId`, `shipmentId`, `created`, `lastModified`)
VALUES
	(2, 1, '2019-08-09 22:45:37', '2019-08-09 22:45:37'),
	(3, 2, '2019-08-09 22:45:37', '2019-08-09 22:45:37'),
	(4, 3, '2019-08-09 22:45:37', '2019-08-09 22:45:37'),
	(5, 4, '2019-08-09 22:45:37', '2019-08-09 22:45:37'),
	(6, 5, '2019-08-09 22:45:37', '2019-08-09 22:45:37'),
	(7, 6, '2019-08-09 22:45:37', '2019-08-09 22:45:37');

INSERT INTO `Contract` (`id`, `shipId`, `shipmentId`, `userId`, `price`, `priceUnit`, `currency`, `payoutId`, `originLocationId`, `destinationLocationId`, `startDate`, `endDate`, `charterType`, `loadingType`, `incotermsId`, `cargoSender`, `cargoSenderOther`, `cargoReceiver`, `cargoReceiverOther`, `cargoInsurance`, `shipInsurance`, `shipAgentId`, `miscellaneousFee`, `demurrage`, `demurrageUnit`, `loadingLaytime`, `dischargeLaytime`, `totalLaytime`, `laytimeUnit`, `despatchType`, `layDaysType`, `created`, `lastModified`)
VALUES
	(1, 1, NULL, 2, 275000000.00, 'NOT_USED', 'RP', 2, 1, 2, '2019-10-20', '2019-11-01', 'LUMPSUM', 'FIOST', 2, 'AS_ORDER', NULL, 'OTHER_PARTY', 'PT. Jaya Selalu', 'TRANSPORTER', 'CUSTOMER', 1, 'TRANSPORTER', NULL, 'NOT_USED', NULL, NULL, NULL, 'NOT_USED', 'NOT_USED', 'SHINC', '2019-07-15 21:33:27', '2019-08-09 22:22:40'),
	(2, 2, NULL, 2, 15000.00, 'TON', 'RP', 1, 1, 2, '2019-10-19', '2019-11-20', 'CHARTER', 'FILO', 3, 'SHIP_OWNER', NULL, 'AS_ORDER', NULL, 'CUSTOMER', 'TRANSPORTER', 2, 'CUSTOMER', 5.00, 'DAY', NULL, NULL, 2, 'DAY', 'DHALFD', 'SHEX', '2019-07-15 21:33:27', '2019-08-09 22:45:08'),
	(3, 1, NULL, 2, 25000.00, 'M3', 'RP', 2, 1, 2, '2019-10-20', '2019-10-29', 'LUMPSUM', 'FIOST', 4, 'CARGO_OWNER', NULL, 'AS_ORDER', NULL, 'TRANSPORTER', 'TRANSPORTER', 1, 'TRANSPORTER', 3.00, 'DAY', 1, 2, 3, 'HOUR', 'DLO', 'NOT_USED', '2019-07-15 21:33:27', '2019-08-09 22:22:25'),
	(4, 3, NULL, 2, 240000000.00, 'NOT_USED', 'RP', 1, 1, 2, '2019-10-17', '2019-11-25', 'CHARTER', 'LIFO', 5, 'CARGO_OWNER', 'PT. Kargo Service Jaya', 'OTHER_PARTY', 'PT. Maju Terus', 'NONE', 'NONE', 2, 'CUSTOMER', 2.00, 'WEEK', NULL, NULL, NULL, 'NOT_USED', 'FD', 'SHINC', '2019-07-15 21:33:27', '2019-08-09 22:45:10'),
	(5, 1, NULL, 2, 300000000.00, 'NOT_USED', 'RP', 2, 1, 2, '2019-10-20', '2019-11-20', 'LUMPSUM', 'FIOST', 6, 'AS_ORDER', NULL, 'AS_ORDER', NULL, 'CUSTOMER', 'CUSTOMER', 1, 'TRANSPORTER', NULL, 'NOT_USED', NULL, NULL, NULL, 'NOT_USED', 'NOT_USED', 'NOT_USED', '2019-07-15 21:33:27', '2019-08-09 22:22:28'),
	(6, 1, 1, 2, 7000.00, 'M3', 'RP', 1, 1, 2, '2019-08-15', '2019-08-25', 'CHARTER', 'FIOST', 2, 'SHIP_OWNER', NULL, 'OTHER_PARTY', 'PT. Sehat Abadi Jaya', 'CUSTOMER', 'CUSTOMER', 2, 'TRANSPORTER', NULL, 'NOT_USED', NULL, NULL, NULL, 'NOT_USED', 'NOT_USED', 'SHINC', '2019-07-15 21:33:27', '2019-08-09 22:18:59'),
	(7, 4, NULL, 2, 6000.00, 'M3', 'RP', 2, 1, 2, '2019-08-14', '2019-09-25', 'CHARTER', 'LIFO', 10, 'OTHER_PARTY', 'PT. Makmur Sentosa', 'OTHER_PARTY', 'PT. Sehat Abadi Jaya', 'TRANSPORTER', 'CUSTOMER', 1, 'TRANSPORTER', 1.00, 'WEEK', 1, 2, 3, 'DAY', 'DDO', 'NOT_USED', '2019-07-15 21:33:27', '2019-08-09 22:45:12'),
	(8, 5, NULL, 2, 318000000.00, 'NOT_USED', 'RP', 1, 1, 2, '2019-08-15', '2019-09-01', 'LUMPSUM', 'LIFO', 10, 'OTHER_PARTY', 'PT. Makmur Sentosa', 'OTHER_PARTY', 'PT. Sehat Abadi Jaya', 'TRANSPORTER', 'CUSTOMER', 1, 'TRANSPORTER', 1.00, 'WEEK', 1, 2, 3, 'DAY', 'NOT_USED', 'NOT_USED', '2019-07-15 21:33:27', '2019-08-09 22:45:13'),
	(9, 2, 2, 2, 15000.00, 'TON', 'RP', 2, 1, 2, '2019-07-01', '2019-08-01', 'CHARTER', 'FIOST', 5, 'AS_ORDER', NULL, 'AS_ORDER', NULL, 'TRANSPORTER', 'TRANSPORTER', 1, 'CUSTOMER', NULL, 'NOT_USED', NULL, NULL, 2, 'WEEK', 'NOT_USED', 'NOT_USED', '2019-07-15 21:33:27', '2019-08-09 22:45:01'),
	(10, 3, 3, 2, 750000000.00, 'NOT_USED', 'RP', 1, 1, 2, '2019-07-01', '2019-10-01', 'LUMPSUM', 'LIFO', 3, 'SHIP_OWNER', NULL, 'SHIP_OWNER', NULL, 'CUSTOMER', 'NONE', 1, 'CUSTOMER', NULL, 'NOT_USED', NULL, NULL, NULL, 'NOT_USED', 'DHALFD', 'SHINC', '2019-07-15 21:33:27', '2019-08-09 22:44:58'),
	(11, 4, 4, 2, 400000000.00, 'NOT_USED', 'RP', 2, 1, 2, '2019-07-01', '2019-07-08', 'CHARTER', 'FIOST', 10, 'AS_ORDER', NULL, 'OTHER_PARTY', 'PT. Aman Jaya', 'CUSTOMER', 'TRANSPORTER', 2, 'TRANSPORTER', 1.00, 'DAY', NULL, NULL, 30, 'HOUR', 'NOT_USED', 'NOT_USED', '2019-07-15 21:33:27', '2019-08-09 22:44:57'),
	(12, 5, 5, 2, 5000.00, 'TON', 'RP', 1, 1, 2, '2019-06-15', '2019-06-25', 'LUMPSUM', 'FILO', 11, 'AS_ORDER', NULL, 'AS_ORDER', NULL, 'NONE', 'NONE', 1, 'CUSTOMER', NULL, 'NOT_USED', 2, 2, 4, 'DAY', 'NOT_USED', 'SHEX', '2019-07-15 21:33:27', '2019-08-09 22:44:55'),
	(13, 6, 6, 2, 5000.00, 'M3', 'RP', 2, 1, 2, '2019-06-15', '2019-06-20', 'CHARTER', 'FILO', 12, 'OTHER_PARTY', 'PT. Makmur Sentosa', 'OTHER_PARTY', 'PT. Sehat Abadi Jaya', 'CUSTOMER', 'CUSTOMER', 2, 'CUSTOMER', 1.00, 'WEEK', NULL, NULL, 2, 'WEEK', 'DHALFD', 'SHEX', '2019-07-15 21:33:27', '2019-08-09 22:44:54');

INSERT INTO `Shipment` (`id`, `shipId`, `originLocationId`, `destinationLocationId`, `departure`, `arrival`, `status`, `shipStatus`, `created`, `lastModified`)
VALUES
	(1, 1, 1, 2, '2019-08-15', '2019-08-21', 'UPCOMING', 'DOCKING_ORIGIN', '2019-08-09 22:23:37', '2019-08-09 22:23:56'),
	(2, 2, 1, 2, '2019-07-01', '2019-07-29', 'LIVE', 'LOADING', '2019-08-09 22:23:37', '2019-08-09 22:32:18'),
	(3, 3, 1, 2, '2019-07-10', '2019-07-15', 'LIVE', 'AT_SEA', '2019-08-09 22:23:37', '2019-08-09 22:35:26'),
	(4, 4, 1, 2, '2019-07-01', '2019-07-06', 'LIVE', 'DISCHARGE', '2019-08-09 22:23:37', '2019-08-09 22:38:45'),
	(5, 5, 1, 2, '2019-07-01', '2019-07-06', 'LIVE', 'DOCKING_DESTINATION', '2019-08-09 22:23:37', '2019-08-09 22:53:07'),
	(6, 6, 1, 2, '2019-06-01', '2019-06-15', 'COMPLETED', 'DISCHARGE', '2019-08-09 22:23:37', '2019-08-09 22:44:23');
