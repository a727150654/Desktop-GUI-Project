package application;
import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import application.Main;
public class RootLayoutController {
	private Main main;
	  public void setMainApp(Main main) {
	        this.main = main;
	    }
	  @FXML
	    private void handleNew() {
	        main.getPersonData().clear();
	        main.setPersonFilePath(null);
	    }
	  @FXML
	    private void handleOpen() {
	        FileChooser fileChooser = new FileChooser();

	        // Set extension filter
	        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
	                "XML files (*.xml)", "*.xml");
	        fileChooser.getExtensionFilters().add(extFilter);

	        // Show open file dialog
	        File file = fileChooser.showOpenDialog(main.getPrimaryStage());

	        if (file != null) {
	            main.loadPersonDataFromFile(file);
	        }
	    }
	  @FXML
	    private void handleSave() {
	        File personFile = main.getPersonFilePath();
	        if (personFile != null) {
	            main.savePersonDataToFile(personFile);
	        } else {
	            handleSaveAs();
	        }
	        
	    }
	  @FXML
	    private void handleSaveAs() {
	        FileChooser fileChooser = new FileChooser();

	        // Set extension filter
	        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
	                "XML files (*.xml)", "*.xml");
	        fileChooser.getExtensionFilters().add(extFilter);

	        // Show save file dialog
	        File file = fileChooser.showSaveDialog(main.getPrimaryStage());

	        if (file != null) {
	            // Make sure it has the correct extension
	            if (!file.getPath().endsWith(".xml")) {
	                file = new File(file.getPath() + ".xml");
	            }
	            main.savePersonDataToFile(file);
	        }
	    }
	  @FXML
	    private void handleAbout() {
	        Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("AddressApp");
	        alert.setHeaderText("About");
	        alert.setContentText("Author: Marco Jakob\nWebsite: http://code.makery.ch");

	        alert.showAndWait();
	    }
	  @FXML
	  private void handleShowBirthdayStatistics() {
	    main.showBirthdayStatistics();
	  }
	
	  @FXML
	  private void handleExit() {
	        System.exit(0);
	    }
	  

}
