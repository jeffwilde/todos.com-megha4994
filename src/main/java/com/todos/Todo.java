package com.todos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.*;

public class Todo {

  private final String title;
  private final boolean completed;
  private final Long order;
  private final String[] tags;

  @JsonCreator
  public Todo(@JsonProperty("title") String title,
              @JsonProperty(value = "completed") boolean completed,
              @JsonProperty(value = "order") long order,
              @JsonProperty(value = "tags") String[] tags) {
    this.title = title;
    this.completed = completed;
    this.order = order;
    this.tags = tags;
  }

  public String getTitle() {
    return title;
  }

  public boolean isCompleted() {
    return completed;
  }

  public long getOrder() {
    return order;
  }

  public String[] getTags () {
    return tags;
  }

  @Override
  public String toString() {
    return "Todo{" +
        "title='" + title + '\'' +
        ", completed=" + completed +
        ", order=" + order +
        ", tags=" + Arrays.toString(tags) +
        '}';
  }

  Todo merge(Todo updatedTodo) {
    return new Todo(
        Optional.ofNullable(updatedTodo.title).orElse(title),
        updatedTodo.completed,
        Optional.ofNullable(updatedTodo.order).orElse(order),
        Optional.ofNullable(updatedTodo.tags).orElse(tags)
    );
  }
}
