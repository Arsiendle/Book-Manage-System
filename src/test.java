import MyJavabean.Book;
import MyJavabean.User;
import MyUIFrame.ModifUI;
import MyUIFrame.NobodyUI;
import MyUIFrame.RegitUI;
import MyUIFrame.UserUI;
import MyUtil.*;
import javafx.collections.ObservableList;

import java.text.ParseException;
import java.util.List;

public class test {
    public static void main(String[] args) throws ParseException {
        /**
         * 此类用来做单元测试或者集成测试
         */
        User user=new User("wq","123",0);
        Book book=new Book();
//        book.collect(user);
//        book.select(user);
//        book.change(user);
//        book.showInfo(user);
//        RegitUtil.regitStart();
//        DBUtil.dbshowallbook(user);
//        NobodyUI.launch(NobodyUI.class);
//        List<Book> list = DBUtil.dbshowinfo(user);
//        System.out.println(list);
//        UserUI.launch(UserUI.class);
//        NobodyUI nobodyUI = new NobodyUI();
//        ObservableList<Book> bookListFromDatabase = nobodyUI.getBookListFromDatabase();
//        System.out.println(bookListFromDatabase);
        DBUtil.dbshowinfo(user);
    }

}
