package com.example.intercrew;

import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class SignUpTest {

    @Rule
    public ActivityTestRule<SignUp> mActivityTestRule = new ActivityTestRule<>(SignUp.class);
    private SignUp mActivity = null;
    private TextView email_id,pw,firstName,lastName;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }


    @Test
    @UiThreadTest
    public void checkInfo() throws Exception {
        assertNotNull(mActivity.findViewById(R.id.firstNameEditText));
        assertNotNull(mActivity.findViewById(R.id.lastNameEditText));


        //firstName test
        firstName = mActivity.findViewById(R.id.firstNameEditText);
        firstName.setText("user1");
        String first = firstName.getText().toString();
        assertNotEquals("user", first);

        //lastName test
        lastName = mActivity.findViewById(R.id.lastNameEditText);
        lastName.setText("user1");
        String last = lastName.getText().toString();
        assertNotEquals("user", last);


    }



    public void tearDown() throws Exception{
        mActivity = null;
    }
    
}
