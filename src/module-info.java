module assignment {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires itextpdf;

	opens assignment to javafx.graphics, javafx.fxml;
}
