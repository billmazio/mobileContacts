package dao;

import model.MobileContact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MobileContactDAOImpl implements IMobileContactDAO {
    private static final List<MobileContact> contacts = new ArrayList<>();

    @Override
    public MobileContact insert(MobileContact mobileContact) {
        if (mobileContact == null) return null;
        contacts.add(mobileContact);
        return mobileContact;
    }

    @Override
    public MobileContact update(long id ,MobileContact mobileContact) {
        if (id != mobileContact.getId()) return null;
        int positionToUpdate = getIndexById(id);
       return contacts.set(positionToUpdate,mobileContact);
    }

    @Override
    public MobileContact get(long id) {
        int pos = getIndexById(id);
        if (pos == -1) return null;
        return contacts.get(pos);
    }

    @Override
    public void delete(long id) {
        contacts.removeIf((contact) -> contact.getId() == id);
    }

    @Override
    public List<MobileContact> getAll() {
       // return new ArrayList<>(contacts);
        return Collections.unmodifiableList(contacts);
    }

    @Override
    public MobileContact get(String phoneNumber) {
        int pos = getIndexByPhoneNumber(phoneNumber);
        if (pos == -1) return null;
        return contacts.get(pos);
    }

    @Override
    public void delete(String phoneNumber) {
        contacts.removeIf((contact -> contact.getPhoneNumber().equals(phoneNumber)));
    }

    @Override
    public boolean phoneNumberExists(String phoneNumber) {
        return getIndexByPhoneNumber(phoneNumber) != -1; // if getIndexByPhoneNumber != -1 ,exists phoneNumber
    }

    @Override
    public boolean userIdExists(long id) {
        return getIndexById(id) != -1;
    }

    private int getIndexById(long id) {
        int pos = -1;

        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getId() == id) {
                pos = i;
                break;
            }
        }
        return pos;
    }

    private int getIndexByPhoneNumber(String phoneNumber) {
        int pos = -1;

        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getPhoneNumber().equals(phoneNumber)) {
                pos = i;
                break;
            }
        }
        return pos;
    }

}
