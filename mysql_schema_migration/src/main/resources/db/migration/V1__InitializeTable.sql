CREATE TABLE `User` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `guid` varchar(36) NOT NULL,
  `username` varchar(20) DEFAULT NULL,
  `email` varchar(512) DEFAULT NULL,
  `phone` bigint(36) unsigned DEFAULT NULL,
  `companyId` int(11) unsigned DEFAULT NULL,
  `type` enum('CUSTOMER', 'TRANSPORTER') CHARACTER SET utf8 NOT NULL DEFAULT 'CUSTOMER',
  `status` enum('ACTIVE', 'INACTIVE') CHARACTER SET utf8 NOT NULL DEFAULT 'ACTIVE',
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `guid` (`guid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Authentication` (
  `guid` varchar(36) NOT NULL,
  `email` varchar(512) DEFAULT NULL,
  `phone` bigint(36) unsigned DEFAULT NULL,
  `password` varchar(1024) DEFAULT NULL,
  `verificationCode` varchar(4) DEFAULT NULL,
  `verificationExpiry` timestamp NULL DEFAULT NULL,
  `status` enum('VERIFIED', 'UNVERIFIED') CHARACTER SET utf8 NOT NULL DEFAULT 'UNVERIFIED',
  `type` enum('CUSTOMER', 'TRANSPORTER') CHARACTER SET utf8 NOT NULL DEFAULT 'CUSTOMER',
  `token` varchar(1024) DEFAULT NULL,
  `salt` varchar(1024) DEFAULT NULL,
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

CREATE TABLE `Facility` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `displayName` varchar(256) DEFAULT NULL,
  `type` varchar(128) DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `Facility` (`displayName`, `type`)
VALUES
    ('Rampdoor', 'Rampdoor'),
    ('Side Board Door', 'Side Board Door'),
    ('Crane', 'Crane'),
    ('Side Board', 'Side Board'),
    ('Double Hull', 'Double Hull'),
    ('Double Deck', 'Double Deck'),
    ('Double Bottom', 'Double Bottom'),
    ('Heater', 'Heater'),
    ('Pump', 'Pump'),
    ('Clamp Shell', 'Clamp Shell'),
    ('Flat Top', 'Flat Top'),
    ('Chiller', 'Chiller'),
    ('Anchor', 'Anchor'),
    ('Anchor Winch', 'Anchor Winch');

CREATE TABLE `ShipFacility` (
  `shipId` int(11) unsigned NOT NULL,
  `facilityId` int(11) unsigned NOT NULL,
  `description` varchar(256) DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`shipId`, `facilityId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Cargo` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `contractId` int(11) unsigned DEFAULT NULL,
  `shipmentId` int(11) unsigned DEFAULT NULL,
  `userId` int(11) unsigned NOT NULL,
  `status` enum('INQUIRY', 'RESERVED', 'DELIVERED', 'CANCELED', 'EXPIRED') CHARACTER SET utf8 NOT NULL DEFAULT 'INQUIRY',
  `cargoTypeId` int(11) unsigned NOT NULL,
  `quantity` int(11) unsigned NOT NULL,
  `originLocationId` int(11) unsigned DEFAULT NULL,
  `destinationLocationId` int(11) unsigned DEFAULT NULL,
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
  `originLocationId` int(11) unsigned DEFAULT NULL,
  `destinationLocationId` int(11) unsigned DEFAULT NULL,
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

CREATE TABLE `Location` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `externalId` varchar(512) DEFAULT NULL,
  `mainName` varchar(128) DEFAULT NULL,
  `secondaryName` varchar(512) DEFAULT NULL,
  `lat` decimal(11, 8) DEFAULT NULL,
  `lon` decimal(11, 8) DEFAULT NULL,
  `route` varchar(128) DEFAULT NULL,
  `locality` varchar(128) DEFAULT NULL,
  `village` varchar(128) DEFAULT NULL,
  `subdistrict` varchar(128) DEFAULT NULL,
  `city` varchar(128) DEFAULT NULL,
  `province` varchar(128) DEFAULT NULL,
  `country` varchar(128) DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY (`externalId`)
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
  `originLocationId` int(11) unsigned DEFAULT NULL,
  `destinationLocationId` int(11) unsigned DEFAULT NULL,
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
  `status` enum('TRANSPORTER_OFFERED', 'CUSTOMER_ACCEPTED', 'CUSTOMER_DECLINED', 'CUSTOMER_NEGOTIATE', 'CUSTOMER_EXPIRED', 'TRANSPORTER_EXPIRED', 'CUSTOMER_ACCEPT_OTHER_CONTRACT') CHARACTER SET utf8 NOT NULL DEFAULT 'TRANSPORTER_OFFERED',
  `expiry` timestamp NULL DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
