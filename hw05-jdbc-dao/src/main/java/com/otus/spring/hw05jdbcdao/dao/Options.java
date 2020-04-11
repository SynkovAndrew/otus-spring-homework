package com.otus.spring.hw05jdbcdao.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Options {
    private boolean selectReferencedObjects;
}
