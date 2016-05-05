ALTER TABLE users DROP CONSTRAINT users_user_categories

ALTER TABLE book_descriptions DROP CONSTRAINT book_descriptions_book_categories

ALTER TABLE book_descriptions DROP CONSTRAINT book_descriptions_languages

ALTER TABLE real_books DROP CONSTRAINT real_books_book_descriptions

ALTER TABLE electronic_books DROP CONSTRAINT electronic_books_book_descriptions

ALTER TABLE queues DROP CONSTRAINT queues_users

ALTER TABLE queues DROP CONSTRAINT queues_real_books

ALTER TABLE comments DROP CONSTRAINT comments_users

ALTER TABLE comments DROP CONSTRAINT comments_electronic_books

ALTER TABLE divisions DROP CONSTRAINT divisions_departments


ALTER TABLE privileges_real DROP CONSTRAINT privileges_real_user_categories

ALTER TABLE privileges_real DROP CONSTRAINT privileges_real_real_books

ALTER TABLE privileges_electronic DROP CONSTRAINT privileges_electronic_user_categories

ALTER TABLE privileges_electronic DROP CONSTRAINT privileges_electronic_electronic_books

---TABLES

DROP TABLE departments
CREATE TABLE departments
	(id INT identity PRIMARY KEY,
	 name VARCHAR(50) NOT NULL UNIQUE
)

DROP TABLE divisions
CREATE TABLE divisions
	(id INT identity PRIMARY KEY,
	 name VARCHAR(50) NOT NULL UNIQUE,
   departmentId INT NOT NULL
)

DROP TABLE users
CREATE TABLE users
	(id INT identity PRIMARY KEY,
	 login VARCHAR(50) NOT NULL UNIQUE,
	 password VARCHAR(50) NOT NULL,
	 firstName VARCHAR(50) NOT NULL,
   secondName VARCHAR(50) NOT NULL,
	 email VARCHAR(50) NOT NULL,
	 roleId  VARCHAR(20) NOT NULL DEFAULT 'NEW',
	 categoryId INT NOT NULL DEFAULT 1,
	 divisionId INT 
	
	 )

DROP TABLE user_categories
CREATE TABLE user_categories
	(id INT identity PRIMARY KEY,
	 name VARCHAR(50) NOT NULL UNIQUE
)

DROP TABLE book_descriptions
CREATE TABLE book_descriptions
	(id INT identity PRIMARY KEY,
	 name VARCHAR(50) NOT NULL,
	 author VARCHAR(50) NOT NULL,
	 bookCategoryId INT NOT NULL,
	 publicationPlace VARCHAR(50) NOT NULL,
	 publicationYear INT NOT NULL,
	 size INT NOT NULL,
	 languageId INT NOT NULL
	 )

DROP TABLE book_categories
CREATE TABLE book_categories
	(id INT identity PRIMARY KEY,
	 name NVARCHAR(50) NOT NULL UNIQUE
)

DROP TABLE real_books
CREATE TABLE real_books
	(id INT identity PRIMARY KEY,
	 bookDescriptionId INT NOT NULL,
	 inventoryNumber INT NOT NULL,
	 cost INT NOT NULL
	 UNIQUE (inventoryNumber))

DROP TABLE debts
CREATE TABLE debts
	(id INT identity PRIMARY KEY,
	 realBookId INT NOT NULL,
	 userId INT NOT NULL,
	 startPeriod DATETIME NOT NULL,
	 endPeriod DATETIME NOT NULL)



DROP TABLE languages
CREATE TABLE languages
	(id INT identity PRIMARY KEY,
	 name VARCHAR(50) NOT NULL UNIQUE)

DROP TABLE comments
CREATE TABLE comments
	(id INT identity PRIMARY KEY,
	 userId INT NOT NULL,
     date DATETIME NOT NULL,
	 message VARCHAR(1000) NOT NULL,
	 electronicBookId INT NOT NULL)

DROP TABLE electronic_books
CREATE TABLE electronic_books
	(id INT identity PRIMARY KEY,
	 bookDescriptionId INT NOT NULL,
	 fileName VARCHAR(50) NOT NULL UNIQUE,
	 capacity INT,
  	 extension VARCHAR(5))

DROP TABLE queues
CREATE TABLE queues
	(id INT identity PRIMARY KEY,
	 userId INT NOT NULL,
	 realBookId INT NOT NULL,
	 date DATETIME NOT NULL
	 UNIQUE (userId ,realBookId))

DROP TABLE privileges_real
CREATE TABLE privileges_real
	(id INT identity PRIMARY KEY,
   	 userCategoryId INT NOT NULL,
     realBookId INT NOT NULL)

DROP TABLE privileges_electronic
CREATE TABLE privileges_electronic
	(id INT identity PRIMARY KEY,
   	 userCategoryId INT NOT NULL,
     electronicBookId INT NOT NULL)

