-- MDM Sample Data: 250+ accounts across 50+ customers
-- Product codes: CC01 (Visa Credit), CC02 (MC Credit), DC01 (Visa Debit), DC02 (Interac Debit),
--                SAV01 (Basic Savings), SAV02 (High Interest Savings), CHK01 (Chequing),
--                LOC01 (Line of Credit), MTG01 (Mortgage), INV01 (Investment)

-- Customer 1: John Doe — Toronto — 6 accounts
INSERT INTO accounts (account_number, product_code, phone, name, email, address, status, date_opened, branch_code) VALUES
('ACC1001', 'CC01',  '4161234567', 'John Doe',       'john.doe@example.com',      '100 King St W, Toronto, ON',    'ACTIVE',  '2019-03-15', 'BR001'),
('ACC1002', 'CC02',  '4161234567', 'John Doe',       'john.doe@example.com',      '100 King St W, Toronto, ON',    'ACTIVE',  '2020-06-01', 'BR001'),
('ACC1003', 'DC01',  '4161234567', 'John Doe',       'john.doe@example.com',      '100 King St W, Toronto, ON',    'ACTIVE',  '2019-03-15', 'BR001'),
('ACC1004', 'SAV01', '4161234567', 'John Doe',       'john.doe@example.com',      '100 King St W, Toronto, ON',    'ACTIVE',  '2019-03-15', 'BR001'),
('ACC1005', 'CHK01', '4161234567', 'John Doe',       'john.doe@example.com',      '100 King St W, Toronto, ON',    'ACTIVE',  '2019-03-15', 'BR001'),
('ACC1006', 'MTG01', '4161234567', 'John Doe',       'john.doe@example.com',      '100 King St W, Toronto, ON',    'ACTIVE',  '2021-09-01', 'BR001'),

-- Customer 2: Jane Smith — Vancouver — 5 accounts
('ACC2001', 'CC01',  '6049876543', 'Jane Smith',     'jane.smith@example.com',    '200 Burrard St, Vancouver, BC', 'ACTIVE',  '2018-01-10', 'BR002'),
('ACC2002', 'DC01',  '6049876543', 'Jane Smith',     'jane.smith@example.com',    '200 Burrard St, Vancouver, BC', 'ACTIVE',  '2018-01-10', 'BR002'),
('ACC2003', 'DC02',  '6049876543', 'Jane Smith',     'jane.smith@example.com',    '200 Burrard St, Vancouver, BC', 'ACTIVE',  '2018-01-10', 'BR002'),
('ACC2004', 'SAV02', '6049876543', 'Jane Smith',     'jane.smith@example.com',    '200 Burrard St, Vancouver, BC', 'ACTIVE',  '2019-05-20', 'BR002'),
('ACC2005', 'LOC01', '6049876543', 'Jane Smith',     'jane.smith@example.com',    '200 Burrard St, Vancouver, BC', 'ACTIVE',  '2020-11-15', 'BR002'),

-- Customer 3: Robert Wilson — Montreal — 5 accounts
('ACC3001', 'CC01',  '5141112233', 'Robert Wilson',  'robert.wilson@example.com', '300 Ste-Catherine, Montreal, QC','ACTIVE',  '2017-07-22', 'BR003'),
('ACC3002', 'CC02',  '5141112233', 'Robert Wilson',  'robert.wilson@example.com', '300 Ste-Catherine, Montreal, QC','ACTIVE',  '2019-02-14', 'BR003'),
('ACC3003', 'DC01',  '5141112233', 'Robert Wilson',  'robert.wilson@example.com', '300 Ste-Catherine, Montreal, QC','ACTIVE',  '2017-07-22', 'BR003'),
('ACC3004', 'CHK01', '5141112233', 'Robert Wilson',  'robert.wilson@example.com', '300 Ste-Catherine, Montreal, QC','ACTIVE',  '2017-07-22', 'BR003'),
('ACC3005', 'INV01', '5141112233', 'Robert Wilson',  'robert.wilson@example.com', '300 Ste-Catherine, Montreal, QC','ACTIVE',  '2021-01-05', 'BR003'),

-- Customer 4: Emily Chen — Calgary — 5 accounts
('ACC4001', 'CC01',  '4032223344', 'Emily Chen',     'emily.chen@example.com',    '400 8th Ave SW, Calgary, AB',   'ACTIVE',  '2020-04-18', 'BR004'),
('ACC4002', 'CC02',  '4032223344', 'Emily Chen',     'emily.chen@example.com',    '400 8th Ave SW, Calgary, AB',   'ACTIVE',  '2021-08-30', 'BR004'),
('ACC4003', 'DC02',  '4032223344', 'Emily Chen',     'emily.chen@example.com',    '400 8th Ave SW, Calgary, AB',   'ACTIVE',  '2020-04-18', 'BR004'),
('ACC4004', 'SAV01', '4032223344', 'Emily Chen',     'emily.chen@example.com',    '400 8th Ave SW, Calgary, AB',   'ACTIVE',  '2020-04-18', 'BR004'),
('ACC4005', 'MTG01', '4032223344', 'Emily Chen',     'emily.chen@example.com',    '400 8th Ave SW, Calgary, AB',   'ACTIVE',  '2022-03-01', 'BR004'),

