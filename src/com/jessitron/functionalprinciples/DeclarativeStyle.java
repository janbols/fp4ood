package com.jessitron.functionalprinciples;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Iterables.filter;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DeclarativeStyle {

    private final List<String> lines = ImmutableList.of("snoethusnt",
            "BUG: asnoetuh",
            "sanoetuhonet",
            "BUG: anoetuhan");

    @Test
    public void doSomethingNonDeclaratively() {
        for (String line : lines) {
            if (line.startsWith("BUG")) {
                report(line);
            }
        }
    }

    private void report(String line) {
        System.out.println(line);
    }

    @Test
    public void filterNormally() {

        reportAll(filterForBugs(lines));

        assertThat(filterForBugs(lines).size(), is(2));
    }

    private void reportAll(List<String> bugLines) {
        for (String bugLine : bugLines) {
            report(bugLine);
        }
    }

    private List<String> filterForBugs(List<String> allLines) {
        List<String> bugLines = new LinkedList<String>();
        for (String line : allLines) {
            if (line.startsWith("BUG")) {
                bugLines.add(line);
            }
        }
        return bugLines;
    }

    @Test
    public void filterMoreDeclaratively() {
        Predicate<String> startsWithBug =
                new Predicate<String>() {
                    public boolean apply(String s) {
                        return s.startsWith("BUG");
                    }
                };
        filter(lines, startsWithBug);

        lines
                .stream()
                .filter(s -> s.startsWith("BUG"))
                .collect(Collectors.toList());
        lines
                .stream()
                .parallel()
                .filter(s -> s.startsWith("BUG"))
                .collect(Collectors.toList());

        assertThat(Iterables.size(bugLines), is(2));
    }
}
