package com.othman.file_dialogs;

import com.othman.app.App;
import com.othman.data.Section;
import com.othman.structures.Stack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is responsible for parsing the tags in the file.
 */
public class Tags {
    /**
     * The stack that holds the tags.
     */
    private static final Stack stack = new Stack();

    /**
     * The pattern that matches the equation tag.
     */
    private static final Pattern compile = Pattern.compile("<equation>(.*)</equation>");

    /**
     * Checks if the tags are balanced.
     */
    public static boolean isBalanced(String str) {
        // Remove the indentation and the trailing spaces.
        str = str.stripIndent().trim();
        // Check if the string is an equation tag.
        Matcher matcher = compile.matcher(str);
        boolean matches = matcher.matches();

        // Check if the string is a closing tag.
        return switch (str) {
            case "</242>" -> stack.pop().equals("<242>");
            case "</section>" -> stack.pop().equals("<section>");
            case "</infix>" -> stack.pop().equals("<infix>");
            case "</postfix>" -> stack.pop().equals("<postfix>");
            default -> {
                // If the string is an equation tag, push the appropriate tag to the stack.
                if (matches) {
                    if (stack.peek().equals("<infix>")) {
                        App.getInstance().getLastSection().addInfix(Evaluate.infixEvaluation(matcher.group(1)));
                    } else if (stack.peek().equals("<postfix>")) {
                        App.getInstance().getLastSection().addPostfix(Evaluate.postfixEvaluation(matcher.group(1)));
                    }
                    yield true;
                    // If the string is an opening tag, push it to the stack.
                } else if (!str.startsWith("<equation>")) {
                    stack.push(str);
                    // If the string is a section tag, add a new section to the sections list.
                    if (str.equals("<section>")) {
                        App.getInstance().addSection(new Section());
                    }
                    yield true;
                }
                yield false;
            }
        };
    }
}
