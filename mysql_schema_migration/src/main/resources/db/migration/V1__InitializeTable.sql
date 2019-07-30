CREATE TABLE `User` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `guid` varchar(36) NOT NULL,
  `username` varchar(20) DEFAULT NULL,
  `email` varchar(512) DEFAULT NULL,
  `phone` int(36) unsigned DEFAULT NULL,
  `companyId` int(11) unsigned DEFAULT NULL,
  `type` enum('CUSTOMER', 'TRANSPORTER', 'NOT_KNOWN') CHARACTER SET utf8 NOT NULL DEFAULT 'NOT_KNOWN',
  `status` enum('ACTIVE', 'INACTIVE') CHARACTER SET utf8 NOT NULL DEFAULT 'ACTIVE',
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `guid` (`guid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Authentication` (
  `guid` varchar(36) NOT NULL,
  `email` varchar(512) DEFAULT NULL,
  `phone` int(36) unsigned DEFAULT NULL,
  `password` varchar(1024) DEFAULT NULL,
  `verificationCode` varchar(4) DEFAULT NULL,
  `verificationExpiry` timestamp NULL DEFAULT NULL,
  `status` enum('VERIFIED', 'UNVERIFIED') CHARACTER SET utf8 NOT NULL DEFAULT 'UNVERIFIED',
  `token` varchar(1024) DEFAULT NULL,
  `created` timestamp DEFAULT CURRENT_TIMESTAMP,
  `lastModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`guid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Company` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(512) DEFAULT NULL,
  `type` enum('CUSTOMER', 'TRANSPORTER') CHARACTER SET utf8 NOT NULL DEFAULT 'CUSTOMER',
  `status` enum('VERIFIED', 'UNVERIFIED') CHARACTER SET utf8 NOT NULL DEFAULT 'UNVERIFIED',
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Identification` (
  `typeId` int(11) unsigned NOT NULL,
  `type` enum('INDIVIDUAL', 'COMPANY') CHARACTER SET utf8 NOT NULL DEFAULT 'COMPANY',
  `documentType` enum('NPWP') CHARACTER SET utf8 NOT NULL DEFAULT 'NPWP',
  `documentId` varchar(512) DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`typeId`, `type`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Ship` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(512) DEFAULT NULL,
  `companyId` int(11) unsigned DEFAULT NULL,
  `shipTypeId` int(11) unsigned DEFAULT NULL,
  `yearBuilt` int(4) unsigned DEFAULT NULL,
  `grossTonnage` int(11) unsigned DEFAULT NULL,
  `status` enum('ACTIVE', 'INACTIVE') CHARACTER SET utf8 NOT NULL DEFAULT 'ACTIVE',
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ShipType` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `displayName` varchar(256) DEFAULT NULL,
  `type` varchar(128) DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `CargoType` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `displayName` varchar(256) DEFAULT NULL,
  `type` varchar(128) DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `CargoType` (`displayName`, `type`)
VALUES
	('Containers', 'FCL'),
	('Boxes or Pallets', 'LCL'),
	('Commodities', 'Bulk');

CREATE TABLE `ShipCargoType` (
  `shipId` int(11) unsigned NOT NULL,
  `cargoTypeId` int(11) unsigned NOT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`shipId`, `cargoTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ContainerType` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `displayName` varchar(256) DEFAULT NULL,
  `type` varchar(128) DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `ContainerType` (`displayName`, `type`)
VALUES
	('20', '20'),
	('20 HC', '20HC'),
	('40', '40'),
	('40 HC', '40HC');

CREATE TABLE `BulkType` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `displayName` varchar(256) DEFAULT NULL,
  `type` varchar(128) DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Cargo` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `contractId` int(11) unsigned DEFAULT NULL,
  `shipmentId` int(11) unsigned DEFAULT NULL,
  `userId` int(11) unsigned NOT NULL,
  `status` enum('INQUIRY', 'RESERVED', 'DELIVERED', 'CANCELED', 'EXPIRED') CHARACTER SET utf8 NOT NULL DEFAULT 'INQUIRY',
  `cargoTypeId` int(11) unsigned NOT NULL,
  `quantity` int(11) unsigned NOT NULL,
  `departure` date DEFAULT NULL,
  `weight` int(11) unsigned DEFAULT NULL,
  `weightUnit` enum('KG', 'TON', 'LB', 'NOT_USED') CHARACTER SET utf8 NOT NULL DEFAULT 'NOT_USED',
  `volume` int(11) unsigned DEFAULT NULL,
  `volumeUnit` enum('M3', 'NOT_USED') CHARACTER SET utf8 NOT NULL DEFAULT 'NOT_USED',
  `length` int(11) unsigned DEFAULT NULL,
  `width` int(11) unsigned DEFAULT NULL,
  `height` int(11) unsigned DEFAULT NULL,
  `dimensionUnit` enum('CM', 'M', 'IN', 'FT', 'NOT_USED') CHARACTER SET utf8 NOT NULL DEFAULT 'NOT_USED',
  `containerTypeId` int(11) unsigned DEFAULT NULL,
  `bulkTypeId` int(11) unsigned DEFAULT NULL,
  `expiry` timestamp NULL DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Shipment` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `shipId` int(11) unsigned NOT NULL,
  `originPortId` int(11) unsigned DEFAULT NULL,
  `destinationPortId` int(11) unsigned DEFAULT NULL,
  `departure` date NULL DEFAULT NULL,
  `arrival` date NULL DEFAULT NULL,
  `status` enum('UPCOMING', 'LIVE', 'COMPLETED', 'CANCELED') CHARACTER SET utf8 NOT NULL DEFAULT 'UPCOMING',
  `shipStatus` enum('DOCKING_ORIGIN', 'LOADING', 'AT_SEA', 'DISCHARGE', 'DOCKING_DESTINATION') CHARACTER SET utf8 NOT NULL DEFAULT 'DOCKING_ORIGIN',
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `CargoShipment` (
  `cargoId` int(11) unsigned NOT NULL,
  `shipmentId` int(11) unsigned NOT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`cargoId`, `shipmentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Port` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `code` int(11) unsigned DEFAULT NULL,
  `name` varchar(512) DEFAULT NULL,
  `lat` decimal(11, 8) DEFAULT NULL,
  `lon` decimal(11, 8) DEFAULT NULL,
  `city` varchar(128) DEFAULT NULL,
  `province` varchar(128) DEFAULT NULL,
  `island` varchar(128) DEFAULT NULL,
  `country` varchar(128) DEFAULT NULL,
  `status` enum('ACTIVE', 'INACTIVE') CHARACTER SET utf8 NOT NULL DEFAULT 'ACTIVE',
  `size` enum('BIG', 'SMALL') CHARACTER SET utf8 NOT NULL DEFAULT 'SMALL',
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Contract` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `shipId` int(11) unsigned DEFAULT NULL,
  `shipmentId` int(11) unsigned DEFAULT NULL,
  `userId` int(11) unsigned NOT NULL,
  `price` decimal(15,2) DEFAULT NULL,
  `priceUnit` enum('KG', 'TON', 'LB', 'M3', 'NOT_USED') CHARACTER SET utf8 NOT NULL DEFAULT 'NOT_USED',
  `currency` varchar(5) DEFAULT 'RP',
  `payoutId` int(11) unsigned DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `charterType` enum('CHARTER', 'LUMPSUM') CHARACTER SET utf8 NOT NULL DEFAULT 'CHARTER',
  `loadingType` enum('FIOST', 'FILO', 'LIFO') CHARACTER SET utf8 NOT NULL DEFAULT 'FIOST',
  `incotermsId` int(11) unsigned DEFAULT NULL,
  `cargoSender` enum('AS_ORDER', 'SHIP_OWNER', 'CARGO_OWNER', 'OTHER_PARTY') CHARACTER SET utf8 NOT NULL DEFAULT 'AS_ORDER',
  `cargoSenderOther` varchar(512) DEFAULT NULL,
  `cargoReceiver` enum('AS_ORDER', 'SHIP_OWNER', 'CARGO_OWNER', 'OTHER_PARTY') CHARACTER SET utf8 NOT NULL DEFAULT 'AS_ORDER',
  `cargoReceiverOther` varchar(512) DEFAULT NULL,
  `cargoInsurance` enum('CUSTOMER', 'TRANSPORTER', 'NONE') CHARACTER SET utf8 NOT NULL DEFAULT 'NONE',
  `shipInsurance` enum('CUSTOMER', 'TRANSPORTER', 'NONE') CHARACTER SET utf8 NOT NULL DEFAULT 'NONE',
  `shipAgentId` int(11) unsigned DEFAULT NULL,
  `miscellaneousFee` enum('CUSTOMER', 'TRANSPORTER') CHARACTER SET utf8 NOT NULL DEFAULT 'TRANSPORTER',
  `demurrage` decimal(11,2) DEFAULT NULL,
  `demurrageUnit` enum('HOUR', 'DAY', 'WEEK', 'NOT_USED') CHARACTER SET utf8 NOT NULL DEFAULT 'NOT_USED',
  `loadingLaytime` int(5) unsigned DEFAULT NULL,
  `dischargeLaytime` int(5) unsigned DEFAULT NULL,
  `totalLaytime` int(5) unsigned DEFAULT NULL,
  `laytimeUnit` enum('HOUR', 'DAY', 'WEEK', 'NOT_USED') CHARACTER SET utf8 NOT NULL DEFAULT 'NOT_USED',
  `despatchType` enum('DHALFD', 'DDO', 'DLO', 'FD', 'NOT_USED') CHARACTER SET utf8 NOT NULL DEFAULT 'NOT_USED',
  `layDaysType` enum('SHINC', 'SHEX', 'WWD', 'FAC', 'NOT_USED') CHARACTER SET utf8 NOT NULL DEFAULT 'NOT_USED',
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY (`shipId`, `userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ShipAgent` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `assigner` enum('CUSTOMER', 'TRANSPORTER') CHARACTER SET utf8 NOT NULL DEFAULT 'CUSTOMER',
  `customerShare` decimal(11,2) NOT NULL,
  `transporterShare` decimal(11,2) NOT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Incoterms` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `displayName` varchar(256) DEFAULT NULL,
  `type` varchar(128) DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Payout` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `contractSigned` decimal(11,2) DEFAULT 0,
  `dockedOrigin` decimal(11,2) DEFAULT 0,
  `loaded` decimal(11,2) DEFAULT 0,
  `dockedDestination` decimal(11,2) DEFAULT 0,
  `discharged` decimal(11,2) DEFAULT 0,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `CargoContract` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `cargoId` int(11) unsigned NOT NULL,
  `contractId` int(11) unsigned NOT NULL,
  `customerId` int(11) unsigned NOT NULL,
  `transporterId` int(11) unsigned NOT NULL,
  `status` enum('TRANSPORTER_OFFERED', 'CUSTOMER_ACCEPTED', 'CUSTOMER_DECLINED', 'CUSTOMER_NEGOTIATE', 'CUSTOMER_EXPIRED', 'TRANSPORTER_EXPIRED') CHARACTER SET utf8 NOT NULL DEFAULT 'TRANSPORTER_OFFERED',
  `expiry` timestamp NULL DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
	(1, 'PT Perahu Abadi', 'TRANSPORTER', 'UNVERIFIED', '2019-07-15 21:31:38', '2019-07-15 21:31:41');

INSERT INTO `Ship` (`id`, `name`, `companyId`, `shipTypeId`, `yearBuilt`, `grossTonnage`, `status`, `created`, `lastModified`)
VALUES
	(1, 'Perahu A1', 1, 2, 1935, 1000, 'ACTIVE', '2019-07-15 21:33:39', '2019-07-15 21:33:51');

INSERT INTO `ShipAgent` (`id`, `assigner`, `customerShare`, `transporterShare`, `created`, `lastModified`)
VALUES
	(1, 'CUSTOMER', 100.00, 0.00, '2019-07-25 21:06:23', '2019-07-25 21:06:23'),
	(2, 'TRANSPORTER', 50.00, 50.00, '2019-07-25 21:06:29', '2019-07-25 21:06:34');

INSERT INTO `Payout` (`id`, `contractSigned`, `dockedOrigin`, `loaded`, `dockedDestination`, `discharged`, `created`, `lastModified`)
VALUES
	(1, 25.00, 25.00, 25.00, 25.00, 0.00, '2019-07-25 21:04:40', '2019-07-25 21:04:40'),
	(2, 100.00, 0.00, 0.00, 0.00, 0.00, '2019-07-25 21:04:40', '2019-07-25 21:08:00');

INSERT INTO `Contract` (`id`, `shipId`, `shipmentId`, `userId`, `price`, `priceUnit`, `currency`, `payoutId`, `startDate`, `endDate`, `charterType`, `loadingType`, `incotermsId`, `cargoSender`, `cargoSenderOther`, `cargoReceiver`, `cargoReceiverOther`, `cargoInsurance`, `shipInsurance`, `shipAgentId`, `miscellaneousFee`, `demurrage`, `demurrageUnit`, `loadingLaytime`, `dischargeLaytime`, `totalLaytime`, `laytimeUnit`, `despatchType`, `layDaysType`, `created`, `lastModified`)
VALUES
	(1, 1, NULL, 2, 275000000.00, 'NOT_USED', 'RP', 2, '2019-10-20', '2019-11-20', 'LUMPSUM', 'FIOST', 2, 'AS_ORDER', NULL, 'AS_ORDER', NULL, 'TRANSPORTER', 'CUSTOMER', 1, 'TRANSPORTER', NULL, 'NOT_USED', NULL, NULL, NULL, 'NOT_USED', 'DDO', 'NOT_USED', '2019-07-15 21:33:27', '2019-07-25 21:08:18'),
	(2, 1, 1, 2, 7000.00, 'M3', 'RP', 1, '2019-08-15', '2019-08-25', 'CHARTER', 'FIOST', 2, 'SHIP_OWNER', NULL, 'OTHER_PARTY', 'PT. Sehat Abadi Jaya', 'CUSTOMER', 'CUSTOMER', 2, 'TRANSPORTER', NULL, 'NOT_USED', NULL, NULL, NULL, 'NOT_USED', 'NOT_USED', 'SHINC', '2019-07-15 21:33:27', '2019-07-25 21:07:43');

INSERT INTO `Cargo` (`id`, `contractId`, `shipmentId`, `userId`, `status`, `cargoTypeId`, `quantity`, `departure`, `weight`, `weightUnit`, `volume`, `volumeUnit`, `length`, `width`, `height`, `dimensionUnit`, `containerTypeId`, `bulkTypeId`, `expiry`, `created`, `lastModified`)
VALUES
	(1, 1, NULL, 1, 'INQUIRY', 3, 1, '2019-10-20', 3000, 'TON', 3041, 'M3', NULL, NULL, NULL, 'NOT_USED', NULL, 4, NULL, '2019-07-15 22:08:05', '2019-07-15 22:08:27'),
	(2, 1, 1, 1, 'RESERVED', 3, 1, '2019-08-15', 7000, 'TON', 5020, 'M3', NULL, NULL, NULL, 'NOT_USED', NULL, 3, NULL, '2019-07-15 22:08:05', '2019-07-25 21:18:35');

INSERT INTO `CargoContract` (`id`, `cargoId`, `contractId`, `customerId`, `transporterId`, `status`, `expiry`, `created`, `lastModified`)
VALUES
	(1, 1, 1, 1, 2, 'TRANSPORTER_OFFERED', NULL, '2019-07-15 22:08:48', '2019-07-15 22:08:48'),
	(2, 2, 1, 1, 2, 'CUSTOMER_ACCEPTED', NULL, '2019-07-15 22:08:48', '2019-07-25 21:09:05');

INSERT INTO `Shipment` (`id`, `shipId`, `originPortId`, `destinationPortId`, `departure`, `arrival`, `status`, `shipStatus`, `created`, `lastModified`)
VALUES
	(1, 1, 1, 2, '2019-08-15', '2019-08-25', 'UPCOMING', 'DOCKING_ORIGIN', '2019-07-25 21:03:51', '2019-07-25 21:10:08');

INSERT INTO `Port` (`id`, `code`, `name`, `lat`, `lon`, `city`, `province`, `island`, `country`, `status`, `size`, `created`, `lastModified`)
VALUES
	(1, 101, 'Tanjung Priok', 99.37578056, 0.18498889, 'Jakarta', 'Jakarta', 'Jawa', 'Indonesia', 'ACTIVE', 'BIG', '2019-07-25 21:24:11', '2019-07-25 21:24:11'),
	(2, 514, 'Kuala Semboja', 117.10083333, 1.02000000, 'Kuala Semboja', 'Kalimantan Timur', 'Kalimantan', 'Indonesia', 'ACTIVE', 'SMALL', '2019-07-25 21:25:00', '2019-07-25 21:25:18');
