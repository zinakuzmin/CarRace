package zrace.server.db;

import java.sql.ResultSet;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Build the Data Base View
 * @author Zina Kuzmin
 *
 */
public class DataBaseViewer extends Stage{
	
	private final String QUERY_ALL_USER_BETS = "Show All User Bets";	
	private final String QUERY_ALL_RACE_BETS = "Show All Race Bets";

	
	/**
	 * the beginner mode button.
	 */
	RadioButton showAllUserBetsBtn = new RadioButton("Show All User Bets");
	/**
	 * the advanced mode button.
	 */
	RadioButton showAllRaceBetsBtn = new RadioButton("Show All Race Bets");
	
	
	/**
	 * {@code currentQuery} - the last query that was requested by the user.
	 */
	private String currentQuery = "";
	/**
	 * {@code tableView} - a javafx component which used for presenting data from database.
	 */
	private TableView<String[]> tableView = new TableView<String[]>();
	/**
	 * {@code btnOk} - a javafx component which control execution on a chosen query from combo box.
	 */
	private Button okBtn = new Button("OK");
	/**
	 * {@code lblStatus} - a javafx component.
	 */
	private Label lblStatus = new Label();
	/**
	 * {@code viewEvents} , {@code detailsBtn}  - a javafx component which use to control table view.
	 */
	private Button delBtn,detailsBtn;
	/**
	 * {@code viewEvents} - a event listener.
	 */
	private ViewEvents viewEvents;
	/**
	 * {@code dataBaseHandler} - used to control on database.
	 */
	private DBHandler dataBaseHandler;

	
	/**
	 * This Constructor create a new instance of DataBaseView class and initialize the stage and it's components.
	 */
	public DataBaseViewer(){
		tableView.setEditable(false);
		
		HBox hBox = new HBox(5);
		hBox.setPadding(new Insets(5, 0, 10, 0));
		ToggleGroup b = new ToggleGroup();
		showAllRaceBetsBtn.setToggleGroup(b);
		showAllUserBetsBtn.setToggleGroup(b);
		hBox.getChildren().addAll(showAllRaceBetsBtn , showAllUserBetsBtn , okBtn);
		
		hBox.setAlignment(Pos.CENTER);
		hBox.setStyle("-fx-border-color: red");
//		okBtn.setOnAction(e -> okButtonListner());
		HBox hBox2 = new HBox(26);
		
		
		delBtn = new Button("Delete");
//		delBtn.setOnAction(e -> deleteButtonListner());
		delBtn.setDisable(true);
		
		
		detailsBtn = new Button("Details");
//		detailsBtn.setOnAction(e -> detailsButtonListner());
		detailsBtn.setDisable(true);
		

		hBox2.getChildren().addAll(delBtn,detailsBtn);
		hBox2.setPadding(new Insets(10, 0, 0, 225));
		hBox2.setStyle("-fx-border-color: red");
		BorderPane pane = new BorderPane();
		pane.setCenter(tableView);
		pane.setTop(hBox);
		pane.setBottom(lblStatus);
		pane.setBottom(hBox2);
		
		Scene scene = new Scene(pane, 600, 250);
		this.setTitle("Cannon Game DB Viewer"); // Set the stage title
		this.setScene(scene); // Place the scene in the stage
		this.setAlwaysOnTop(true);
		this.show(); // Display the stage
		this.setOnCloseRequest( e-> {
			viewEvents.viewClosed();
		});
		
		
	}
	
