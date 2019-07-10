package com.nostis.lightning_core.task;

import com.nostis.lightning_core.model.Lightning;
import com.nostis.lightning_core.service.CustomerService;
import com.nostis.lightning_core.service.LightningService;
import com.nostis.lightning_core.soap_api_client.SoapClientLightnings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;

public class FetchData {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private LightningService lightningService;

    @Scheduled(fixedRate = 60000)
    public void updateLightnings() {
        List<Lightning> lightningsToAdd = new ArrayList<>();

        customerService.getAllCustomers().forEach(c -> {
            Lightning lightning = new Lightning();
            Map<String, String> lightningObtained = SoapClientLightnings.findLightning(c.getLocation().get(0), c.getLocation().get(1), 50, "<key>");

            lightning.setArea(new ArrayList<>(Arrays.asList(c.getLocation().get(0), c.getLocation().get(1))));
            lightning.setCount(Integer.valueOf(lightningObtained.get("count")));
            lightning.setCreated(Calendar.getInstance());
            lightning.setDirection(lightningObtained.get("direction"));
            lightning.setDistance(Float.valueOf(lightningObtained.get("distance")));
            lightning.setInter(Integer.valueOf(lightningObtained.get("interval")));

            lightningsToAdd.add(lightning);
        });

        lightningService.saveLightnings(lightningsToAdd);
    }

    @Scheduled(fixedDelay = 60000)
    public void removeUnnecessaryLightnings() {
        Calendar actualMinusThirtyMin = Calendar.getInstance();
        actualMinusThirtyMin.set(Calendar.MINUTE, actualMinusThirtyMin.get(Calendar.MINUTE) - 30);

        lightningService.deleteLightningsBefore(actualMinusThirtyMin);
    }
}
