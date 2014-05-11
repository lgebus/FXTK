/* The MIT License (MIT)
 *
 * Copyright (c) 2014 Laurent Gebus
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
 package lgebus.fxtk.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.util.Builder;
import javafx.util.BuilderFactory;

/**
 * A BuilderFactory that can be extended with custom Builders.
 * Must be passed as parameter to FXMLLoader.
 */
public class CustomBuilderFactory implements BuilderFactory {
    /**
     * Adds a ne Builder to the factory. Assumes the new Builder has a default constructor
     * @param type
     * @param builder 
     */
  public void addBuilder( Class type, Class<? extends Builder> builder) {
    builders.put(type, builder);
  }
  /**
   * First checks in the default factory if a builder is available; if not, searches it in its local map and instantiate it.
   * @param type the type for which a Builder is searched
   * @return new instance of the Builder or null if none is found
   */
  @Override
  public Builder<?> getBuilder(Class<?> type) {
    Class<? extends Builder> builder = builders.get(type);
    try {
      return  ( builder == null) ? defaultBuilderFactory.getBuilder(type) : builder.newInstance();
    } catch (InstantiationException | IllegalAccessException ex) {
      Logger.getLogger(CustomBuilderFactory.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }
  private final Map<Class,Class<? extends Builder>> builders = new HashMap<>();
  private final JavaFXBuilderFactory defaultBuilderFactory = new JavaFXBuilderFactory();

}
