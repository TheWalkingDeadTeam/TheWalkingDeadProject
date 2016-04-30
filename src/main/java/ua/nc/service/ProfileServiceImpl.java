package ua.nc.service;


import ua.nc.entity.profile.FieldValue;
import ua.nc.entity.profile.Profile;
import ua.nc.entity.profile.ProfileField;
import ua.nc.entity.profile.ProfileFieldValue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel on 28.04.2016.
 */
public class ProfileServiceImpl implements ProfileService {
    @Override
    public Profile getProfile(UserDetailsImpl userDetails, int cesID) {

        return null;
    }

    public List<FieldValue> parseProfile(UserDetailsImpl userDetails, int cesID, Profile profile) {
        List<FieldValue> result = new ArrayList<>();
        for (ProfileField profileField : profile.getFields()) {
            switch (profileField.getFieldType()) {
                case "number":
                    result.add(new FieldValue(profileField.getID(), userDetails.getID(), null,
                            Double.parseDouble(profileField.getValues().get(0).getValue()), null, null));
                    break;
                case "text":
                case "tel":
                case "textarea":
                    result.add(new FieldValue(profileField.getID(), userDetails.getID(),
                            profileField.getValues().get(0).getValue(), null, null, null));
                    break;
                case "date":
                    DateFormat format = new SimpleDateFormat();
                    try {
                        result.add(new FieldValue(profileField.getID(), userDetails.getID(), null,
                                null, format.parse(profileField.getValues().get(0).getValue()), null));
                    } catch (ParseException e) {
                        e.printStackTrace(); // say smth about wrong date
                    }
                    break;
                case "select":
                case "checkbox":
                case "radio":
                    if (!profileField.isMultipleChoice()){
                        result.add(new FieldValue(profileField.getID(), userDetails.getID(), null,
                                null, null, Integer.parseInt(profileField.getValues().get(0).getID())));
                    } else {
                        for (ProfileFieldValue pfValue : profileField.getValues()){
                            result.add(new FieldValue(profileField.getID(), userDetails.getID(), null,
                                    null, null, Integer.parseInt(pfValue.getID())));
                        }
                    }
                    break;
            }
        }
        return result;
    }

    private boolean isApplied(UserDetailsImpl userDetails, int cesID){
        // List<Application> resultSet = applicationDAO.read(userDetails, cesID)
        // if (resultSet == null || resultSet.size() == 0){
        //      return false;
        // } else {
        //      return true;
        // }
        return false;

    }

    private void createProfile(List<FieldValue> fieldValues) {
        for (FieldValue fieldValue : fieldValues) {
        // dao.create
        }
    }

    private void updateProfile(List<FieldValue> fieldValues) {
        for (FieldValue fieldValue : fieldValues) {
        // dao.update
        }
    }

    @Override
    public void setProfile(UserDetailsImpl userDetails, int cesID, Profile profile){
        List<FieldValue> fieldValues = new ArrayList<>();
        fieldValues = parseProfile(userDetails, cesID, profile);
        if (isApplied(userDetails, cesID)){
            updateProfile(fieldValues);
        } else {
            createProfile(fieldValues);
        }
    }
}
