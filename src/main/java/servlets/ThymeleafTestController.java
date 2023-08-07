package servlets;

import model.StateTimezone;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import servіce.TimeService;
import servіce.TimezoneRequestBodyParser;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/time")
public class ThymeleafTestController extends HttpServlet {


    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IOException {
        resp.setContentType("text/html");
        ServletContext servletContext = req.getServletContext();
        TemplateEngine engine = (TemplateEngine) servletContext.getAttribute("TemplateEngine");
        engine.process("time", new Context(), resp.getWriter());
        resp.getWriter().close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        TimezoneRequestBodyParser timezoneRequestBodyParser = new TimezoneRequestBodyParser();
        TimezoneRequestBodyParser rezultParsRequest=  timezoneRequestBodyParser.parsRequest(req);
        String zoneIdForRespons = getTimeZoneIdForRespons(  rezultParsRequest,  req,  resp);
        Context simpleContext = new Context(req.getLocale(),
               Map.of("currentTimeDateZone", TimeService.getTimeDateTimeZoneByZoneId(zoneIdForRespons)));
        TemplateEngine engine = (TemplateEngine) req.getServletContext().getAttribute("TemplateEngine");
        engine.process("time", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }

     private String  getTimeZoneIdForRespons(TimezoneRequestBodyParser rezultParsRequest, HttpServletRequest req, HttpServletResponse resp){
         String nameTimeZone ="GMT0";
         if(rezultParsRequest.getModelRequest().getState() == StateTimezone.ABSENT){
             Cookie []  cookies = req.getCookies();
             for(Cookie cookie:cookies){
                 if(cookie.getName().equals("lastTimezone")){
                     nameTimeZone=cookie.getValue();
                 }
             }
         }

         if(rezultParsRequest.getModelRequest().getState() == StateTimezone.INVALID) {
             nameTimeZone=rezultParsRequest.getModelRequest().getNameTimezone();
         }
         if(rezultParsRequest.getModelRequest().getState() == StateTimezone.VALID){
             nameTimeZone=rezultParsRequest.getModelRequest().getNameTimezone();
             Cookie cookie = new Cookie("lastTimezone",nameTimeZone);
             cookie.setMaxAge(-1);
             resp.addCookie(cookie);
         }
         return nameTimeZone;
     }
}
