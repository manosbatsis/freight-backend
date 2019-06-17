CREATE TABLE `User` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `guid` varchar(36) NOT NULL,
  `username` varchar(20) DEFAULT NULL,
  `email` varchar(512) DEFAULT NULL,
  `phone` int(36) unsigned DEFAULT NULL,
  `companyId` int(11) unsigned DEFAULT NULL,
  `type` enum('CUSTOMER', 'TRANSPORTER' ,'CUSTOMER_TRANSPORTER', 'NOT_KNOWN') CHARACTER SET utf8 NOT NULL DEFAULT 'NOT_KNOWN',
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
  `type` enum('CUSTOMER', 'TRANSPORTER', 'CUSTOMER_TRANSPORTER') CHARACTER SET utf8 NOT NULL DEFAULT 'CUSTOMER',
  `status` enum('ACTIVE', 'INACTIVE') CHARACTER SET utf8 NOT NULL DEFAULT 'ACTIVE',
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
  `yearBuilt` int(4) unsigned DEFAULT NULL,
  `grossTonnage` int(11) unsigned DEFAULT NULL,
  `status` enum('ACTIVE', 'INACTIVE') CHARACTER SET utf8 NOT NULL DEFAULT 'ACTIVE',
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `CargoType` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `displayName` varchar(512) DEFAULT NULL,
  `type` varchar(512) DEFAULT NULL,
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
  `displayName` varchar(512) DEFAULT NULL,
  `type` varchar(512) DEFAULT NULL,
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
  `displayName` varchar(512) DEFAULT NULL,
  `type` varchar(512) DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `BulkType` (`displayName`, `type`)
VALUES
	('Coal', 'COAL'),
	('Sand', 'SAND');

CREATE TABLE `Cargo` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `shipId` int(11) unsigned DEFAULT NULL,
  `shipmentId` int(11) unsigned DEFAULT NULL,
  `userId` int(11) unsigned NOT NULL,
  `status` enum('INQUIRY', 'RESERVED', 'LOADED', 'DELIVERED', 'DELAYED', 'CANCELED') CHARACTER SET utf8 NOT NULL DEFAULT 'INQUIRY',
  `cargoTypeId` int(11) unsigned NOT NULL,
  `quantity` int(11) unsigned NOT NULL,
  `weight` int(11) unsigned DEFAULT NULL,
  `weightUnit` enum('KG', 'TON', 'LB', 'NOT_USED') CHARACTER SET utf8 NOT NULL DEFAULT 'NOT_USED',
  `length` int(11) unsigned DEFAULT NULL,
  `width` int(11) unsigned DEFAULT NULL,
  `height` int(11) unsigned DEFAULT NULL,
  `dimensionUnit` enum('CM', 'M', 'IN', 'FT', 'NOT_USED') CHARACTER SET utf8 NOT NULL DEFAULT 'NOT_USED',
  `containerTypeId` int(11) unsigned DEFAULT NULL,
  `bulkTypeId` int(11) unsigned DEFAULT NULL,
  `price` decimal(11, 2) DEFAULT NULL,
  `currency` varchar(5) DEFAULT 'RP',
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY (`shipId`, `userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Shipment` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `shipId` int(11) unsigned NOT NULL,
  `originPortId` int(11) unsigned DEFAULT NULL,
  `destinationPortId` int(11) unsigned DEFAULT NULL,
  `estimatedDeparture` timestamp NULL DEFAULT NULL,
  `estimatedArrival` timestamp NULL DEFAULT NULL,
  `status` enum('OPEN', 'CLOSED', 'COMPLETED', 'CANCELED') CHARACTER SET utf8 NOT NULL DEFAULT 'OPEN',
  `shipStatus` enum('DOCKING_ORIGIN', 'LOADING', 'AT_SEA', 'DISCHARGE', 'DOCKING_DESTINATION') CHARACTER SET utf8 NOT NULL DEFAULT 'DOCKING_ORIGIN',
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Port` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `code` int(11) unsigned DEFAULT NULL,
  `name` varchar(512) DEFAULT NULL,
  `lat` decimal(11, 8) DEFAULT NULL,
  `lon` decimal(11, 8) DEFAULT NULL,
  `city` varchar(512) DEFAULT NULL,
  `province` varchar(512) DEFAULT NULL,
  `postalCode` varchar(512) DEFAULT NULL,
  `country` varchar(512) DEFAULT NULL,
  `status` enum('ACTIVE', 'INACTIVE') CHARACTER SET utf8 NOT NULL DEFAULT 'ACTIVE',
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