DROP TABLE reports
CREATE TABLE reports
	(id INT identity PRIMARY KEY,
     librarianId INT NOT NULL,
   	 date DATETIME NOT NULL,
     operation VARCHAR(50),
     startPeriod DATETIME NOT NULL,
     endPeriod DATETIME NOT NULL,
     realBookId INT NOT NULL,
     userId INT NOT NULL)
--ADD CONSTRAINT

ALTER TABLE users ADD CONSTRAINT users_user_categories
FOREIGN KEY ([categoryId]) REFERENCES user_categories

ALTER TABLE book_descriptions ADD CONSTRAINT book_descriptions_book_categories
FOREIGN KEY ([bookCategoryId]) REFERENCES book_categories

ALTER TABLE book_descriptions ADD CONSTRAINT book_descriptions_languages
FOREIGN KEY ([languageId]) REFERENCES languages

ALTER TABLE real_books ADD CONSTRAINT  real_books_book_descriptions
FOREIGN KEY ([bookDescriptionId]) REFERENCES book_descriptions

ALTER TABLE electronic_books ADD CONSTRAINT  electronic_books_book_descriptions
FOREIGN KEY ([bookDescriptionId]) REFERENCES book_descriptions

ALTER TABLE queues ADD CONSTRAINT  queues_users
FOREIGN KEY ([userId]) REFERENCES users

ALTER TABLE queues ADD CONSTRAINT  queues_real_books
FOREIGN KEY ([realBookId]) REFERENCES real_books

ALTER TABLE comments ADD CONSTRAINT  comments_users
FOREIGN KEY ([userId]) REFERENCES users

ALTER TABLE comments ADD CONSTRAINT  comments_electronic_books
FOREIGN KEY ([electronicBookId]) REFERENCES electronic_books

ALTER TABLE divisions ADD CONSTRAINT  divisions_departments
FOREIGN KEY ([departmentId]) REFERENCES departments



ALTER TABLE privileges_real ADD CONSTRAINT  privileges_real_user_categories
FOREIGN KEY ([userCategoryId]) REFERENCES user_categories

ALTER TABLE privileges_real ADD CONSTRAINT  privileges_real_real_books
FOREIGN KEY ([realBookId]) REFERENCES real_books

ALTER TABLE privileges_electronic ADD CONSTRAINT  privileges_electronic_user_categories
FOREIGN KEY ([userCategoryId]) REFERENCES user_categories

ALTER TABLE privileges_electronic ADD CONSTRAINT  privileges_electronic_electronic_books
FOREIGN KEY ([electronicBookId]) REFERENCES electronic_books






--VIEW
DROP VIEW view_users_all
GO 
CREATE VIEW view_users_all
AS
    SELECT haveDebt,t.id ,login,password,firstName,secondName,email,roleId, categoryId, category,divisionId,departmentId
FROM (
SELECT dbo.have_debt(users.id) as haveDebt,users.id as id,login,password,firstName,secondName,email,users.roleId as roleId,categoryId,user_categories.name as category,divisionId
	FROM users,user_categories
	WHERE users.categoryId=user_categories.id) as t
LEFT JOIN divisions
ON t.divisionId = divisions.id
GO


DROP VIEW view_users
GO 
CREATE VIEW view_users
AS
SELECT haveDebt,t.id ,login,password,firstName,secondName,email,roleId, categoryId, category,divisionId,departmentId,departments.name as department,divisions.name as division
FROM (


SELECT dbo.have_debt(users.id) as haveDebt,users.id as id,login,password,firstName,secondName,email,users.roleId as roleId, categoryId,user_categories.name as category,divisionId
FROM users,user_categories
WHERE  users.categoryId=user_categories.id AND
			 users.roleId not like 'ADMINISTRATOR') as t
LEFT JOIN divisions
ON t.divisionId = divisions.id
LEFT JOIN departments
ON departmentId = departments.id
GO

DROP VIEW view_active_users
GO 
CREATE VIEW view_active_users
AS
SELECT haveDebt,t.id ,login,password,firstName,secondName,email,roleId, categoryId, category,divisionId,departmentId,departments.name as department,divisions.name as division
FROM (


SELECT dbo.have_debt(users.id) as haveDebt, users.id as id,login,password,firstName,secondName,email,users.roleId as roleId,categoryId,user_categories.name as category,divisionId
FROM users,user_categories
WHERE users.categoryId=user_categories.id AND
			users.roleId not like 'NEW' AND
			users.roleId not like 'ADMINISTRATOR') as t
LEFT JOIN divisions
ON t.divisionId = divisions.id
LEFT JOIN departments
ON departmentId = departments.id
GO


