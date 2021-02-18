package com.example.tk.designMode.filter;

/**
 * 开通门户,已经对接api接口
 */
public class IsApiFilter extends AbstractOpenDoorFilter {

    @Override
    protected boolean selfFilter(String warehouseCode) {
        return false;
    }

    @Override
    protected void doWork() {

    }

    @Override
    protected OpenDoorEnum getEnum() {
        return OpenDoorEnum.IS_API;
    }
}