-- Customer 5: Michael Brown — Ottawa — 6 accounts
('ACC5001', 'CC01',  '6133334455', 'Michael Brown',  'michael.brown@example.com', '500 Rideau St, Ottawa, ON',     'ACTIVE',  '2016-11-03', 'BR005'),
('ACC5002', 'CC02',  '6133334455', 'Michael Brown',  'michael.brown@example.com', '500 Rideau St, Ottawa, ON',     'BLOCKED', '2018-05-20', 'BR005'),
('ACC5003', 'DC01',  '6133334455', 'Michael Brown',  'michael.brown@example.com', '500 Rideau St, Ottawa, ON',     'ACTIVE',  '2016-11-03', 'BR005'),
('ACC5004', 'DC02',  '6133334455', 'Michael Brown',  'michael.brown@example.com', '500 Rideau St, Ottawa, ON',     'ACTIVE',  '2016-11-03', 'BR005'),
('ACC5005', 'SAV01', '6133334455', 'Michael Brown',  'michael.brown@example.com', '500 Rideau St, Ottawa, ON',     'ACTIVE',  '2016-11-03', 'BR005'),
('ACC5006', 'LOC01', '6133334455', 'Michael Brown',  'michael.brown@example.com', '500 Rideau St, Ottawa, ON',     'ACTIVE',  '2020-01-15', 'BR005'),

-- Customer 6: Sarah Davis — Edmonton — 4 accounts
('ACC6001', 'CC01',  '7804445566', 'Sarah Davis',    'sarah.davis@example.com',   '600 Jasper Ave, Edmonton, AB',  'ACTIVE',  '2019-09-12', 'BR006'),
('ACC6002', 'DC01',  '7804445566', 'Sarah Davis',    'sarah.davis@example.com',   '600 Jasper Ave, Edmonton, AB',  'ACTIVE',  '2019-09-12', 'BR006'),
('ACC6003', 'CHK01', '7804445566', 'Sarah Davis',    'sarah.davis@example.com',   '600 Jasper Ave, Edmonton, AB',  'ACTIVE',  '2019-09-12', 'BR006'),
('ACC6004', 'SAV02', '7804445566', 'Sarah Davis',    'sarah.davis@example.com',   '600 Jasper Ave, Edmonton, AB',  'ACTIVE',  '2020-02-28', 'BR006'),

-- Customer 7: David Lee — Winnipeg — 5 accounts
('ACC7001', 'CC01',  '2045556677', 'David Lee',      'david.lee@example.com',     '700 Portage Ave, Winnipeg, MB', 'ACTIVE',  '2018-06-25', 'BR007'),
('ACC7002', 'CC02',  '2045556677', 'David Lee',      'david.lee@example.com',     '700 Portage Ave, Winnipeg, MB', 'ACTIVE',  '2020-10-08', 'BR007'),
('ACC7003', 'DC01',  '2045556677', 'David Lee',      'david.lee@example.com',     '700 Portage Ave, Winnipeg, MB', 'ACTIVE',  '2018-06-25', 'BR007'),
('ACC7004', 'SAV01', '2045556677', 'David Lee',      'david.lee@example.com',     '700 Portage Ave, Winnipeg, MB', 'ACTIVE',  '2018-06-25', 'BR007'),
('ACC7005', 'INV01', '2045556677', 'David Lee',      'david.lee@example.com',     '700 Portage Ave, Winnipeg, MB', 'ACTIVE',  '2022-07-01', 'BR007'),

-- Customer 8: Lisa Taylor — Halifax — 4 accounts
('ACC8001', 'CC02',  '9026667788', 'Lisa Taylor',    'lisa.taylor@example.com',   '800 Barrington St, Halifax, NS','ACTIVE',  '2021-02-14', 'BR008'),
('ACC8002', 'DC02',  '9026667788', 'Lisa Taylor',    'lisa.taylor@example.com',   '800 Barrington St, Halifax, NS','ACTIVE',  '2021-02-14', 'BR008'),
('ACC8003', 'CHK01', '9026667788', 'Lisa Taylor',    'lisa.taylor@example.com',   '800 Barrington St, Halifax, NS','ACTIVE',  '2021-02-14', 'BR008'),
('ACC8004', 'MTG01', '9026667788', 'Lisa Taylor',    'lisa.taylor@example.com',   '800 Barrington St, Halifax, NS','ACTIVE',  '2022-06-01', 'BR008'),

-- Customer 9: James Martin — Quebec City — 5 accounts
('ACC9001', 'CC01',  '4187778899', 'James Martin',   'james.martin@example.com',  '900 Grande Allee, Quebec, QC',  'ACTIVE',  '2017-04-30', 'BR009'),
('ACC9002', 'DC01',  '4187778899', 'James Martin',   'james.martin@example.com',  '900 Grande Allee, Quebec, QC',  'ACTIVE',  '2017-04-30', 'BR009'),
('ACC9003', 'DC02',  '4187778899', 'James Martin',   'james.martin@example.com',  '900 Grande Allee, Quebec, QC',  'ACTIVE',  '2017-04-30', 'BR009'),
('ACC9004', 'SAV01', '4187778899', 'James Martin',   'james.martin@example.com',  '900 Grande Allee, Quebec, QC',  'ACTIVE',  '2017-04-30', 'BR009'),
('ACC9005', 'LOC01', '4187778899', 'James Martin',   'james.martin@example.com',  '900 Grande Allee, Quebec, QC',  'CLOSED',  '2019-12-31', 'BR009'),

