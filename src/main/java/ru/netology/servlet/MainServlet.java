package netology.servlet;

import netology.controller.PostController;
import netology.repository.PostRepository;
import netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class MainServlet extends HttpServlet {
    private PostController controller;
    private final List<String> methodList = List.of("GET", "POST", "DELETE");
    private final String DEFAULT_PATH = "/api/posts/";

    @Override
    public void init() {
        final var repository = new PostRepository();
        final var service = new PostService(repository);
        controller = new PostController(service);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // если деплоились в root context, то достаточно этого
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();
            // primitive routing
            if (method.equals(methodList.get(0)) && path.equals(DEFAULT_PATH)) {
                controller.all(resp);
                return;
            }
            if (method.equals(methodList.get(0)) && path.matches(DEFAULT_PATH + "\\d+")) {
                // easy way
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                controller.getById(id, resp);
                return;
            }
            if (method.equals(methodList.get(1)) && path.equals(DEFAULT_PATH)) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (method.equals(methodList.get(2)) && path.matches(DEFAULT_PATH+ "\\d+")) {
                // easy way
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                controller.removeById(id, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}