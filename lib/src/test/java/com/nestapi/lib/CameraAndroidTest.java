/*
 * Copyright 2016, Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nestapi.lib;

import android.os.Build;
import android.os.Parcel;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import junit.framework.Assert;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class CameraAndroidTest {

    public static final String TEST_CAMERA_JSON = "/test-camera.json";
    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testCameraToParcel() {
        try {
            String json = IOUtils.toString(this.getClass().getResourceAsStream(TEST_CAMERA_JSON),
                    "utf-8");
            Camera camera = mapper.readValue(json, Camera.class);

            Parcel parcel = Parcel.obtain();
            camera.writeToParcel(parcel, 0);

            parcel.setDataPosition(0);

            Camera cameraFromParcel = Camera.CREATOR.createFromParcel(parcel);
            assertEquals(camera, cameraFromParcel);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