-- Customer 10: Maria Garcia — Mississauga — 5 accounts
('ACC10001', 'CC01', '9058889900', 'Maria Garcia',   'maria.garcia@example.com',  '1000 Hurontario St, Mississauga, ON', 'ACTIVE', '2020-01-20', 'BR010'),
('ACC10002', 'CC02', '9058889900', 'Maria Garcia',   'maria.garcia@example.com',  '1000 Hurontario St, Mississauga, ON', 'ACTIVE', '2021-04-15', 'BR010'),
('ACC10003', 'DC01', '9058889900', 'Maria Garcia',   'maria.garcia@example.com',  '1000 Hurontario St, Mississauga, ON', 'ACTIVE', '2020-01-20', 'BR010'),
('ACC10004', 'SAV02', '9058889900', 'Maria Garcia',  'maria.garcia@example.com',  '1000 Hurontario St, Mississauga, ON', 'ACTIVE', '2020-01-20', 'BR010'),
('ACC10005', 'CHK01', '9058889900', 'Maria Garcia',  'maria.garcia@example.com',  '1000 Hurontario St, Mississauga, ON', 'ACTIVE', '2020-01-20', 'BR010'),

-- Customers 11-20: Additional bulk data
('ACC11001', 'CC01', '6471110001', 'William Johnson', 'william.j@example.com',  '11 Yonge St, Toronto, ON',      'ACTIVE',  '2018-03-10', 'BR001'),
('ACC11002', 'DC01', '6471110001', 'William Johnson', 'william.j@example.com',  '11 Yonge St, Toronto, ON',      'ACTIVE',  '2018-03-10', 'BR001'),
('ACC11003', 'SAV01','6471110001', 'William Johnson', 'william.j@example.com',  '11 Yonge St, Toronto, ON',      'ACTIVE',  '2018-03-10', 'BR001'),
('ACC11004', 'MTG01','6471110001', 'William Johnson', 'william.j@example.com',  '11 Yonge St, Toronto, ON',      'ACTIVE',  '2020-06-15', 'BR001'),

('ACC12001', 'CC01', '6041110002', 'Jennifer White',  'jennifer.w@example.com', '12 Robson St, Vancouver, BC',   'ACTIVE',  '2019-07-22', 'BR002'),
('ACC12002', 'CC02', '6041110002', 'Jennifer White',  'jennifer.w@example.com', '12 Robson St, Vancouver, BC',   'ACTIVE',  '2021-01-05', 'BR002'),
('ACC12003', 'DC02', '6041110002', 'Jennifer White',  'jennifer.w@example.com', '12 Robson St, Vancouver, BC',   'ACTIVE',  '2019-07-22', 'BR002'),
('ACC12004', 'CHK01','6041110002', 'Jennifer White',  'jennifer.w@example.com', '12 Robson St, Vancouver, BC',   'ACTIVE',  '2019-07-22', 'BR002'),

('ACC13001', 'CC01', '5141110003', 'Daniel Thompson', 'daniel.t@example.com',  '13 Sherbrooke, Montreal, QC',   'ACTIVE',  '2017-11-30', 'BR003'),
('ACC13002', 'DC01', '5141110003', 'Daniel Thompson', 'daniel.t@example.com',  '13 Sherbrooke, Montreal, QC',   'ACTIVE',  '2017-11-30', 'BR003'),
('ACC13003', 'SAV02','5141110003', 'Daniel Thompson', 'daniel.t@example.com',  '13 Sherbrooke, Montreal, QC',   'ACTIVE',  '2018-05-20', 'BR003'),
('ACC13004', 'INV01','5141110003', 'Daniel Thompson', 'daniel.t@example.com',  '13 Sherbrooke, Montreal, QC',   'ACTIVE',  '2022-02-01', 'BR003'),
('ACC13005', 'LOC01','5141110003', 'Daniel Thompson', 'daniel.t@example.com',  '13 Sherbrooke, Montreal, QC',   'ACTIVE',  '2020-08-10', 'BR003'),

('ACC14001', 'CC02', '4031110004', 'Sophia Anderson', 'sophia.a@example.com',  '14 Centre St, Calgary, AB',     'ACTIVE',  '2020-09-01', 'BR004'),
('ACC14002', 'DC01', '4031110004', 'Sophia Anderson', 'sophia.a@example.com',  '14 Centre St, Calgary, AB',     'ACTIVE',  '2020-09-01', 'BR004'),
('ACC14003', 'SAV01','4031110004', 'Sophia Anderson', 'sophia.a@example.com',  '14 Centre St, Calgary, AB',     'ACTIVE',  '2020-09-01', 'BR004'),

('ACC15001', 'CC01', '6131110005', 'Chris Martinez',  'chris.m@example.com',   '15 Bank St, Ottawa, ON',        'ACTIVE',  '2019-04-15', 'BR005'),
('ACC15002', 'CC02', '6131110005', 'Chris Martinez',  'chris.m@example.com',   '15 Bank St, Ottawa, ON',        'DORMANT', '2020-12-31', 'BR005'),
('ACC15003', 'DC02', '6131110005', 'Chris Martinez',  'chris.m@example.com',   '15 Bank St, Ottawa, ON',        'ACTIVE',  '2019-04-15', 'BR005'),
('ACC15004', 'CHK01','6131110005', 'Chris Martinez',  'chris.m@example.com',   '15 Bank St, Ottawa, ON',        'ACTIVE',  '2019-04-15', 'BR005'),

