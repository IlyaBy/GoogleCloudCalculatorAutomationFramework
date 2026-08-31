package model;

public class ComputeEngineInstance {

    private final String numberOfInstances;
    private final String operatingSystem;
    private final String provisioningModel;
    private final String machineFamily;
    private final String series;
    private final String machineType;
    private final String gpuType;
    private final String gpuNumber;
    private final String localSSD;
    private final String region;
    private final String discountOptions;

    public ComputeEngineInstance(String numberOfInstances, String operatingSystem, String provisioningModel,
                                 String machineFamily, String series, String machineType,
                                 String gpuType, String gpuNumber, String localSSD,
                                 String region, String discountOptions) {
        this.numberOfInstances = numberOfInstances;
        this.operatingSystem = operatingSystem;
        this.provisioningModel = provisioningModel;
        this.machineFamily = machineFamily;
        this.series = series;
        this.machineType = machineType;
        this.gpuType = gpuType;
        this.gpuNumber = gpuNumber;
        this.localSSD = localSSD;
        this.region = region;
        this.discountOptions = discountOptions;
    }

    public String getNumberOfInstances() { return numberOfInstances; }
    public String getOperatingSystem() { return operatingSystem; }
    public String getProvisioningModel() { return provisioningModel; }
    public String getMachineFamily() { return machineFamily; }
    public String getSeries() { return series; }
    public String getMachineType() { return machineType; }
    public String getGpuType() { return gpuType; }
    public String getGpuNumber() { return gpuNumber; }
    public String getLocalSSD() { return localSSD; }
    public String getRegion() { return region; }
    public String getDiscountOptions() { return discountOptions; }
}
