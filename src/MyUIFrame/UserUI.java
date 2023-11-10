package MyUIFrame;

import MyJavabean.Book;
import MyUtil.CollectUtil;
import MyUtil.DBUtil;
import MyUtil.EnterUtil;
import MyUtil.SelectBookUtile;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.SQLException;

public class UserUI extends Application {

    private static Stage primaryStage;
    Book book=new Book();
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        Button newBookButton = createNavigationButton("新书录入", "NewBookScene");
        Button updateBookButton = createNavigationButton("变更书目信息", "UpdateBookScene");
        Button displayBooksButton = createNavigationButton("显示所有书籍信息", "DisplayBooksScene");

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(newBookButton, updateBookButton, displayBooksButton);

        Scene scene = new Scene(root, 300, 200);

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
        if (sceneName.equals("NewBookScene")) {
            // 创建新书录入界面
            root = createNewBookScene();
            newScene = new Scene(root, 400, 250);
        } else if (sceneName.equals("UpdateBookScene")) {
            // 创建变更书目信息界面
            root = createUpdateBookScene();
            newScene = new Scene(root, 400, 250);
        } else if (sceneName.equals("DisplayBooksScene")) {
            // 创建显示所有书籍信息界面
            root = createDisplayBooksScene();
            newScene = new Scene(root, 600, 400);
        }


        newStage.setScene(newScene);
        newStage.show();
    }

    private Parent createDisplayBooksScene() {
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
        ObservableList<Book> bookList = getBookListFromDatabase();

        // 将书籍数据添加到表格
        tableView.setItems(bookList);

        root.getChildren().add(tableView);

        return root;
    }

    private ObservableList<Book> getBookListFromDatabase() {
        // 模拟从数据库中获取书库中的所有书籍信息
        ObservableList<Book> bookList =
                FXCollections.observableArrayList(DBUtil.dbshowallbook(EnterUtil.input));
        // TODO...例子:bookList.add(Book对象)

        return bookList;
    }

    //变更书目信息
    private Parent createUpdateBookScene() {
        GridPane root = new GridPane();
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setVgap(5);
        root.setHgap(5);
        root.setAlignment(Pos.CENTER);


        Label isbnLabel = new Label("ISBN:");
        TextField isbnTextField = new TextField();
        Label categoryNumberLabel = new Label("Category Number:");
        TextField categoryNumberTextField = new TextField();
        Label nameLabel = new Label("Name:");
        TextField nameTextField = new TextField();
        Label categoryLabel = new Label("Category:");
        TextField categoryTextField = new TextField();
        Label priceLabel = new Label("Price:");
        TextField priceTextField = new TextField();
        Button creatButton = new Button("修改");

        creatButton.setOnAction(event -> {
            String isbn = isbnTextField.getText();
            String categoryNumber = categoryNumberTextField.getText();
            String name = nameTextField.getText();
            String category = categoryTextField.getText();
            float price = Float.parseFloat(priceTextField.getText());
            book.setISBN(isbn);
            book.setCategory_number(categoryNumber);
            book.setName(name);
            book.setCategory(category);
            book.setPrice(price);
            // 执行操作
            // ...
            SelectBookUtile.SelectStart(EnterUtil.input, book, isbn);
            if (!DBUtil.isFind) {
                showDialog("您要修改的图书不存在");
            } else {
                try {
                    DBUtil.dbUpdateBookMatch(book, isbn);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            // 弹出提示框或更新界面等操作
            // ...
        });

        root.add(isbnLabel, 0, 0);
        root.add(isbnTextField, 1, 0);
        root.add(categoryNumberLabel, 0, 1);
        root.add(categoryNumberTextField, 1, 1);
        root.add(nameLabel, 0, 2);
        root.add(nameTextField, 1, 2);
        root.add(categoryLabel, 0, 3);
        root.add(categoryTextField, 1, 3);
        root.add(priceLabel, 0, 4);
        root.add(priceTextField, 1, 4);

        root.add(creatButton, 1, 6);

        return root;
    }

    private Parent createNewBookScene() {
        GridPane root = new GridPane();
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setVgap(5);
        root.setHgap(5);
        root.setAlignment(Pos.CENTER);


        Label isbnLabel = new Label("ISBN:");
        TextField isbnTextField = new TextField();
        Label categoryNumberLabel = new Label("Category Number:");
        TextField categoryNumberTextField = new TextField();
        Label nameLabel = new Label("Name:");
        TextField nameTextField = new TextField();
        Label categoryLabel = new Label("Category:");
        TextField categoryTextField = new TextField();
        Label priceLabel = new Label("Price:");
        TextField priceTextField = new TextField();
        Label numberLabel = new Label("Number:");
        TextField numberTextField = new TextField();
        Button creatButton = new Button("创建");

        creatButton.setOnAction(event -> {
            String isbn = isbnTextField.getText();
            String categoryNumber = categoryNumberTextField.getText();
            String name = nameTextField.getText();
            String category = categoryTextField.getText();
            float price = Float.parseFloat(priceTextField.getText());

            int number = Integer.parseInt(numberTextField.getText());
            if (number < 1 || number > 4) {
                showDialog("输入参数不合法,请输入1到4的整数");
            } else {
                // 执行新书录入操作
                // ...
                book.setISBN(isbn);
                book.setCategory_number(categoryNumber);
                book.setName(name);
                book.setCategory(category);
                book.setPrice(price);
                book.setNumber(number);
                // 弹出提示框或更新界面等操作
                CollectUtil.CollectStart(EnterUtil.input, book);
                // ...
            }
        });

        root.add(isbnLabel, 0, 0);
        root.add(isbnTextField, 1, 0);
        root.add(categoryNumberLabel, 0, 1);
        root.add(categoryNumberTextField, 1, 1);
        root.add(nameLabel, 0, 2);
        root.add(nameTextField, 1, 2);
        root.add(categoryLabel, 0, 3);
        root.add(categoryTextField, 1, 3);
        root.add(priceLabel, 0, 4);
        root.add(priceTextField, 1, 4);
        root.add(numberLabel, 0, 5);
        root.add(numberTextField, 1, 5);
        root.add(creatButton, 1, 6);

        return root;
    }


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