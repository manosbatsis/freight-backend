package com.freight.guice;

/**
 * It is necessary to override the guice bridge due to the guice scope context having an incorrect visibility
 * (IS LOCAL, needs to be NORMAL to allow injections in filters)
 */
public abstract class FreightGuiceBridge {

    private final static FreightGuiceBridgeImpl INSTANCE = new FreightGuiceBridgeImpl();

    public static FreightGuiceBridgeImpl getGuiceBridge() {
        return INSTANCE;
    }
}
