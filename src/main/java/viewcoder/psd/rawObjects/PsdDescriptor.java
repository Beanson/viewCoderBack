/*
 * This file is part of java-psd-library.
 * 
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package viewcoder.psd.rawObjects;

import viewcoder.psd.parse.PsdInputStream;
import viewcoder.psd.parse.PsdObjectBase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class PsdDescriptor holds meta data of a layer.
 *
 * @author Dmitry Belsky
 */
public class PsdDescriptor extends PsdObjectBase {

    /**
     * The class id or layer type.
     */
    private final String classId;

    /**
     * a map containing all values of the descriptor
     */
    private HashMap<String, PsdObjectBase> objects = new HashMap<String, PsdObjectBase>();

    /**
     * Instantiates a new psd descriptor.
     *
     * @param stream the stream
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public PsdDescriptor(PsdInputStream stream) throws IOException {
        // Unicode string: name from classID
        int nameLen = stream.readInt() * 2;
        stream.skipBytes(nameLen);

        classId = stream.readPsdString();
        int itemsCount = stream.readInt();
        logger.finest("PsdDescriptor.itemsCount: " + itemsCount);

        for (int i = 0; i < itemsCount; i++) {
            String key = stream.readPsdString();

            logger.finest("PsdDescriptor.key new: " + key);
            objects.put(key, new PsdObjectBase().loadPsdObject(stream));
        }
        logger.finest("PsdDescriptor.objects.size: " + objects.size());
    }

    /**
     * Gets the class id.
     *
     * @return the class id
     */
    public String getClassId() {
        return classId;
    }

    /**
     * Gets the objects.
     *
     * @return the objects
     */
    public Map<String, PsdObjectBase> getObjects() {
        return objects;
    }

    /**
     * Gets the.
     *
     * @param key the key
     * @return the psd object
     */
    public PsdObjectBase get(String key) {
        return objects.get(key);
    }

    /**
     * Contains key.
     *
     * @param key the key
     * @return true, if successful
     */
    public boolean containsKey(String key) {
        return objects.containsKey(key);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Objc:" + objects.toString();
    }

}