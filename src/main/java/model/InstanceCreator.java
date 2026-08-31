package model;

import java.util.Properties;

public class InstanceCreator {

    private static final String INSTANCES = "instance.instances";
    private static final String OP_SYSTEM = "instance.operatingSystem";
    private static final String PROV_MODEL = "instance.provisioningModel";
    private static final String MACHINE_FAMILY = "instance.machineFamily";
    private static final String SERIES = "instance.series";
    private static final String MACHINE_TYPE = "instance.machineType";
    private static final String GPU_TYPE = "instance.gpuType";
    private static final String GPU_NUMBER = "instance.gpuNumber";
    private static final String LOCAL_SSD = "instance.localSSD";
    private static final String REGION = "instance.region";
    private static final String DISCOUNT = "instance.discountOptions";

    private InstanceCreator() {}

    public static ComputeEngineInstance createFromProperties(Properties properties) {
        return new ComputeEngineInstance(
                properties.getProperty(INSTANCES),
                properties.getProperty(OP_SYSTEM),
                properties.getProperty(PROV_MODEL),
                properties.getProperty(MACHINE_FAMILY),
                properties.getProperty(SERIES),
                properties.getProperty(MACHINE_TYPE),
                properties.getProperty(GPU_TYPE),
                properties.getProperty(GPU_NUMBER),
                properties.getProperty(LOCAL_SSD),
                properties.getProperty(REGION),
                properties.getProperty(DISCOUNT)
        );
    }
}