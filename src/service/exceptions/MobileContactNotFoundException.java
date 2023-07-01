package service.exceptions;

import model.MobileContact;

public class MobileContactNotFoundException extends Exception{
    private final static long serialVersionUID = 1L;

    public MobileContactNotFoundException(MobileContact mobileContact) {
        super("The mobile contact with t phone number " + mobileContact.getPhoneNumber() + " was not found");
    }


    public MobileContactNotFoundException(String phoneNumber) {
        super("The mobile contact with the phone number " + phoneNumber + " was not found");
    }

    public MobileContactNotFoundException(long id) {
        super("The mobile contact with the id " + id + " was not found");
    }

}
