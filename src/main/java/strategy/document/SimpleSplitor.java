package strategy.document;

import entity.document.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class SimpleSplitor implements DocumentSplitor {
    private static final String REGEX = "(\\.)+|(,)+|( )+|(-)+|(\\?)+|(!)+|(;)+|(:)+|(/d)+|(/n)+";

    @Override
    public List<String> splitDocumentToWords(Document doc) {
        final List<String> res = new ArrayList<>();
        final String[] lines = doc.getContent().split("\n");
        for (final String line : lines) {
            final String[] words = line.split(REGEX);
            res.addAll(Arrays.asList(words));
        }

        return res;
    }
}
