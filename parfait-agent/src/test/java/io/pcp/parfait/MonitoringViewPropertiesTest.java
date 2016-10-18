package io.pcp.parfait;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MonitoringViewPropertiesTest {

    private String originalNameValue;
    private String originalClusterValue;
    private String originalIntervalValue;
    private String originalStartupValue;

    @Before
    public void setup() {
        // setProperty returns the old value of that property.
        originalNameValue = System.setProperty(MonitoringViewProperties.PARFAIT_NAME, "true");
        originalClusterValue = System.setProperty(MonitoringViewProperties.PARFAIT_CLUSTER, "true");
        originalIntervalValue = System.setProperty(MonitoringViewProperties.PARFAIT_INTERVAL, "true");
        originalStartupValue = System.setProperty(MonitoringViewProperties.PARFAIT_STARTUP, "true");
    }

    private void reset(String name, String value) {
        if (value == null) {
            System.clearProperty(name);
        } else {
            System.setProperty(name, value);
        }
    }

    @After
    public void teardown() {
        reset(MonitoringViewProperties.PARFAIT_NAME, originalNameValue);
        reset(MonitoringViewProperties.PARFAIT_CLUSTER, originalClusterValue);
        reset(MonitoringViewProperties.PARFAIT_INTERVAL, originalIntervalValue);
        reset(MonitoringViewProperties.PARFAIT_STARTUP, originalStartupValue);
    }

    @Test
    public void checkValidClusterSetting() {
        System.setProperty(MonitoringViewProperties.PARFAIT_CLUSTER, "123");
        assertEquals("123", MonitoringViewProperties.getDefaultCluster("anyname"));
    }

    @Test
    public void checkDefaultClusterSetting() {
        System.clearProperty(MonitoringViewProperties.PARFAIT_CLUSTER);
        assertNotEquals("123", MonitoringViewProperties.getDefaultCluster("somename"));
    }

    @Test
    public void checkValidIntervalSetting() {
        System.clearProperty(MonitoringViewProperties.PARFAIT_INTERVAL);
        String interval = MonitoringViewProperties.getDefaultInterval();
        System.setProperty(MonitoringViewProperties.PARFAIT_INTERVAL, "bad-do-not-modify");
        assertEquals(interval, MonitoringViewProperties.getDefaultInterval());
    }

    @Test
    public void checkInvalidIntervalSetting() {
        System.setProperty(MonitoringViewProperties.PARFAIT_INTERVAL, "13000");
        assertEquals("13000", MonitoringViewProperties.getDefaultInterval());
        System.clearProperty(MonitoringViewProperties.PARFAIT_INTERVAL);
    }

    @Test
    public void checkValidStartupSetting() {
        System.clearProperty(MonitoringViewProperties.PARFAIT_STARTUP);
        String startup = MonitoringViewProperties.getDefaultStartup();
        System.setProperty(MonitoringViewProperties.PARFAIT_STARTUP, "bad-do-not-modify");
        assertEquals(startup, MonitoringViewProperties.getDefaultStartup());
    }

    @Test
    public void checkInvalidStartupSetting() {
        System.setProperty(MonitoringViewProperties.PARFAIT_STARTUP, "15000");
        assertEquals("15000", MonitoringViewProperties.getDefaultStartup());
        System.clearProperty(MonitoringViewProperties.PARFAIT_STARTUP);
    }
}
