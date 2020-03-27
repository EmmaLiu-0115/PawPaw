package com.example.pawpaw;

import androidx.annotation.Nullable;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity {

    //@Override
    //protected void onCreate(Bundle savedInstanceState) {
    //    super.onCreate(savedInstanceState);
    //    getFragmentManager().beginTransaction().replace(android.R.id.content, new PreferenceFragment()).commit();

    //}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PreferenceFragment()).commit();
        // Not working, trying to check user inputs to shared preferences before they are entered. I still can't get it to work properly.
        /*
        EditTextPreference myPref;
        myPref = (EditTextPreference) getPreferenceScreen().findPreference("edit_text_preference_3");
        myPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                String set = (String) newValue;
                if (validatePhoneNumber(set)){
                    preference.setDefaultValue(set);
                    return true;
                }
                else{
                    Toast.makeText(SettingsActivity.this, "Please input a phone number", Toast.LENGTH_LONG).show();
                    preference.setDefaultValue(prefs.getString(preference.getKey(), ""));
                    return false;
                }


                return true;
            }
        });
         */
    }

    private static boolean validatePhoneNumber(String phoneNo) {
        //validate phone numbers of format "1234567890"
        if (phoneNo.matches("\\d{10}")) return true;
            //validating phone number with -, . or spaces
        else if(phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
            //validating phone number with extension length from 3 to 5
        else if(phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) return true;
            //validating phone number where area code is in braces ()
        else if(phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) return true;
            //return false if nothing matches the input
        else return false;
    }

    public static class PreferenceFragment extends android.preference.PreferenceFragment{
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