DROP VIEW view_book_descriptions
GO 
CREATE VIEW view_book_descriptions
AS
SELECT book_descriptions.id as id, book_descriptions.name as name, author, bookCategoryId,book_categories.name as bookCategory, publicationPlace,publicationYear,size, languageId as languageId,languages.name as language
FROM book_descriptions, book_categories,languages
WHERE	bookCategoryId =  book_categories.id AND
	    languageId = languages.id
	   
GO

DROP VIEW view_real_books
GO 
CREATE VIEW view_real_books
AS
SELECT dbo.have_book(real_books.id) as available, real_books.id as id, inventoryNumber, cost,view_book_descriptions.id as bookDescriptionId, name, author, bookCategoryId,bookCategory, publicationPlace,publicationYear,size, languageId,language
FROM view_book_descriptions,real_books
WHERE	real_books.bookDescriptionId = view_book_descriptions.id
GO

DROP VIEW view_electronic_books
GO 
CREATE VIEW view_electronic_books
AS
SELECT  electronic_books.id as id, fileName, capacity, extension, view_book_descriptions.id as bookDescriptionId, name, author, bookCategoryId,bookCategory, publicationPlace,publicationYear,view_book_descriptions.size, languageId,language
FROM view_book_descriptions,electronic_books
WHERE	electronic_books.bookDescriptionId = view_book_descriptions.id
GO

DROP VIEW view_queues
GO 
CREATE VIEW view_queues
AS
SELECT queues.id as id,users.id as userId, users.login, users.firstName, users.secondName,users.email,queues.realBookId,queues.date 
FROM queues,users
WHERE queues.userId = users.id
GO

DROP VIEW view_comments
GO 
CREATE VIEW view_comments
AS
SELECT comments.id as id,comments.electronicBookId,comments.date, comments.message,users.id as userId, users.firstName, users.secondName
FROM comments,users
WHERE comments.userId = users.id
GO

DROP VIEW view_user_categories
GO 
CREATE VIEW view_user_categories
AS
SELECT id as id, name 
FROM user_categories
GO

DROP VIEW view_book_categories
GO 
CREATE VIEW view_book_categories
AS
SELECT id as id,name as name 
FROM book_categories
GO

DROP VIEW view_languages
GO 
CREATE VIEW view_languages
AS
SELECT id as id,name as name 
FROM languages
GO

DROP VIEW view_departments
GO 
CREATE VIEW view_departments
AS
SELECT id as id,name as name 
FROM departments
GO

DROP VIEW view_divisions
GO 
CREATE VIEW view_divisions
AS
SELECT id as id,name as name,departmentId 
FROM divisions
GO

DROP VIEW view_all_debts
GO
CREATE VIEW view_all_debts
AS
SELECT t.id, behind,startPeriod, endPeriod,inventoryNumber,t.name,author,cost, userId, login, firstName , secondName,email,divisions.departmentId as departmentId,divisionId
FROM
(select debts.id as id, dbo.behind(endPeriod) as behind,startPeriod, endPeriod,inventoryNumber,name,author,cost,users.id as userId, login, firstName , secondName,email,divisionId
from debts,view_real_books,users
where debts.realBookId = view_real_books.id and debts.userId = users.id) as t
LEFT JOIN divisions
ON divisions.id = divisionId
GO


--FUNCTION
DROP FUNCTION exist_user
Go
CREATE FUNCTION exist_user (@login VARCHAR(50))
RETURNS INT
AS
BEGIN
	IF EXISTS(SELECT * FROM users WHERE login=@login)
		RETURN 1
	RETURN 0
END
GO

DROP FUNCTION exist_real_book
Go
CREATE FUNCTION exist_real_book (@inv int)
RETURNS INT
AS
BEGIN
	IF EXISTS(SELECT * FROM real_books WHERE inventoryNumber=@inv)
		RETURN 1
	RETURN 0
END
GO

DROP FUNCTION can_delete_book_description
Go
CREATE FUNCTION can_delete_book_description (@id int)
RETURNS INT
AS
BEGIN
	IF EXISTS(SELECT * FROM real_books  WHERE real_books.bookDescriptionId=@id) OR
       EXISTS(SELECT * FROM electronic_books WHERE electronic_books.bookDescriptionId=@id )
		RETURN 0
	RETURN 1
END
GO

DROP FUNCTION have_book
Go
CREATE FUNCTION have_book (@realBookId INT)
RETURNS INT
AS
BEGIN
	IF (exists(select * from debts where realBookId=@realBookId))
		RETURN 0 
	RETURN 1
END
GO

DROP FUNCTION can_delete_user
Go
CREATE FUNCTION can_delete_user (@id int)
RETURNS INT
AS
BEGIN
	IF EXISTS(SELECT * FROM debts WHERE debts.userId=@id) OR
       EXISTS(SELECT * FROM comments WHERE comments.userId=@id) OR
	   EXISTS(SELECT * FROM queues WHERE queues.userId=@id) 
	   RETURN 0
	RETURN 1
