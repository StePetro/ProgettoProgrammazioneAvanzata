CREATE SCHEMA IF NOT EXISTS `archivio_gap`;
USE `archivio_gap`;

CREATE TABLE IF NOT EXISTS `archivioaffitti` (
  `idAffitto` int(11) NOT NULL AUTO_INCREMENT,
  `codiceUtente` int(11) NOT NULL,
  `locale` varchar(45) NOT NULL,
  `checkin` date NOT NULL,
  `checkout` date NOT NULL,
  `affittoGiornaliero` double NOT NULL,
  `percentualeTasse` double NOT NULL,
  PRIMARY KEY (`idAffitto`),
  UNIQUE KEY `idAffitto_UNIQUE` (`idAffitto`)
) ENGINE=InnoDB AUTO_INCREMENT=501 DEFAULT CHARSET=latin1;

INSERT INTO `archivioaffitti` (`idAffitto`,`codiceUtente`,`locale`,`checkin`,`checkout`,`affittoGiornaliero`,`percentualeTasse`) VALUES (124,123456789,'Casa delle Rose','2018-12-20','2018-12-25',210,0.22);
INSERT INTO `archivioaffitti` (`idAffitto`,`codiceUtente`,`locale`,`checkin`,`checkout`,`affittoGiornaliero`,`percentualeTasse`) VALUES (127,123456789,'Casa al Colletto','2019-01-01','2019-01-05',105,0.21);
INSERT INTO `archivioaffitti` (`idAffitto`,`codiceUtente`,`locale`,`checkin`,`checkout`,`affittoGiornaliero`,`percentualeTasse`) VALUES (135,123456789,'Villa delle Rose','2020-03-19','2020-04-04',103,0.22);
INSERT INTO `archivioaffitti` (`idAffitto`,`codiceUtente`,`locale`,`checkin`,`checkout`,`affittoGiornaliero`,`percentualeTasse`) VALUES (136,123456789,'Casa dei Pini','2018-03-02','2018-04-14',55,0.22);
INSERT INTO `archivioaffitti` (`idAffitto`,`codiceUtente`,`locale`,`checkin`,`checkout`,`affittoGiornaliero`,`percentualeTasse`) VALUES (137,123456789,'Casa dei Pini','2017-03-11','2017-05-17',55,0.22);
INSERT INTO `archivioaffitti` (`idAffitto`,`codiceUtente`,`locale`,`checkin`,`checkout`,`affittoGiornaliero`,`percentualeTasse`) VALUES (138,123456789,'Villa Sontuosa','2017-02-09','2017-02-25',300,0.22);
INSERT INTO `archivioaffitti` (`idAffitto`,`codiceUtente`,`locale`,`checkin`,`checkout`,`affittoGiornaliero`,`percentualeTasse`) VALUES (139,123456789,'Casa delle Mura','2016-02-12','2016-02-28',40,0.22);
INSERT INTO `archivioaffitti` (`idAffitto`,`codiceUtente`,`locale`,`checkin`,`checkout`,`affittoGiornaliero`,`percentualeTasse`) VALUES (140,123456789,'Casa delle Mura','2015-02-13','2015-03-29',40,0.22);
INSERT INTO `archivioaffitti` (`idAffitto`,`codiceUtente`,`locale`,`checkin`,`checkout`,`affittoGiornaliero`,`percentualeTasse`) VALUES (141,123456789,'Casa delle Mura','2014-10-03','2015-01-31',45,0.22);
INSERT INTO `archivioaffitti` (`idAffitto`,`codiceUtente`,`locale`,`checkin`,`checkout`,`affittoGiornaliero`,`percentualeTasse`) VALUES (467,123456789,'Casa dei Limoni','2019-02-24','2019-02-28',33,0.22);
INSERT INTO `archivioaffitti` (`idAffitto`,`codiceUtente`,`locale`,`checkin`,`checkout`,`affittoGiornaliero`,`percentualeTasse`) VALUES (468,123456789,'Villa dei Limoni','2020-02-02','2020-02-09',100,0.22);
INSERT INTO `archivioaffitti` (`idAffitto`,`codiceUtente`,`locale`,`checkin`,`checkout`,`affittoGiornaliero`,`percentualeTasse`) VALUES (469,123456789,'Casa Olivia','2019-02-21','2019-02-22',90,0.22);
INSERT INTO `archivioaffitti` (`idAffitto`,`codiceUtente`,`locale`,`checkin`,`checkout`,`affittoGiornaliero`,`percentualeTasse`) VALUES (471,123456789,'Villa delle Rose','2019-02-15','2019-02-17',55,0.22);
INSERT INTO `archivioaffitti` (`idAffitto`,`codiceUtente`,`locale`,`checkin`,`checkout`,`affittoGiornaliero`,`percentualeTasse`) VALUES (472,123456789,'Villa delle Rose','2019-02-17','2019-02-20',55,0.22);
INSERT INTO `archivioaffitti` (`idAffitto`,`codiceUtente`,`locale`,`checkin`,`checkout`,`affittoGiornaliero`,`percentualeTasse`) VALUES (473,123456789,'Villa delle Rose','2019-02-20','2019-02-24',55,0.22);
INSERT INTO `archivioaffitti` (`idAffitto`,`codiceUtente`,`locale`,`checkin`,`checkout`,`affittoGiornaliero`,`percentualeTasse`) VALUES (474,123456789,'Villa delle Rose','2019-02-25','2019-02-28',55,0.22);
INSERT INTO `archivioaffitti` (`idAffitto`,`codiceUtente`,`locale`,`checkin`,`checkout`,`affittoGiornaliero`,`percentualeTasse`) VALUES (475,123456789,'Casa Gioia','2019-02-23','2019-02-27',23,0.22);
INSERT INTO `archivioaffitti` (`idAffitto`,`codiceUtente`,`locale`,`checkin`,`checkout`,`affittoGiornaliero`,`percentualeTasse`) VALUES (487,123456789,'Casa al Colletto','2019-02-03','2019-02-04',80,0.22);
INSERT INTO `archivioaffitti` (`idAffitto`,`codiceUtente`,`locale`,`checkin`,`checkout`,`affittoGiornaliero`,`percentualeTasse`) VALUES (488,123456789,'Casa al Colletto','2019-02-04','2019-02-07',80,0.22);
INSERT INTO `archivioaffitti` (`idAffitto`,`codiceUtente`,`locale`,`checkin`,`checkout`,`affittoGiornaliero`,`percentualeTasse`) VALUES (490,123456789,'Villa dei Limoni','2018-03-02','2018-03-04',105,0.22);
INSERT INTO `archivioaffitti` (`idAffitto`,`codiceUtente`,`locale`,`checkin`,`checkout`,`affittoGiornaliero`,`percentualeTasse`) VALUES (491,123456789,'Villa dei Limoni','2017-03-03','2017-04-14',105,0.22);
INSERT INTO `archivioaffitti` (`idAffitto`,`codiceUtente`,`locale`,`checkin`,`checkout`,`affittoGiornaliero`,`percentualeTasse`) VALUES (492,123456789,'Villa dei Limoni','2018-03-28','2018-04-06',105,0.22);
INSERT INTO `archivioaffitti` (`idAffitto`,`codiceUtente`,`locale`,`checkin`,`checkout`,`affittoGiornaliero`,`percentualeTasse`) VALUES (493,123456789,'Casa delle Rose','2017-03-03','2017-04-08',70,0.22);
INSERT INTO `archivioaffitti` (`idAffitto`,`codiceUtente`,`locale`,`checkin`,`checkout`,`affittoGiornaliero`,`percentualeTasse`) VALUES (494,123456789,'Casa delle Rose','2017-05-06','2017-06-03',70,0.22);
INSERT INTO `archivioaffitti` (`idAffitto`,`codiceUtente`,`locale`,`checkin`,`checkout`,`affittoGiornaliero`,`percentualeTasse`) VALUES (495,123456789,'Casa delle Rose','2017-07-30','2017-08-01',70,0.22);
INSERT INTO `archivioaffitti` (`idAffitto`,`codiceUtente`,`locale`,`checkin`,`checkout`,`affittoGiornaliero`,`percentualeTasse`) VALUES (499,123456789,'Casa dei Pini','2019-02-28','2019-03-11',70,0.22);
INSERT INTO `archivioaffitti` (`idAffitto`,`codiceUtente`,`locale`,`checkin`,`checkout`,`affittoGiornaliero`,`percentualeTasse`) VALUES (500,123456789,'Casa delle Rose','2019-02-28','2019-03-11',70,0.22);

