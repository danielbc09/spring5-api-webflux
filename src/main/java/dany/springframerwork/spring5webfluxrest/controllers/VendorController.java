package dany.springframerwork.spring5webfluxrest.controllers;

import dany.springframerwork.spring5webfluxrest.domain.Vendor;
import dany.springframerwork.spring5webfluxrest.repositories.VendorRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by bautisj on 4/23/2018.
 */
@RestController
public class VendorController {

    public static final String BASE_URL = "/api/v1/vendors";
    public final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping(BASE_URL)
    public Flux<Vendor> getAllVendors(){
        return vendorRepository.findAll();
    }

    @GetMapping(BASE_URL + "/{id}")
    public Mono<Vendor> getVendorById (@PathVariable String id) {
        return vendorRepository.findById(id);
    }
}
