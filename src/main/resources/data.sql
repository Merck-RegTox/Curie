insert into software(name) VALUES ('leadscope')
insert into software(name) VALUES ('vega')
insert into software(name) VALUES ('qsar toolbox')
insert into software(name) VALUES ('acd perceptra')
insert into software(name) VALUES ('opera')
insert into software(name) VALUES ('test')

insert into endpoint(name) VALUES('sperm effect')
insert into endpoint(name) VALUES('reproductive toxicity')
insert into endpoint(name) VALUES('mutagenicity')
insert into endpoint(name) VALUES('carcinogenicity')

insert into model(name, eid, sid) VALUES('repro female mouse', 2, 1)
insert into model(name, eid, sid) VALUES('repro male mouse', 2, 1)
insert into model(name, eid, sid) VALUES('repro male rat', 2, 1)
insert into model(name, eid, sid) VALUES('repro male rat', 2, 1)
insert into model(name, eid, sid) VALUES('repro mouse sperm', 1, 1)
insert into model(name, eid, sid) VALUES('repro rat sperm', 1, 1)

insert into chemical(cas, smile) VALUES('109-99-9', 'C1CCOC1')
insert into chemical(cas, smile) VALUES('2682-20-4', 'CN1SC=CC1=O')
insert into chemical(cas, smile) VALUES('75-77-4', 'C[Si](C)(C)Cl')
insert into chemical(cas, smile) VALUES('75-09-2', 'ClCCl')
insert into chemical(cas, smile) VALUES('110-54-3', 'CCCCCC')

insert into prediction(chemical_id, prediction_raw, prediction, reliability_raw, reliability, model_id) values(1, 'positive', 'positive', '0.64', '0.64', 1);
insert into prediction(chemical_id, prediction_raw, prediction, reliability_raw, reliability, model_id) values(2, 'not in domain', 'not in domain', '0.758', '0.758', 1);
insert into prediction(chemical_id, prediction_raw, prediction, reliability_raw, reliability, model_id) values(3, 'not in domain', 'not in domain', '0.813', '0.813', 1);
insert into prediction(chemical_id, prediction_raw, prediction, reliability_raw, reliability, model_id) values(4, 'not in domain', 'not in domain', '0.807', '0.807', 1);
insert into prediction(chemical_id, prediction_raw, prediction, reliability_raw, reliability, model_id) values(5, 'not in domain', 'not in domain', '0.742', '0.742', 1);






