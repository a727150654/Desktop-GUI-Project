package application;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import application.Main;
import application.Person;
import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.parameter.AddressType;
import ezvcard.property.Address;
import ezvcard.property.Birthday;
import ezvcard.property.StructuredName;
import ezvcard.property.Telephone;

public class PersonOverviewController {
		@FXML
	    private TableView<Person> personTable;
	    @FXML
	    private TableColumn<Person, String> firstNameColumn;
	    @FXML
	    private TableColumn<Person, String> lastNameColumn;
	    @FXML
	    private Text firstNameLabel;
	    @FXML
	    private Text lastNameLabel;
	    @FXML
	    private Text streetLabel;
	    @FXML
	    private Text postalCodeLabel;
	    @FXML
	    private Text cityLabel;
	    @FXML
	    private Text birthdayLabel;
	    @FXML
	    private Text emailLabel;
	    @FXML
	    private ImageView imageLabel;
	    @FXML
	    private Text phoneLabel;
	    @FXML
	    private Text urlLabel;
	    @FXML 
	    private Text dropHereLabel;
	    @FXML
	    private Hyperlink urlLink;
	    // Reference to the main application.
	    private Main main;
	    public PersonOverviewController() {
	    }
	    
	    @FXML
	    private void initialize() {
	    	firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
	    	lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
	        showPersonDetails(null);
	        personTable.getSelectionModel().selectedItemProperty().addListener(
	                (observable, oldValue, newValue) -> showPersonDetails(newValue));
	       
	    }
	    
	    @FXML
	    private void handleDragOver(DragEvent event) {
	    	if(event.getDragboard().hasFiles()) {
	    		event.acceptTransferModes(TransferMode.ANY);
	    	}
	    }
	    
	    @FXML
	    private void handleDrop(DragEvent event) throws Exception {
	    	List<File> file =event.getDragboard().getFiles();
	    	loadVcardFromFile(file.get(0));
	    	
	    
	    }
	  
	    public void setMainApp(Main main) {
	    	this.main = main;
	    	personTable.setItems(main.getPersonData());
	    }
	   