	/**
	 * This function is internal to the DataBaseView Class.
	 * It purpose is to update the information that is being presented to the user when the delete button is pressed.
	 */
//	private void deleteButtonListner() {
//		if(!tableView.getSelectionModel().isEmpty()) {
//			disableNameColumnEditable();
//			
//			if(currentQuery.equals(QUERY_ALL_USER_BETS)) {
//				int row = tableView.getSelectionModel().getSelectedCells().get(0).getRow();
//				int playerId =(int)Integer.parseInt((String)(tableView.getColumns().get(0).getCellData(row)));
//				dataBaseHandler.delPlayer(playerId);
//				tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());
//				tableView.getSelectionModel().clearSelection();
//				
//				
//				delBtn.setDisable(false);
//				detailsBtn.setDisable(false);
//			}
//			else if(currentQuery.equals(QUERY_ALL_RACE_BETS) || currentQuery.equals(QUERY_ALL_GAMES)) {
//				int row = tableView.getSelectionModel().getSelectedCells().get(0).getRow();
//				int playerId =(int)Integer.parseInt((String)(tableView.getColumns().get(0).getCellData(row)));
//				int gameID =(int)Integer.parseInt((String)(tableView.getColumns().get(1).getCellData(row)));
//				dataBaseHandler.delGame(playerId, gameID);
//				tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());
//				tableView.getSelectionModel().clearSelection();
//				
//				
//				delBtn.setDisable(false);
//				detailsBtn.setDisable(false);
//			}
//			
//		}
//		
//	}
	
	/**
	 * This function is internal to the DataBaseView Class.
	 * It purpose is to update the information that is being presented to the user when the details button is pressed.
	 */
//	private void detailsButtonListner() {
//		if(!tableView.getSelectionModel().isEmpty()) {
//			disableNameColumnEditable();
//			
//			if(currentQuery.equals(QUERY_ALL_PLAYERS)) {
//				currentQuery = QUERY_ALL_PLAYER_GAMES;
//				int row = tableView.getSelectionModel().getSelectedCells().get(0).getRow();
//				int playerId =(int)Integer.parseInt((String)(tableView.getColumns().get(0).getCellData(row)));
//				ResultSet rs = dataBaseHandler.getAllPlayerGames(playerId);
//				populateTableView(rs,tableView);
//				
//			
//				delBtn.setDisable(false);
//				detailsBtn.setDisable(false);
//				
//			}
//			else if(currentQuery.equals(QUERY_ALL_PLAYER_GAMES) || currentQuery.equals(QUERY_ALL_GAMES)) {
//				currentQuery = QUERY_ALL_GAMES_EVENTS;
//				int row = tableView.getSelectionModel().getSelectedCells().get(0).getRow();
//				int playerId =(int)Integer.parseInt((String)(tableView.getColumns().get(0).getCellData(row)));
//				int gameID =(int)Integer.parseInt((String)(tableView.getColumns().get(1).getCellData(row)));
//				ResultSet rs = dataBaseHandler.getPlayerGameEvents(playerId, gameID);
//				populateTableView(rs,tableView);
//				
//				
//				delBtn.setDisable(true);
//				detailsBtn.setDisable(true);
//			}
//		}
//	}
	
	/**
	 * This function is internal to the DataBaseView Class.
	 * It purpose is to update the information that is being presented to the user when the ok button is pressed.
	 */
//	private void okButtonListner() {
//		try {
//			if(showAllPlayersRadioBtn.isSelected())
//			{
//				currentQuery = QUERY_ALL_PLAYERS;
//				ResultSet rs = dataBaseHandler.getAllPlayers();
//				populateTableView(rs,tableView);
//				
//			
//				delBtn.setDisable(false);
//				detailsBtn.setDisable(false);
//				enableNameColumnEditable(); 
//			}
//			else if(showAllGamesRadioBtn.isSelected()) {
//				currentQuery = QUERY_ALL_GAMES;
//				ResultSet rs = dataBaseHandler.getAllGames();
//				populateTableView(rs,tableView);
//				
//				delBtn.setDisable(false);
//				detailsBtn.setDisable(false);
//				disableNameColumnEditable();
//			}
//			else if(gamesRankRadioBtn.isSelected()) {
//				currentQuery = QUERY_TOP_3_GAMES_RANK;
//				ResultSet rs = dataBaseHandler.getTopThree();
//				populateTableView(rs,tableView);
//				
//				
//				delBtn.setDisable(false);
//				detailsBtn.setDisable(false);
//				enableNameColumnEditable();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//			
//		
//	}
	
	/**
	 * This function is internal to the DataBaseView Class.
	 * This function enable the user t0 edit the current information the is presented - only name column if presented.
	 */
//	private void enableNameColumnEditable() {
//		tableView.setEditable(true);
//		TableColumn<String[], ?> col = tableView.getColumns().get(1);
//		col.setEditable(true);
//	}
	
