package dany.springframerwork.spring5webfluxrest.controllers;

import dany.springframerwork.spring5webfluxrest.domain.Vendor;
import dany.springframerwork.spring5webfluxrest.repositories.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(BASE_URL)
    public Mono<Void> createNewVendor(@RequestBody Publisher<Vendor> vendorPublisher){
        return vendorRepository.saveAll(vendorPublisher).then();
    }

    @PutMapping(BASE_URL + "/{id}")
    Mono<Vendor>  update(@PathVariable String id, @RequestBody Vendor vendor) {
        vendor.setId(id);
        return  vendorRepository.save(vendor);
    }

    @PatchMapping(BASE_URL +"/{id}")
    Mono<Vendor> patch(@PathVariable String id, @RequestBody Vendor vendor) {
        Vendor foundVendor = vendorRepository.findById(id).block();
        if (!foundVendor.getFirstName().equals(vendor.getFirstName())){
            foundVendor.setFirstName(vendor.getFirstName());
            return  vendorRepository.save(foundVendor);
        }
        return Mono.just(vendor);
    }
}
