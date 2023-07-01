package service;

import dao.IMobileContactDAO;
import dto.MobileContactDTO;
import dto.UserDetailsDTO;
import model.MobileContact;
import model.UserDetails;
import service.exceptions.MobileContactNotFoundException;
import service.exceptions.PhoneNumberAlreadyExistsException;
import service.exceptions.UserIdAlreadyExistsException;

import java.util.List;

public class MobileContactServiceImpl implements IMobileContactService{
    private final IMobileContactDAO dao;

    public MobileContactServiceImpl(IMobileContactDAO dao) {
        this.dao = dao;
    }

    @Override
    public MobileContact insertMobileContact(MobileContactDTO contactDTO) throws PhoneNumberAlreadyExistsException, UserIdAlreadyExistsException {
        MobileContact mobileContact = null;
        try{
            mobileContact = new MobileContact();
            if (dao.phoneNumberExists(contactDTO.getPhoneNumber())) {
                throw new PhoneNumberAlreadyExistsException(mobileContact);
            }
            if (dao.userIdExists(contactDTO.getId())) {
                throw new UserIdAlreadyExistsException(mobileContact);
            }
           mapMobileContact(mobileContact,contactDTO);

            mobileContact = dao.insert(mobileContact);
        }catch (PhoneNumberAlreadyExistsException | UserIdAlreadyExistsException e) {
            System.err.println("error");
            throw e;
        }
        return mobileContact;
    }

    @Override
    public MobileContact updateMobileContact(long id, MobileContactDTO contactDTO) throws MobileContactNotFoundException, PhoneNumberAlreadyExistsException {
        MobileContact mobileContact;

        try{
            mobileContact = new MobileContact();
            mapMobileContact(mobileContact,contactDTO);

            if (id != contactDTO.getId() || !dao.userIdExists(id)) {
                throw new MobileContactNotFoundException(id);
            }

            if (dao.phoneNumberExists(contactDTO.getPhoneNumber())) {
                if (!dao.get(contactDTO.getId()).equals(dao.get(contactDTO.getPhoneNumber())))
                throw new PhoneNumberAlreadyExistsException(mobileContact);
            }

            mobileContact = dao.update(id,mobileContact);
        }catch (PhoneNumberAlreadyExistsException | MobileContactNotFoundException e) {
            System.err.println("error");;
            throw e;
        }
        return mobileContact;
    }
//    @Override
//    public MobileContact updateMobileContact(long id, MobileContactDTO contactDTO)
//            throws MobileContactNotFoundException, PhoneNumberAlreadyExistsException, UserIdAlreadyExistsException {
//        MobileContact mobileContact;
//        try {
//            mobileContact = new MobileContact();
//
//            if (id != contactDTO.getId() || !dao.userIdExists(id)) {
//                throw new MobileContactNotFoundException(id);
//            }
//
//            if (dao.phoneNumberExists(contactDTO.getPhoneNumber())) {
//
//                throw new PhoneNumberAlreadyExistsException(mobileContact);
//            }
//
//            if (dao.userIdExists(contactDTO.getId()) && contactDTO.getId() != id) {
//                throw new UserIdAlreadyExistsException(mobileContact);
//            }
//
//            mapMobileContact(mobileContact, contactDTO);
//
//            mobileContact = dao.update(id, mobileContact);
//        } catch (PhoneNumberAlreadyExistsException | UserIdAlreadyExistsException e) {
//            e.printStackTrace();
//            throw e;
//        }
//        return mobileContact;
//    }


    @Override
    public void deleteMobileContact(String phoneNumber) throws MobileContactNotFoundException {
        MobileContact mobileContact;
        try{
            mobileContact = new MobileContact();
            if (!dao.phoneNumberExists(phoneNumber)) {
                throw new MobileContactNotFoundException(mobileContact);
            }
            dao.delete(phoneNumber);
        }catch (MobileContactNotFoundException e) {
            System.err.println("error");;
            throw e;
        }
    }

    @Override
    public void deleteMobileContact(long id) throws MobileContactNotFoundException {
        MobileContact mobileContact;
        try{
            mobileContact = new MobileContact();
            if (!dao.userIdExists(id)) {
                throw new MobileContactNotFoundException(mobileContact);
            }
            dao.delete(id);
        }catch (MobileContactNotFoundException e) {
            System.err.println("error");;
            throw e;
        }
    }

    @Override
    public MobileContact getMobileContact(String phoneNumber) throws MobileContactNotFoundException {
        MobileContact mobileContact;
        try{
            mobileContact = dao.get(phoneNumber);
            if (mobileContact == null) {
                throw new MobileContactNotFoundException(phoneNumber);
            }
           return mobileContact;
        }catch (MobileContactNotFoundException e) {
            System.err.println("error");;
            throw e;
        }

    }

    @Override
    public MobileContact getMobileContact(long id) throws MobileContactNotFoundException {
        MobileContact mobileContact;
        try{
            mobileContact = dao.get(id);
            if (mobileContact == null) {
                throw new MobileContactNotFoundException(id);
            }
            return mobileContact;
        }catch (MobileContactNotFoundException e) {
            System.err.println("error");
            throw e;
        }
    }

    @Override
    public List<MobileContact> getAllMobileContacts() {
        return dao.getAll();
    }


    private void mapMobileContact(MobileContact mobileContact, MobileContactDTO mobileContactDTO) {
        mobileContact.setId(mobileContactDTO.getId());
        mobileContact.setPhoneNumber(mobileContactDTO.getPhoneNumber());
        UserDetails userDetails = new UserDetails();
        mapUserDetails(userDetails, mobileContactDTO.getUserDetails());
        mobileContact.setUserDetails(userDetails);
    }

    private void mapUserDetails(UserDetails userDetails, UserDetailsDTO userDetailsDTO) {
        userDetails.setId((userDetailsDTO.getId()));
        userDetails.setFirstname(userDetailsDTO.getFirstname());
        userDetails.setLastname(userDetailsDTO.getLastname());
    }
}
