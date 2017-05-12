import MySQLdb

db=MySQLdb.connect(user='root',passwd='STUDENT',host='127.0.0.1')
#daca nu da import la MySQLdb,in linia de comanda dati: easy_install mysql-python
#sau pip install mysql-python   (prima varianta ar trebui sa mearga)

c=db.cursor()
c.execute('create database if not exists proiectip_a2')
c.execute(
    "CREATE TABLE IF NOT EXISTS `proiectip_a2`.`notificari` ("
    "  `id` INT NOT NULL,"
    "  `Repeatable` TINYINT NOT NULL,"
    "  `Interval` BIGINT UNSIGNED NULL,"
    "  `Time` TIMESTAMP NOT NULL,"
    "  `Text` VARCHAR(1000) NULL,"
    "  `user_id` INT NOT NULL,"
    "  PRIMARY KEY (`id`))")
c.execute(
    "CREATE TABLE IF NOT EXISTS `proiectip_a2`.`useri` ("
	"  `id` INT NOT NULL,"
	"  `token` BIGINT NOT NULL,"
	"  `Country` VARCHAR(255) NOT NULL,"
	"  `City` VARCHAR(255) NOT NULL,"
	"  `newsCrawler` TINYINT NOT NULL,"
	"  `hazzardCrawler` TINYINT NOT NULL,"
	"  `weatherCrawler` TINYINT NOT NULL,"
	"  `email` VARCHAR(255) NULL,"
	"  PRIMARY KEY (`id`), UNIQUE KEY `token` (`token`))")


c.close()
db.close()