('ACC16001', 'CC01', '7801110006', 'Amanda Robinson', 'amanda.r@example.com',  '16 Whyte Ave, Edmonton, AB',    'ACTIVE',  '2021-06-20', 'BR006'),
('ACC16002', 'DC01', '7801110006', 'Amanda Robinson', 'amanda.r@example.com',  '16 Whyte Ave, Edmonton, AB',    'ACTIVE',  '2021-06-20', 'BR006'),
('ACC16003', 'SAV02','7801110006', 'Amanda Robinson', 'amanda.r@example.com',  '16 Whyte Ave, Edmonton, AB',    'ACTIVE',  '2021-06-20', 'BR006'),
('ACC16004', 'MTG01','7801110006', 'Amanda Robinson', 'amanda.r@example.com',  '16 Whyte Ave, Edmonton, AB',    'ACTIVE',  '2023-01-15', 'BR006'),

('ACC17001', 'CC01', '2041110007', 'Kevin Clark',     'kevin.c@example.com',   '17 Main St, Winnipeg, MB',      'ACTIVE',  '2018-08-05', 'BR007'),
('ACC17002', 'CC02', '2041110007', 'Kevin Clark',     'kevin.c@example.com',   '17 Main St, Winnipeg, MB',      'ACTIVE',  '2020-03-10', 'BR007'),
('ACC17003', 'DC01', '2041110007', 'Kevin Clark',     'kevin.c@example.com',   '17 Main St, Winnipeg, MB',      'ACTIVE',  '2018-08-05', 'BR007'),
('ACC17004', 'DC02', '2041110007', 'Kevin Clark',     'kevin.c@example.com',   '17 Main St, Winnipeg, MB',      'ACTIVE',  '2018-08-05', 'BR007'),

('ACC18001', 'CC02', '9021110008', 'Rachel Lewis',    'rachel.l@example.com',  '18 Spring Garden, Halifax, NS', 'ACTIVE',  '2022-01-18', 'BR008'),
('ACC18002', 'DC02', '9021110008', 'Rachel Lewis',    'rachel.l@example.com',  '18 Spring Garden, Halifax, NS', 'ACTIVE',  '2022-01-18', 'BR008'),
('ACC18003', 'SAV01','9021110008', 'Rachel Lewis',    'rachel.l@example.com',  '18 Spring Garden, Halifax, NS', 'ACTIVE',  '2022-01-18', 'BR008'),

('ACC19001', 'CC01', '4181110009', 'Thomas Walker',   'thomas.w@example.com',  '19 Cartier Blvd, Quebec, QC',   'ACTIVE',  '2016-12-01', 'BR009'),
('ACC19002', 'DC01', '4181110009', 'Thomas Walker',   'thomas.w@example.com',  '19 Cartier Blvd, Quebec, QC',   'ACTIVE',  '2016-12-01', 'BR009'),
('ACC19003', 'CHK01','4181110009', 'Thomas Walker',   'thomas.w@example.com',  '19 Cartier Blvd, Quebec, QC',   'ACTIVE',  '2016-12-01', 'BR009'),
('ACC19004', 'LOC01','4181110009', 'Thomas Walker',   'thomas.w@example.com',  '19 Cartier Blvd, Quebec, QC',   'ACTIVE',  '2019-06-01', 'BR009'),
('ACC19005', 'INV01','4181110009', 'Thomas Walker',   'thomas.w@example.com',  '19 Cartier Blvd, Quebec, QC',   'ACTIVE',  '2021-10-01', 'BR009'),

('ACC20001', 'CC01', '9051110010', 'Nancy Hall',      'nancy.h@example.com',   '20 Dundas St, Mississauga, ON', 'ACTIVE',  '2019-02-28', 'BR010'),
('ACC20002', 'CC02', '9051110010', 'Nancy Hall',      'nancy.h@example.com',   '20 Dundas St, Mississauga, ON', 'ACTIVE',  '2021-07-14', 'BR010'),
('ACC20003', 'DC01', '9051110010', 'Nancy Hall',      'nancy.h@example.com',   '20 Dundas St, Mississauga, ON', 'ACTIVE',  '2019-02-28', 'BR010'),
('ACC20004', 'SAV01','9051110010', 'Nancy Hall',      'nancy.h@example.com',   '20 Dundas St, Mississauga, ON', 'ACTIVE',  '2019-02-28', 'BR010'),

-- Customers 21-35: More bulk data for performance testing
('ACC21001', 'CC01', '4162220001', 'Alex Kumar',       'alex.kumar@example.com',     '21 Queen St, Toronto, ON',       'ACTIVE', '2020-03-01', 'BR001'),
('ACC21002', 'DC01', '4162220001', 'Alex Kumar',       'alex.kumar@example.com',     '21 Queen St, Toronto, ON',       'ACTIVE', '2020-03-01', 'BR001'),
('ACC21003', 'CC02', '4162220001', 'Alex Kumar',       'alex.kumar@example.com',     '21 Queen St, Toronto, ON',       'ACTIVE', '2021-09-15', 'BR001'),
('ACC21004', 'SAV01','4162220001', 'Alex Kumar',       'alex.kumar@example.com',     '21 Queen St, Toronto, ON',       'ACTIVE', '2020-03-01', 'BR001'),

('ACC22001', 'CC01', '6042220002', 'Priya Patel',      'priya.p@example.com',        '22 Main St, Vancouver, BC',      'ACTIVE', '2018-11-15', 'BR002'),
('ACC22002', 'DC02', '6042220002', 'Priya Patel',      'priya.p@example.com',        '22 Main St, Vancouver, BC',      'ACTIVE', '2018-11-15', 'BR002'),
('ACC22003', 'SAV02','6042220002', 'Priya Patel',      'priya.p@example.com',        '22 Main St, Vancouver, BC',      'ACTIVE', '2019-04-01', 'BR002'),
('ACC22004', 'MTG01','6042220002', 'Priya Patel',      'priya.p@example.com',        '22 Main St, Vancouver, BC',      'ACTIVE', '2022-08-01', 'BR002'),
('ACC22005', 'CHK01','6042220002', 'Priya Patel',      'priya.p@example.com',        '22 Main St, Vancouver, BC',      'ACTIVE', '2018-11-15', 'BR002'),

