package com.example.tk.designMode.filter;

/**
 * 门户过滤器
 */
public abstract class AbstractOpenDoorFilter extends AbstractApiFilter {

    /**
     *校验是否开通门户
     * @return
     */
    protected boolean isOpen(String warehouseCode){
        return true;
    }


}