	/**
	 * This function is internal to the DataBaseView Class.
	 * This function disenable the user t0 edit the current information the is presented - only name column if presented.
	 */
	private void disableNameColumnEditable() {
		tableView.setEditable(false);
		TableColumn<String[], ?> col = tableView.getColumns().get(1);
		col.setEditable(false);
	}
	
	/**
	 * This function is internal to the DataBaseView Class.
	 * This function fill the tableView column's and row's data with the information that rs has.
	 * 
	 * @param rs - the ResultSet Answer that returned by sql query.
	 * @param tableView - a table view to fill the information from rs.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void populateTableView(ResultSet rs, TableView tableView) { 
		tableView.getColumns().clear();
		tableView.setItems(FXCollections.observableArrayList());
		try { 
			int numOfColums = rs.getMetaData().getColumnCount();
			for(int i = 1 ; i <= numOfColums ; i++)
			{
				final int columNum = i - 1;
				TableColumn<String[],String> column = new TableColumn<>(rs.getMetaData().getColumnLabel(i));
				column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue()[columNum]));
				column.setCellFactory(e -> new EditCell());
				column.setEditable(false);
				tableView.getColumns().add(column);
			}
			while(rs.next())
			{
				String[] cells = new String[numOfColums];
				for(int i = 1 ; i <= numOfColums ; i++)
					cells[i - 1] = rs.getString(i);
				tableView.getItems().add(cells);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data");
		}
	}
	
	/**
	 * Setting To DataBaseView a DataBaseHandler for query's execution.
	 * 
	 * @param dataBaseHandler - a data base handler class that is being used for execute different query's.
	 */
	public void setDataBaseHandler(DBHandler dataBaseHandler) {
		this.dataBaseHandler = dataBaseHandler;
	}
	
	/**
	 * setting to DataBaseView a event Listener view.
	 * 
	 * @param viewEvents - viewEvents listener.
	 */
	public void setViewEventsListner(ViewEvents viewEvents){
		this.viewEvents = viewEvents;
	}

	
	
	/**
	 * This Class Manages each tableview cell in the DataBaseView tableView that can be edited.
	 * 
	 *
	 */
	class EditCell extends TableCell<String[], String> {

        private TextField textField = new TextField();
        
        
        /**
         * This Constructor Create a EditCell Class.
         */
        EditCell() {   
            textProperty().bind(itemProperty());
            setGraphic(textField);
            setContentDisplay(ContentDisplay.TEXT_ONLY);

            textField.setOnAction(evt -> commitEdit(textField.getText()));
        }

        /**
         * This Method is Called by EditCell when the user start editing table view cell.
         */
        @Override
        public void startEdit() {
            super.startEdit();
            textField.setText(getItem());
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            textField.requestFocus();          
        }

        /**
         * This Method is Called by EditCell when the user cancel editing table view cell.
         */
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }

        /**
         * Editing Selected Cell using Handler.
         */
        @Override
        public void commitEdit(String val) {
            super.commitEdit(val);
            int row = tableView.getSelectionModel().getSelectedCells().get(0).getRow();
            int playerId =(int)Integer.parseInt((String)(tableView.getColumns().get(0).getCellData(row)));
//            dataBaseHandler.updatePlayerFullName(playerId, val); 
            
            tableView.getSelectionModel().clearSelection();
            setContentDisplay(ContentDisplay.TEXT_ONLY);
            
          
//            if(currentQuery == QUERY_ALL_PLAYERS)
//            {
//				ResultSet res = dataBaseHandler.getAllPlayers();
//				populateTableView(res,tableView);
//				
//			
//				delBtn.setDisable(false);
//				detailsBtn.setDisable(false);
//				enableNameColumnEditable();
//            }
//            else if(currentQuery == QUERY_TOP_3_GAMES_RANK)
//            {
//				ResultSet res = dataBaseHandler.getTopThree();
//				populateTableView(res,tableView);
//				
//				
//				delBtn.setDisable(false);
//				detailsBtn.setDisable(false);
//				enableNameColumnEditable();
//            }
        }

    }
}
