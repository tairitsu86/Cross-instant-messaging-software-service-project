CREATE TABLE "USER" (
    "USER_ID" VARCHAR(255) NOT NULL,
    "INSTANT_MESSAGING_SOFTWARE" VARCHAR(255) NOT NULL,
    "INSTANT_MESSAGING_SOFTWARE_USER_ID" VARCHAR(255) NOT NULL,
    "USER_NAME" VARCHAR(255) NOT NULL,
    PRIMARY KEY ("USER_ID")
); 

CREATE TABLE "GROUP" (
    "GROUP_ID" VARCHAR(255) NOT NULL,
    "GROUP_NAME" VARCHAR(255) NOT NULL,
    PRIMARY KEY ("GROUP_ID")
);

CREATE TABLE "MEMBER" (
    "MEMBER_ID" VARCHAR(255) NOT NULL,
    "GROUP_ID_FOREIGN_KEY" VARCHAR(255) NOT NULL,
    "USER_ID_FOREIGN_KEY" VARCHAR(255) NOT NULL,
    PRIMARY KEY ("MEMBER_ID"),
    FOREIGN KEY ("GROUP_ID_FOREIGN_KEY") REFERENCES "GROUP"("GROUP_ID"),
    FOREIGN KEY ("USER_ID_FOREIGN_KEY") REFERENCES "USER"("USER_ID")
);