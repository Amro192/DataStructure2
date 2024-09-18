package com.othman.app;

import com.othman.data.ImbalancedTagsException;
import com.othman.data.Section;
import com.othman.file_dialogs.Tags;
import com.othman.structures.Cursor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for holding the application data.
 */
public class App {
    /**
     * The singleton instance.
     */
    private static final App instance = new App();

    /**
     * The cursor object.
     */
    private final Cursor cursor = new Cursor();

    /**
     * The sections list.
     */
    private final List<Section> sections = new ArrayList<>();

    /**
     * public constructor
     */
    public static App getInstance() {
        return instance;
    }

    /**
     * Returns the cursor attribute
     */
    public Cursor getCursor() {
        return cursor;
    }

    /**
     * Returns the sections list
     */
    public List<Section> getSections() {
        return sections;
    }

    /**
     * Returns the last section in the sections list
     */
    public Section getLastSection() {
        return sections.getLast();
    }

    /**
     * Returns the first section in the sections list
     */
    public Section getFirstSection() {
        return sections.getFirst();
    }

    /**
     * Returns the section at the specified index
     */
    public void addSection(Section section) {
        sections.add(section);
    }

    /**
     * Reads the file at the specified path
     */
    public void readFile(Path path) throws IOException {
        // check if the file is empty
        try (var reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                // check if the tags are balanced
                if (!Tags.isBalanced(line)) {
                    throw new ImbalancedTagsException();
                }
            }
        }
    }
}
