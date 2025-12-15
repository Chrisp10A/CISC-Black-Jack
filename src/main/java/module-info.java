module com.example.javafxblackjack {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javafxblackjack to javafx.fxml;
    exports com.example.javafxblackjack;
}