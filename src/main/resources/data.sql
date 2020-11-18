INSERT INTO holder VALUES('8f252e3f-e6c0-4046-af85-5ab2942bf7e5', NOW(), 'test1@test.com', TRUE, 'xyzert', NULL);
INSERT INTO profiles VALUES ('b06c06dc-6551-4068-adfd-dec0f717df47', '1 route du test', NOW(), 'Bertrand', 'Dudu', '+33698756321', NULL, '8f252e3f-e6c0-4046-af85-5ab2942bf7e5');
INSERT INTO bank VALUES ('c2c57056-845f-43a7-9959-b141c5419ca4', NOW(), 'CIC somewhere', 'FR761354163213216543013354', TRUE, 'My CIC', 'CIC23132ZZ', NULL, '8f252e3f-e6c0-4046-af85-5ab2942bf7e5');
INSERT INTO movement VALUES ('d7387840-f594-4e40-b157-72d926f1b929', -100.60, NOW(), NULL, 'c2c57056-845f-43a7-9959-b141c5419ca4', '8f252e3f-e6c0-4046-af85-5ab2942bf7e5');
INSERT INTO movement VALUES ('9de1dc74-8fc6-4fcb-b5a8-2575c73e1b61', 500.60, NOW(), NULL, 'c2c57056-845f-43a7-9959-b141c5419ca4', '8f252e3f-e6c0-4046-af85-5ab2942bf7e5');

INSERT INTO holder VALUES('69fc0109-1d1f-447e-bd0c-2629b46e004a', NOW()- interval '2 month', 'test2@test.com', TRUE, 'xyzerts', NULL);
INSERT INTO profiles VALUES ('e526d039-a3d4-4147-afba-9c3f8cc25f1d', '8 route du test', NOW()- interval '1 month', 'Albert', 'Durand', '+33698798321', NOW() - interval '14day', '69fc0109-1d1f-447e-bd0c-2629b46e004a');
INSERT INTO bank VALUES ('ad51b676-c9eb-4dda-bbe4-5f1d86504158', NOW() - interval '8day', 'Some Domiciliation', 'FR7851321354321354321', TRUE, 'Bank of mine', 'N26135216858132', NULL, '69fc0109-1d1f-447e-bd0c-2629b46e004a');
INSERT INTO movement VALUES ('4129b324-d705-4878-b36e-ee4bd411fa66', 1500, NOW() - interval '8day', NULL, 'ad51b676-c9eb-4dda-bbe4-5f1d86504158', '69fc0109-1d1f-447e-bd0c-2629b46e004a');

INSERT INTO holder VALUES('920932f9-459b-4e2e-b958-4f450dca41b4', NOW() - interval '2 month', 'test3@test.com', TRUE, 'xyzerts', NOW() - interval '2 day');
INSERT INTO profiles VALUES ('bf780c25-858e-4747-a11e-4b4337b10c3f', '8 avenue du test', NOW() - interval '2 month', 'George', 'Albert', '+41598798321', NOW() - interval '2 day', '920932f9-459b-4e2e-b958-4f450dca41b4');
INSERT INTO bank VALUES ('70e18c36-afd1-4ba0-84d2-3a38b92b5580', NOW(), 'test domiciliation', 'FR7851321ERE1254651', TRUE, 'test bank', 'SGE135216858132',NULL, '920932f9-459b-4e2e-b958-4f450dca41b4');
INSERT INTO movement VALUES ('b88eb848-2a41-4b4a-b44d-3aed84a3f5b2', 315, NOW() - interval '2 month', NULL, '70e18c36-afd1-4ba0-84d2-3a38b92b5580', '920932f9-459b-4e2e-b958-4f450dca41b4');

INSERT INTO holder VALUES('bcf46fb1-db79-4344-8f59-9694a61ae2f2', NOW() - interval '4 month', 'test4@test.com', TRUE, 'azerty', NOW() - interval '2 month');
INSERT INTO profiles VALUES ('ffd81646-714b-423f-8b12-44206a7634ed', '7 impasse du test', NOW() - interval '4 month', 'Antonio', 'Alvarez', '+4154798321', NOW() - interval '2 month', 'bcf46fb1-db79-4344-8f59-9694a61ae2f2');
INSERT INTO bank VALUES ('a4aa5aed-6e90-4bcf-b30a-d24b0699be4e', NOW(), 'another domiciliation', 'ES7851321ERE1254651', TRUE, 'The bank I use', 'BKA135216858132', NULL, 'bcf46fb1-db79-4344-8f59-9694a61ae2f2');
INSERT INTO movement VALUES ('6e3b2350-96ad-4abe-acd8-0bd26fa4fa5b', 1.6, NOW() - interval '4 month', NULL, 'a4aa5aed-6e90-4bcf-b30a-d24b0699be4e', 'bcf46fb1-db79-4344-8f59-9694a61ae2f2');

