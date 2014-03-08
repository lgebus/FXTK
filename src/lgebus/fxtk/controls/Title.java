/*
 * 
 * 
 */
package lgebus.fxtk.controls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.beans.DefaultProperty;
import javafx.beans.property.StringProperty;
import javafx.css.CssMetaData;
import javafx.css.ParsedValue;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.StyleConverter;
import javafx.css.Styleable;
import javafx.css.StyleableProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import static javafx.scene.layout.StackPane.getAlignment;
import javafx.scene.text.Font;

/**
 *
 * @author Laurent
 */
@DefaultProperty("label")
public class Title extends StackPane {

    private Label text = new Label();
    private final HBox line = new HBox();

    /**
     * Styleable properties
     */
    private SimpleStyleableObjectProperty<Pos> textPos = new SimpleStyleableObjectProperty(TEXT_POS_META_DATA) {
        @Override
        protected void invalidated() {
            setAlignment(getLabel(), (Pos) get());
        }
    };
    private SimpleStyleableObjectProperty<Pos> linePos = new SimpleStyleableObjectProperty(LINE_POS_META_DATA) {
        @Override
        protected void invalidated() {
            setAlignment(line, (Pos) get());
            fixBackground();
        }
    };
    /**
     * 
     */
    public Title() {
        getStyleClass().add("title");
        line.getStyleClass().add("line");
        line.setMaxHeight(USE_PREF_SIZE);
        getChildren().addAll(line, text);
        setTextPos(Pos.CENTER);
        setLinePos(Pos.CENTER);
    }
    /**
     * 
     * @param newText 
     */
    public void setLabel(Label newText) {
        getChildren().remove(getLabel());
        Pos oldPos = getTextPos();
        text = newText;
        getChildren().add(newText);
        setTextPos(oldPos);
        fixBackground();
    }
    /**
     * Label's background must be transparent when line is not in the middle
     */
    private void fixBackground() {
        if (!(getAlignment(line) == Pos.CENTER || getAlignment(line) == Pos.CENTER_RIGHT || getAlignment(line) == Pos.CENTER_LEFT)) {
            getLabel().setStyle("-fx-background-color: transparent;");
        }
    }
    /**
     * for additional customizations of the label.
     * @return 
     */
    public Label getLabel() {
        return text;
    }

    public final void setLinePos(Pos pos) {
        linePos.set(pos);
    }

    public final Pos getLinePos() {
        return line.getAlignment();
    }

    public final void setTextPos(Pos pos) {
        textPos.set(pos);
    }

    public final Pos getTextPos() {
        return getLabel().getAlignment();
    }

    public void setText(String label) {
        textProperty().set(label);
    }

    public String getText() {
        return textProperty().get();
    }

    public StringProperty textProperty() {
        return getLabel().textProperty();
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return STYLEABLES; 
    }

    private static final CssMetaData<Title, Pos> TEXT_POS_META_DATA = new CssMetaData("-text-pos", PosConverter.getInstance()) {
        @Override
        public boolean isSettable(Styleable styleable) {
            return true;
        }

        @Override
        public StyleableProperty getStyleableProperty(Styleable styleable) {
            return ((Title) styleable).textPos;
        }
    };
    private static final CssMetaData<Title, Pos> LINE_POS_META_DATA = new CssMetaData("-line-pos", PosConverter.getInstance()) {
        @Override
        public boolean isSettable(Styleable styleable) {
            return true;
        }

        @Override
        public StyleableProperty getStyleableProperty(Styleable styleable) {
            return ((Title) styleable).linePos;
        }
    };

    private static class PosConverter extends StyleConverter<String, Pos> {

        private static class Holder {
            static PosConverter INSTANCE = new PosConverter();
        }

        public static StyleConverter<String, Pos> getInstance() {
            return Holder.INSTANCE;
        }

        @Override
        public Pos convert(ParsedValue<String, Pos> value, Font font) {
            return Pos.valueOf(value.getValue());
        }
    }

    private final static List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

    static {
        final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(StackPane.getClassCssMetaData());
        styleables.addAll(getClassCssMetaData());
        styleables.add(LINE_POS_META_DATA);
        styleables.add(TEXT_POS_META_DATA);
        STYLEABLES = Collections.unmodifiableList(styleables);
    }
}
