package com.example.tk.designMode.filter;

/**
 * 开通门户,未对接api接口
 */
public class NoApiFilter extends AbstractOpenDoorFilter {


    @Override
    protected boolean selfFilter(String warehouseCode) {
        return false;
    }

    @Override
    protected void doWork() {

    }

    @Override
    protected OpenDoorEnum getEnum() {
        return OpenDoorEnum.NO_API;
    }
}
