package com.dxc.assignment.bookstore.repository;

public class DBQueries {
        public static final String INSERT_BOOK_DETAILS = """
            insert into book_details(isbn,title,authors,year,price,genre)
            values (?,?,?,?,?,?)
            """;

        public static final String UPDATE_BOOK_DETAILS = """
            update book_details set title = ? , authors = ? , year = ? , price = ? , genre = ? where isbn = ?
            """;

        public static final String SELECT_BOOK_DETAILS_BY_ISBN = """
            Select * From book_details Where isbn = ?
            """;

        public static final String SELECT_BOOK_DETAILS_BY_TITLE = """
                Select * From book_details Where title = ?
                        """;

        public static final String SELECT_BOOK_DETAILS_BY_AUTHORS = """
                Select * From book_details Where authors = ?
                        """;
                        
        public static final String SELECT_BOOK_DETAILS_BY_AUTHORS_AND_TITLE = """
                Select * From book_details Where authors = ? and title = ?
                        """;

        public static final String DELETE_BOOK_DETAILS_BY_ISBN = """
                Delete From book_details Where isbn = ?
                        """;

        public static final String GET_USER_BY_USERNAME = """
                Select * From users Where username = ?
                        """;
}
