package fran.fourcade.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GWConfig {

    @Bean
    public RouteLocator configureRoutes(RouteLocatorBuilder builder,
                                        @Value("${api-tpi-backend.url-microservicio-empleados}") String uriEmpleados,
                                        @Value("${api-tpi-backend.url-microservicio-pruebas}") String uriPruebas) {
        return builder.routes()
                //Ruteo al microservicio de Empleados
                .route( p -> p.path("/api/empleados/**").uri(uriEmpleados))
                //Ruteo al microservicio de Pruebas
                .route(p -> p.path("/api/pruebas/**").uri(uriPruebas))
                .build();
    }
}