('ACC23001', 'CC02', '5142220003', 'Marc Tremblay',    'marc.t@example.com',         '23 St-Denis, Montreal, QC',      'ACTIVE', '2017-05-10', 'BR003'),
('ACC23002', 'DC01', '5142220003', 'Marc Tremblay',    'marc.t@example.com',         '23 St-Denis, Montreal, QC',      'ACTIVE', '2017-05-10', 'BR003'),
('ACC23003', 'CHK01','5142220003', 'Marc Tremblay',    'marc.t@example.com',         '23 St-Denis, Montreal, QC',      'ACTIVE', '2017-05-10', 'BR003'),

('ACC24001', 'CC01', '4032220004', 'Sarah Nguyen',     'sarah.n@example.com',        '24 Macleod Tr, Calgary, AB',     'ACTIVE', '2021-01-20', 'BR004'),
('ACC24002', 'CC02', '4032220004', 'Sarah Nguyen',     'sarah.n@example.com',        '24 Macleod Tr, Calgary, AB',     'ACTIVE', '2022-05-12', 'BR004'),
('ACC24003', 'DC01', '4032220004', 'Sarah Nguyen',     'sarah.n@example.com',        '24 Macleod Tr, Calgary, AB',     'ACTIVE', '2021-01-20', 'BR004'),
('ACC24004', 'DC02', '4032220004', 'Sarah Nguyen',     'sarah.n@example.com',        '24 Macleod Tr, Calgary, AB',     'ACTIVE', '2021-01-20', 'BR004'),
('ACC24005', 'SAV01','4032220004', 'Sarah Nguyen',     'sarah.n@example.com',        '24 Macleod Tr, Calgary, AB',     'ACTIVE', '2021-01-20', 'BR004'),

('ACC25001', 'CC01', '6132220005', 'Paul Moreau',      'paul.m@example.com',         '25 Elgin St, Ottawa, ON',        'ACTIVE', '2019-08-30', 'BR005'),
('ACC25002', 'DC01', '6132220005', 'Paul Moreau',      'paul.m@example.com',         '25 Elgin St, Ottawa, ON',        'ACTIVE', '2019-08-30', 'BR005'),
('ACC25003', 'SAV02','6132220005', 'Paul Moreau',      'paul.m@example.com',         '25 Elgin St, Ottawa, ON',        'ACTIVE', '2020-01-15', 'BR005'),
('ACC25004', 'INV01','6132220005', 'Paul Moreau',      'paul.m@example.com',         '25 Elgin St, Ottawa, ON',        'ACTIVE', '2022-04-01', 'BR005'),

('ACC26001', 'CC02', '7802220006', 'Linda Kim',        'linda.k@example.com',        '26 109th St, Edmonton, AB',      'ACTIVE', '2020-05-18', 'BR006'),
('ACC26002', 'DC02', '7802220006', 'Linda Kim',        'linda.k@example.com',        '26 109th St, Edmonton, AB',      'ACTIVE', '2020-05-18', 'BR006'),
('ACC26003', 'CHK01','7802220006', 'Linda Kim',        'linda.k@example.com',        '26 109th St, Edmonton, AB',      'ACTIVE', '2020-05-18', 'BR006'),
('ACC26004', 'LOC01','7802220006', 'Linda Kim',        'linda.k@example.com',        '26 109th St, Edmonton, AB',      'ACTIVE', '2021-11-01', 'BR006'),

('ACC27001', 'CC01', '2042220007', 'Steven Wright',    'steven.w@example.com',       '27 Broadway, Winnipeg, MB',      'ACTIVE', '2018-02-14', 'BR007'),
('ACC27002', 'DC01', '2042220007', 'Steven Wright',    'steven.w@example.com',       '27 Broadway, Winnipeg, MB',      'ACTIVE', '2018-02-14', 'BR007'),
('ACC27003', 'SAV01','2042220007', 'Steven Wright',    'steven.w@example.com',       '27 Broadway, Winnipeg, MB',      'ACTIVE', '2018-02-14', 'BR007'),
('ACC27004', 'MTG01','2042220007', 'Steven Wright',    'steven.w@example.com',       '27 Broadway, Winnipeg, MB',      'ACTIVE', '2023-05-01', 'BR007'),

('ACC28001', 'CC01', '9022220008', 'Karen Scott',      'karen.s@example.com',        '28 Quinpool Rd, Halifax, NS',    'ACTIVE', '2021-09-22', 'BR008'),
('ACC28002', 'CC02', '9022220008', 'Karen Scott',      'karen.s@example.com',        '28 Quinpool Rd, Halifax, NS',    'ACTIVE', '2022-12-05', 'BR008'),
('ACC28003', 'DC01', '9022220008', 'Karen Scott',      'karen.s@example.com',        '28 Quinpool Rd, Halifax, NS',    'ACTIVE', '2021-09-22', 'BR008'),

