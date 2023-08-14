package ru;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import ru.config.JavaConfig;
import ru.controller.PostController;
import ru.repository.PostRepository;
import ru.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class Main extends HttpServlet {
    private PostController controller;
    private static final String PATH = "/api/posts/";
    private static final String PATH_WITH_NUM = PATH + "\\d+";
    private static final String POST = "POST";
    private static final String GET = "GET";
    private static final String DELETE = "DELETE";


    @Override
    public void init() {
        var context = new AnnotationConfigApplicationContext("ru");
        context.getBean(JavaConfig.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // если деплоились в root context, то достаточно этого
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();
            // primitive routing
            if (method.equals(GET) && path.equals(PATH)) {
                controller.all(resp);
                return;
            }
            if (method.equals(GET) && path.matches(PATH_WITH_NUM)) {
                // easy way
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                controller.getById(id, resp);
                return;
            }
            if (method.equals(POST) && path.equals(PATH)) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (method.equals(DELETE) && path.matches(PATH_WITH_NUM)) {
                // easy way
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                controller.removeById(id, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch(
                Exception e)

        {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}