INSERT INTO jbossews.USER (username, user_type, first_name, last_name, email) VALUES ('admin@mybank.p5.de', 'Admin', 'AFirst', 'ALast', 'admin@mybank.p5.de');
INSERT INTO jbossews.USER (username, user_type, first_name, last_name, email) VALUES ('bcm@mybank.p5.de', 'BCMManager', 'BCMFirst', 'BCMLast', 'bcm@mybank.p5.de');
INSERT INTO jbossews.USER (username, user_type, first_name, last_name, email) VALUES ('apartner@mybank.p5.de', 'Partner', 'APartner', 'ALast', 'apartner@mybank.p5.de');
INSERT INTO jbossews.USER (username, user_type, first_name, last_name, email) VALUES ('bpartner@mybank.p5.de', 'Partner', 'BPartner', 'BLast', 'bpartner@mybank.p5.de');
INSERT INTO jbossews.BEACON (activation_date, city, country, free_text1, free_text2, gps_latitude, gps_longitude, house, location, location_type, major_id, minor_id, partner_input, placement_date, status, street, title, uuid, zip) VALUES ('2015-10-01', 'Acity', 'Acountry', 'Free Text 1', 'Free Text 2', '48.2081743', '16.37381890000006', 'AHouse', 'ATM', 'Partner', 1, 1, 'Partner input', '2015-10-01', 'Active', 'AStreet', 'TitleA', 'aaa-xxx-123', 'AZip');
INSERT INTO jbossews.BEACON (activation_date, city, country, free_text1, free_text2, gps_latitude, gps_longitude, house, location, location_type, major_id, minor_id, partner_input, placement_date, status, street, title, uuid, zip) VALUES ('2015-09-01', 'Bcity', 'Bcountry', 'Free Text 1', 'Free Text 2', '48.2081743', '16.37381890000006', 'BHouse', 'ATM', 'Bank Filiale', 2, 2, 'Input', '2015-09-01', 'Inactive', 'BStreet', 'TitleB', 'bbb-xxx-123', 'BZip');
INSERT INTO jbossews.BEACON (activation_date, city, country, free_text1, free_text2, gps_latitude, gps_longitude, house, location, location_type, major_id, minor_id, partner_input, placement_date, status, street, title, uuid, zip) VALUES ('2015-09-01', 'Bcity', 'Bcountry', 'Free Text 1', 'Free Text 2', '48.2081743', '16.37381890000006', 'BHouse', 'ATM', 'Bank Filiale', 3, 4, 'Input', '2015-09-01', 'Inactive', 'BStreet', 'TitleB', '123-y456-67sss-fff', 'BZip');