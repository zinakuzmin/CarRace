package client;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * Login Pane View 
 * @author Moran Tenzer & Ben Nave
 */
public class LoginView extends BorderPane {
	/**
	 * the name text field.
	 */
	private TextField txtNameField = new TextField();
	/**
	 * the id text field.
	 */
	private TextField txtIdField = new TextField();
	/**
	 * the text area.
	 */
	private TextArea textArea = new TextArea();
	/**
	 * the login status.
	 */
	private Boolean isActionEnabled;
	/**
	 * the client's controller listener.
	 */
	private ViewControllerEvents controllerEvent;
	/**
	 * the login button.
	 */
	private Button btnOK;
	
	/**
	 * build the login pane in the client's main stage.
	 */
	public LoginView() {
		isActionEnabled = true;
		txtNameField.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		txtIdField.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		
		btnOK = new Button("Login");
		btnOK.setMinHeight(55);
		btnOK.setOnAction(e -> {
			if(isActionEnabled) {
				String tempName = txtNameField.getText();
				String tempID = txtIdField.getText();
				
				if(tempID.equals("") && tempName.equals("")) {
					Platform.runLater(() -> {
						textArea.appendText("User Name & User ID Cannot Be Empty, Please try again.\n");
					});
					return;
				}
				
				else if(tempName.equals("")) {
					Platform.runLater(() -> {
						textArea.appendText("User Name Cannot Be Empty, Please try again.\n");
					});
					return;
				}

				else if(tempID.equals("")) {
					Platform.runLater(() -> {
						textArea.appendText("User ID Cannot Be Empty, Please try again.\n");
					});
					return;
				}
				controllerEvent.connect(tempName, tempID);
			}
		});

		GridPane gridPane = new GridPane();
		gridPane.add(new Label("\tUser Name:\t"), 0, 0);
		gridPane.add(txtNameField, 1, 0);
		gridPane.add(new Label("\tUser ID:\t"), 0, 1);
		gridPane.add(txtIdField, 1, 1);
		
		HBox hBox = new HBox(5);
		hBox.setPadding(new Insets(5, 0, 10, 0));
		hBox.getChildren().addAll(gridPane, btnOK);

		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(5, 5, 5, 5));
		borderPane.setStyle("-fx-border-color: red");
		borderPane.setCenter(hBox);


		this.setTop(borderPane);
		this.setCenter(new ScrollPane(textArea));
		this.setLayoutX(200);
		this.setLayoutY(100);
	}
	
	/**
	 * @return return the cilent's controller listener.
	 */
	public ViewControllerEvents getControllerListener() {
		return controllerEvent;
	}

	/**
	 * this method sets the cilent's controller event.
	 * 
	 * @param controllerListener - the cilent's controller event.
	 */
	public void setControllerListener(ViewControllerEvents controllerListener) {
		this.controllerEvent = controllerListener;
	}
	
	/**
	 * @return return the name text field.
	 */
	public TextField getNameField() {
		return txtNameField;
	}
	
	/**
	 * @return return the id text field.
	 */
	public TextField getIdField() {
		return txtIdField;
	}
	
	/**
	 * @return return the text area.
	 */
	public TextArea getTextArea() {
		return textArea;
	}
	
	/**
	 * @return return the button.
	 */
	public Button getButton() {
		return btnOK;
	}
	
	/**
	 * this method checks if the client is able to login.
	 * 
	 * @param isEnabled - the client's login status.
	 */
	public void setIsActionEnabled(boolean isEnabled){
		this.isActionEnabled = isEnabled;
	}
	
}
