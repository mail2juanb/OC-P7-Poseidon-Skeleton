insert into Users(fullname, username, password, role) values
    ('Administrator', 'admin', '$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa', 'ADMIN'),
    ('user', 'user', '$2a$10$X5eEon.VJnN1S8nggslRgeHLR.oNfQRvMm4TY/ENhbJbBmJ/Jf6Y.', 'USER'),
    ('Moi', 'moi', '', 'USER');

insert into Trade(account, type, buyquantity, sellquantity, buyprice, sellprice, tradedate, security, status, trader, benchmark, book, creationname, creationdate, revisionname, revisiondate, dealname, dealtype, sourcelistid, side) values
    ('account-1', 'type-1', 10, 11, 10, 11, '2025-02-28 12:55:00', 'Security-1', 'Active', 'Trader-1', 'Benchmark-1', 'Book-1', 'CreationName-1', '2025-02-28 12:55:00', 'RevisionName-1', '2025-02-28 12:55:00', 'DealName-1', 'DealType-1', 'SourceListId-1', 'Buy');

insert into RuleName(name, description, json, template, sqlstr, sqlpart) values
    ('Rule A', 'Description of Rule A', 'json A', 'Template A', 'sqlstr A', 'sqlpart A');

insert into Rating(moodysrating, sandprating, fitchrating, ordernumber) values
    ('moodysRating A', 'sandPRating A', 'fitchRating A', 1);

insert into CurvePoint(CurveId, asOfDate, term, value, creationDate) values
    (1, '2025-02-28 12:55:00', 1, 1, '2025-02-28 12:55:00');

insert into BidList(account, type, bidQuantity, askQuantity, bid, ask, benchmark, bidListDate, commentary, security, status, trader, book, creationName, creationDate, revisionName, revisionDate, dealName, dealType, sourceListId, side) values
    ('account-1', 'type-1', 1, 2, 3, 4, 'benchmark-1', '2025-02-28 12:55:00', 'commentary-1', 'security-1', 'status-1', 'trader-1', 'book-1', 'creationName-1', '2025-02-28 12:55:00', 'revisionName-1', '2025-02-28 12:55:00', 'dealName-1', 'dealType-1', 'sourceListId-1', 'side-1');