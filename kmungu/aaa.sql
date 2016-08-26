CREATE TABLE `zipcode_full` (
  `zipCode` varchar(5) DEFAULT NULL,
  `sido` varchar(21) DEFAULT NULL,
  `sidoE` varchar(17) DEFAULT NULL,
  `sigungu` varchar(25) DEFAULT NULL,
  `sigunguE` varchar(27) DEFAULT NULL,
  `eupmyun` varchar(15) DEFAULT NULL,
  `eupmyunE` varchar(20) DEFAULT NULL,
  `doroCode` varchar(12) DEFAULT NULL,
  `doro` varchar(33) DEFAULT NULL,
  `doroE` varchar(46) DEFAULT NULL,
  `underground` char(1) DEFAULT NULL,
  `buildingNo1` varchar(5) DEFAULT NULL COMMENT '''건물본번 + 건물부번''',
  `buildingNo2` varchar(4) DEFAULT NULL,
  `buildingManageNo` varchar(25) DEFAULT NULL,
  `daryang` varchar(1) DEFAULT NULL,
  `building` varchar(60) DEFAULT NULL COMMENT '''건물명''',
  `dongCode` varchar(10) DEFAULT NULL COMMENT '''건물명''',
  `dong` varchar(16) DEFAULT NULL COMMENT '''건물명''',
  `ri` varchar(18) DEFAULT NULL,
  `dongAdmin` varchar(24) DEFAULT NULL,
  `san` varchar(4) DEFAULT NULL,
  `zibun1` varchar(4) DEFAULT NULL,
  `zibunSerial` varchar(2) DEFAULT NULL,
  `zibun2` varchar(4) DEFAULT NULL,
  `aaa` varchar(45) DEFAULT NULL,
  `bbb` varchar(45) DEFAULT NULL,
  KEY `idx_zipcode_full_sigungu1` (`sigungu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


LOAD DATA LOCAL INFILE 'G:/down/zipcode_DB/지역별전체 DB/20160805_도로명주소_지역별전체/충청북도.txt' INTO TABLE kmungu.zipcode_full CHARACTER SET 'utf8' FIELDS TERMINATED BY '|' IGNORE 1 LINES;

-- UPDATE
--   zipcode    
-- SET
SELECT 
    zipCode,
    concat(eupmyun,
            doro,
            buildingNo1,
            CASE buildingNo2
                WHEN 0 THEN ''
                ELSE CONCAT('-', buildingNo2)
            END,
            CASE CONCAT(dong, building)
                WHEN '' THEN ''
                ELSE CASE dong
                    WHEN '' THEN CONCAT('(', building, ')')
                    ELSE CASE building
                        WHEN '' THEN CONCAT('(', dong, ')')
                        ELSE CONCAT('(', dong, ',', building, ')')
                    END
                END
            END) searchAddressDoro,
    concat(dong,
            zibun1,
            CASE zibun2
                WHEN 0 THEN ''
                ELSE CONCAT('-', zibun2)
            END,
            CASE CONCAT(building)
                WHEN '' THEN ''
                ELSE CONCAT('(', building, ')')
            END) searchAddressZibun,
    concat(CASE eupmyun
                WHEN '' THEN ''
                ELSE CONCAT(eupmyun, ' ')
            END,
            CASE doro
                WHEN '' THEN ''
                ELSE CONCAT(doro, ' ')
            END,
            buildingNo1,
            CASE buildingNo2
                WHEN 0 THEN ''
                ELSE CONCAT('-', buildingNo2)
            END,
            CASE CONCAT(dong, building)
                WHEN '' THEN ''
                ELSE CASE dong
                    WHEN '' THEN CONCAT(' (', building, ')')
                    ELSE CASE building
                        WHEN '' THEN CONCAT(' (', dong, ')')
                        ELSE CONCAT(' (', dong, ',', building, ')')
                    END
                END
            END) addressDoro,
    concat(CASE dong
                WHEN '' THEN ''
                ELSE CONCAT(dong, ' ')
            END,
            zibun1,
            CASE zibun2
                WHEN 0 THEN ''
                ELSE CONCAT('-', zibun2)
            END,
            CASE CONCAT(building)
                WHEN '' THEN ''
                ELSE CONCAT(' (', building, ')')
            END) addressZibun
FROM
    zipcode_full
where
    building like '신일해피%'
limit 100;

ALTER TABLE `kmungu`.`zipcode_full` 
ADD INDEX `idx_zipcode_full_sido1` (`sido` ASC),
ADD INDEX `idx_zipcode_full_sigungu1` (`sigungu` ASC);