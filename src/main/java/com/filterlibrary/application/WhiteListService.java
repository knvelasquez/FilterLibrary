package com.filterlibrary.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;

@Component
public class WhiteListService {
    private static final Logger logger = LogManager.getLogger(WhiteListService.class);
    private HashMap<String, HashMap<Object, Boolean>> whiteList = new HashMap<>();

    public void update(String item, String filter, Boolean status) {
        if (!whiteList.containsKey(item)) {
            whiteList.put(item, new HashMap<>());
        }
        String className = filter;
        whiteList.get(item).put(className, status);
    }

    public Boolean check(String item, String filter) {
        final Boolean[] result = {true};
        Optional.ofNullable(whiteList.get(item)).ifPresent(url -> {
            Optional.ofNullable(url.get(filter)).ifPresent(status -> {
                result[0] = status.booleanValue();
            });
        });
        logger.debug(new StringBuilder("url:")
                .append(item)
                .append(";filter:")
                .append(filter)
                .append(";result:")
                .append(result[0])
                .toString()
        );
        return result[0];
    }


}
