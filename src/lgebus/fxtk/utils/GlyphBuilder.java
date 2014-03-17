/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lgebus.fxtk.utils;

import java.util.TreeMap;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.util.Builder;
import org.controlsfx.glyphfont.Glyph;
import org.controlsfx.glyphfont.GlyphFontRegistry;

/**
 *
 * @author Laurent
 */
public class GlyphBuilder extends TreeMap<String, Object> implements Builder<Node> {

    private String name;
    private Double size = -1.0;
    private Color color;

    @Override
    public Node build() {
        Glyph res = (Glyph)GlyphFontRegistry.glyph(name);
        if( size > -1.0) {
            res.setSize(size);
        }
        res.setColor( color);
        return res;
    }

    @Override
    public Object put(String key, Object value) {
        if ("name".equalsIgnoreCase(key)) {
            name = (String) value;
        } else if ("size".equalsIgnoreCase(key)) {
            size = Double.parseDouble((String) value);
        }else if ("color".equalsIgnoreCase(key)) {
            color = Color.valueOf((String) value);
        }else {
            throw new IllegalArgumentException("Unknown Glyph property: " + key);
        }
        return null;
    }
}
