package com.jessitron.functionalprinciples.lazy;

import static com.jessitron.functionalprinciples.fluent.FluentIterable.take;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;

import com.google.common.collect.FluentIterable;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

public class PrettierLazyIteration {
  private static final Predicate<String> STARTS_WITH_BUG_PREDICATE = new Predicate<String>() {
    public boolean apply(String s) {
      return s.startsWith("BUG");
    }
  };
  private static final Function<String,String> TRANSFORM_BUG_LINE_FUNCTION = new Function<String, String>() {
    public String apply(String input) {
      String[] thing = input.split(" ");
      return "Saw the bug at " + thing[0] + " on " + thing[1];
    }
  };

  // objectives:
  // read from a file stream one line at a time.
  // ignore lines that don't start with BUG: blah
  // take the next five BUG lines
  // publish those somewhere.

  @Test
  public void functionalStyleWithFluentInterface() throws IOException {
    final RandomAccessFile br = new RandomAccessFile("pretendLogFile.txt", "r");

    FluentIterable.from(new RandomFileIterable(br))
            .filter(STARTS_WITH_BUG_PREDICATE)
            .transform(TRANSFORM_BUG_LINE_FUNCTION)
            .limit(40)
            .toList()
            .forEach(this::report);


    for (String s : take(new RandomFileIterable(br))    // how to get the lines
        .filterBy(STARTS_WITH_BUG_PREDICATE)            // which lines to choose
        .transformWith(TRANSFORM_BUG_LINE_FUNCTION)     // how to transform the lines
        .limit(40)                                      // how many to use
        ) {
      report(s);                                        // do this for each one.
    }

    br.close();
  }

  private void report(String s) {
    System.out.println(s);
  }

  private static class FileIterator implements Iterator<String> {

    private final RandomAccessFile file;

    private FileIterator(RandomAccessFile file) {
      this.file = file;
    }

    @Override
    public String next() {
      try {
        waitUntilFileHasMoreData(file);
        return file.readLine();
      }catch ( InterruptedException e) {
          throw new RuntimeException(e);
      }
      catch ( IOException e) {
          throw new RuntimeException(e);
      }
    }

    @Override
    public boolean hasNext() {
      return true;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  private static class RandomFileIterable implements Iterable<String> {
    private final RandomAccessFile br;

    public RandomFileIterable(RandomAccessFile br) {
      this.br = br;
    }

    @Override
    public Iterator<String> iterator() {
      return new FileIterator(br);
    }
  }


  private static void waitUntilFileHasMoreData(RandomAccessFile br) throws IOException, InterruptedException {
    while (br.length() <= br.getFilePointer()) {
      Thread.sleep(100);    // wait for more input
    }
  }

}
