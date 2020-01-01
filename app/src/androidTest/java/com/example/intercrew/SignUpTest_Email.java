package com.example.intercrew;

import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.intercrew.Admin.Signin_Admin;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.After;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class SignUpTest_Email {

    @Rule
    public ActivityTestRule<SignUp> mActivityTestRule = new ActivityTestRule<>(SignUp.class);
    private SignUp mActivity = null;
    private TextView email_id,pw;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }


    @Test
    @UiThreadTest
    public void checkEmail() throws Exception {
        assertNotNull(mActivity.findViewById(R.id.emailEditText));
        assertNotNull(mActivity.findViewById(R.id.passEditText));

        //email test
        email_id = mActivity.findViewById(R.id.emailEditText);
        email_id.setText("user1");
        String email = email_id.getText().toString();
        assertNotEquals("user", email);

        //password test
        pw = mActivity.findViewById(R.id.passEditText);
        pw.setText("user1");
        String pass = pw.getText().toString();
        assertNotEquals("user", pass);
    }

}