INSERT INTO connections VALUES ('69fc0109-1d1f-447e-bd0c-2629b46e004a', TRUE, 'bcf46fb1-db79-4344-8f59-9694a61ae2f2', '69fc0109-1d1f-447e-bd0c-2629b46e004a');
INSERT INTO connections VALUES ('c1c4b399-c4ea-4fbb-8044-b49e2d588783', TRUE, 'bcf46fb1-db79-4344-8f59-9694a61ae2f2', '920932f9-459b-4e2e-b958-4f450dca41b4');
INSERT INTO connections VALUES ('717f1be6-31e5-410f-9cec-651b52138592', TRUE, '8f252e3f-e6c0-4046-af85-5ab2942bf7e5', '920932f9-459b-4e2e-b958-4f450dca41b4');
INSERT INTO connections VALUES ('a683a418-1483-4791-8063-5ad125c79132', FALSE, '69fc0109-1d1f-447e-bd0c-2629b46e004a', '920932f9-459b-4e2e-b958-4f450dca41b4');
INSERT INTO connections VALUES ('ed96448a-b994-4806-b73a-24b0af203c75', TRUE, '8f252e3f-e6c0-4046-af85-5ab2942bf7e5', 'bcf46fb1-db79-4344-8f59-9694a61ae2f2');
INSERT INTO connections VALUES ('d24c23a7-7357-40ac-8e83-63c5831a449e', TRUE, '69fc0109-1d1f-447e-bd0c-2629b46e004a', 'bcf46fb1-db79-4344-8f59-9694a61ae2f2');
INSERT INTO connections VALUES ('28ff48ce-e6bd-49c3-a8e4-f77f027cf96b', FALSE, '920932f9-459b-4e2e-b958-4f450dca41b4', 'bcf46fb1-db79-4344-8f59-9694a61ae2f2');	
INSERT INTO connections VALUES ('dd81442e-e954-4db1-8460-6bbfb6cb6000', TRUE, '69fc0109-1d1f-447e-bd0c-2629b46e004a', '8f252e3f-e6c0-4046-af85-5ab2942bf7e5');
INSERT INTO connections VALUES ('29adfd3f-87b9-4c7b-9c20-05502e6cad1c', TRUE, 'bcf46fb1-db79-4344-8f59-9694a61ae2f2', '8f252e3f-e6c0-4046-af85-5ab2942bf7e5');

INSERT INTO transactions VALUES ('0604a88d-779a-4471-b943-ea64fee09e62', 500, NOW(), 'a test description', 25, NULL,'29adfd3f-87b9-4c7b-9c20-05502e6cad1c');	
INSERT INTO transactions VALUES ('1993198b-80a3-4432-9ca8-f9045b2caa00', 300, NOW(), 'a test description', 15, NULL,  '717f1be6-31e5-410f-9cec-651b52138592');
INSERT INTO movement VALUES ('4591da5a-5206-4a96-8f43-9d66aee0b045', -525, NOW(), NULL, NULL, '8f252e3f-e6c0-4046-af85-5ab2942bf7e5', '0604a88d-779a-4471-b943-ea64fee09e62');
INSERT INTO movement VALUES ('f45fc2ed-c706-404f-82f4-7ebbb868ecd4', 500, NOW(), NULL, NULL, 'bcf46fb1-db79-4344-8f59-9694a61ae2f2', '0604a88d-779a-4471-b943-ea64fee09e62');
INSERT INTO movement VALUES ('255d2b17-bc42-4185-aa1d-03e32cb2477e', -315, NOW(), NULL, NULL, '920932f9-459b-4e2e-b958-4f450dca41b4', '1993198b-80a3-4432-9ca8-f9045b2caa00');
INSERT INTO movement VALUES ('88c1a8ba-dc1d-43e5-b4db-3374c3a180e4', 300, NOW(), NULL, NULL, '8f252e3f-e6c0-4046-af85-5ab2942bf7e5', '1993198b-80a3-4432-9ca8-f9045b2caa00');