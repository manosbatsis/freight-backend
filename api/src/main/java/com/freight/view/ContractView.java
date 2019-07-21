package com.freight.view;

import com.freight.model.Contract;
import com.freight.model.Incoterms;
import com.freight.model.Payout;
import com.freight.model.ShipAgent;
import com.freight.model.TimeUnit;
import com.freight.model.Type;

import java.math.BigDecimal;
import java.time.Instant;


/**
 * Created by toshikijahja on 3/26/19.
 */
public class ContractView {

    private final Contract contract;

    public ContractView(final Contract contract) {
        this.contract = contract;
    }

    public int getId() {
        return contract.getId();
    }

    public ShipView getShip() {
        return new ShipView(contract.getShip());
    }

    public int getUserId() {
        return contract.getUserId();
    }

    public BigDecimal getPrice() {
        return contract.getPrice();
    }

    public Contract.PriceUnit getPriceUnit() {
        return contract.getPriceUnit();
    }

    public String getCurrency() {
        return contract.getCurrency();
    }

    public Payout getPayout() {
        return contract.getPayout();
    }

    public Instant getStartDate() {
        return contract.getStartDate();
    }

    public Instant getEndDate() {
        return contract.getEndDate();
    }

    public Contract.CharterType getCharterType() {
        return contract.getCharterType();
    }

    public Contract.LoadingType getLoadingType() {
        return contract.getLoadingType();
    }

    public Incoterms getIncoterms() {
        return contract.getIncoterms();
    }

    public Contract.CargoHandler getCargoSender() {
        return contract.getCargoSender();
    }

    public String getCargoSenderOther() {
        return contract.getCargoSenderOther();
    }

    public Contract.CargoHandler getCargoReceiver() {
        return contract.getCargoReceiver();
    }

    public String getCargoReceiverOther() {
        return contract.getCargoReceiverOther();
    }

    public Contract.InsuranceProvider getCargoInsurance() {
        return contract.getCargoInsurance();
    }

    public Contract.InsuranceProvider getShipInsurance() {
        return contract.getShipInsurance();
    }

    public ShipAgent getShipAgent() {
        return contract.getShipAgent();
    }

    public Type getMiscellaneousFee() {
        return contract.getMiscellaneousFee();
    }

    public BigDecimal getDemurrage() {
        return contract.getDemurrage();
    }

    public TimeUnit getDemurrageUnit() {
        return contract.getDemurrageUnit();
    }

    public Integer getLoadingLaytime() {
        return contract.getLoadingLaytime();
    }

    public Integer getDischargeLaytime() {
        return contract.getDischargeLaytime();
    }

    public Integer getTotalLaytime() {
        return contract.getTotalLaytime();
    }

    public TimeUnit getLaytimeUnit() {
        return contract.getLaytimeUnit();
    }

    public Contract.DespatchType getDespatchType() {
        return contract.getDespatchType();
    }

    public Contract.LayDaysType getLayDaysType() {
        return contract.getLayDaysType();
    }
}
