module assignment {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	
	opens assignment to javafx.graphics, javafx.fxml;
}
