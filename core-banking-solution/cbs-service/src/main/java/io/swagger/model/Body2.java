package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Body2
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-04-22T12:04:48.784Z")

public class Body2   {
  @JsonProperty("fromAccountId")
  private Long fromAccountId = null;

  @JsonProperty("toAccountId")
  private Long toAccountId = null;

  @JsonProperty("amount")
  private BigDecimal amount = null;

  @JsonProperty("comments")
  private String comments = null;

  public Body2 fromAccountId(Long fromAccountId) {
    this.fromAccountId = fromAccountId;
    return this;
  }

  /**
   * Get fromAccountId
   * @return fromAccountId
  **/
  @ApiModelProperty(example = "263175", value = "")


  public Long getFromAccountId() {
    return fromAccountId;
  }

  public void setFromAccountId(Long fromAccountId) {
    this.fromAccountId = fromAccountId;
  }

  public Body2 toAccountId(Long toAccountId) {
    this.toAccountId = toAccountId;
    return this;
  }

  /**
   * Get toAccountId
   * @return toAccountId
  **/
  @ApiModelProperty(example = "34564", value = "")


  public Long getToAccountId() {
    return toAccountId;
  }

  public void setToAccountId(Long toAccountId) {
    this.toAccountId = toAccountId;
  }

  public Body2 amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  /**
   * amount to be transfer
   * @return amount
  **/
  @ApiModelProperty(example = "100.0", value = "amount to be transfer")

  @Valid

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Body2 comments(String comments) {
    this.comments = comments;
    return this;
  }

  /**
   * Comments for this transaction
   * @return comments
  **/
  @ApiModelProperty(value = "Comments for this transaction")


  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Body2 body2 = (Body2) o;
    return Objects.equals(this.fromAccountId, body2.fromAccountId) &&
        Objects.equals(this.toAccountId, body2.toAccountId) &&
        Objects.equals(this.amount, body2.amount) &&
        Objects.equals(this.comments, body2.comments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fromAccountId, toAccountId, amount, comments);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Body2 {\n");
    
    sb.append("    fromAccountId: ").append(toIndentedString(fromAccountId)).append("\n");
    sb.append("    toAccountId: ").append(toIndentedString(toAccountId)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    comments: ").append(toIndentedString(comments)).append("\n");
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

