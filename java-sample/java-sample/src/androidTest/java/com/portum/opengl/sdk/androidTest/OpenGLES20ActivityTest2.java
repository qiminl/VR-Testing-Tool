package com.portum.opengl.sdk.androidTest;

import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.portum.opengl.sdk.sample.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class OpenGLES20ActivityTest2 extends ActivityInstrumentationTestCase2<OpenGLES20Activity> {
    private static final int COUNT = 1000;

    private OpenGLES20Activity mActivity;
    private TextView tv;

    public OpenGLES20ActivityTest2() {

        super(OpenGLES20Activity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
//        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
//        Espresso.setFailureHandler(new CustomFailureHandler(instrumentation));

        mActivity = getActivity();
        tv = (TextView) mActivity.findViewById(com.portum.opengl.sdk.sample.R.id.textView3);
    }

    @Test
    public void testStartup() throws Exception {
        Thread.sleep(TimeUnit.SECONDS.toMillis(5));
        assertNotNull(mActivity);
    }

    @Test
    public void testViewState() throws Exception{

        onView(withId(com.portum.opengl.sdk.sample.R.id.editX)).perform(typeText("3"),
                closeSoftKeyboard());

        onView(withId(com.portum.opengl.sdk.sample.R.id.editY)).perform(typeText("3"),
                closeSoftKeyboard());

        onView(withId(com.portum.opengl.sdk.sample.R.id.editZ)).perform(typeText("3"),
                closeSoftKeyboard());

        onView(withId(com.portum.opengl.sdk.sample.R.id.button)).perform(click());

        Thread.sleep(TimeUnit.SECONDS.toMillis(5));


    }
}