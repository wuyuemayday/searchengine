package controller;

import entity.document.Document;
import entity.document.DocumentData;
import entity.task.TaskRequest;
import entity.task.TaskResponse;
import repository.DocumentsRepo;
import strategy.document.ContentSplitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SearchWorkerControllerImpl implements SearchWorkerController {
    private final DocumentsRepo repository;
    private final ContentSplitor splitor;

    public SearchWorkerControllerImpl(final DocumentsRepo repository,
                                      final ContentSplitor splitor) {
        this.repository = repository;
        this.splitor = splitor;
    }

    @Override
    public TaskResponse processTFIDFTask(final TaskRequest request) {
        final List<DocumentData> response = new ArrayList<>();
        final List<Document> documents = this.repository.getDocumentsByIDs(request.getDocumentIDs());
        for (final Document doc : documents) {
            final List<String> words = this.splitor.splitDocumentToWords(doc.getContent());
            final Map<String, Integer> freq = this.getFrequency(words);

            final DocumentData data = new DocumentData(doc.getID(), words.size(), freq);
            response.add(data);
        }

        return new TaskResponse(response);
    }

    private Map<String, Integer> getFrequency(final List<String> words) {
        final Map<String, Integer> res = new HashMap<>();
        for (final String word : words) {
            res.put(word, res.getOrDefault(word, 0) + 1);
        }

        return res;
    }
}
