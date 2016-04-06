package com.mikalai.library.utils;
/**
 * Constants
 * 
 * @author Mikalai_Churakou
 */
public class Constants {
	/*database constants*/
	public static String DB_DBO = "dbo";


	/*URL*/
	public static String PATH_FILES = "d:\\DEV-TOOLS\\LIBRARY_FILES\\";

		
	/*messages*/
	public static String MSG_REPEAT_LOGIN = "registration.Repeat_login";
	public static String MSG_ACC_REG = "registration.Account_reg";
	public static String MSG_DB_PROBLEM = "library.Db_problem";
	public static String MSG_ACC_NOT_REG = "login.Acc_Not_Reg";
	public static String MSG_ACC_NOT_ACTIVE = "login.Acc_Not_Active";
	public static String MSG_BOOK_ADDED = "book.New_book_added";
	public static String MSG_DUPLICATE_INV = "book.Dublicate_inv";
	public static String MSG_SUBSUDIARY = "book.Subsidary";
	public static String MSG_BOOK_GIVEN = "book.Book_given";
	public static String MSG_BOOK_RETURNED = "book.Book_returned";
	public static String MSG_YOU_ADDED_TO_QUEUE = "queue.You_added_to_queue";
	public static String MSG_YOU_CANT_ADD_TWICE = "queue.You_cant_add_twice";
	public static String MSG_YOU_DELETED_FROM_QUEUE = "queue.You_deleted_from_queue";
	public static String MSG_YOU_DONT_ATTEND_IN_QUEUE = "queue.You_dont_attend_in_queue";
	public static String MSG_USER_DELETED_FROM_QUEUE = "queue.User_deleted_from_queue";
	public static String MSG_UNIQUE_FAILD = "library.Unique_failed";
	public static String MSG_TOTAL_GIVEN = "report.Total_given";
	public static String MSG_TOTAL_RETURNED = "report.Total_returned";
	public static String MSG_LIBRARIAN_REPORT = "report.Librarian_report";
	public static String MSG_RETURNED = "report.Returned";
	public static String MSG_GIVEN = "report.Given";
	public static String MSG_LIBRARIAN = "report.Librarian";
	public static String MSG_OPERATION = "report.Operation";
	public static String MSG_DATE = "report.Date";
	public static String MSG_START = "report.Start";
	public static String MSG_END = "report.End";
	public static String MSG_INVENTORY = "report.Inventory";
	public static String MSG_USER = "report.User";
	public static String MSG_BOOK = "report.Book";
	
	
	
	
	
	
	/*fields*/
	public static String FIELD_ID = "id";
	public static String FIELD_LOGIN = "login";
	public static String FIELD_PASSWORD = "password";
	public static String FIELD_FIRST_NAME = "firstName";
	public static String FIELD_SECOND_NAME = "secondName";
	public static String FIELD_EMAIL = "email";
	public static String FIELD_ROLE = "role";
	public static String FIELD_ROLE_ID = "roleId";
	public static String FIELD_CATEGORY = "category";
	public static String FIELD_CATEGORY_ID = "categoryId";
	public static String FIELD_AUTHOR = "author";
	public static String FIELD_BOOK_CATEGORIES_ID = "bookCategoryId";
	public static String FIELD_BOOK_CATEGORY = "bookCategory";
	public static String FIELD_PUBLICATION_PLACE = "publicationPlace";
	public static String FIELD_PUBLICATION_YEAR = "publicationYear";
	public static String FIELD_SIZE = "size";
	public static String FIELD_LANGUAGE_ID = "languageId";
	public static String FIELD_LANGUAGE = "language";
	public static String FIELD_USER_CATEGORIES_ID = "userCategoryId";
	public static String FIELD_USER_CATEGORY = "userCategory";
	public static String FIELD_NAME = "name";
	public static String FIELD_BOOK_DESCRIPTION_ID = "bookDescriptionId";
	public static String FIELD_INVENTORY_NUMBER = "inventoryNumber";
	public static String FIELD_COST = "cost";
	public static String FIELD_AVAILABLE = "available";
	public static String FIELD_HAVE_DEBT = "haveDebt";
	public static String FIELD_BEHIND = "behind";
	public static String FIELD_START_PERIOD = "startPeriod";
	public static String FIELD_END_PERIOD = "endPeriod";
	public static String FIELD_FILE_NAME = "fileName";
	public static String FIELD_CAPACITY = "capacity";
	public static String FIELD_EXTENSION = "extension";
	public static String FIELD_USER_ID = "userId";
	public static String FIELD_REAL_BOOK_ID = "realBookId";
	public static String FIELD_DATE = "date";
	public static String FIELD_ELECTRONIC_BOOK_ID = "electronicBookId";
	public static String FIELD_MESSAGE = "message";
	public static String FIELD_COUNT = "count";
	public static String FIELD_ROLE_TITLE = "roleTitle";
	public static String FIELD_CATEGORY_TITLE = "categoryTitle";
	public static String FIELD_NAME_RU = "name_ru";
	public static String FIELD_DEPARTMENT_ID = "departmentId";
	public static String FIELD_DIVISION_ID = "divisionId";
	public static String FIELD_LIBRARIAN = "librarian";
	public static String FIELD_OPERATION = "operation";
	
	public static String FIELD_BOOK = "book";
	public static String FIELD_PERSON = "person";
	
	
	
	/*attributes*/
	public static String ATTRIBUTE_USER = "user";

	/*role id*/
	public static int NEW_ROLE_ID = 1;
	public static int USER_ROLE_ID = 2;
	public static int LIBRARIAN_ROLE_ID = 3;
	public static int ADMINISTRATOR_ROLE_ID = 4;
	
	/*operation*/
	public static String OPERATION_DELETE = "del";
	public static String OPERATION_EDIT = "edit";
	public static String OPERATION_ADD = "add";
	
	/*kind of searching*/
	public static String EQUALS = "eq";
	public static String NOT_EQUALS = "ne";
	public static String BEGIN_WITH = "bw";
	public static String CONTAIN = "cn";
	public static String LESS = "lt";
	public static String LESS_OR_EQUAL = "le";
	public static String GREATER = "gt";
	public static String GREATER_OR_EQUAL = "ge";
	
	
	/* values */
	public static String VALUE_EMPTY = "_empty";
	
	/* format*/
	public static final String DATE_FORMAT_SHORT = "d.M.yyyy";
	public static final String DATE_FORMAT = "dd/MM/yyyy h:mm a";
	public static final String DATE_FORMAT_REPORT = "dd.MM.yyyy";
	
	
	
}
 