/*
 * Copyright (C) 2006 The Android Open Source Project
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

package neon.pixel.components.bitmap;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.nio.ByteBuffer;

public final class SerializableBitmap implements Serializable {
    private byte [] bitmap;
    private int width;
    private int height;
    private Bitmap.Config config;

    public SerializableBitmap (byte[] bitmap, int width, int height, Bitmap.Config config) {
        this.bitmap = bitmap;
        this.width = width;
        this.height = height;
        this.config = config;
    }

    public static SerializableBitmap from (Bitmap source) {
        ByteBuffer wrapper = ByteBuffer.allocate (source.getByteCount ());
        source.copyPixelsToBuffer (wrapper);

        return new SerializableBitmap (wrapper.array (), source.getWidth (), source.getHeight (), source.getConfig ());
    }

    public Bitmap getBitmap () {
        ByteBuffer wrapper = ByteBuffer.wrap (bitmap);
        Bitmap bitmap = Bitmap.createBitmap (width, height, config);
        wrapper.rewind ();

        bitmap.copyPixelsFromBuffer (wrapper);

        return bitmap;
    }
}