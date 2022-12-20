package me.nothing.login_.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import me.nothing.login_.model.ExtraHour;
import me.nothing.login_.model.Staff;


public interface ExtraHourService {
    
    List<ExtraHour> suboExtraHour(Integer id);

    ExtraHour createExtraHour(ExtraHour extraHour);

    ExtraHour delExtraHour(Integer id);

    ExtraHour updExtraHour(Integer id, ExtraHour extraHour);

    ExtraHour approvExtraHour(Integer id);

    ExtraHour rejecExtraHour(Integer id);

}