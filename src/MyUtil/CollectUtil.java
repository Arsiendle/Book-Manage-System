package MyUtil;

import MyJavabean.Book;
import MyJavabean.User;

public class CollectUtil {
    public static void CollectStart(User user, Book book) {
        //Â¼ÈëĞÂÊé
            DBUtil.dbcollectMatch(user,book);
    }
}
