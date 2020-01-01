package com.example.intercrew;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class AddServicesClinicUnitTest {

    private static final String FAKE_STRING = "Adding service successful";

    @Mock
    Context mMockContext;

    @Test
    public void readStringFromContext_LocalizedString() {

        AddServicesClinic myObjectUnderTest = new AddServicesClinic();

        // ...when the string is returned from the object under test...
        String result = myObjectUnderTest.validate("serviceName","role");

        // ...then the result should be the expected one.
        assertThat(result, is(FAKE_STRING));
    }

}


