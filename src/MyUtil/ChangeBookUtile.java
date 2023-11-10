package MyUtil;

import MyJavabean.Book;
import MyJavabean.User;

public class ChangeBookUtile {
    public static void changeBookStart(User user,Book book, String ISBN, int option) {
            DBUtil.dbChangeBookMatch(book,ISBN,option);
    }
}
