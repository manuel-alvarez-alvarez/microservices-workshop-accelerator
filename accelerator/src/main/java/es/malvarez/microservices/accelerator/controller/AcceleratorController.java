package es.malvarez.microservices.accelerator.controller;

import es.malvarez.microservices.accelerator.service.AcceleratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Web controller to be used by the accelerator.
 */
@RequestMapping("/")
@Controller
public class AcceleratorController {

    private final AcceleratorService acceleratorService;

    @Autowired
    protected AcceleratorController(final AcceleratorService acceleratorService) {
        this.acceleratorService = acceleratorService;
    }

    @RequestMapping(value = "/toggle", method = RequestMethod.PUT)
    @ResponseBody
    public String toggle() {
        acceleratorService.toggle();
        return acceleratorService.getStatus().name();
    }

    @RequestMapping(value = "/status", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String getStatus() {
        return acceleratorService.getStatus().name();
    }
}
