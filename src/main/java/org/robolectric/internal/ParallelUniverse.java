package org.robolectric.internal;

import android.app.Application;
import android.content.res.Resources;
import org.robolectric.AndroidManifest;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.SdkEnvironment;
import org.robolectric.res.ResourceLoader;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowResources;
import org.robolectric.util.DatabaseConfig;

import java.lang.reflect.Method;

import static org.robolectric.Robolectric.shadowOf;

public class ParallelUniverse implements ParallelUniverseInterface {
    public void resetStaticState() {
        Robolectric.reset();
    }

    @Override public void setDatabaseMap(DatabaseConfig.DatabaseMap databaseMap) {
        DatabaseConfig.setDatabaseMap(databaseMap);
    }

    @Override public void setupApplicationState(Method method, TestLifecycle testLifecycle, SdkEnvironment sdkEnvironment, boolean strictI18n, ResourceLoader systemResourceLoader) {
        ShadowResources.setSystemResources(systemResourceLoader);
        String qualifiers = RobolectricTestRunner.determineResourceQualifiers(method);
        shadowOf(Resources.getSystem().getConfiguration()).overrideQualifiers(qualifiers);

        AndroidManifest appManifest = sdkEnvironment.getAppManifest();
        ResourceLoader resourceLoader = null;
        if (appManifest != null) {
            resourceLoader = RobolectricTestRunner.getAppResourceLoader(systemResourceLoader, appManifest);
        }

        final Application application = (Application) testLifecycle.createApplication(method, sdkEnvironment.getAppManifest());
        if (application != null) {
            ShadowApplication.bind(application, appManifest, resourceLoader);
            shadowOf(application.getResources().getConfiguration()).overrideQualifiers(qualifiers);
            shadowOf(application).setStrictI18n(strictI18n);
        }

        Robolectric.application = application;
    }
}
