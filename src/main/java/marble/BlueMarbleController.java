package marble;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BlueMarbleController {
	BlueMarble blueMarble;

	@FXML
	public void initialize() {
		blueMarble = new BlueMarble();
		LocalDate date = LocalDate.now();
		LocalDate minusDate = date.minusDays(7);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDate = minusDate.format(formatter);
		blueMarble.setDate(formattedDate);
		datePicker.setValue(minusDate);
	}

	@FXML
	private DatePicker datePicker;

	@FXML
	private ImageView blueMarbleImage;

	@FXML
	private Button addImage;

	@FXML
	private CheckBox enhancedImage;

	@FXML
	private CheckBox blackAndWhite;

	@FXML
	private TextArea messageBox;

	private boolean greyscale = false;
	private boolean enhanced = false;


	@FXML
	void onBlackAndWhite(ActionEvent event) {
		if (blackAndWhite.isSelected()) {
			greyscale = true;
		} else {
			greyscale = false;
		}
	}

	@FXML
	void onAddImage(ActionEvent event) throws IOException {
		if (enhanced) {
			blueMarble.setEnhanced(true);
		}

		InputStream file = blueMarble.getImage();
		Image image = new Image(file);

		blueMarbleImage.setImage(image);
		blueMarbleImage.setPreserveRatio(true);

		ColorAdjust monochrome = new ColorAdjust();
		monochrome.setSaturation(-1);

		blueMarbleImage.setImage(image);
		blueMarbleImage.setPreserveRatio(true);

		if (greyscale) {
			monochrome.setSaturation(-1);
		} else {
			monochrome.setSaturation(0);
		}

		blueMarbleImage.setEffect(monochrome);
	}

	@FXML
	void onDatePicker(ActionEvent event) throws IllegalArgumentException {
		LocalDate date = datePicker.getValue();

		if (!date.isBefore(LocalDate.now())) {
			messageBox.setText("enter a past date!");
			throw new IllegalArgumentException("enter a past date");
		} else {
			messageBox.setText("");
		}

		if (!date.isBefore(LocalDate.parse("2018-06-01")) && enhanced) {
			messageBox.setText("enter earlier date than June 2018 for enhanced images!");
			throw new IllegalArgumentException("enter earlier date than June 2018 for enhanced images!");
		} else {
			messageBox.setText("");
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDate = date.format(formatter);
		blueMarble.setDate(formattedDate);
	}

	@FXML
	void onEnhancedImage(ActionEvent event) {
		if (enhancedImage.isSelected()) {
			enhanced = true;
		} else {
			enhanced = false;
		}
	}
}



