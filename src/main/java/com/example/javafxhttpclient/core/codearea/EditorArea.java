package com.example.javafxhttpclient.core.codearea;

import javafx.geometry.Insets;
import org.fxmisc.richtext.CodeArea;

import java.io.IOException;
import java.time.Duration;

@SuppressWarnings("unused")
public class EditorArea extends CodeArea {
    private JSONHighlighter highlighter;

    public EditorArea() {
        getStyleClass().add("editor-area");
        setWrapText(true);
        setPadding(new Insets(5));
        multiPlainChanges()
                .successionEnds(Duration.ofMillis(1))
                .subscribe(ignore -> highlight());
    }

    private void highlight() {
        this.setStyleSpans(0, highlighter.computeHighlighting(getText()));
    }

    public void setHighlighter(JSONHighlighter highlighter) {
        this.highlighter = highlighter;

        // Re-computes the highlighting using the new JSONHighlighter
        this.highlight();
    }

    public void addStyleSheet(String css) {
        getStylesheets().add(css);
    }

    public void setText(String text, JSONHighlighter highlighter) {
        clear();
        appendText(text);
        setHighlighter(highlighter);
    }

    public void setText(String text, JSONFormatter formatter, JSONHighlighter highlighter) {
        clear();
        String formattedText = text;

        if (formatter != null) {
            try {
                formattedText = String.valueOf(formatter.format(text));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        appendText(formattedText);
        setHighlighter(highlighter);
    }
}