END
GO

DROP FUNCTION can_delete_real_book
Go
CREATE FUNCTION can_delete_real_book (@id int)
RETURNS INT
AS
BEGIN
	IF EXISTS(SELECT * FROM debts WHERE realBookId=@id)
		RETURN 0
	RETURN 1
END
GO

DROP FUNCTION have_debt
Go
CREATE FUNCTION have_debt (@userId INT)
RETURNS INT
AS
BEGIN
	IF (exists(select * from debts where userId=@userId))
		RETURN 1 
	RETURN 0
END
GO


DROP FUNCTION behind
Go
CREATE FUNCTION behind (@end smalldatetime)
RETURNS VARCHAR(3)
AS
BEGIN
	IF (getdate()>@end)
		RETURN 'Yes' 
	RETURN 'No'
END
GO

DROP FUNCTION user_debts
GO
CREATE FUNCTION user_debts (@userId int)
RETURNS TABLE
AS
RETURN
SELECT t.id as id, behind,startPeriod, endPeriod,inventoryNumber,t.name as name,author,cost,userId, login, firstName , secondName, email,divisionId,divisions.departmentId as departmentId
FROM
(select debts.id as id, dbo.behind(endPeriod) as behind,startPeriod, endPeriod,inventoryNumber,name,author,cost,users.id as userId, login, firstName , secondName, email,divisionId
from debts,view_real_books,users
where debts.realBookId = view_real_books.id and debts.userId = @userId and debts.userId = users.id) as t
LEFT JOIN divisions
ON divisionId = divisions.id

GO



DROP FUNCTION new_file_id
Go
CREATE FUNCTION new_file_id ()
RETURNS INT
AS
BEGIN
DECLARE @RES INT 
SET @RES = (SELECT MAX(id) + 1 FROM electronic_books)
IF @RES is NULL
	SET @RES = 1
RETURN @RES
END
GO

DROP FUNCTION get_file_name
Go
CREATE FUNCTION get_file_name (@id INT)
RETURNS VARCHAR(50)
AS
BEGIN
	DECLARE @RES VARCHAR(50)
	SET @RES = (SELECT fileName FROM electronic_books where id = @id)
	RETURN @RES
END
GO

DROP FUNCTION exist_queue
Go
CREATE FUNCTION exist_queue (@userId INT,@realBookId INT)
RETURNS INT
AS
BEGIN
	IF EXISTS(SELECT * FROM queues WHERE userId=@userId and realBookId = @realBookId)
		RETURN 1
	RETURN 0
END
GO

DROP FUNCTION can_delete_electronic_book
Go
CREATE FUNCTION can_delete_electronic_book (@id int)
RETURNS INT
AS
BEGIN
	IF EXISTS(SELECT * FROM comments
		 WHERE comments.electronicBookId=@id)
		RETURN 0
	RETURN 1
END
GO

DROP FUNCTION statistic_pipe_real_books
GO
CREATE FUNCTION statistic_pipe_real_books ()
RETURNS TABLE
AS
RETURN
select min(bookCategory) as name, count(*) as count from view_real_books
group by bookCategoryId
GO


DROP FUNCTION statistic_pipe_electronic_books
GO
CREATE FUNCTION statistic_pipe_electronic_books ()
RETURNS TABLE
AS
RETURN
select min(bookCategory) as name, count(*) as count from view_electronic_books
group by bookCategoryId
GO


DROP FUNCTION can_delete_department
Go
CREATE FUNCTION can_delete_department (@id int)
RETURNS INT
AS
BEGIN
	IF EXISTS(SELECT * FROM groups WHERE groups.departmentId=@id) OR
       EXISTS(SELECT * FROM cathedras WHERE cathedras.departmentId=@id)
	   RETURN 0
	RETURN 1
END
GO

DROP FUNCTION exist_department
Go
CREATE FUNCTION exist_department (@id INT, @name VARCHAR(50))
RETURNS INT
AS
BEGIN
	IF EXISTS(SELECT * FROM departments WHERE name=@name  and (departments.id <> @id OR @id = 0))
		RETURN 1
	RETURN 0
END
GO

DROP FUNCTION can_delete_book_category
Go
CREATE FUNCTION can_delete_book_category (@id int)
RETURNS INT
AS
BEGIN
	IF EXISTS(SELECT * FROM book_descriptions WHERE book_descriptions.bookCategoryId=@id)
       RETURN 0
	RETURN 1
END
GO

DROP FUNCTION exist_book_category
Go
CREATE FUNCTION exist_book_category (@id INT, @name VARCHAR(50))
RETURNS INT
AS
BEGIN
	IF EXISTS(SELECT * FROM book_categories WHERE name=@name and (book_categories.id <> @id OR @id = 0))
		RETURN 1
	RETURN 0
