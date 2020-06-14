// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.PreparedQuery;


/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //Datastore initialization and query to datastore to retrieve comments
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(
        new Query("Comment").addSort("timestamp", SortDirection.DESCENDING)
    );

    ArrayList<String> comments = new ArrayList<String>();
    for (Entity entity : results.asIterable()) {
      String content = (String) entity.getProperty("content");
      comments.add(content);
    }

    //Responding with json-converted comments ArrayList
    response.setContentType("application/json;");
    response.getWriter().println(convertToJson(comments));
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Input
    String newComment = request.getParameter("commentary").trim();
    if (newComment.isEmpty()) {
      response.setContentType("text/html");
      response.getWriter().println("Please enter a legitimate comment!");
      return;
    }
    long timestamp = System.currentTimeMillis();

    //comments.add(0, newComment);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    Entity commentEntity = new Entity("Comment");
    commentEntity.setProperty("content", newComment);
    commentEntity.setProperty("timestamp", timestamp);

    datastore.put(commentEntity);

    // Redirect back to the HTML page
    response.sendRedirect("/index.html");
  }

  private String convertToJson(ArrayList comments) {
    String json = "{";
    for (int c = 0; c < comments.size(); c++) {
        json += "\"comment" + (c+1) + "\": \"" + comments.get(c) + "\"";
        if (c != comments.size() -1) json += ", ";
    }
    json += "}";
    return json;
  }
}
