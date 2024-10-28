package com.example.demo.ui.controller

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/sample")
class SampleController {

    @GetMapping("/hello")
    fun sampleEndpoint(): String {
        return "sample"
    }
}
