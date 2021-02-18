package com.example.tk.designMode.filter;

public abstract class AbstractApiFilter {

    /**
     *具体接口实现
     * @return
     */
    protected abstract boolean selfFilter(String warehouseCode);

    protected abstract void doWork();

    protected abstract OpenDoorEnum getEnum();

    /**
     * 获取走哪个fitter
     * @param warehouseCode
     * @return
     */
    public boolean filter(String warehouseCode){
        return selfFilter(warehouseCode);
    }

}
