package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;


/**
 * Represents a bid in the bid list system.
 * <p>
 * This entity is mapped to the "bidlist" table in the database and contains all
 * relevant fields for recording bid-related data such as account name, bid/ask quantities,
 * and audit trail fields like creation and revision timestamps.
 * </p>
 *
 * <p>
 * Validation constraints ensure that key business fields are not blank and do not exceed maximum lengths.
 * </p>
 */
@Slf4j
@Data
@Entity
@Table(name = "bidlist")
public class BidList implements DomainModel<BidList> {


    /**
     * Primary identifier for the bid list entry. Auto-generated.
     * <p>Constrained between -128 and 127, stored as a TINYINT in the database.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bidlistid", updatable = false)
    private Integer id;

    /**
     * Account associated with the bid.
     * Must not be null or blank. Maximum length is 30 characters.
     */
    @NotBlank(message = "account can't be null nor blank")
    @Size(max = 30, message = "account must not exceed 30 characters.")
    @Column(length = 30, nullable = false)
    private String account;

    /**
     * Type of the bid.
     * Must not be null or blank. Maximum length is 30 characters.
     */
    @NotBlank(message = "Type can't be null nor blank")
    @Size(max = 30, message = "type must not exceed 30 characters.")
    @Column(length = 30, nullable = false)
    private String type;

    /**
     * Quantity of the bid.
     */
    @Column(name = "bidquantity")
    private Double bidQuantity = 0.0;

    /**
     * Quantity of the ask.
     */
    @Column(name = "askquantity")
    private Double askQuantity = 0.0;

    /**
     * Price offered for the bid.
     */
    private Double bid = 0.0;

    /**
     * Asking price for the asset.
     */
    private Double ask = 0.0;

    /**
     * Benchmark reference for the bid.
     * Optional. Maximum length is 125 characters.
     */
    @Size(max = 125, message = "benchmark must not exceed 125 characters.")
    @Column(length = 125)
    private String benchmark = "";

    /**
     * Timestamp of the bid entry.
     * Initialized to current time by default.
     */
    @Column(name = "bidlistdate")
    private Timestamp bidListDate = new Timestamp(System.currentTimeMillis());

    /**
     * Commentary or notes related to the bid.
     * Optional. Maximum length is 125 characters.
     */
    @Size(max = 125, message = "commentary must not exceed 125 characters.")
    @Column(length = 125)
    private String commentary = "";

    /**
     * Security related to the bid.
     * Optional. Maximum length is 125 characters.
     */
    @Size(max = 125, message = "security must not exceed 125 characters.")
    @Column(length = 125)
    private String security = "";

    /**
     * Status of the bid.
     * Optional. Maximum length is 10 characters.
     */
    @Size(max = 10, message = "status must not exceed 10 characters.")
    @Column(length = 10)
    private String status = "";

    /**
     * Name of the trader who initiated the bid.
     * Optional. Maximum length is 125 characters.
     */
    @Size(max = 125, message = "trader must not exceed 125 characters.")
    @Column(length = 125)
    private String trader = "";

    /**
     * Book or portfolio the bid belongs to.
     * Optional. Maximum length is 125 characters.
     */
    @Size(max = 125, message = "book must not exceed 125 characters.")
    @Column(length = 125)
    private String book = "";

    /**
     * Name of the user who created the bid.
     * Optional. Maximum length is 125 characters.
     */
    @Size(max = 125, message = "creationName must not exceed 125 characters.")
    @Column(name = "creationname", length = 125)
    private String creationName = "";

    /**
     * Timestamp when the bid was created.
     * Initialized to current time by default.
     */
    @Column(name = "creationdate")
    private Timestamp creationDate = new Timestamp(System.currentTimeMillis());

    /**
     * Name of the user who last revised the bid.
     * Optional. Maximum length is 125 characters.
     */
    @Size(max = 125, message = "revisionName must not exceed 125 characters.")
    @Column(name = "revisionname", length = 125)
    private String revisionName = "";

    /**
     * Timestamp when the bid was last revised.
     * Initialized to current time by default.
     */
    @Column(name = "revisiondate")
    private Timestamp revisionDate = new Timestamp(System.currentTimeMillis());

    /**
     * Name of the deal associated with the bid.
     * Optional. Maximum length is 125 characters.
     */
    @Size(max = 125, message = "dealName must not exceed 125 characters.")
    @Column(name = "dealname", length = 125)
    private String dealName = "";

    /**
     * Type of deal associated with the bid.
     * Optional. Maximum length is 125 characters.
     */
    @Size(max = 125, message = "dealType must not exceed 125 characters.")
    @Column(name = "dealtype", length = 125)
    private String dealType = "";

    /**
     * Identifier of the source list from which this bid originated.
     * Optional. Maximum length is 125 characters.
     */
    @Size(max = 125, message = "sourceListId must not exceed 125 characters.")
    @Column(name = "sourcelistid", length = 125)
    private String sourceListId = "";

    /**
     * Indicates the side of the trade.
     * Optional. Maximum length is 125 characters.
     */
    @Size(max = 125, message = "side must not exceed 125 characters.")
    @Column(length = 125)
    private String side = "";


    /**
     * Updates the current bid list with selected fields from the provided instance.
     *
     * <p>Only the following fields are updated:</p>
     * <ul>
     *     <li>{@code account}</li>
     *     <li>{@code type}</li>
     *     <li>{@code bidQuantity}</li>
     * </ul>
     *
     * @param bidList the source {@link BidList} object to update from
     * @return the updated {@link BidList} instance
     */
    @Override
    public BidList update(BidList bidList){
        this.account = bidList.getAccount();
        this.type = bidList.getType();
        this.bidQuantity = bidList.getBidQuantity();
        this.revisionDate = new Timestamp(System.currentTimeMillis());

        return this;
    }

}
