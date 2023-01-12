module assignment {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires itextpdf;
	requires javax.mail;
	requires activation;
	requires org.junit.jupiter.api;

	opens assignment to javafx.graphics, javafx.fxml;
}
