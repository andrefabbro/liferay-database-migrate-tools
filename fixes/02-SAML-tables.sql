--
-- Table structure for table `SamlIdpSpConnection`
--
DROP TABLE IF EXISTS `SamlIdpSpConnection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SamlIdpSpConnection` (
  `SAMLIDPSPCONNECTIONID` bigint(20) NOT NULL DEFAULT '0',
  `COMPANYID` bigint(20) DEFAULT NULL,
  `USERID` bigint(20) DEFAULT NULL,
  `USERNAME` varchar(75)  DEFAULT NULL,
  `CREATEDATE` datetime DEFAULT NULL,
  `MODIFIEDDATE` datetime DEFAULT NULL,
  `SAMLSPENTITYID` text ,
  `ASSERTIONLIFETIME` bigint(20) DEFAULT NULL,
  `ATTRIBUTENAMES` text ,
  `ATTRIBUTESENABLED` int(11) DEFAULT NULL,
  `ATTRIBUTESNAMESPACEENABLED` int(11) DEFAULT NULL,
  `ENABLED` int(11) DEFAULT NULL,
  `METADATAURL` text ,
  `METADATAXML` longtext ,
  `METADATAUPDATEDDATE` datetime DEFAULT NULL,
  `NAME` varchar(75)  DEFAULT NULL,
  `NAMEIDATTRIBUTE` text ,
  `NAMEIDFORMAT` text ,
  PRIMARY KEY (`SAMLIDPSPCONNECTIONID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SamlIdpSpSession`
--

DROP TABLE IF EXISTS `SamlIdpSpSession`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SamlIdpSpSession` (
  `SAMLIDPSPSESSIONID` bigint(20) NOT NULL DEFAULT '0',
  `COMPANYID` bigint(20) DEFAULT NULL,
  `USERID` bigint(20) DEFAULT NULL,
  `USERNAME` varchar(75)  DEFAULT NULL,
  `CREATEDATE` datetime DEFAULT NULL,
  `MODIFIEDDATE` datetime DEFAULT NULL,
  `SAMLIDPSSOSESSIONID` bigint(20) DEFAULT NULL,
  `SAMLSPENTITYID` text ,
  `NAMEIDFORMAT` text ,
  `NAMEIDVALUE` text ,
  PRIMARY KEY (`SAMLIDPSPSESSIONID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SamlIdpSsoSession`
--

DROP TABLE IF EXISTS `SamlIdpSsoSession`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SamlIdpSsoSession` (
  `SAMLIDPSSOSESSIONID` bigint(20) NOT NULL DEFAULT '0',
  `COMPANYID` bigint(20) DEFAULT NULL,
  `USERID` bigint(20) DEFAULT NULL,
  `USERNAME` varchar(75)  DEFAULT NULL,
  `CREATEDATE` datetime DEFAULT NULL,
  `MODIFIEDDATE` datetime DEFAULT NULL,
  `SAMLIDPSSOSESSIONKEY` varchar(75)  DEFAULT NULL,
  PRIMARY KEY (`SAMLIDPSSOSESSIONID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SamlSpAuthRequest`
--

DROP TABLE IF EXISTS `SamlSpAuthRequest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SamlSpAuthRequest` (
  `SAMLSPAUTHNREQUESTID` bigint(20) NOT NULL DEFAULT '0',
  `COMPANYID` bigint(20) DEFAULT NULL,
  `CREATEDATE` datetime DEFAULT NULL,
  `SAMLIDPENTITYID` text ,
  `SAMLSPAUTHREQUESTKEY` varchar(75)  DEFAULT NULL,
  PRIMARY KEY (`SAMLSPAUTHNREQUESTID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SamlSpIdpConnection`
--

DROP TABLE IF EXISTS `SamlSpIdpConnection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SamlSpIdpConnection` (
  `SAMLSPIDPCONNECTIONID` bigint(20) NOT NULL DEFAULT '0',
  `COMPANYID` bigint(20) DEFAULT NULL,
  `USERID` bigint(20) DEFAULT NULL,
  `USERNAME` varchar(75)  DEFAULT NULL,
  `CREATEDATE` datetime DEFAULT NULL,
  `MODIFIEDDATE` datetime DEFAULT NULL,
  `SAMLIDPENTITYID` text ,
  `ASSERTIONSIGNATUREREQUIRED` int(11) DEFAULT NULL,
  `CLOCKSKEW` bigint(20) DEFAULT NULL,
  `ENABLED` int(11) DEFAULT NULL,
  `FORCEAUTHN` int(11) DEFAULT NULL,
  `LDAPIMPORTENABLED` int(11) DEFAULT NULL,
  `METADATAURL` text ,
  `METADATAXML` longtext ,
  `METADATAUPDATEDDATE` datetime DEFAULT NULL,
  `NAME` varchar(75)  DEFAULT NULL,
  `NAMEIDFORMAT` text ,
  `SIGNAUTHNREQUEST` int(11) DEFAULT NULL,
  `USERATTRIBUTEMAPPINGS` text ,
  PRIMARY KEY (`SAMLSPIDPCONNECTIONID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SamlSpMessage`
--

DROP TABLE IF EXISTS `SamlSpMessage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SamlSpMessage` (
  `SAMLSPMESSAGEID` bigint(20) NOT NULL DEFAULT '0',
  `COMPANYID` bigint(20) DEFAULT NULL,
  `CREATEDATE` datetime DEFAULT NULL,
  `SAMLIDPENTITYID` text ,
  `SAMLIDPRESPONSEKEY` varchar(75)  DEFAULT NULL,
  `EXPIRATIONDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`SAMLSPMESSAGEID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SamlSpSession`
--

DROP TABLE IF EXISTS `SamlSpSession`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SamlSpSession` (
  `SAMLSPSESSIONID` bigint(20) NOT NULL DEFAULT '0',
  `COMPANYID` bigint(20) DEFAULT NULL,
  `USERID` bigint(20) DEFAULT NULL,
  `USERNAME` varchar(75)  DEFAULT NULL,
  `CREATEDATE` datetime DEFAULT NULL,
  `MODIFIEDDATE` datetime DEFAULT NULL,
  `SAMLSPSESSIONKEY` varchar(75)  DEFAULT NULL,
  `ASSERTIONXML` longtext ,
  `JSESSIONID` varchar(200)  DEFAULT NULL,
  `NAMEIDFORMAT` text ,
  `NAMEIDNAMEQUALIFIER` text ,
  `NAMEIDSPNAMEQUALIFIER` text ,
  `NAMEIDVALUE` text ,
  `SESSIONINDEX` varchar(75)  DEFAULT NULL,
  `TERMINATED_` int(11) DEFAULT NULL,
  PRIMARY KEY (`SAMLSPSESSIONID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

