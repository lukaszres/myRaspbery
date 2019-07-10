package com.lkre.index.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.json.JSONObject;
import com.lkre.index.services.DatabaseService;

public class AddTemperatureServlet extends HttpServlet {

    private static final long serialVersionUID = -2894970089091096353L;

    @Override
    public void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        StringBuffer jb = new StringBuffer();
        String line;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*report an error*/ }

        String content = jb.toString();

        JSONObject jsonObject = new JSONObject(content);
        BigDecimal temp = jsonObject.getBigDecimal("temp");
        String date = (String) jsonObject.get("date");

        Timestamp timestamp = null;
        try {
            String pattern2 = "yyyy-MM-dd hh:mm:ss.SSSSSS";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern2);
            Date date1 = dateFormat.parse(date);
            timestamp = new java.sql.Timestamp(date1.getTime());
        } catch (Exception e) { //this generic but you can control another types of exception
            // look the origin of exception
        }

        DatabaseService databaseService = new DatabaseService();
        databaseService.addTemperature(temp, timestamp);

        PrintWriter out = response.getWriter();
        out.write("Everything is ok");
        out.close();

        System.out.println(content);
    }

    @Override
    public void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        // Set the response message's MIME type
        response.setContentType("text/html;charset=UTF-8");
        // Allocate a output writer to write the response message into the network socket
        // Write the response message, in an HTML page
        try (PrintWriter out = response.getWriter()) {
            out.println("GET method is unsupported");
        }
    }
}