package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.threeten.bp.LocalDate;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Transaction
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-04-22T12:04:48.784Z")

public class Transaction   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("transactionDate")
  private LocalDate transactionDate = null;

  @JsonProperty("valueDate")
  private LocalDate valueDate = null;

  @JsonProperty("narration")
  private String narration = null;

  @JsonProperty("withdrawl")
  private BigDecimal withdrawl = null;

  @JsonProperty("deposit")
  private BigDecimal deposit = null;

  @JsonProperty("availableBalance")
  private BigDecimal availableBalance = null;

  public Transaction id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Transaction transactionDate(LocalDate transactionDate) {
    this.transactionDate = transactionDate;
    return this;
  }

  /**
   * Get transactionDate
   * @return transactionDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public LocalDate getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(LocalDate transactionDate) {
    this.transactionDate = transactionDate;
  }

  public Transaction valueDate(LocalDate valueDate) {
    this.valueDate = valueDate;
    return this;
  }

  /**
   * Get valueDate
   * @return valueDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public LocalDate getValueDate() {
    return valueDate;
  }

  public void setValueDate(LocalDate valueDate) {
    this.valueDate = valueDate;
  }

  public Transaction narration(String narration) {
    this.narration = narration;
    return this;
  }

  /**
   * Description of the transaction
   * @return narration
  **/
  @ApiModelProperty(value = "Description of the transaction")


  public String getNarration() {
    return narration;
  }

  public void setNarration(String narration) {
    this.narration = narration;
  }

  public Transaction withdrawl(BigDecimal withdrawl) {
    this.withdrawl = withdrawl;
    return this;
  }

  /**
   * Get withdrawl
   * @return withdrawl
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getWithdrawl() {
    return withdrawl;
  }

  public void setWithdrawl(BigDecimal withdrawl) {
    this.withdrawl = withdrawl;
  }

  public Transaction deposit(BigDecimal deposit) {
    this.deposit = deposit;
    return this;
  }

  /**
   * Get deposit
   * @return deposit
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getDeposit() {
    return deposit;
  }

  public void setDeposit(BigDecimal deposit) {
    this.deposit = deposit;
  }

  public Transaction availableBalance(BigDecimal availableBalance) {
    this.availableBalance = availableBalance;
    return this;
  }

  /**
   * Get availableBalance
   * @return availableBalance
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getAvailableBalance() {
    return availableBalance;
  }

  public void setAvailableBalance(BigDecimal availableBalance) {
    this.availableBalance = availableBalance;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Transaction transaction = (Transaction) o;
    return Objects.equals(this.id, transaction.id) &&
        Objects.equals(this.transactionDate, transaction.transactionDate) &&
        Objects.equals(this.valueDate, transaction.valueDate) &&
        Objects.equals(this.narration, transaction.narration) &&
        Objects.equals(this.withdrawl, transaction.withdrawl) &&
        Objects.equals(this.deposit, transaction.deposit) &&
        Objects.equals(this.availableBalance, transaction.availableBalance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, transactionDate, valueDate, narration, withdrawl, deposit, availableBalance);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Transaction {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    transactionDate: ").append(toIndentedString(transactionDate)).append("\n");
    sb.append("    valueDate: ").append(toIndentedString(valueDate)).append("\n");
    sb.append("    narration: ").append(toIndentedString(narration)).append("\n");
    sb.append("    withdrawl: ").append(toIndentedString(withdrawl)).append("\n");
    sb.append("    deposit: ").append(toIndentedString(deposit)).append("\n");
    sb.append("    availableBalance: ").append(toIndentedString(availableBalance)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

