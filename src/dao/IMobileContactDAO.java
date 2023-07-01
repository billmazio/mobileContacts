package dao;

import model.MobileContact;

import java.util.List;

public interface IMobileContactDAO {

    MobileContact insert(MobileContact mobileContact);
    MobileContact update(long id, MobileContact mobileContact);
    MobileContact get(long id);
    void delete(long id);
    List<MobileContact> getAll();

    MobileContact get(String phoneNumber);
    void delete(String phoneNumber);
    boolean phoneNumberExists(String phoneNumber);
    boolean userIdExists(long id);
}
