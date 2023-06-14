ALTER TABLE Product DROP FOREIGN KEY FK5414co2yomrmr1wbiahodoi6j;
ALTER TABLE Product DROP COLUMN specialityDocument_id;
ALTER TABLE Product DROP COLUMN specialityDescription;

ALTER TABLE ProductLabelContent DROP FOREIGN KEY FKo78331b0piyfvhsjhibs5ckmo;
ALTER TABLE ProductLabelContent DROP COLUMN specialityDocument_id;
ALTER TABLE ProductLabelContent DROP COLUMN specialityDescription;
