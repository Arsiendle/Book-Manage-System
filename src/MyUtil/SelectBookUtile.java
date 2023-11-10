package MyUtil;

import MyJavabean.Book;
import MyJavabean.User;

public class SelectBookUtile {
    public static void SelectStart(User user, Book book,String ISBN) {
            DBUtil.dbSelectBookMatch(book,ISBN);
    }
}