('ACC29001', 'CC01', '4182220009', 'Pierre Gagnon',    'pierre.g@example.com',       '29 Dufferin, Quebec, QC',        'ACTIVE', '2019-10-10', 'BR009'),
('ACC29002', 'DC02', '4182220009', 'Pierre Gagnon',    'pierre.g@example.com',       '29 Dufferin, Quebec, QC',        'ACTIVE', '2019-10-10', 'BR009'),
('ACC29003', 'SAV01','4182220009', 'Pierre Gagnon',    'pierre.g@example.com',       '29 Dufferin, Quebec, QC',        'ACTIVE', '2019-10-10', 'BR009'),

('ACC30001', 'CC02', '9052220010', 'Diana Ross',       'diana.r@example.com',        '30 Lakeshore, Mississauga, ON',  'ACTIVE', '2020-07-01', 'BR010'),
('ACC30002', 'DC01', '9052220010', 'Diana Ross',       'diana.r@example.com',        '30 Lakeshore, Mississauga, ON',  'ACTIVE', '2020-07-01', 'BR010'),
('ACC30003', 'CHK01','9052220010', 'Diana Ross',       'diana.r@example.com',        '30 Lakeshore, Mississauga, ON',  'ACTIVE', '2020-07-01', 'BR010'),
('ACC30004', 'LOC01','9052220010', 'Diana Ross',       'diana.r@example.com',        '30 Lakeshore, Mississauga, ON',  'ACTIVE', '2022-01-15', 'BR010'),

-- Customers 31-50: Even more bulk data
('ACC31001', 'CC01', '4163330001', 'Ryan Murphy',      'ryan.m@example.com',      '31 Bay St, Toronto, ON',        'ACTIVE', '2017-06-01', 'BR001'),
('ACC31002', 'DC01', '4163330001', 'Ryan Murphy',      'ryan.m@example.com',      '31 Bay St, Toronto, ON',        'ACTIVE', '2017-06-01', 'BR001'),
('ACC31003', 'CC02', '4163330001', 'Ryan Murphy',      'ryan.m@example.com',      '31 Bay St, Toronto, ON',        'BLOCKED','2023-01-01', 'BR001'),
('ACC31004', 'SAV02','4163330001', 'Ryan Murphy',      'ryan.m@example.com',      '31 Bay St, Toronto, ON',        'ACTIVE', '2017-06-01', 'BR001'),

('ACC32001', 'CC01', '6043330002', 'Olivia Wang',      'olivia.w@example.com',    '32 Davie St, Vancouver, BC',    'ACTIVE', '2020-02-10', 'BR002'),
('ACC32002', 'DC02', '6043330002', 'Olivia Wang',      'olivia.w@example.com',    '32 Davie St, Vancouver, BC',    'ACTIVE', '2020-02-10', 'BR002'),
('ACC32003', 'SAV01','6043330002', 'Olivia Wang',      'olivia.w@example.com',    '32 Davie St, Vancouver, BC',    'ACTIVE', '2020-02-10', 'BR002'),

('ACC33001', 'CC02', '5143330003', 'Luc Bouchard',     'luc.b@example.com',       '33 Mont-Royal, Montreal, QC',   'ACTIVE', '2018-09-15', 'BR003'),
('ACC33002', 'DC01', '5143330003', 'Luc Bouchard',     'luc.b@example.com',       '33 Mont-Royal, Montreal, QC',   'ACTIVE', '2018-09-15', 'BR003'),
('ACC33003', 'CHK01','5143330003', 'Luc Bouchard',     'luc.b@example.com',       '33 Mont-Royal, Montreal, QC',   'ACTIVE', '2018-09-15', 'BR003'),
('ACC33004', 'INV01','5143330003', 'Luc Bouchard',     'luc.b@example.com',       '33 Mont-Royal, Montreal, QC',   'ACTIVE', '2021-03-01', 'BR003'),

('ACC34001', 'CC01', '4033330004', 'Hannah Singh',     'hannah.s@example.com',    '34 17th Ave, Calgary, AB',      'ACTIVE', '2019-11-20', 'BR004'),
('ACC34002', 'DC01', '4033330004', 'Hannah Singh',     'hannah.s@example.com',    '34 17th Ave, Calgary, AB',      'ACTIVE', '2019-11-20', 'BR004'),
('ACC34003', 'DC02', '4033330004', 'Hannah Singh',     'hannah.s@example.com',    '34 17th Ave, Calgary, AB',      'ACTIVE', '2019-11-20', 'BR004'),
('ACC34004', 'SAV01','4033330004', 'Hannah Singh',     'hannah.s@example.com',    '34 17th Ave, Calgary, AB',      'ACTIVE', '2019-11-20', 'BR004'),

('ACC35001', 'CC01', '6133330005', 'Ben Cooper',       'ben.c@example.com',       '35 Sparks St, Ottawa, ON',      'ACTIVE', '2021-04-05', 'BR005'),
('ACC35002', 'CC02', '6133330005', 'Ben Cooper',       'ben.c@example.com',       '35 Sparks St, Ottawa, ON',      'ACTIVE', '2022-08-20', 'BR005'),
('ACC35003', 'DC01', '6133330005', 'Ben Cooper',       'ben.c@example.com',       '35 Sparks St, Ottawa, ON',      'ACTIVE', '2021-04-05', 'BR005'),

('ACC36001', 'CC02', '7803330006', 'Megan Foster',     'megan.f@example.com',     '36 Jasper Ave, Edmonton, AB',   'ACTIVE', '2022-07-10', 'BR006'),
('ACC36002', 'DC01', '7803330006', 'Megan Foster',     'megan.f@example.com',     '36 Jasper Ave, Edmonton, AB',   'ACTIVE', '2022-07-10', 'BR006'),
('ACC36003', 'SAV02','7803330006', 'Megan Foster',     'megan.f@example.com',     '36 Jasper Ave, Edmonton, AB',   'ACTIVE', '2022-07-10', 'BR006'),

