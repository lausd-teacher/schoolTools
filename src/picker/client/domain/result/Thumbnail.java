/*
 * Copyright 2011 floreysoft GmbH (www.floreysoft.net)
 *
 * Written by Sergej Soller (ssoller@q-ric.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package picker.client.domain.result;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Represents a thumbnail of a picked document
 */
public final class Thumbnail extends JavaScriptObject {
  protected Thumbnail() {
  }

  /**
   * @return A URL for the publicly accessible thumbnail.
   */
  public native String getUrl() /*-{
    return this.url;
  }-*/;

  /**
   * @return Thumbnail width
   */
  public native Integer getWidth() /*-{
    return this.width ? @java.lang.Integer::valueOf(I)(this.width) : null;
  }-*/;

  /**
   * @return Thumbnail height
   */
  public native Integer getHeight() /*-{
    return this.height ? @java.lang.Integer::valueOf(I)(this.height) : null;
  }-*/;
}