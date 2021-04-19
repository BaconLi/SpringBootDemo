package com.dospyer.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

/**
 * @Author: peigen
 * @Date: 2021/4/19 上午9:32
 */
@Data
public class User {
    public interface Update{}
    public interface Add{}

    @NotNull(groups = {Update.class})
    @Null(groups = {Add.class})
    private Long id;
    @Size(min=2, max=20,groups = {Add.class,Update.class})
    private String name;
    @Range(min=1, max=120,groups = {Add.class,Update.class})
    private Integer age;
}
