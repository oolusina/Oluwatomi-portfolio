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

  ArrayList<String> comments = new ArrayList<String>();
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //response.setContentType("text/html;");
    //response.getWriter().println("<h1>A work in progress...</h1>");

    comments.add("This is just to practice JSON conversion");
    comments.add("This would be the second message");
    comments.add("It is ok that these are hard-coded");
    comments.add("An extra would not hurt");
    //Responding with json-converted ArrayList
    String json = convertToJson(comments);
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  private String convertToJson(ArrayList comments) {
    String json = "{";
    json += "\"message1\": ";
    json += "\"" + comments.get(0) + "\"";
    json += ", ";
    json += "\"message2\": ";
    json += "\"" + comments.get(1) + "\"";
    json += ", ";
    json += "\"message3\": ";
    json += "\"" + comments.get(2) + "\"";
    json += ", ";
    json += "\"message4\": ";
    json += "\"" + comments.get(3) + "\"";
    json += "}";
    return json;
  }
}