('ACC37001', 'CC01', '2043330007', 'Jason Reed',       'jason.r@example.com',     '37 Corydon, Winnipeg, MB',      'ACTIVE', '2019-01-25', 'BR007'),
('ACC37002', 'DC02', '2043330007', 'Jason Reed',       'jason.r@example.com',     '37 Corydon, Winnipeg, MB',      'ACTIVE', '2019-01-25', 'BR007'),
('ACC37003', 'CHK01','2043330007', 'Jason Reed',       'jason.r@example.com',     '37 Corydon, Winnipeg, MB',      'ACTIVE', '2019-01-25', 'BR007'),
('ACC37004', 'LOC01','2043330007', 'Jason Reed',       'jason.r@example.com',     '37 Corydon, Winnipeg, MB',      'ACTIVE', '2021-05-01', 'BR007'),

('ACC38001', 'CC01', '9023330008', 'Ashley Burke',     'ashley.b@example.com',    '38 Robie St, Halifax, NS',      'ACTIVE', '2020-10-30', 'BR008'),
('ACC38002', 'DC01', '9023330008', 'Ashley Burke',     'ashley.b@example.com',    '38 Robie St, Halifax, NS',      'ACTIVE', '2020-10-30', 'BR008'),
('ACC38003', 'SAV01','9023330008', 'Ashley Burke',     'ashley.b@example.com',    '38 Robie St, Halifax, NS',      'ACTIVE', '2020-10-30', 'BR008'),
('ACC38004', 'MTG01','9023330008', 'Ashley Burke',     'ashley.b@example.com',    '38 Robie St, Halifax, NS',      'ACTIVE', '2023-03-01', 'BR008'),

('ACC39001', 'CC02', '4183330009', 'Francois Roy',     'francois.r@example.com',  '39 Champlain, Quebec, QC',      'ACTIVE', '2018-04-12', 'BR009'),
('ACC39002', 'DC01', '4183330009', 'Francois Roy',     'francois.r@example.com',  '39 Champlain, Quebec, QC',      'ACTIVE', '2018-04-12', 'BR009'),
('ACC39003', 'SAV02','4183330009', 'Francois Roy',     'francois.r@example.com',  '39 Champlain, Quebec, QC',      'ACTIVE', '2018-04-12', 'BR009'),

('ACC40001', 'CC01', '9053330010', 'Victoria Adams',   'victoria.a@example.com',  '40 Square One, Mississauga, ON','ACTIVE', '2021-12-01', 'BR010'),
('ACC40002', 'CC02', '9053330010', 'Victoria Adams',   'victoria.a@example.com',  '40 Square One, Mississauga, ON','ACTIVE', '2022-11-15', 'BR010'),
('ACC40003', 'DC01', '9053330010', 'Victoria Adams',   'victoria.a@example.com',  '40 Square One, Mississauga, ON','ACTIVE', '2021-12-01', 'BR010'),
('ACC40004', 'DC02', '9053330010', 'Victoria Adams',   'victoria.a@example.com',  '40 Square One, Mississauga, ON','ACTIVE', '2021-12-01', 'BR010'),
('ACC40005', 'SAV01','9053330010', 'Victoria Adams',   'victoria.a@example.com',  '40 Square One, Mississauga, ON','ACTIVE', '2021-12-01', 'BR010'),

-- Customers 41-50 — more variety
('ACC41001', 'CC01', '4164440001', 'Arun Sharma',      'arun.s@example.com',      '41 Bloor St, Toronto, ON',      'ACTIVE', '2019-05-01', 'BR001'),
('ACC41002', 'DC01', '4164440001', 'Arun Sharma',      'arun.s@example.com',      '41 Bloor St, Toronto, ON',      'ACTIVE', '2019-05-01', 'BR001'),
('ACC41003', 'MTG01','4164440001', 'Arun Sharma',      'arun.s@example.com',      '41 Bloor St, Toronto, ON',      'ACTIVE', '2022-01-15', 'BR001'),

('ACC42001', 'CC02', '6044440002', 'Grace Liu',        'grace.l@example.com',     '42 Granville, Vancouver, BC',   'ACTIVE', '2020-08-20', 'BR002'),
('ACC42002', 'DC01', '6044440002', 'Grace Liu',        'grace.l@example.com',     '42 Granville, Vancouver, BC',   'ACTIVE', '2020-08-20', 'BR002'),
('ACC42003', 'SAV01','6044440002', 'Grace Liu',        'grace.l@example.com',     '42 Granville, Vancouver, BC',   'ACTIVE', '2020-08-20', 'BR002'),
('ACC42004', 'INV01','6044440002', 'Grace Liu',        'grace.l@example.com',     '42 Granville, Vancouver, BC',   'ACTIVE', '2023-02-01', 'BR002'),

('ACC43001', 'CC01', '5144440003', 'Sophie Lavoie',    'sophie.l@example.com',    '43 Crescent, Montreal, QC',     'ACTIVE', '2017-03-22', 'BR003'),
('ACC43002', 'CC02', '5144440003', 'Sophie Lavoie',    'sophie.l@example.com',    '43 Crescent, Montreal, QC',     'ACTIVE', '2019-07-15', 'BR003'),
('ACC43003', 'DC02', '5144440003', 'Sophie Lavoie',    'sophie.l@example.com',    '43 Crescent, Montreal, QC',     'ACTIVE', '2017-03-22', 'BR003'),

