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

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.textfield.CustomTextField;

/**
 * A text field with a button on its right side.
 */
public class ButtonTextField extends CustomTextField {

    public ButtonTextField() {
        getStyleClass().add("button-text-field");
        rightProperty().set(createButton());
    }
    /**
     * Creates the button. It is an empty StackPane with its own style class.
     * @return an empty StackPane
     */
    private Node createButton() {
        final Node button = new StackPane();
        button.getStyleClass().setAll("action-button");
        button.setCursor(Cursor.DEFAULT); // don't show TextField cursor
        return button;
    }
    /**
     * Sets the content of the button. Will usually be an icon, but it could be any node
     * @param icon 
     */
    public void setIcon(Node icon) {
        getButton().getChildren().setAll(icon);
    }
    /**
     * Returns the node displayed in the button
     * @return the node or null if button has no content
     */
    public Node getIcon() {
        return getButton().getChildren().size() == 0 ? null : getButton().getChildren().get(1);
    }
    /**
     * Use this method to attach an EventHandler to the button.
     * @return the button
     */
    public final StackPane getButton() {
        return (StackPane) rightProperty().get();
    }
    
    @Override protected String getUserAgentStylesheet() {
        return ButtonTextField.class.getResource("buttontextfield.css").toExternalForm();
    }
}
