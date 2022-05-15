package com.example.javafxhttpclient.core.codearea;

import org.fxmisc.richtext.model.StyleSpans;

import java.util.Collection;

/**
 * Highlights strings in various data formats.
 */
public interface Highlighter {
    /**
     * Returns the StyleSpans needed for highlighting the given text which
     * can then be used by EverestCodeArea.
     *
     * @param text The string to highlight
     */
    StyleSpans<Collection<String>> computeHighlighting(String text);
}
