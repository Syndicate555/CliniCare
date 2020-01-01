package com.example.intercrew;

import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.intercrew.Admin.ModifyServices;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class ModifyServicesTest {

    @Rule
    public ActivityTestRule<ModifyServices> mActivityTestRule = new ActivityTestRule<>(ModifyServices.class);
    private ModifyServices mActivity = null;
    private TextView service,role;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }


    @Test
    @UiThreadTest
    public void checkInfo() throws Exception {
        assertNotNull(mActivity.findViewById(R.id.NameId));
        assertNotNull(mActivity.findViewById(R.id.RoleId));

        service = mActivity.findViewById(R.id.NameId);
        service.setText("service1");
        String service_name = service.getText().toString();
        assertNotEquals("user", service_name);

        role = mActivity.findViewById(R.id.RoleId);
        role.setText("price1");
        String role_ = role.getText().toString();
        assertNotEquals("role1", role_);

    }

    public  void checkButton(){
        assertNotNull(mActivity.findViewById(R.id.AddServiceButton));
        onView(withId(R.id.AddServiceButton)).perform(click());
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}
