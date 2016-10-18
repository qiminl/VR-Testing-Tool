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
public class OpenGLES20ActivityTest extends ActivityInstrumentationTestCase2<OpenGLES20Activity> {
	private static final int COUNT = 1000;

	private OpenGLES20Activity mActivity;
    private TextView tv;

	public OpenGLES20ActivityTest() {

        super(OpenGLES20Activity.class);
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
        setActivityInitialTouchMode(true);
		injectInstrumentation(InstrumentationRegistry.getInstrumentation());

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


    /**
     * More elegant solution with monkey doesn't work because
     * impossible get android.permission.SET_ACTIVITY_WATCHER
     *      Runtime.getRuntime().exec("monkey -p " + getActivity().getPackageName() + " " + COUNT);
     */
    @Test
	public void testSurviveMonkeyStyleUsage() throws Exception {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getInstrumentation().getUiAutomation().executeShellCommand("monkey -p "
					+ getActivity().getPackageName() + " " + COUNT);
		} else {
			DisplayMetrics displaymetrics = new DisplayMetrics();
			getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

			int height = displaymetrics.heightPixels;
			int width = displaymetrics.widthPixels;

			Random rnd = new Random();

			for (int i = 0; i < COUNT; i++) {
				TouchUtils.drag(this, rnd.nextFloat() * width, rnd.nextFloat() * height,
						rnd.nextFloat() * width, rnd.nextFloat() * height, 1);
			}
		}

        Thread.sleep(TimeUnit.SECONDS.toMillis(5));

        assertNotNull(mActivity);
	}
}