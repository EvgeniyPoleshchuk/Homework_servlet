package ru.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.controller.PostController;
import ru.repository.PostRepository;
import ru.service.PostService;

@Configuration
public class JavaConfig {

    @Bean
    public PostController postController(PostService service){
        return new PostController(service);

    }
    @Bean
    public PostService service(PostRepository repository){
        return new PostService(repository);
    }
    @Bean
    public PostRepository repository(){
        return new PostRepository();
    }
}
