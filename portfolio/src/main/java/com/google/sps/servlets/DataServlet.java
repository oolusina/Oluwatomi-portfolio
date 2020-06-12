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

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

  ArrayList<String> comments = new ArrayList<String>() {};
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //response.setContentType("text/html;");
    //response.getWriter().println("<h1>A work in progress...</h1>");

    //Responding with json-converted ArrayList
    String json = convertToJson(comments);
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Input
    String newComment = request.getParameter("commentary").trim();
    if (newComment.isEmpty()) {
      response.setContentType("text/html");
      response.getWriter().println("Please enter a legitimate comment.");
      return;
    }
    comments.add(0, newComment);

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