END
GO

DROP FUNCTION exist_language
Go
CREATE FUNCTION exist_language (@id INT, @name VARCHAR(50))
RETURNS INT
AS
BEGIN
	IF EXISTS(SELECT * FROM languages WHERE name=@name and (languages.id <> @id OR @id = 0))
		RETURN 1
	RETURN 0
END
GO

DROP FUNCTION can_delete_language
Go
CREATE FUNCTION can_delete_language (@id int)
RETURNS INT
AS
BEGIN
	IF EXISTS(SELECT * FROM book_descriptions WHERE book_descriptions.languageId=@id)
       
	   RETURN 0
	RETURN 1
END
GO

DROP FUNCTION exist_user_category
Go
CREATE FUNCTION exist_user_category (@id INT, @name VARCHAR(50))
RETURNS INT
AS
BEGIN
	IF EXISTS(SELECT * FROM user_categories WHERE name=@name and (user_categories.id <> @id OR @id = 0))
		RETURN 1
	RETURN 0
END
GO

DROP FUNCTION can_delete_user_category
Go
CREATE FUNCTION can_delete_user_category (@id int)
RETURNS INT
AS
BEGIN
	IF EXISTS(SELECT * FROM users WHERE users.categoryId=@id)
       
	   RETURN 0
	RETURN 1
END
GO

DROP FUNCTION exist_division
Go
CREATE FUNCTION exist_division (@id INT, @name VARCHAR(50))
RETURNS INT
AS
BEGIN
	IF EXISTS(SELECT * FROM divisions WHERE name=@name and (divisions.id <> @id OR @id = 0))
		RETURN 1
	RETURN 0
END
GO

DROP FUNCTION can_delete_division
Go
CREATE FUNCTION can_delete_division (@id int)
RETURNS INT
AS
BEGIN
	IF EXISTS(SELECT * FROM users WHERE users.divisionId=@id)
       
	   RETURN 0
	RETURN 1
END
GO


DROP FUNCTION user_statistic
GO
CREATE FUNCTION user_statistic ()
RETURNS TABLE
AS
RETURN
select min(category) as name, count(*) as count from view_active_users
group by categoryId
union
select 'User' as name,count(*)as count from view_active_users
union
Select 'Librarian' as name, count(*) as count from view_active_users where roleId = 'LIBRARIAN'
GO



DROP FUNCTION book_statistic
GO
CREATE FUNCTION book_statistic()
RETURNS TABLE
AS
RETURN
Select 'Real book' as name, count(*) as count from view_real_books
union
Select 'Electronic book' as name, count(*) as count from view_electronic_books
union
Select 'Comments' as name, count(*) as count from view_comments
GO


DROP FUNCTION real_books_for_user_category
GO
CREATE FUNCTION real_books_for_user_category (@categoryId INT)
RETURNS TABLE
AS
RETURN
SELECT available,view_real_books.id as id, inventoryNumber, cost,bookDescriptionId, name, author, bookCategoryId,bookCategory, publicationPlace,publicationYear,size, languageId,language  
FROM view_real_books,privileges_real
WHERE
view_real_books.id =  realBookId AND
userCategoryId = @categoryId
GO


DROP FUNCTION electronic_books_for_user_category
GO
CREATE FUNCTION electronic_books_for_user_category (@categoryId INT)
RETURNS TABLE
AS
RETURN
SELECT view_electronic_books.id as id, fileName, capacity, extension, bookDescriptionId, name, author, bookCategoryId,bookCategory, publicationPlace,publicationYear,size, languageId,language
FROM view_electronic_books,privileges_electronic
WHERE
view_electronic_books.id =  electronicBookId AND
userCategoryId = @categoryId
GO


DROP FUNCTION report
GO
CREATE FUNCTION report (@startPeriod DATETIME,@endPeriod DATETIME)
RETURNS TABLE
AS
RETURN

SELECT librarian,operation,date,startPeriod,endPeriod,inventoryNumber,book,users.secondName + ' ' + users.firstName as person

FROM

(select 
users.secondName + ' ' + users.firstName as librarian,
operation,
date,
startPeriod,
endPeriod,
view_real_books.inventoryNumber,
view_real_books.name + ' ' + view_real_books.author as book,
userId
from reports,users,view_real_books
WHERE 
librarianId = users.id AND
realBookId = view_real_books.id) as t,users
WHERE users.id = t.userId AND date between @startPeriod and @endPeriod
GO


--PROCEDURE
DROP PROC add_user 
GO
CREATE PROC add_user @login VARCHAR(50),@password VARCHAR(50),@firstName VARCHAR(50),@secondName VARCHAR(50),
@email VARCHAR(50), @divisionId INT 
AS
INSERT INTO users(login,password,firstName,secondName,email,divisionId)
VALUES(@login,@password,@firstName,@secondName,@email,@divisionId)
GO

