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
package lgebus.fxtk.controls;

import java.io.File;
import java.net.URI;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.StringConverter;
import org.controlsfx.glyphfont.GlyphFontRegistry;

/**
 * A file or folder selector control.
 * Requires controlsFX library.
 */
public class FileSelector extends ButtonTextField {

    private DirectoryChooser dirChooser;
    private FileChooser fileChooser;
    private Window parent;
    private final ObjectProperty<URI> uriProperty = new SimpleObjectProperty();
    /**
     * Default constructor. The button displays a folder icon from Font Awesome.
     */
    public FileSelector() {
        this( GlyphFontRegistry.glyph("FontAwesome|FOLDER_OPEN_ALT"));
    }
    /**
     * Builds the FileSelector.
     * @param icon content of the button.
     */
    public FileSelector( Node icon) {
        setIcon( icon);
        getButton().setOnMouseClicked((e) -> showChooser());
        textProperty().bindBidirectional(uriProperty, new StringConverter<URI>() {

            @Override
            public String toString(URI object) {
                return object == null ? "" : new File(object).getPath();
            }

            @Override
            public URI fromString(String string) {
                if (string == null || "".equals(string)) {
                    return null;
                }
                return new File(string).toURI();
            }
        });
    }

    public ObjectProperty<URI> URIProperty() {
        return uriProperty;
    }

    public URI getURI() {
        return URIProperty().getValue();
    }

    public void setURI(URI uri) {
        uriProperty.setValue(uri);
    }
    /**
     * Set the popup that will be displayed when user presses the button.
     * @param chooser a DirectoryChooser
     * @param parent can be null
     * @return this FileSelector
     */
    public FileSelector setChooser(DirectoryChooser chooser, Window parent) {
        this.dirChooser = chooser;
        this.fileChooser = null;
        this.parent = parent;
        return this;
    }
    /**
     * Set the popup that will be displayed when user presses the button.
     * @param chooser a FileChooser
     * @param parent can be null
     * @return this FileSelector
     */
    public FileSelector setChooser(FileChooser chooser, Window parent) {
        this.fileChooser = chooser;
        this.dirChooser = null;
        this.parent = parent;
        return this;
    }
    /**
     * The chooser is shown as a popup dialog and when user has selected a file or folder, the URI property gets updated.
     * Sets the initial folder accordingly to the current URI value.
     * Throws an IllegalStateException when the chooser has not been previously defined.
     */
    private void showChooser() {
        if( dirChooser == null && fileChooser == null) {
            throw new IllegalStateException("FileSelector: setChooser() must be invoked before.");
        }
        if (dirChooser != null && getURI() != null) {
            dirChooser.setInitialDirectory(new File(getURI()));
        }
        if( fileChooser != null && getURI() != null) {
            fileChooser.setInitialDirectory( new File(getURI()).getParentFile());
        }
        File selected = (dirChooser != null) ? dirChooser.showDialog(parent) : fileChooser.showOpenDialog(parent);
        if (selected != null) {
            URI uri = selected.toURI();
            setURI(uri);
        }
    }
}
