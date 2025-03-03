insert into Users(fullname, username, password, role) values('Administrator', 'admin', '$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa', 'ADMIN');
insert into Users(fullname, username, password, role) values('User', 'user', '$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa', 'USER');

insert into Users(fullname, username, password, role) values('Moi', 'moi', '', 'USER');

insert into Trade(account, type, buyQuantity, sellQuantity, buyPrice, sellPrice, tradedate, security, status, trader, benchmark, book, creationname, creationdate, revisionname, revisiondate, dealname, dealtype, sourcelistid, side)
values('account-1', 'type-1', 10, 11, 10, 11, '2025-02-28 12:55:00', 'Security-1', 'Active', 'Trader-1', 'Benchmark-1', 'Book-1', 'CreationName-1', '2025-02-28 12:55:00', 'RevisionName-1', '2025-02-28 12:55:00', 'DealName-1', 'DealType-1', 'SourceListId-1', 'Buy');