DROP PROC delete_user 
GO
CREATE PROC delete_user @id int
AS
DELETE FROM users WHERE id=@id
GO

DROP PROC edit_user 
GO
CREATE PROC edit_user @id int,@firstName VARCHAR(50),@secondName VARCHAR(50),@email VARCHAR(50),@divisionId INT
AS
UPDATE users
SET
firstName=@firstName,
secondName=@secondName,
email=@email,
divisionId=@divisionId
WHERE id=@id
GO

DROP PROC admin_edit_user 
GO
CREATE PROC admin_edit_user @id int,@firstName VARCHAR(50),@secondName VARCHAR(50),@email VARCHAR(50), @roleId VARCHAR(20), @categoryId INT,@divisionId INT
AS
UPDATE users
SET
firstName=@firstName,
secondName=@secondName,
email=@email,
roleId=@roleId,
categoryId=@categoryId,
divisionId=@divisionId
WHERE id=@id
GO

DROP PROC delete_book_description 
GO
CREATE PROC delete_book_description @id int
AS
DELETE FROM book_descriptions WHERE id=@id
GO

DROP PROC edit_book_description 
GO
CREATE PROC edit_book_description @id int,@name VARCHAR(50),@author VARCHAR(50),@bookCategoryId INT,@publicationPlace VARCHAR(50),@publicationYear INT,@size INT,@languageId INT
AS
UPDATE book_descriptions
SET
name=@name,
author=@author,
bookCategoryId=@bookCategoryId,
publicationPlace=@publicationPlace,
publicationYear=@publicationYear,
size=@size,
languageId=@languageId
WHERE id=@id
GO

DROP PROC add_book_description 
GO
CREATE PROC add_book_description @name VARCHAR(50),@author VARCHAR(50),@bookCategoryId INT,@publicationPlace VARCHAR(50),@publicationYear INT,@size INT,@languageId INT
AS
INSERT INTO book_descriptions( name,author,bookCategoryId,publicationPlace,publicationYear,size,languageId)
VALUES(@name,@author,@bookCategoryId,@publicationPlace,@publicationYear,@size,@languageId)
GO

DROP PROC delete_real_book 
GO
CREATE PROC delete_real_book @id int
AS
DELETE FROM real_books WHERE id=@id
GO

DROP PROC edit_real_book
GO
CREATE PROC edit_real_book @id int, @inventoryNumber int,@cost int
AS
UPDATE real_books
SET
cost=@cost,
inventoryNumber=@inventoryNumber
WHERE id=@id
GO

DROP PROC add_real_book 
GO
CREATE PROC add_real_book @inventoryNumber int,@cost int,@bookDescriptionId INT
AS
INSERT INTO real_books( inventoryNumber,cost,bookDescriptionId)
VALUES(@inventoryNumber,@cost,@bookDescriptionId)
GO

DROP PROC give_book 
GO
CREATE PROC give_book @realBookId int,@userId int,@startPeriod DATETIME,@endPeriod DATETIME 
AS
INSERT INTO debts(realBookId,userId,startPeriod,endPeriod)
VALUES(@realBookId,@userId,@startPeriod,@endPeriod)
GO

DROP PROC return_book 
GO
CREATE PROC return_book @id int
AS
DELETE FROM debts WHERE id=@id
GO

DROP PROC add_electronic_book 
GO
CREATE PROC add_electronic_book @bookDescriptionId int,@fileName varchar(50),@capacity INT, @extension varchar(5)
AS
INSERT INTO electronic_books( bookDescriptionId,fileName,capacity,extension)
VALUES(@bookDescriptionId,@fileName,@capacity,@extension)
GO

DROP PROC delete_electronic_book 
GO
CREATE PROC delete_electronic_book  @id int
AS
DELETE FROM electronic_books  WHERE id=@id
GO

DROP PROC add_user_in_queue 
GO
CREATE PROC add_user_in_queue @userId INT,@realBookId INT 
AS
INSERT INTO queues(userId,realBookId,date)
VALUES(@userId,@realBookId,getdate())
GO

DROP PROC delete_user_from_queue 
GO
CREATE PROC delete_user_from_queue @userId INT,@realBookId INT 
AS
DELETE FROM queues
WHERE userId=@userId and realBookId = @realBookId
GO

DROP PROC delete_user_from_queue_by_id 
GO
CREATE PROC delete_user_from_queue_by_id @id INT 
AS
DELETE FROM queues
WHERE id=@id

GO

DROP PROC add_comment 
GO
CREATE PROC add_comment @userId int,@electronicBookId int,@message VARCHAR(1000)
AS
INSERT INTO comments( userId,electronicBookId,date,message)
VALUES(@userId,@electronicBookId,getDate(),@message)
GO

