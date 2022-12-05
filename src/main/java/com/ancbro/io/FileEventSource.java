package com.ancbro.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileEventSource implements EventSource {

  private String path;

  public FileEventSource(String path) {
    this.path = path;
  }

  @Override
  public void commitToEventSource(String line) throws IOException {
    Files.write(Paths.get(this.getPath()), line.getBytes());
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }
}
