package MyUIFrame;

import MyJavabean.Book;
import MyJavabean.User;
import MyUtil.DBUtil;
import MyUtil.EnterUtil;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.text.ParseException;
import java.util.List;

public class NobodyUI extends Application {
    User user=new User();
    Book book = new Book();
    private  static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        Button borrowButton = createNavigationButton("借书", "BorrowBookScene");
        Button returnButton = createNavigationButton("还书", "ReturnBookScene");
        Button displayButton = createNavigationButton("显示所有借阅信息", "AllImformScene");
        Button searchButton = createNavigationButton("根据ISBN查询书", "SearchBookScene");
        Button renewButton = createNavigationButton("续借", "KeepinBookScene");
        Button showAllBooksButton = createNavigationButton("显示书库所有书", "AllBookScene");

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(borrowButton, returnButton, displayButton, searchButton, renewButton, showAllBooksButton);

        Scene scene = new Scene(root, 400, 300);

        primaryStage.setTitle("图书管理系统");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
            // 在主舞台关闭时终止程序
            System.exit(0);
        });
        primaryStage.show();
    }

    private Button createNavigationButton(String buttonText, String sceneName) {
        Button button = new Button(buttonText);
        button.setOnAction(event -> navigateToScene(sceneName));
        return button;
    }

    private void navigateToScene(String sceneName) {
        Stage newStage = new Stage();
        newStage.setTitle(sceneName);

        Parent root = null;
        Scene newScene = null;

        // 根据sceneName的值创建对应的界面
        if (sceneName.equals("BorrowBookScene")) {
            // 创建借书界面
            root = createBorrowBookScene();
            newScene = new Scene(root, 400, 250);
        } else if (sceneName.equals("ReturnBookScene")) {
            // 创建还书界面
            root = createReturnBookScene();
            newScene = new Scene(root, 400, 250);
        } else if (sceneName.equals("AllImformScene")) {
            // 创建显示所有借阅信息界面
            root = createAllImformScene();
            newScene = new Scene(root, 400, 250);
        } else if (sceneName.equals("SearchBookScene")) {
            // 创建根据ISBN查询书界面
            root = createSearchBookScene();
            newScene = new Scene(root, 400, 250);
        } else if (sceneName.equals("KeepinBookScene")) {
            // 创建续借界面
            root = createKeepinBookScene();
            newScene = new Scene(root, 400, 250);
        } else if (sceneName.equals("AllBookScene")) {
            // 创建显示书库所有书界面
            root = createAllBookScene();
            newScene = new Scene(root, 600, 400);
        }


        newStage.setScene(newScene);
        newStage.show();
    }

    //    借书
    private Parent createBorrowBookScene() {

        GridPane root = new GridPane();
        root.setPadding(new Insets(10));
        root.setVgap(5);
        root.setHgap(5);
        root.setAlignment(Pos.CENTER);

        Label isbnLabel = new Label("ISBN:");
        TextField isbnTextField = new TextField();
        Label showBookInform = new Label("信息展示:");
        TextArea informTextArea = new TextArea(); // 使用TextArea来显示信息，可滚动显示
        informTextArea.setEditable(false); // 设置信息展示区域不可编辑

        Button searchButton = new Button("搜索");

        searchButton.setOnAction(event -> {
            String isbn = isbnTextField.getText();
            // 根据ISBN搜索书籍信息
            String bookInformation = searchBookByISBN(book,isbn);

            // 将搜索到的书籍信息显示在信息展示TextArea中
            informTextArea.setText(bookInformation);

        });

        Button borrowButton = new Button("借书");

        borrowButton.setOnAction(event -> {
            String isbn = isbnTextField.getText();

            // 执行借书操作
            // ...
            try {
                book.borrow(EnterUtil.input, isbn);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            // 弹出提示框或更新界面等操作
            // ...
        });

        GridPane.setHalignment(isbnLabel, HPos.RIGHT);
        GridPane.setHalignment(showBookInform, HPos.RIGHT);
        GridPane.setHalignment(searchButton, HPos.LEFT);

        // 将信息展示区域放置在滚动面板中
        ScrollPane scrollPane = new ScrollPane(informTextArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(200); // 设置滚动面板的高度

        root.add(isbnLabel, 0, 0);
        root.add(isbnTextField, 1, 0);
        root.add(searchButton, 2, 0);
        root.add(showBookInform, 0, 1);
        root.add(scrollPane, 1, 1, 2, 1);
        root.add(borrowButton, 1, 2);

        return root;
    }

    private String searchBookByISBN(Book book,String isbn) {
        // 根据ISBN搜索书籍信息的逻辑
        // ...
        // 返回搜索到的书籍信息

        return "书籍信息：\n" + DBUtil.dbSelectBookById(book,isbn);
    }

    //还书
    private Parent createReturnBookScene() {
        GridPane root = new GridPane();
        root.setPadding(new Insets(10));
        root.setVgap(5);
        root.setHgap(5);
        root.setAlignment(Pos.CENTER);

        Label isbnLabel = new Label("ISBN:");
        TextField isbnTextField = new TextField();

        Button returnButton = new Button("还书");

        returnButton.setOnAction(event -> {
            Book book1=new Book();
            String isbn = isbnTextField.getText();
            DBUtil.dbSelectBookMatch(book1,isbn);
            if (!DBUtil.isFind) {
                showDialog("此书不存在");
            } else {
                if (DBUtil.dbreturnbook(EnterUtil.input.getName(),isbn)) {
                    showDialog("还书成功");
                }else {
                    showDialog("您未借此书");
                }
            }
            // 执行还书操作
            // ...

            // 弹出提示框或更新界面等操作
            // ...
        });

        GridPane.setHalignment(isbnLabel, HPos.RIGHT);

        root.add(isbnLabel, 0, 0);
        root.add(isbnTextField, 1, 0);
        root.add(returnButton, 2, 0);

        return root;
    }

    //显示所有借阅信息
    private Parent createAllImformScene() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);

        // 创建表格控件
        TableView<Book> tableView = new TableView<>();

        // 创建列，并设置列名和属性绑定
        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));

        TableColumn<Book, String> nameColumn = new TableColumn<>("name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Book, String> cateColumn = new TableColumn<>("category");
        cateColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Book, String> exdateColumn = new TableColumn<>("expiredate");
        exdateColumn.setCellValueFactory(new PropertyValueFactory<>(
                "expireDate"));

        // 将列添加到表格
        tableView.getColumns().addAll(isbnColumn, nameColumn,
                cateColumn,exdateColumn);

        // 获取书库中的所有书籍信息
        ObservableList<Book> bookList = getBookListFromDatabase();

        // 将书籍数据添加到表格
        tableView.setItems(bookList);

        root.getChildren().add(tableView);

        return root;
    }

    public ObservableList<Book> getBookListFromDatabase() {
        // 模拟从数据库中获取书库中的所有书籍信息
        ObservableList<Book> bookList =
                FXCollections.observableArrayList(DBUtil.dbshowinfo(EnterUtil.input));
        return bookList;
    }

    //根据ISBN查询
    private Parent createSearchBookScene() {
        GridPane root = new GridPane();
        root.setPadding(new Insets(10));
        root.setVgap(5);
        root.setHgap(5);
        root.setAlignment(Pos.CENTER);

        Label searchLabel = new Label("ISBN:");
        TextField isbnTextField = new TextField();

        Label categoryNumberLabel = new Label("Category Number:");
        Label canumText = new Label();
        Label nameLabel = new Label("Name:");
        Label nameText = new Label();
        Label categoryLabel = new Label("Category:");
        Label cateText = new Label();
        Label priceLabel = new Label("Price:");
        Label priceText = new Label();
        Label numberLabel = new Label("Number:");
        Label numText = new Label();

        Button searchButton = new Button("搜索");

        searchButton.setOnAction(event -> {
            String isbn = isbnTextField.getText();
            String[] s = DBUtil.dbSelectBookByIdshuzu(book, isbn);
            canumText.setText(s[1]);
            nameText.setText(s[2]);
            cateText.setText(s[3]);
            priceText.setText(s[4]);
            numText.setText(s[5]);
            // 执行查询操作
            // ...

            // 弹出提示框或更新界面等操作
            // ...
        });


        root.add(searchLabel, 0, 0);
        root.add(isbnTextField, 1, 0);

        root.add(categoryNumberLabel, 0, 1);
        root.add(canumText, 1, 1);
        root.add(nameLabel, 0, 2);
        root.add(nameText, 1, 2);
        root.add(categoryLabel, 0, 3);
        root.add(cateText, 1, 3);
        root.add(priceLabel, 0, 4);
        root.add(priceText, 1, 4);
        root.add(numberLabel, 0, 5);
        root.add(numText, 1, 5);

        root.add(searchButton, 2, 0);

        return root;
    }

    //续借
    private Parent createKeepinBookScene() {
        GridPane root = new GridPane();
        root.setPadding(new Insets(10));
        root.setVgap(5);
        root.setHgap(5);
        root.setAlignment(Pos.CENTER);

        Label isbnLabel = new Label("ISBN:");
        TextField isbnTextField = new TextField();

        Button keepinButton = new Button("续借");

        keepinButton.setOnAction(event -> {
            String isbn = isbnTextField.getText();
            if (!DBUtil.dbrenew(EnterUtil.input, isbn)) {
                showDialog("没有借这本书");
            } else {
                showDialog("续借成功");
                /*这里添加代码，用this.getDate(i)修改数据库对应位置的date*/
            }
            // 执行续借操作
            // ...

            // 弹出提示框或更新界面等操作
            // ...
        });

        GridPane.setHalignment(isbnLabel, HPos.RIGHT);

        root.add(isbnLabel, 0, 0);
        root.add(isbnTextField, 1, 0);
        root.add(keepinButton, 2, 0);

        return root;
    }

    //显示书库所有书
    private Parent createAllBookScene() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);

        // 创建表格控件
        TableView<Book> tableView = new TableView<>();

        // 创建列，并设置列名和属性绑定
        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));

        TableColumn<Book, String> canumColumn = new TableColumn<>("category_number");
        canumColumn.setCellValueFactory(new PropertyValueFactory<>("category_number"));

        TableColumn<Book, String> nameColumn = new TableColumn<>("name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Book, String> cateColumn = new TableColumn<>("category");
        cateColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Book, Double> priceColumn = new TableColumn<>("price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Book, Integer> numColumn = new TableColumn<>("number");
        numColumn.setCellValueFactory(new PropertyValueFactory<>("number"));

        // 将列添加到表格
        tableView.getColumns().addAll(isbnColumn, canumColumn, nameColumn, cateColumn, priceColumn, numColumn);

        // 获取书库中的所有书籍信息
        ObservableList<Book> bookList = getAllBookListFromDatabase();

        // 将书籍数据添加到表格
        tableView.setItems(bookList);

        root.getChildren().add(tableView);

        return root;
    }

    public ObservableList<Book> getAllBookListFromDatabase() {
        // 模拟从数据库中获取书库中的所有书籍信息
        ObservableList<Book> bookList =
                FXCollections.observableArrayList(DBUtil.dbshowallbook(EnterUtil.input));
        return bookList;
    }

    //    弹窗
    //    弹窗
    public static void showDialog(String message) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(primaryStage);

        VBox dialogVBox = new VBox(20);
        dialogVBox.setAlignment(Pos.CENTER);

        Label label = new Label(message);
        dialogVBox.getChildren().add(label);

        Scene dialogScene = new Scene(dialogVBox, 200, 75); // 调整对话框的宽度和高度
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }
}