	    private void showPersonDetails(Person person) {
	        if (person != null) {
	            // Fill the labels with info from the person object.
	            firstNameLabel.setText(person.getFirstName());
	            lastNameLabel.setText(person.getLastName());
	            streetLabel.setText(person.getStreet());
	            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
	            cityLabel.setText(person.getCity());
	            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
	            //TODO email has to be formated
	            emailLabel.setText(person.getEmail());
	            imageLabel.setImage(new Image(person.getImagePath()));
	            phoneLabel.setText(person.getPhone());
	          //  urlLabel.setText(person.getUrl());
	          
	            urlLink.setText(person.getUrl());
	            urlLink.setOnAction(new EventHandler<ActionEvent>() {
		  	          @Override public void handle(ActionEvent e) {
		  	              Desktop d = Desktop.getDesktop();
		  	              try {
							d.browse(new URI(person.getUrl()));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (URISyntaxException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		  	          }
		  	      });
	            // TODO: We need a way to convert the birthday into a String! 
	            // birthdayLabel.setText(...);
	        } else {
	            // Person is null, remove all the text.
	            firstNameLabel.setText("");
	            lastNameLabel.setText("");
	            streetLabel.setText("");
	            postalCodeLabel.setText("");
	            cityLabel.setText("");
	            birthdayLabel.setText("");
	            emailLabel.setText("");
	            imageLabel.setImage(new Image("application/Icon.png"));
	            phoneLabel.setText("");
	            urlLink.setText("");

	        }
	    }
	    
	    final WebView browser = new WebView();
	    final WebEngine webEngine = browser.getEngine();
	  
	    @FXML
	    private void handleDeletePerson() {
	        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
	        if (selectedIndex >= 0) {
	        	personTable.getItems().remove(selectedIndex);
	        }else {
	        	 // Nothing selected.
	            Alert alert = new Alert(AlertType.WARNING);
	            alert.initOwner(main.getPrimaryStage());
	            alert.setTitle("No Selection");
	            alert.setHeaderText("No Person Selected");
	            alert.setContentText("Please select a person in the table.");
	            alert.showAndWait();
	        }
	        
	    }
	    
	    @FXML
	    private void handleNewPerson() {
	        Person tempPerson = new Person();
	        boolean okClicked = main.showPersonEditDialog(tempPerson);
	        if (okClicked) {
	            main.getPersonData().add(tempPerson);
	        }
	    }
	   
	    @FXML
	    private void handleEditPerson() {
	        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
	        if (selectedPerson != null) {
	            boolean okClicked = main.showPersonEditDialog(selectedPerson);
	            if (okClicked) {
	                showPersonDetails(selectedPerson);
	            }

	        } else {
	            // Nothing selected.
	            Alert alert = new Alert(AlertType.WARNING);
	            alert.initOwner(main.getPrimaryStage());
	            alert.setTitle("No Selection");
	            alert.setHeaderText("No Person Selected");
	            alert.setContentText("Please select a person in the table.");

	            alert.showAndWait();
	        }
	    }
	    @SuppressWarnings("deprecation")
		public void loadVcardFromFile(File file) throws Exception {
			
			try {
				
				VCard vcard = Ezvcard.parse(file).first();
				Person tempPerson = new Person();
			//	LocalDateAdapter localDateAdapter = new LocalDateAdapter();
				//tempPerson.setBirthday(localDateAdapter.unmarshal(vcard.getBirthday().getDate()));
				//LocalDate localdate = new LocalDate();
			//	System.out.println(vcard.getBirthday().getDate());
				tempPerson.setBirthday(LocalDate.of(vcard.getBirthday().getDate().getYear()+1900
						,vcard.getBirthday().getDate().getMonth()+1
						,vcard.getBirthday().getDate().getDate() ));
				//System.out.println(vcard.getAddresses().get(0).getRegion());
				tempPerson.setCity(vcard.getAddresses().get(0).getRegion());
				tempPerson.setStreet(vcard.getAddresses().get(0).getStreetAddress());
				tempPerson.setPostalCode(Integer.parseInt(vcard.getAddresses().get(0).getPostalCode()));
				tempPerson.setFirstName(vcard.getStructuredName().getGiven());
				tempPerson.setLastName(vcard.getStructuredName().getFamily());
				tempPerson.setPhone(vcard.getTelephoneNumbers().get(0).getText());
				tempPerson.setUrl(vcard.getUrls().get(0).getValue());
				
				main.getPersonData().add(tempPerson);
				
			} catch (IOException e) {
				 Alert alert = new Alert(AlertType.WARNING);
		            alert.initOwner(main.getPrimaryStage());
		            alert.setTitle("No Selection");
		            alert.setHeaderText("No Person Selected");
		            alert.setContentText("Please select a person in the table.");
			}
			
			
		}
	    @FXML
		public void handlesaveVcard() throws IOException {
	    	 Person person = personTable.getSelectionModel().getSelectedItem();
	    	 if(person != null) {
				VCard vcard = new VCard();
				StructuredName n = new StructuredName();
				n.setFamily(person.getLastName());
				n.setGiven(person.getFirstName());
				Calendar c = Calendar.getInstance();
				c.clear();
				c.set(Calendar.YEAR, person.getBirthday().getYear());
				c.set(Calendar.MONTH, person.getBirthday().getMonthValue());
				c.set(Calendar.DATE,person.getBirthday().getDayOfMonth());
				Birthday birthday = new Birthday(c.getTime());
				Address adr = new Address();
				Telephone telephoneNumber = new Telephone(person.getPhone());
				adr.setPostalCode(String.valueOf(person.getPostalCode()));
				adr.setStreetAddress(person.getStreet());
				adr.setRegion(person.getCity());
				adr.getTypes().add(AddressType.HOME);
				adr.setLabel(person.getStreet() +" "+ person.getCity()+" " + String.valueOf(person.getPostalCode()));
				vcard.addAddress(adr);
				vcard.setStructuredName(n);
				vcard.addEmail(person.getEmail());
				vcard.setBirthday(birthday);
				vcard.addTelephoneNumber(telephoneNumber);
				vcard.addUrl(person.getUrl());
				vcard.setFormattedName(person.getFirstName()+ " "+ person.getLastName());
				 FileChooser fileChooser = new FileChooser();
				 FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
			                "vCalendar File (*.vcf)", "*.vcf");
			        fileChooser.getExtensionFilters().add(extFilter);
			    File file = fileChooser.showSaveDialog(main.getPrimaryStage());
				Ezvcard.write(vcard).go(file);
	    	 }else {
	    		 Alert alert = new Alert(AlertType.WARNING);
		            alert.initOwner(main.getPrimaryStage());
		            alert.setTitle("No Selection");
		            alert.setHeaderText("No Person Selected");
		            alert.setContentText("Please select a person in the table.");
		            alert.showAndWait(); 
	    	 }
		}
	    
	   
}
