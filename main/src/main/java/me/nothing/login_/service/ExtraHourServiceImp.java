package me.nothing.login_.service;

import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import org.springframework.beans.factory.annotation.Value;
import me.nothing.login_.model.ExtraHour;
import me.nothing.login_.model.Staff;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;

@Service
public class ExtraHourServiceImp implements ExtraHourService{
    private static final Logger LOGGER = Logger.getLogger(Service.class.getName());

    @Autowired
    WebClient webClient;

    public ExtraHourServiceImp(@Value("${content-service}") String baseURL){
        this.webClient = WebClient.builder()
                .baseUrl(baseURL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .filter(logRequest())
                .build();
    }
    private ExchangeFilterFunction logRequest() {
        return (clientRequest, next) -> {
            LOGGER.info("Request: {} {}" + clientRequest.method() + clientRequest.url());
            clientRequest.headers()
                    .forEach((name, values) -> values.forEach(value -> LOGGER.info("{}={}" + name + value)));
            return next.exchange(clientRequest);
        };
    }


    @Override
    public List<ExtraHour> suboExtraHour(Integer id) {
        Flux<ExtraHour> retrievedExtraList = webClient.get()
                                    .uri("/extra/get/{id}", id)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .exchangeToFlux(response ->{
                                        if(response.statusCode().equals(HttpStatus.OK)){
                                            return response.bodyToFlux(ExtraHour.class);
                                        }
                                        else{
                                            return response.createException().flatMapMany(Flux::error);
                                        }
                                    });

        return retrievedExtraList.collectList().block();
    }
    
    @Override
    public ExtraHour createExtraHour(ExtraHour extraHour) {
        Mono<ExtraHour> createLeave = webClient.post().uri("/extra/post")
                                            .body(Mono.just(extraHour), ExtraHour.class)
                                            .retrieve()
                                            .bodyToMono(ExtraHour.class)
                                            .timeout(Duration.ofMillis(10_000));
                                        
        return createLeave.block();
    }

    @Override
    public ExtraHour approvExtraHour(Integer id) {        
        Mono<ExtraHour> approvedExtraHour = webClient.put().uri("/extra/approve/put/{id}", id).retrieve().bodyToMono(ExtraHour.class);
        return approvedExtraHour.block();
    }

    @Override
    public ExtraHour delExtraHour(Integer id) {        
        Mono<ExtraHour> deletedExtra = webClient.put().uri("/extra/delete/put/{id}", id).retrieve().bodyToMono(ExtraHour.class);

        return deletedExtra.block();
    }

    @Override
    public ExtraHour rejecExtraHour(Integer id) {
        Mono<ExtraHour> rejectExtra = webClient.put().uri("/extra/reject/put/{id}", id).retrieve().bodyToMono(ExtraHour.class);

        return rejectExtra.block();
    }

    @Override
    public ExtraHour updExtraHour(Integer id, ExtraHour extraHour) {
        Mono<ExtraHour> updatedExtra = webClient.put().uri("/extra/put").body(Mono.just(extraHour), ExtraHour.class).retrieve().bodyToMono(ExtraHour.class);

        return updatedExtra.block();
    }

    
}
