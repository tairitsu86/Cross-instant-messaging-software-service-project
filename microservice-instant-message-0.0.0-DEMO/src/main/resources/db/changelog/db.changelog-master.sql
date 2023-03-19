IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = N'user')
	BEGIN
		CREATE TABLE [dbo].[user](
			[user_id] [NVARCHAR](255) NOT NULL CONSTRAINT user_id_pk PRIMARY KEY CLUSTERED,
			[instant_messaging_software] [NVARCHAR](255) NULL,
			[instant_messaging_software_user_id] [NVARCHAR](255) NULL,
			[user_name] [NVARCHAR](255) NULL,
			[enable] [BIT] DEFAULT 1,
			[register_time] DATETIME2 DEFAULT SYSDATETIME()
		) ON [PRIMARY]
	END
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = N'group')
	BEGIN
		CREATE TABLE [dbo].[group](
			[group_id] [NVARCHAR](255) NOT NULL CONSTRAINT group_id_pk PRIMARY KEY CLUSTERED,
			[group_name] [NVARCHAR](255) NULL,
			[enable] [BIT] DEFAULT 1,
			[create_time] DATETIME2 DEFAULT SYSDATETIME()
		) ON [PRIMARY]
	END
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = N'member')
	BEGIN
		CREATE TABLE [dbo].[member](
			[member_id] [NVARCHAR](255) NOT NULL CONSTRAINT member_id_pk PRIMARY KEY,
			[group_id_foreign_key] [NVARCHAR](255) NOT NULL CONSTRAINT group_id_fk FOREIGN KEY REFERENCES [group]([group_id]),
			[user_id_foreign_key] [NVARCHAR](255) NOT NULL CONSTRAINT user_id_fk FOREIGN KEY REFERENCES [user]([user_id]),
			[enable] [BIT] DEFAULT 1,
			[join_time] DATETIME2 DEFAULT SYSDATETIME()
		) ON [PRIMARY]
	END