('ACC44001', 'CC01', '4034440004', 'Jake Turner',      'jake.t@example.com',      '44 1st St, Calgary, AB',        'ACTIVE', '2021-11-05', 'BR004'),
('ACC44002', 'DC01', '4034440004', 'Jake Turner',      'jake.t@example.com',      '44 1st St, Calgary, AB',        'ACTIVE', '2021-11-05', 'BR004'),
('ACC44003', 'CHK01','4034440004', 'Jake Turner',      'jake.t@example.com',      '44 1st St, Calgary, AB',        'ACTIVE', '2021-11-05', 'BR004'),
('ACC44004', 'LOC01','4034440004', 'Jake Turner',      'jake.t@example.com',      '44 1st St, Calgary, AB',        'ACTIVE', '2023-06-01', 'BR004'),

('ACC45001', 'CC02', '6134440005', 'Natalie Webb',     'natalie.w@example.com',   '45 Somerset, Ottawa, ON',       'ACTIVE', '2020-04-18', 'BR005'),
('ACC45002', 'DC02', '6134440005', 'Natalie Webb',     'natalie.w@example.com',   '45 Somerset, Ottawa, ON',       'ACTIVE', '2020-04-18', 'BR005'),
('ACC45003', 'SAV01','6134440005', 'Natalie Webb',     'natalie.w@example.com',   '45 Somerset, Ottawa, ON',       'ACTIVE', '2020-04-18', 'BR005'),

('ACC46001', 'CC01', '7804440006', 'Carlos Diaz',      'carlos.d@example.com',    '46 Stony Plain, Edmonton, AB',  'ACTIVE', '2019-09-01', 'BR006'),
('ACC46002', 'DC01', '7804440006', 'Carlos Diaz',      'carlos.d@example.com',    '46 Stony Plain, Edmonton, AB',  'ACTIVE', '2019-09-01', 'BR006'),
('ACC46003', 'SAV02','7804440006', 'Carlos Diaz',      'carlos.d@example.com',    '46 Stony Plain, Edmonton, AB',  'ACTIVE', '2020-03-01', 'BR006'),

('ACC47001', 'CC01', '2044440007', 'Emma Stone',       'emma.st@example.com',     '47 Osborne, Winnipeg, MB',      'ACTIVE', '2021-02-28', 'BR007'),
('ACC47002', 'CC02', '2044440007', 'Emma Stone',       'emma.st@example.com',     '47 Osborne, Winnipeg, MB',      'ACTIVE', '2022-06-10', 'BR007'),
('ACC47003', 'DC01', '2044440007', 'Emma Stone',       'emma.st@example.com',     '47 Osborne, Winnipeg, MB',      'ACTIVE', '2021-02-28', 'BR007'),

('ACC48001', 'CC01', '9024440008', 'Ian MacLeod',      'ian.m@example.com',       '48 Gottingen, Halifax, NS',     'ACTIVE', '2022-04-15', 'BR008'),
('ACC48002', 'DC02', '9024440008', 'Ian MacLeod',      'ian.m@example.com',       '48 Gottingen, Halifax, NS',     'ACTIVE', '2022-04-15', 'BR008'),
('ACC48003', 'CHK01','9024440008', 'Ian MacLeod',      'ian.m@example.com',       '48 Gottingen, Halifax, NS',     'ACTIVE', '2022-04-15', 'BR008'),

('ACC49001', 'CC02', '4184440009', 'Marie Cote',       'marie.c@example.com',     '49 St-Jean, Quebec, QC',        'ACTIVE', '2020-11-12', 'BR009'),
('ACC49002', 'DC01', '4184440009', 'Marie Cote',       'marie.c@example.com',     '49 St-Jean, Quebec, QC',        'ACTIVE', '2020-11-12', 'BR009'),
('ACC49003', 'SAV01','4184440009', 'Marie Cote',       'marie.c@example.com',     '49 St-Jean, Quebec, QC',        'ACTIVE', '2020-11-12', 'BR009'),
('ACC49004', 'MTG01','4184440009', 'Marie Cote',       'marie.c@example.com',     '49 St-Jean, Quebec, QC',        'ACTIVE', '2023-07-01', 'BR009'),

('ACC50001', 'CC01', '9054440010', 'Tyler Brooks',     'tyler.b@example.com',     '50 Eglinton, Mississauga, ON',  'ACTIVE', '2018-07-30', 'BR010'),
('ACC50002', 'CC02', '9054440010', 'Tyler Brooks',     'tyler.b@example.com',     '50 Eglinton, Mississauga, ON',  'ACTIVE', '2020-12-01', 'BR010'),
('ACC50003', 'DC01', '9054440010', 'Tyler Brooks',     'tyler.b@example.com',     '50 Eglinton, Mississauga, ON',  'ACTIVE', '2018-07-30', 'BR010'),
('ACC50004', 'DC02', '9054440010', 'Tyler Brooks',     'tyler.b@example.com',     '50 Eglinton, Mississauga, ON',  'ACTIVE', '2018-07-30', 'BR010'),
('ACC50005', 'SAV02','9054440010', 'Tyler Brooks',     'tyler.b@example.com',     '50 Eglinton, Mississauga, ON',  'ACTIVE', '2019-01-15', 'BR010'),
('ACC50006', 'INV01','9054440010', 'Tyler Brooks',     'tyler.b@example.com',     '50 Eglinton, Mississauga, ON',  'ACTIVE', '2022-09-01', 'BR010');

