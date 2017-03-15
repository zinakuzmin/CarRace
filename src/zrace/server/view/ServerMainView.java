package zrace.server.view;

import java.sql.ResultSet;
import java.sql.SQLException;

import zrace.server.db.DBHandler;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ServerMainView extends Application {
	// private TableView<?> tableView = new TableView<Object>();
	private TextArea textArea;

	public ServerMainView(TextArea logActivity) {
		this.textArea = logActivity;
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		buildActivityViewer(arg0);

	}

	public void buildActivityViewer(Stage stage) {

		stage.setTitle("Zrace server");
		Group root = new Group();
		Scene scene = new Scene(root, 500, 500, Color.WHITE);

		TabPane tabPane = new TabPane();

		BorderPane borderPane = new BorderPane();

		tabPane.getTabs().add(createActivityTab(scene));
		tabPane.getTabs().add(createDBViewerTab());

		// bind to take available space
		borderPane.prefHeightProperty().bind(scene.heightProperty());
		borderPane.prefWidthProperty().bind(scene.widthProperty());

		borderPane.setCenter(tabPane);
		root.getChildren().add(borderPane);
		stage.setScene(scene);
		stage.show();

		for (int i = 0; i < 20; i++)
			addActivityLog("log " + i + "\n");

	}

	private Tab createActivityTab(Scene scene) {
		Tab activityTab = new Tab();
		activityTab.setClosable(false);

		Pane pane = new Pane();

		Label activityLabel = new Label("View activity log");
		pane.getChildren().add(activityLabel);

		// Scene scene = new Scene(pane, 500, 500);
		pane.prefWidthProperty().bind(scene.widthProperty());
		pane.prefHeightProperty().bind(scene.heightProperty());

		ScrollPane scroll = new ScrollPane();

		// Center ScrollPane
		scroll.layoutXProperty().bind(
				pane.widthProperty().divide(2)
						.subtract(scroll.widthProperty().divide(2)));
		scroll.layoutYProperty().bind(
				pane.heightProperty().divide(2)
						.subtract(scroll.heightProperty().divide(2)));
		scroll.setPrefSize(400, 400);

		// scroll.layoutXProperty().bind(pane.widthProperty());
		// scroll.layoutYProperty().bind(pane.heightProperty());
		// scroll.setPrefSize(200, 200);

		// ListView<String> list = new ListView<>();
		// textArea = new TextArea();

		textArea.prefWidthProperty().bind(scroll.widthProperty());
		textArea.prefHeightProperty().bind(scroll.heightProperty());
		// textArea.setPrefSize(400,400 );

		textArea.appendText("zina\n");
		textArea.appendText("second line\n");
		textArea.appendText("zina\n");
		textArea.appendText("second line\n");
		textArea.appendText("zina\n");
		textArea.appendText("second line\n");
		textArea.appendText("zina\n");
		textArea.appendText("second line\n");
		textArea.appendText("zina\n");
		textArea.appendText("second line\n");
		textArea.appendText("zina\n");
		textArea.appendText("second line\n");
		textArea.appendText("zina\n");
		textArea.appendText("second line\n");
		scroll.setContent(textArea);

		pane.getChildren().addAll(scroll);

		activityTab.setContent(pane);

		activityTab.setText("View activity log");
		return activityTab;

	}

	private Tab createDBViewerTab() {

		TableView<?> tableView = new TableView<Object>();
		Button btShowContents = new Button("Show Contents");
		Label lblStatus = new Label();

		ComboBox<String> comboBox = new ComboBox<>(setComboboxOptions());
		comboBox.setVisibleRowCount(5);

		HBox hBox = new HBox(5);
		hBox.getChildren().add(new Label("DB selector"));
		hBox.getChildren().add(comboBox);
		hBox.getChildren().add(btShowContents);
		hBox.setAlignment(Pos.CENTER);
		BorderPane pane = new BorderPane();
		pane.setCenter(tableView);
		pane.setTop(hBox);
		pane.setBottom(lblStatus);

		Tab dbViewerTab = new Tab();
		dbViewerTab.setClosable(false);
		dbViewerTab.setContent(pane);
		dbViewerTab.setText("View DB statistics");
		btShowContents.setOnAction(e -> showContents("String", tableView));
		return dbViewerTab;

	}

	private ObservableList<String> setComboboxOptions() {
		ObservableList<String> options = FXCollections.observableArrayList(
				"View races status", "View system revenue", "3", "4", "5");

		return options;
	}

	private void addActivityLog(String log) {
		textArea.appendText(log);

	}

	private void showContents(String comboboxChoise, TableView tableView) {
		// private void showContents() {
		DBHandler db = new DBHandler();
		ResultSet res = db.getAllUsers();
		ResultSet res1 = db.getAllActiveRaces(false);
		ResultSet res2 = db.getAllSystemRevenue();
		ResultSet res3 = db.getAllUsersRevenue();
		ResultSet res4 = db.getRaceBets(1001);
		ResultSet res5 = db.getRacesStatus();
		populateTableView(res5, tableView);
		// String tableName = comboboxChoise;
		// try {
		// String queryString = "select * from " + tableName;
		// ResultSet resultSet = stmt.executeQuery(queryString);
		// resultSet = stmt.executeQuery(queryString);
		// populateTableView(resultSet, tableView);
		// } catch (SQLException ex) {
		// ex.printStackTrace();
		// }
	}

	private void populateTableView(ResultSet rs, TableView tableView) {
		tableView.getColumns().clear();
		ObservableList<ObservableList> data = FXCollections
				.observableArrayList();
		try {

			/**********************************
			 * TABLE COLUMN ADDED DYNAMICALLY *
			 **********************************/
			for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
				// We are using non property style for making dynamic table
				final int j = i;
				TableColumn col = new TableColumn(rs.getMetaData()
						.getColumnName(i + 1));

				col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(
							CellDataFeatures<ObservableList, String> param) {
						if (param.getValue().get(j) != null)
							return new SimpleStringProperty(param.getValue()
									.get(j).toString());
						else
							return null;
					}
				});

				tableView.getColumns().addAll(col);
				System.out.println("Column [" + i + "] ");
			}

			/********************************
			 * Data added to ObservableList *
			 ********************************/
			while (rs.next()) {
				// Iterate Row
				ObservableList<String> row = FXCollections
						.observableArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					// Iterate Column
					row.add(rs.getString(i));
				}
				System.out.println("Row [1] added " + row);
				data.add(row);

			}

			// FINALLY ADDED TO TableView
			tableView.setItems(data);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data");
		}
	}
}
