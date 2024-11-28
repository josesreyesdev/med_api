ALTER TABLE patients ADD COLUMN active tinyint;

UPDATE patients SET active = 1;

ALTER TABLE physicians MODIFY active tinyint NOT NULL;