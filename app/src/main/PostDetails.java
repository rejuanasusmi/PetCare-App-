package com.example.trysomething;

public class PostDetails {

    private String PetType,PetAge,PetImage,Price,Contact,Email;
    //   private String documentId;

    public PostDetails(){
    }

    public PostDetails(String petType, String petAge,String petImage, String price, String contact,String email) {
        PetType = petType;
        PetAge = petAge;
        Price = price;
        Contact = contact;
        PetImage = petImage;
        Email = email;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPetImage() {
        return PetImage;
    }

    public void setPetImage(String petImage) {
        PetImage = petImage;
    }

    public String getPetType() {
        return PetType;
    }

    public String getPetAge() {
        return PetAge;
    }

    public String getPrice() {
        return Price;
    }

    public String getContact() {
        return Contact;
    }

    public void setPetType(String petType) {
        PetType = petType;
    }

    public void setPetAge(String petAge) {
        PetAge = petAge;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public void setContact(String contact) {
        Contact = contact;
    }
}
