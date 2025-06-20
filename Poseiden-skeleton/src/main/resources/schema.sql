DROP TABLE IF EXISTS BidList;
DROP TABLE IF EXISTS Trade;
DROP TABLE IF EXISTS CurvePoint;
DROP TABLE IF EXISTS Rating;
DROP TABLE IF EXISTS RuleName;
DROP TABLE IF EXISTS Users;


CREATE TABLE IF NOT EXISTS BidList (
    BidListId           tinyint(4) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    account             VARCHAR(30) NOT NULL,
    type                VARCHAR(30) NOT NULL,
    bidQuantity         DOUBLE,
    askQuantity         DOUBLE,
    bid                 DOUBLE,
    ask                 DOUBLE,
    benchmark           VARCHAR(125),
    bidListDate         TIMESTAMP,
    commentary          VARCHAR(125),
    security            VARCHAR(125),
    status              VARCHAR(10),
    trader              VARCHAR(125),
    book                VARCHAR(125),
    creationName        VARCHAR(125),
    creationDate        TIMESTAMP,
    revisionName        VARCHAR(125),
    revisionDate        TIMESTAMP,
    dealName            VARCHAR(125),
    dealType            VARCHAR(125),
    sourceListId        VARCHAR(125),
    side                VARCHAR(125)
);

CREATE TABLE IF NOT EXISTS Trade (
    TradeId             tinyint(4) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    account             VARCHAR(30) NOT NULL,
    type                VARCHAR(30) NOT NULL,
    buyQuantity         DOUBLE,
    sellQuantity        DOUBLE,
    buyPrice            DOUBLE,
    sellPrice           DOUBLE,
    tradeDate           TIMESTAMP,
    security            VARCHAR(125),
    status              VARCHAR(10),
    trader              VARCHAR(125),
    benchmark           VARCHAR(125),
    book                VARCHAR(125),
    creationName        VARCHAR(125),
    creationDate        TIMESTAMP,
    revisionName        VARCHAR(125),
    revisionDate        TIMESTAMP,
    dealName            VARCHAR(125),
    dealType            VARCHAR(125),
    sourceListId        VARCHAR(125),
    side                VARCHAR(125)
);

CREATE TABLE IF NOT EXISTS CurvePoint (
    Id                  tinyint(4) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    CurveId             tinyint,
    asOfDate            TIMESTAMP,
    term                DOUBLE,
    value               DOUBLE,
    creationDate        TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Rating (
    Id                  tinyint(4) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    moodysRating        VARCHAR(125),
    sandPRating         VARCHAR(125),
    fitchRating         VARCHAR(125),
    orderNumber         tinyint
);

CREATE TABLE IF NOT EXISTS RuleName (
    Id                  tinyint(4) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name                VARCHAR(125),
    description         VARCHAR(125),
    json                VARCHAR(125),
    template            VARCHAR(512),
    sqlStr              VARCHAR(125),
    sqlPart             VARCHAR(125)
);

CREATE TABLE IF NOT EXISTS Users (
    Id                  tinyint(4) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username            VARCHAR(125),
    password            VARCHAR(125),
    fullname            VARCHAR(125),
    role                VARCHAR(125)
);