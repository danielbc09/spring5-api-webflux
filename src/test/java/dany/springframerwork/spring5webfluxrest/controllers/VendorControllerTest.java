package dany.springframerwork.spring5webfluxrest.controllers;

import dany.springframerwork.spring5webfluxrest.domain.Vendor;
import dany.springframerwork.spring5webfluxrest.repositories.CategoryRepository;
import dany.springframerwork.spring5webfluxrest.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * Created by bautisj on 4/23/2018.
 */
@RestController
public class VendorControllerTest {

    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController vendorController;

    private Vendor vendor1;
    private Vendor vendor2;

    @Before
    public void setUp() throws Exception {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();

        vendor1 = Vendor.builder()
                .id("abcdefg")
                .firstName("Daniel")
                .lastName("Bautista").build();
        vendor2 = Vendor.builder()
                .id("avsssd")
                .firstName("Jhon")
                .lastName("Doe")
                .build();
    }

    @Test
    public void getAllVendors() throws Exception {
        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(vendor1, vendor2));

        webTestClient.get().uri(vendorController.BASE_URL)
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);

    }

    @Test
    public void getVendorById() throws Exception {
        BDDMockito.given(vendorRepository.findById("avsssd"))
                .willReturn(Mono.just(vendor1));

        webTestClient.get()
                .uri(vendorController.BASE_URL + "/avsssd")
                .exchange()
                .expectBody(Vendor.class);
    }

    @Test
    public void testCreateVendor(){
        BDDMockito.given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().build()));

        Mono<Vendor> vendorMono = Mono.just(vendor1);

        webTestClient.post()
                .uri(vendorController.BASE_URL)
                .body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }
}