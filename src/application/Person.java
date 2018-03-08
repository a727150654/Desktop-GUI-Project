package application;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
	 private final StringProperty firstName;
	    private final StringProperty lastName;
	    private final StringProperty street;
	    private final IntegerProperty postalCode;
	    private final StringProperty city;
	    private final ObjectProperty<LocalDate> birthday;
	    private final StringProperty email;
	    private final StringProperty imagePath; 
	    private final StringProperty url;
	    private final StringProperty phone;
	  
	    public Person() {
	        this(null, null);
	    }
	    public Person(String firstName, String lastName) {
	        this.firstName = new SimpleStringProperty(firstName);
	        this.lastName = new SimpleStringProperty(lastName);
	        
	        // Some initial dummy data, just for convenient testing.
	        this.street = new SimpleStringProperty("some street");
	        this.postalCode = new SimpleIntegerProperty(1234);
	        this.city = new SimpleStringProperty("some city");
	        this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
	        this.email = new SimpleStringProperty("some email");
	        this.imagePath = new SimpleStringProperty("application/doge.png");
	        this.url = new SimpleStringProperty("http://www.google.com");
	        this.phone = new SimpleStringProperty("(123)456-7890");
	    }
	    public String getUrl() {
	    	return url.get();
	    }
	    public void setUrl(String url) {
	    	this.url.set(url);
	    }
	    public String getPhone() {
	    	return phone.get();
	    }
	    public void setPhone(String phone) {
	    	this.phone.set(phone);
	    }
	    public String getEmail() {
	    	return email.get();
	    }
	    
	    public void setEmail(String email) {
	    	this.email.set(email);
	    }
	    public String getImagePath() {
	    	return imagePath.get();
	    }
	    public void setImagePath(String imagePath) {
	    	this.imagePath.set(imagePath);
	    }
	    public String getFirstName() {
	        return firstName.get();
	    }

	    public void setFirstName(String firstName) {
	        this.firstName.set(firstName);
	    }

	    public StringProperty firstNameProperty() {
	        return firstName;
	    }

	    public String getLastName() {
	        return lastName.get();
	    }

	    public void setLastName(String lastName) {
	        this.lastName.set(lastName);
	    }

	    public StringProperty lastNameProperty() {
	        return lastName;
	    }

	    public String getStreet() {
	        return street.get();
	    }

	    public void setStreet(String street) {
	        this.street.set(street);
	    }

	    public StringProperty streetProperty() {
	        return street;
	    }

	    public int getPostalCode() {
	        return postalCode.get();
	    }

	    public void setPostalCode(int postalCode) {
	        this.postalCode.set(postalCode);
	    }

	    public IntegerProperty postalCodeProperty() {
	        return postalCode;
	    }

	    public String getCity() {
	        return city.get();
	    }

	    public void setCity(String city) {
	        this.city.set(city);
	    }

	    public StringProperty cityProperty() {
	        return city;
	    }
	    	
	    @XmlJavaTypeAdapter(LocalDateAdapter.class)
	    public LocalDate getBirthday() {
	        return birthday.get();
	    }

	    public void setBirthday(LocalDate birthday) {
	        this.birthday.set(birthday);
	    }

	    public ObjectProperty<LocalDate> birthdayProperty() {
	        return birthday;
	    }
	/*    public Image getImage() {
	    	return image;
	    }
	    
	    public void setImage(Image image) {
	    	this.image = image;
	    } */
	    

}
