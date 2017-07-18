package com.mnazarenka.dao.utils;

import com.mnazarenka.dao.entity.EconomAppartment;
import com.mnazarenka.dao.entity.LuxAppartment;
import com.mnazarenka.dao.entity.StandartAppartment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * Created by Николай on 18.07.2017.
 */
@Component
public class AppartmentUtil {
    private HashMap<String, Class> classes;

    public Class getAppartmentClass(String type) {
        return classes.get(type);
    }

    @PostConstruct
    public void init() {
        classes = new HashMap<>();
        classes.put("lux", LuxAppartment.class);
        classes.put("standart", StandartAppartment.class);
        classes.put("econom", EconomAppartment.class);
    }
}
