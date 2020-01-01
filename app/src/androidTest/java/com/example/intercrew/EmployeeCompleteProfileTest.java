package com.example.intercrew;

import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;

import com.example.intercrew.Admin.ModifyServices;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class EmployeeCompleteProfileTest {
    @Rule
    public ActivityTestRule<EmployeeCompleteProfile> mActivityTestRule = new ActivityTestRule<>(EmployeeCompleteProfile.class);
    private EmployeeCompleteProfile mActivity = null;
    private TextView address,number,company,description,insurance,payment;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }

    @Test
    @UiThreadTest
    public void onCreate() {
        assertNotNull(mActivity.findViewById(R.id.address));
        assertNotNull(mActivity.findViewById(R.id.number));
        assertNotNull(mActivity.findViewById(R.id.company));
        assertNotNull(mActivity.findViewById(R.id.description));
        assertNotNull(mActivity.findViewById(R.id.insurance));
        assertNotNull(mActivity.findViewById(R.id.payment));

        address = mActivity.findViewById(R.id.address);
        address.setText("Address St.");
        String address_name = address.getText().toString();
        assertNotEquals("user", address_name);

        number = mActivity.findViewById(R.id.number);
        number.setText("324512");
        String number_ = number.getText().toString();
        assertNotEquals("number1", number_);

        company = mActivity.findViewById(R.id.company);
        company.setText("New Company");
        String company_ = company.getText().toString();
        assertNotEquals("company1", company_);

        description = mActivity.findViewById(R.id.description);
        description.setText("Describing stuff");
        String description_ = description.getText().toString();
        assertNotEquals("description1", description_);

        insurance = mActivity.findViewById(R.id.insurance);
        insurance.setText("AS23F2EF");
        String insurance_ = insurance.getText().toString();
        assertNotEquals("insurance1", insurance_);

        payment = mActivity.findViewById(R.id.payment);
        payment.setText("1,234");
        String payment_ = payment.getText().toString();
        assertNotEquals("payment1", payment_);



        }


    @Test
    public void completeProfile() {
        assertNotNull(mActivity.findViewById(R.id.radioYes));
        onView(withId(R.id.radioYes)).perform(click());

        assertNotNull(mActivity.findViewById(R.id.completeProfile));
        onView(withId(R.id.completeProfile)).perform(click());
    }
}