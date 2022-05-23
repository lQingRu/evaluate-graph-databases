package com.qingru.graph.controller;

import com.qingru.graph.service.TigerGraph.TigerGraphTestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/tigergraph")
@AllArgsConstructor
public class TigerGraphController {
    private TigerGraphTestService testService;

    @GetMapping("")
    public void test() throws SQLException {
        testService.displayBuiltinsInTabularForm();
    }

    @GetMapping("/person")
    public void getAllPerson() throws SQLException {
        testService.displayAllPersonInTabularForm();
    }
}
