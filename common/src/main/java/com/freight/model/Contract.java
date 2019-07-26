package com.freight.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * Created by toshikijahja on 3/26/19.
 */
@Entity
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipId")
    private Ship ship;

    @Column
    private Integer shipmentId;

    @Column
    private int userId;

    @Column
    private BigDecimal price;

    @Column
    @Enumerated(EnumType.STRING)
    private PriceUnit priceUnit;

    @Column
    private String currency;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payoutId")
    private Payout payout;

    @Column
    private Instant startDate;

    @Column
    private Instant endDate;

    @Column
    @Enumerated(EnumType.STRING)
    private CharterType charterType;

    @Column
    @Enumerated(EnumType.STRING)
    private LoadingType loadingType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "incotermsId")
    private Incoterms incoterms;

    @Column
    @Enumerated(EnumType.STRING)
    private CargoHandler cargoSender;

    @Column
    private String cargoSenderOther;

    @Column
    @Enumerated(EnumType.STRING)
    private CargoHandler cargoReceiver;

    @Column
    private String cargoReceiverOther;

    @Column
    @Enumerated(EnumType.STRING)
    private InsuranceProvider cargoInsurance;

    @Column
    @Enumerated(EnumType.STRING)
    private InsuranceProvider shipInsurance;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipAgentId")
    private ShipAgent shipAgent;

    @Column
    @Enumerated(EnumType.STRING)
    private Type miscellaneousFee;

    @Column
    private BigDecimal demurrage;

    @Column
    @Enumerated(EnumType.STRING)
    private TimeUnit demurrageUnit;

    @Column
    private Integer loadingLaytime;

    @Column
    private Integer dischargeLaytime;

    @Column
    private Integer totalLaytime;

    @Column
    @Enumerated(EnumType.STRING)
    private TimeUnit laytimeUnit;

    @Column
    @Enumerated(EnumType.STRING)
    private DespatchType despatchType;

    @Column
    @Enumerated(EnumType.STRING)
    private LayDaysType layDaysType;

    @Column
    @CreationTimestamp
    private Instant created;

    @Column
    @UpdateTimestamp
    private Instant lastModified;

    public enum PriceUnit {
        KG,
        TON,
        LB,
        M3,
        NOT_USED
    }

    public enum CharterType {
        CHARTER,
        LUMPSUM
    }

    public enum LoadingType {
        FIOST,
        FILO,
        LIFO
    }

    public enum CargoHandler {
        AS_ORDER,
        SHIP_OWNER,
        CARGO_OWNER,
        OTHER_PARTY
    }

    public enum InsuranceProvider {
        CUSTOMER,
        TRANSPORTER,
        NONE
    }

    public enum DespatchType {
        DHALFD,
        DDO,
        DLO,
        FD,
        NOT_USED
    }

    public enum LayDaysType {
        SHINC,
        SHEX,
        WWD,
        FAC,
        NOT_USED
    }

    public Contract() {}

    private Contract(final Builder builder) {
        this.id = builder.id;
        this.ship = builder.ship;
        this.shipmentId = builder.shipmentId;
        this.userId = builder.userId;
        this.price = builder.price;
        this.priceUnit = builder.priceUnit;
        this.currency = builder.currency;
        this.payout = builder.payout;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.charterType = builder.charterType;
        this.loadingType = builder.loadingType;
        this.incoterms = builder.incoterms;
        this.cargoInsurance = builder.cargoInsurance;
        this.shipInsurance = builder.shipInsurance;
        this.shipAgent = builder.shipAgent;
        this.miscellaneousFee = builder.miscellaneousFee;
        this.demurrage = builder.demurrage;
        this.demurrageUnit = builder.demurrageUnit;
        this.loadingLaytime = builder.loadingLaytime;
        this.dischargeLaytime = builder.dischargeLaytime;
        this.totalLaytime = builder.totalLaytime;
        this.laytimeUnit = builder.laytimeUnit;
        this.despatchType = builder.despatchType;
        this.layDaysType = builder.layDaysType;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(final Ship ship) {
        this.ship = ship;
    }

    public Integer getShipmentId() {
        return this.shipmentId;
    }

    public void setShipmentId(final Integer shipmentId) {
        this.shipmentId = shipmentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(final int userId) {
        this.userId = userId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public PriceUnit getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(final PriceUnit priceUnit) {
        this.priceUnit = priceUnit;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public Payout getPayout() {
        return payout;
    }

    public void setPayout(final Payout payout) {
        this.payout = payout;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(final Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(final Instant endDate) {
        this.endDate = endDate;
    }

    public CharterType getCharterType() {
        return charterType;
    }

    public void setCharterType(final CharterType charterType) {
        this.charterType = charterType;
    }

    public LoadingType getLoadingType() {
        return loadingType;
    }

    public void setLoadingType(final LoadingType loadingType) {
        this.loadingType = loadingType;
    }

    public Incoterms getIncoterms() {
        return incoterms;
    }

    public void setIncoterms(final Incoterms incoterms) {
        this.incoterms = incoterms;
    }

    public CargoHandler getCargoSender() {
        return cargoSender;
    }

    public void setCargoSender(final CargoHandler cargoSender) {
        this.cargoSender = cargoSender;
    }

    public String getCargoSenderOther() {
        return cargoSenderOther;
    }

    public void setCargoSenderOther(final String cargoSenderOther) {
        this.cargoSenderOther = cargoSenderOther;
    }

    public CargoHandler getCargoReceiver() {
        return cargoReceiver;
    }

    public void setCargoReceiver(final CargoHandler cargoReceiver) {
        this.cargoReceiver = cargoReceiver;
    }

    public String getCargoReceiverOther() {
        return cargoReceiverOther;
    }

    public void setCargoReceiverOther(final String cargoReceiverOther) {
        this.cargoReceiverOther = cargoReceiverOther;
    }

    public InsuranceProvider getCargoInsurance() {
        return cargoInsurance;
    }

    public void setCargoInsurance(final InsuranceProvider cargoInsurance) {
        this.cargoInsurance = cargoInsurance;
    }

    public InsuranceProvider getShipInsurance() {
        return shipInsurance;
    }

    public void setShipInsurance(final InsuranceProvider shipInsurance) {
        this.shipInsurance = shipInsurance;
    }

    public ShipAgent getShipAgent() {
        return shipAgent;
    }

    public void setShipAgent(final ShipAgent shipAgent) {
        this.shipAgent = shipAgent;
    }

    public Type getMiscellaneousFee() {
        return miscellaneousFee;
    }

    public void setMiscellaneousFee(final Type miscellaneousFee) {
        this.miscellaneousFee = miscellaneousFee;
    }

    public BigDecimal getDemurrage() {
        return demurrage;
    }

    public void setDemurrage(final BigDecimal demurrage) {
        this.demurrage = demurrage;
    }

    public TimeUnit getDemurrageUnit() {
        return demurrageUnit;
    }

    public void setDemurrageUnit(final TimeUnit demurrageUnit) {
        this.demurrageUnit = demurrageUnit;
    }

    public Integer getLoadingLaytime() {
        return loadingLaytime;
    }

    public void setLoadingLaytime(final Integer loadingLaytime) {
        this.loadingLaytime = loadingLaytime;
    }

    public Integer getDischargeLaytime() {
        return dischargeLaytime;
    }

    public void setDischargeLaytime(final Integer dischargeLaytime) {
        this.dischargeLaytime = dischargeLaytime;
    }

    public Integer getTotalLaytime() {
        return totalLaytime;
    }

    public void setTotalLaytime(final Integer totalLaytime) {
        this.totalLaytime = totalLaytime;
    }

    public TimeUnit getLaytimeUnit() {
        return laytimeUnit;
    }

    public void setLaytimeUnit(final TimeUnit laytimeUnit) {
        this.laytimeUnit = laytimeUnit;
    }

    public DespatchType getDespatchType() {
        return despatchType;
    }

    public void setDespatchType(final DespatchType despatchType) {
        this.despatchType = despatchType;
    }

    public LayDaysType getLayDaysType() {
        return layDaysType;
    }

    public void setLayDaysType(final LayDaysType layDaysType) {
        this.layDaysType = layDaysType;
    }

    public void setCreated(final Instant created) {
        this.created = created;
    }

    public void setLastModified(final Instant lastModified) {
        this.lastModified = lastModified;
    }

    public static class Builder {
        private int id;
        private Ship ship;
        private Integer shipmentId;
        private int userId;
        private BigDecimal price;
        private PriceUnit priceUnit = PriceUnit.NOT_USED;
        private String currency;
        private Payout payout;
        private Instant startDate;
        private Instant endDate;
        private CharterType charterType;
        private LoadingType loadingType;
        private Incoterms incoterms;
        private CargoHandler cargoSender;
        private String cargoSenderOther;
        private CargoHandler cargoReceiver;
        private String cargoReceiverOther;
        private InsuranceProvider cargoInsurance;
        private InsuranceProvider shipInsurance;
        private ShipAgent shipAgent;
        private Type miscellaneousFee;
        private BigDecimal demurrage;
        private TimeUnit demurrageUnit = TimeUnit.NOT_USED;
        private Integer loadingLaytime;
        private Integer dischargeLaytime;
        private Integer totalLaytime;
        private TimeUnit laytimeUnit = TimeUnit.NOT_USED;
        private DespatchType despatchType = DespatchType.NOT_USED;
        private LayDaysType layDaysType = LayDaysType.NOT_USED;

        public Builder id(final int id) {
            this.id = id;
            return this;
        }

        public Builder ship(final Ship ship) {
            this.ship = ship;
            return this;
        }

        public Builder shipmentId(final Integer shipmentId) {
            this.shipmentId = shipmentId;
            return this;
        }

        public Builder userId(final int userId) {
            this.userId = userId;
            return this;
        }

        public Builder price(final BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder priceUnit(final PriceUnit priceUnit) {
            this.priceUnit = priceUnit;
            return this;
        }

        public Builder currency(final String currency) {
            this.currency = currency;
            return this;
        }

        public Builder payout(final Payout payout) {
            this.payout = payout;
            return this;
        }

        public Builder startDate(final Instant startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(final Instant endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder charterType(final CharterType charterType) {
            this.charterType = charterType;
            return this;
        }

        public Builder loadingType(final LoadingType loadingType) {
            this.loadingType = loadingType;
            return this;
        }

        public Builder incoterms(final Incoterms incoterms) {
            this.incoterms = incoterms;
            return this;
        }

        public Builder cargoSender(final CargoHandler cargoSender) {
            this.cargoSender = cargoSender;
            return this;
        }

        public Builder cargoSenderOther(final String cargoSenderOther) {
            this.cargoSenderOther = cargoSenderOther;
            return this;
        }

        public Builder cargoReceiver(final CargoHandler cargoReceiver) {
            this.cargoReceiver = cargoReceiver;
            return this;
        }

        public Builder cargoReceiverOther(final String cargoReceiverOther) {
            this.cargoReceiverOther = cargoReceiverOther;
            return this;
        }

        public Builder cargoInsurance(final InsuranceProvider cargoInsurance) {
            this.cargoInsurance = cargoInsurance;
            return this;
        }

        public Builder shipInsurance(final InsuranceProvider shipInsurance) {
            this.shipInsurance = shipInsurance;
            return this;
        }

        public Builder shipAgent(final ShipAgent shipAgent) {
            this.shipAgent = shipAgent;
            return this;
        }

        public Builder miscellaneousFee(final Type miscellaneousFee) {
            this.miscellaneousFee = miscellaneousFee;
            return this;
        }

        public Builder demurrage(final BigDecimal demurrage) {
            this.demurrage = demurrage;
            return this;
        }

        public Builder demurrageUnit(final TimeUnit demurrageUnit) {
            this.demurrageUnit = demurrageUnit;
            return this;
        }

        public Builder loadingLaytime(final Integer loadingLaytime) {
            this.loadingLaytime = loadingLaytime;
            return this;
        }

        public Builder dischargeLaytime(final Integer dischargeLaytime) {
            this.dischargeLaytime = dischargeLaytime;
            return this;
        }

        public Builder totalLaytime(final Integer totalLaytime) {
            this.totalLaytime = totalLaytime;
            return this;
        }

        public Builder laytimeUnit(final TimeUnit laytimeUnit) {
            this.laytimeUnit = laytimeUnit;
            return this;
        }

        public Builder despatchType(final DespatchType despatchType) {
            this.despatchType = despatchType;
            return this;
        }

        public Builder layDaysType(final LayDaysType layDaysType) {
            this.layDaysType = layDaysType;
            return this;
        }

        public Contract build() {
            return new Contract(this);
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(ship)
                .append(shipmentId)
                .append(userId)
                .append(price)
                .append(priceUnit)
                .append(currency)
                .append(payout)
                .append(startDate)
                .append(endDate)
                .append(charterType)
                .append(loadingType)
                .append(incoterms)
                .append(cargoSender)
                .append(cargoSenderOther)
                .append(cargoReceiver)
                .append(cargoReceiverOther)
                .append(cargoInsurance)
                .append(shipInsurance)
                .append(shipAgent)
                .append(miscellaneousFee)
                .append(demurrage)
                .append(demurrageUnit)
                .append(loadingLaytime)
                .append(dischargeLaytime)
                .append(totalLaytime)
                .append(laytimeUnit)
                .append(despatchType)
                .append(layDaysType)
                .append(created)
                .append(lastModified)
                .toHashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Contract that = (Contract) o;
        return new EqualsBuilder()
                .append(id, that.id)
                .append(ship, that.ship)
                .append(shipmentId, that.shipmentId)
                .append(userId, that.userId)
                .append(price, that.price)
                .append(priceUnit, that.priceUnit)
                .append(currency, that.currency)
                .append(payout, that.payout)
                .append(startDate, that.startDate)
                .append(endDate, that.endDate)
                .append(charterType, that.charterType)
                .append(loadingType, that.loadingType)
                .append(incoterms, that.incoterms)
                .append(cargoSender, that.cargoSender)
                .append(cargoSenderOther, that.cargoSenderOther)
                .append(cargoReceiver, that.cargoReceiver)
                .append(cargoReceiverOther, that.cargoReceiverOther)
                .append(cargoInsurance, that.cargoInsurance)
                .append(shipInsurance, that.shipInsurance)
                .append(shipAgent, that.shipAgent)
                .append(miscellaneousFee, that.miscellaneousFee)
                .append(demurrage, that.demurrage)
                .append(demurrageUnit, that.demurrageUnit)
                .append(loadingLaytime, that.loadingLaytime)
                .append(dischargeLaytime, that.dischargeLaytime)
                .append(totalLaytime, that.totalLaytime)
                .append(laytimeUnit, that.laytimeUnit)
                .append(despatchType, that.despatchType)
                .append(layDaysType, that.layDaysType)
                .append(created, that.created)
                .append(lastModified, that.lastModified)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("id", id)
                .append("ship", ship)
                .append("shipmentId", shipmentId)
                .append("userId", userId)
                .append("price", price)
                .append("priceUnit", priceUnit)
                .append("currency", currency)
                .append("payout", payout)
                .append("startDate", startDate)
                .append("endDate", endDate)
                .append("charterType", charterType)
                .append("loadingType", loadingType)
                .append("incoterms", incoterms)
                .append("cargoSender", cargoSender)
                .append("cargoSenderOther", cargoSenderOther)
                .append("cargoInsurance", cargoInsurance)
                .append("cargoReceiver", cargoReceiver)
                .append("cargoReceiverOther", cargoReceiverOther)
                .append("shipInsurance", shipInsurance)
                .append("shipAgent", shipAgent)
                .append("miscellaneousFee", miscellaneousFee)
                .append("demurrage", demurrage)
                .append("demurrageUnit", demurrageUnit)
                .append("loadingLaytime", loadingLaytime)
                .append("dischargeLaytime", dischargeLaytime)
                .append("totalLaytime", totalLaytime)
                .append("laytimeUnit", laytimeUnit)
                .append("despatchType", despatchType)
                .append("layDaysType", layDaysType)
                .append("created", created)
                .append("lastModified", lastModified)
                .toString();
    }
}