DROP PROC delete_comment 
GO
CREATE PROC delete_comment  @commentId INT 
AS
DELETE FROM comments
WHERE id=@commentId

GO

DROP PROC delete_department 
GO
CREATE PROC delete_department   @id INT 
AS
DELETE FROM departments
WHERE id=@id
GO

DROP PROC edit_department
GO
CREATE PROC edit_department @id int, @name VARCHAR(55)
AS
UPDATE departments
SET
name=@name
WHERE id=@id
GO

DROP PROC add_department
GO
CREATE PROC add_department  @name VARCHAR(55)
AS
INSERT INTO departments(name)
VALUES(@name)
GO

DROP PROC delete_book_category 
GO
CREATE PROC delete_book_category   @id INT 
AS
DELETE FROM book_categories
WHERE id=@id
GO

DROP PROC edit_book_category
GO
CREATE PROC edit_book_category @id int, @name VARCHAR(55)
AS
UPDATE book_categories
SET
name=@name
WHERE id=@id
GO

DROP PROC add_book_category
GO
CREATE PROC add_book_category  @name VARCHAR(55)
AS
INSERT INTO book_categories(name)
VALUES(@name)
GO

DROP PROC add_language
GO
CREATE PROC add_language @name VARCHAR(55)
AS
INSERT INTO languages(name)
VALUES(@name)
GO

DROP PROC delete_language 
GO
CREATE PROC delete_language   @id INT 
AS
DELETE FROM languages
WHERE id=@id
GO

DROP PROC edit_language
GO
CREATE PROC edit_language @id int, @name VARCHAR(55)
AS
UPDATE languages
SET
name=@name
WHERE id=@id
GO

DROP PROC add_user_category
GO
CREATE PROC add_user_category @name VARCHAR(55)
AS
INSERT INTO user_categories(name)
VALUES(@name)
GO

DROP PROC delete_user_category
GO
CREATE PROC delete_user_category   @id INT 
AS
DELETE FROM user_categories
WHERE id=@id
GO

DROP PROC edit_user_category
GO
CREATE PROC edit_user_category @id int, @name VARCHAR(55)
AS
UPDATE user_categories
SET
name=@name
WHERE id=@id
GO

DROP PROC add_division
GO
CREATE PROC add_division @name VARCHAR(55),@departmentId INT
AS
INSERT INTO divisions(name,departmentId)
VALUES(@name,@departmentId)
GO

DROP PROC delete_division
GO
CREATE PROC delete_division  @id INT 
AS
DELETE FROM divisions
WHERE id=@id
GO

DROP PROC edit_division
GO
CREATE PROC edit_division @id int, @name VARCHAR(55),@departmentId INT
AS
UPDATE divisions
SET
name=@name,
departmentId = @departmentId
WHERE id=@id
GO


DROP PROC clear_real_privileges
GO
CREATE PROC clear_real_privileges  @realBookId INT 
AS
DELETE FROM privileges_real
WHERE realBookId=@realBookId
GO



DROP PROC add_real_privileges
GO
CREATE PROC add_real_privileges @realBookId INT ,@userCategoryId INT
AS
INSERT INTO privileges_real(realBookId,userCategoryId)
VALUES(@realBookId,@userCategoryId)
GO

DROP PROC clear_electronic_privileges
GO
CREATE PROC clear_electronic_privileges  @electronicBookId INT 
AS
DELETE FROM privileges_electronic
WHERE electronicBookId=@electronicBookId
GO



DROP PROC add_electronic_privileges
GO
CREATE PROC add_electronic_privileges @electronicBookId INT ,@userCategoryId INT
AS
INSERT INTO privileges_electronic(electronicBookId,userCategoryId)
VALUES(@electronicBookId,@userCategoryId)
GO

DROP PROC add_report 
GO
CREATE PROC add_report @librarianId INT,@operation VARCHAR(50),@start DATETIME, @end DATETIME,@realBookId INT,
 @userId INT 
AS
INSERT INTO reports(librarianId,date,operation,startPeriod,endPeriod,realBookId,userId)
VALUES(@librarianId,getDate(),@operation,@start,@end,@realBookId,@userId)
GO


--INSERT DATE

--DEPARTMENTS
INSERT INTO departments(name)
VALUES('Mathematical')
INSERT INTO departments(name)
VALUES('Physical')
INSERT INTO departments(name)
VALUES('Historical')
INSERT INTO departments(name)
VALUES('Juridical')

--DIVISIONS

INSERT INTO divisions(name,departmentId)
VALUES('POIT-51',1)
INSERT INTO divisions(name,departmentId)
VALUES('PM-51',1)
INSERT INTO divisions(name,departmentId)
VALUES('EK-51',1)
INSERT INTO divisions(name,departmentId)
VALUES('MPU',1)
INSERT INTO divisions(name,departmentId)
VALUES('VMiP',1)
INSERT INTO divisions(name,departmentId)
VALUES('DY',1)
INSERT INTO divisions(name,departmentId)
VALUES('ASOI',2)




