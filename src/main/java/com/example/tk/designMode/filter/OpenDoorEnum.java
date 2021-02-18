package com.example.tk.designMode.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OpenDoorEnum {
    NO_OPPEN(1,"没有开通门户"),
    IS_API(2,"开通门户,接入api"),
    NO_API(3,"开通门户,没有接入api")
    ;

    private Integer  code;
    private String desc;

}
