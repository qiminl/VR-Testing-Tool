package com.portum.opengl.sdk.androidTest;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.Instrumentation;
import android.graphics.Point;
import android.os.Build;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;
import android.test.TouchUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;

import com.portum.opengl.sdk.sample.OpenGLES20Activity;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class OpenGLES20ActivityTest extends ActivityInstrumentationTestCase2<OpenGLES20Activity> {
	private static final int COUNT = 1000;

	private OpenGLES20Activity mActivity;

	public OpenGLES20ActivityTest() {
		super(OpenGLES20Activity.class);
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
        setActivityInitialTouchMode(true);
		injectInstrumentation(InstrumentationRegistry.getInstrumentation());

		mActivity = getActivity();
	}

	@Test
	public void testStartup() throws Exception {
		Thread.sleep(TimeUnit.SECONDS.toMillis(5));
		assertNotNull(mActivity);
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