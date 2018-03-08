package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import application.Person;

import java.io.File;
import java.net.MalformedURLException;
import java.time.LocalDate;

import application.DateUtil;

public class PersonEditDialogController {
	 @FXML
	    private TextField firstNameField;
	    @FXML
	    private TextField lastNameField;
	    @FXML
	    private TextField streetField;
	    @FXML
	    private TextField postalCodeField;
	    @FXML
	    private TextField cityField;
	    @FXML
	    private TextField birthdayField;
	    @FXML
	    private TextField emailField;
	    @FXML
	    private ImageView imageView;
	    @FXML
	    private TextField phoneField;
	    @FXML
	    private TextField urlField;
	    @FXML
	    private DatePicker datepicker;
	    private Stage dialogStage;
	    private Person person;
	    private boolean okClicked = false;
	    
	    
	    @FXML
	    private void initialize() {
	    }
	    
	    public void setDialogStage(Stage dialogStage) {
	        this.dialogStage = dialogStage;
	    }
	    
	    public void setPerson(Person person) {
	        this.person = person;
	        
	        firstNameField.setText(person.getFirstName());
	        lastNameField.setText(person.getLastName());
	        streetField.setText(person.getStreet());
	        postalCodeField.setText(Integer.toString(person.getPostalCode()));
	        cityField.setText(person.getCity());
	     //   birthdayField.setText(DateUtil.format(person.getBirthday()));
	     //   birthdayField.setPromptText("dd.mm.yyyy");
	        //TODO set format
	        datepicker.setValue(person.getBirthday());
	        emailField.setText(person.getEmail());
	        imageView.setImage(new Image(person.getImagePath()));
	        phoneField.setText(person.getPhone());
	        urlField.setText(person.getUrl());
	        
	        
	    }
	    public boolean isOkClicked() {
	        return okClicked;
	    }
	    @FXML
	    private void handleOk() {
	        if (isInputValid()) {
	            person.setFirstName(firstNameField.getText());
	            person.setLastName(lastNameField.getText());
		       if(streetField.getText() == null || streetField.getText().length() == 0) {
		    	  
		       }else {
		            person.setStreet(streetField.getText());
		       }
		       if(postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
		    	   
		       }else {
		            person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
		            }
		       if(cityField.getText() == null || cityField.getText().length() == 0) {
		    	   
		       }else {
	            person.setCity(cityField.getText());
		       		}
	          //  person.setBirthday(DateUtil.parse(birthdayField.getText()));
	            //TODO set format
	            datepicker.setOnAction(event ->{
		        	LocalDate date = datepicker.getValue();
		        	person.setBirthday(date);
		        });
	            LocalDate date = datepicker.getValue();
	        	person.setBirthday(date);
	        	 if(emailField.getText() == null || emailField.getText().length() == 0) {
	        		 
	        	 }else {
	            person.setEmail(emailField.getText());
	        	 }
	        	 if(phoneField.getText() == null || phoneField.getText().length() == 0) {
	        		 
	        	 }else {
	            person.setPhone(DateUtil.parserPhone(phoneField.getText()));
	        	 }
	        	 if(urlField.getText() == null || urlField.getText().length() == 0) {
	        		 
	        	 }else {
	            person.setUrl(urlField.getText());
	        	 }
	            okClicked = true;
	            dialogStage.close();
	        }
	    }
	    @FXML
	    private void handleCancel() {
	        dialogStage.close();
	    }
	    @FXML
	    private void handleChangeImage() throws MalformedURLException {
	    	FileChooser fileChooser = new FileChooser();
	    	FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
	    			"Image Files","*.bmp", "*.png", "*.jpg", "*.gif");
	    	fileChooser.getExtensionFilters().add(extFilter);
	    	File file = fileChooser.showOpenDialog(new Stage());
	    	if(file != null) {
	    		String imageFile = file.toURI().toURL().toString();
	    		Image newImage = new Image(imageFile);
	    		imageView.setImage(newImage);
	    		System.out.println(file.getAbsolutePath());
	    		//person.setImage(newImage);
	    		person.setImagePath(imageFile);
	    		System.out.println(imageFile);
	    	}
	    	
	    	
	    }
		private boolean isInputValid() {
			String errorMessage = "";

	        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
	            errorMessage += "No valid first name!\n"; 
	        }
	        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
	            errorMessage += "No valid last name!\n"; 
	        }
	   

	        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
	          //  errorMessage += "No valid postal code!\n"; 
	        } else {
	            // try to parse the postal code into an int.
	            try {
	                Integer.parseInt(postalCodeField.getText());
	            } catch (NumberFormatException e) {
	                errorMessage += "No valid postal code (must be an integer)!\n"; 
	            }
	        }

	    

	    
	        //TODO set check for vaild email
	        if(emailField.getText()==null || emailField.getText().length()==0) {
	        	//errorMessage += "No vaild email";
	        }else {
	        	if(!DateUtil.isValidEmailAddress(emailField.getText())) {
	        		errorMessage += "No valid Email!\n";
	        	}
	        	//TODO set check for vaild email
	        }
	        if(phoneField.getText()==null || phoneField.getText().length()==0) {
	        	
	        }else if(!DateUtil.isValidPhone(phoneField.getText())) {
	        	errorMessage += "NO valid Phone! Use only 10 digit phone number or in the format (###)###-####\n";
	        }
	        if(urlField.getText()==null|| urlField.getText().length() == 0) {
	        	
	        }else if(!DateUtil.isValidUrl(urlField.getText())) {
	        	errorMessage += "Not valid URL!. Use format http://\"Your URL\" \n";
	        }
	        if (errorMessage.length() == 0) {
	            return true;
	        } else {
	            // Show the error message.
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.initOwner(dialogStage);
	            alert.setTitle("Invalid Fields");
	            alert.setHeaderText("Please correct invalid fields");
	            alert.setContentText(errorMessage);

	            alert.showAndWait();

	            return false;
	        }
	   			
			}
		
		
		
}