--USER CATEGORIES
INSERT INTO user_categories(name)
VALUES('Student')
INSERT INTO user_categories(name)
VALUES('Employee')
INSERT INTO user_categories(name)
VALUES('Graduate')
INSERT INTO user_categories(name)
VALUES('Teacher')

--USERS
INSERT INTO users(login,password,secondName,firstName,email,roleId,categoryId)
VALUES('admin','admin','admin','admin','admin','ADMINISTRATOR',2)
INSERT INTO users(login,password,secondName,firstName,email,roleId,categoryId,divisionId)
VALUES('a','1','Mikalai','Churakou','badbug1@yandex.ru','LIBRARIAN',2,1)
INSERT INTO users(login,password,secondName,firstName,email,roleId,categoryId,divisionId)
VALUES('b','2','user1','user1','1@yandex.ru','USER',2,2)
INSERT INTO users(login,password,secondName,firstName,email,roleId,divisionId)
VALUES('c','3','user2','user2','2@yandex.ru','USER',1)

DECLARE @i INT;
SET @i=1;
WHILE @i < 20 
BEGIN
    INSERT INTO users(login,password,secondName,firstName,email,roleId,divisionId)
	VALUES(@i,@i,convert(varchar,@i)+'s',convert(varchar,@i)+'f',convert(varchar,@i)+'@yandex.ru','USER',1)
    SET @i = @i + 1;
END
GO

--BOOK CATEGORIES
INSERT INTO book_categories(name)
VALUES('Detective')
INSERT INTO book_categories(name)
VALUES('Child')
INSERT INTO book_categories(name)
VALUES('History')
INSERT INTO book_categories(name)
VALUES('Romance')
INSERT INTO book_categories(name)
VALUES('Adventure')
INSERT INTO book_categories(name)
VALUES('Prose')
INSERT INTO book_categories(name)
VALUES('Fantasy')
INSERT INTO book_categories(name)
VALUES('Humor')

--LANGUAGES
INSERT INTO languages(name)
VALUES('Russian')
INSERT INTO languages(name)
VALUES('English')

--BOOK DESCRIPTIONS
INSERT INTO book_descriptions(name,author,bookCategoryId,publicationPlace,publicationYear,size,languageId)
VALUES('NAME1','AUTHOR1',4,'PLACE1',1990,421,2)
INSERT INTO book_descriptions(name,author,bookCategoryId,publicationPlace,publicationYear,size,languageId)
VALUES('NAME2','AUTHOR2',4,'PLACE1',1965,1242,1)
INSERT INTO book_descriptions(name,author,bookCategoryId,publicationPlace,publicationYear,size,languageId)
VALUES('NAME3','AUTHOR3',8,'PLACE1',1948,678,1)
INSERT INTO book_descriptions(name,author,bookCategoryId,publicationPlace,publicationYear,size,languageId)
VALUES('NAME4','AUTHOR4',2,'PLACE1',1985,600,1)
INSERT INTO book_descriptions(name,author,bookCategoryId,publicationPlace,publicationYear,size,languageId)
VALUES('NAME5','AUTHOR5',2,'PLACE12',1990,600,1)
INSERT INTO book_descriptions(name,author,bookCategoryId,publicationPlace,publicationYear,size,languageId)
VALUES('NAME6','AUTHOR6',7,'PLACE12',1995,600,1)
INSERT INTO book_descriptions(name,author,bookCategoryId,publicationPlace,publicationYear,size,languageId)
VALUES('NAME7','AUTHOR7',3,'PLACE12',1950,492,1)

--REAL BOOKS
INSERT INTO real_books(bookDescriptionId,inventoryNumber,cost)
VALUES(1,1,100)
INSERT INTO real_books(bookDescriptionId,inventoryNumber,cost)
VALUES(2,2,200)
INSERT INTO real_books(bookDescriptionId,inventoryNumber,cost)
VALUES(3,3,300)
INSERT INTO real_books(bookDescriptionId,inventoryNumber,cost)
VALUES(4,4,400)
INSERT INTO real_books(bookDescriptionId,inventoryNumber,cost)
VALUES(5,5,500)
INSERT INTO real_books(bookDescriptionId,inventoryNumber,cost)
VALUES(6,6,600)
INSERT INTO real_books(bookDescriptionId,inventoryNumber,cost)
VALUES(7,7,700)

--QUEUES
INSERT INTO queues(userId,realBookId,date)
VALUES(2,1,getDate())
INSERT INTO queues(userId,realBookId,date)
VALUES(3,1,getDate())
INSERT INTO queues(userId,realBookId,date)
VALUES(4,1,getDate())


