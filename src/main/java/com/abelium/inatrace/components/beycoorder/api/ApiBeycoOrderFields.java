package com.abelium.inatrace.components.beycoorder.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.types.BeycoPrivacy;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.Instant;
import java.util.List;

public class ApiBeycoOrderFields extends ApiBaseEntity {

    @ApiModelProperty(value = "Title of Beyco coffee order")
    @Min(1)
    @Max(70)
    private String title;

    @ApiModelProperty(value = "Privacy of order")
    private BeycoPrivacy privacy;

    @ApiModelProperty(value = "Array of coffee orders")
    private List<ApiBeycoOrderCoffees> offerCoffees;

    @ApiModelProperty(value = "When order is available")
    private Instant availableAt;

    @ApiModelProperty(value = "Location of export")
    private ApiBeycoPortOfExport portOfExport;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BeycoPrivacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(BeycoPrivacy privacy) {
        this.privacy = privacy;
    }

    public Instant getAvailableAt() {
        return availableAt;
    }

    public void setAvailableAt(Instant availableAt) {
        this.availableAt = availableAt;
    }

    public ApiBeycoPortOfExport getPortOfExport() {
        return portOfExport;
    }

    public void setPortOfExport(ApiBeycoPortOfExport portOfExport) {
        this.portOfExport = portOfExport;
    }

    public List<ApiBeycoOrderCoffees> getOfferCoffees() {
        return offerCoffees;
    }

    public void setOfferCoffees(List<ApiBeycoOrderCoffees> offerCoffees) {
        this.offerCoffees = offerCoffees;
    }
}
