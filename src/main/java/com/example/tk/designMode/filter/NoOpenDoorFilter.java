package com.example.tk.designMode.filter;

public class NoOpenDoorFilter extends AbstractOpenDoorFilter {


    @Override
    protected boolean selfFilter(String warehouseCode) {
        return super.isOpen(warehouseCode);
    }


    @Override
    protected void doWork() {

    }

    @Override
    protected OpenDoorEnum getEnum() {
        return OpenDoorEnum.NO_OPPEN;
    }
}
