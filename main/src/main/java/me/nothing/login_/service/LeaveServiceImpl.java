package me.nothing.login_.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import org.springframework.beans.factory.annotation.Value;
import me.nothing.login_.model.Leave;
import me.nothing.login_.model.Staff;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;

@Service
public class LeaveServiceImpl implements LeaveService {

    private static final Logger LOGGER = Logger.getLogger(Service.class.getName());

    @Autowired
    WebClient webClient;

    public LeaveServiceImpl(@Value("${content-service}") String baseURL){
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
    public List<Leave> findLeaveWithStaffId(Integer id) {
        Flux<Leave> retrievedLeaveList = webClient.get()
                                    .uri("/getWithStaffId/{id}", id)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .exchangeToFlux(response ->{
                                        if(response.statusCode().equals(HttpStatus.OK)){
                                            return response.bodyToFlux(Leave.class);
                                        }
                                        else{
                                            return response.createException().flatMapMany(Flux::error);
                                        }
                                    });
                                    

        return retrievedLeaveList.collectList().block();
    }
    @Override
    public Leave createLeaveHistory(Integer stfid, Leave leaves) {
        Mono<Leave> createLeave = webClient.post().uri("/post/{stfid}", stfid)
                                            .body(Mono.just(leaves), Leave.class)
                                            .retrieve()
                                            .bodyToMono(Leave.class)
                                            .timeout(Duration.ofMillis(10_000));
                                        
        return createLeave.block();
    }

    @Override
    public Leave updateLeaveHistory(Leave leave) {
        Mono<Leave> updatedLeave = webClient.put().uri("/leave/put").body(Mono.just(leave), Leave.class).retrieve().bodyToMono(Leave.class);

        return updatedLeave.block();
    }
    
    @Override
    public Leave getLeaveWithLeaveId(Integer id) {
        Mono<Leave> getLeaveWithLeaveId = webClient.get()
                                            .uri("/getLeave/{id}", id)
                                            .accept(MediaType.APPLICATION_JSON)
                                            .exchangeToMono(response->{
                                                if(response.statusCode().equals(HttpStatus.OK)){
                                                    return response.bodyToMono(Leave.class);
                                                }
                                                else{
                                                    return response.createException().flatMap(Mono::error);
                                                }
                                            });
        return getLeaveWithLeaveId.block();
    }
    @Override
    public List<Staff> getSubordinate(Integer id) {
        Flux<Staff> retrievedStaffList = webClient.get()
                                    .uri("/getSubordinate/{id}", id)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .exchangeToFlux(response ->{
                                        if(response.statusCode().equals(HttpStatus.OK)){
                                            return response.bodyToFlux(Staff.class);
                                        }
                                        else{
                                            return response.createException().flatMapMany(Flux::error);
                                        }
                                    });

        return retrievedStaffList.collectList().block();
    }
    
    @Override
    public Leave approvLeave(Leave leave) {        
        Mono<Leave> approvedLeave = webClient.put().uri("/approve/put").body(Mono.just(leave),Leave.class).retrieve().bodyToMono(Leave.class);
        return approvedLeave.block();
    }

    @Override
    public Leave deleteLeave(int id) {        
        Mono<Leave> deletedLeave = webClient.put().uri("/delete/put/{id}", id).retrieve().bodyToMono(Leave.class);
        return deletedLeave.block();
    }

    @Override
    public Leave withdrawLeave(int id) {        
        Mono<Leave> withdrawedLeave = webClient.put().uri("/withdraw/put/{id}",id).retrieve().bodyToMono(Leave.class);
        return withdrawedLeave.block();
    }
    
    @Override
    public List<Leave> getpendingLeave(int id){
        Flux<Leave> pendingleave  = webClient.get().uri("/viewpending/{id}",id)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .exchangeToFlux(response->{
                                        if(response.statusCode().equals(HttpStatus.OK)){
                                            return response.bodyToFlux(Leave.class);
                                        }
                                        else{
                                            return response.createException().flatMapMany(Flux::error);
                                        }
                                    });
        return pendingleave.collectList().block();
    }


    @Override
    public List<Leave> findLeaveWithDay(String day){
        Flux<Leave> retrievedLeaveList = webClient.get()
                                    .uri("/getByday/{day}", day)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .exchangeToFlux(response ->{
                                        if(response.statusCode().equals(HttpStatus.OK)){
                                            return response.bodyToFlux(Leave.class);
                                        }
                                        else{
                                            return response.createException().flatMapMany(Flux::error);
                                        }
                                    });

        return retrievedLeaveList.collectList().block();
    }

}
