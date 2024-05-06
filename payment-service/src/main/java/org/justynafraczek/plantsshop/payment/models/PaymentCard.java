/*
 * Plants mircro service
 * Micro service to order plants
 *
 * OpenAPI spec version: 1.0.0
 * Contact: supportm@bp.org
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package org.justynafraczek.plantsshop.payment.models;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * PaymentCard
 */

public class PaymentCard {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("validTo")
  private String validTo = null;

  @JsonProperty("number")
  private String number = null;

  @JsonProperty("CVV2")
  private String cvv2 = null;

  public PaymentCard name(String name) {
    this.name = name;
    return this;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public PaymentCard validTo(String validTo) {
    this.validTo = validTo;
    return this;
  }

  public String getValidTo() {
    return validTo;
  }

  public void setValidTo(String validTo) {
    this.validTo = validTo;
  }

  public PaymentCard number(String number) {
    this.number = number;
    return this;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getCVV2() {
    return cvv2;
  }

  public void setCVV2(String cvv2) {
    this.cvv2 = cvv2;
  }

  public PaymentCard cvv2(String cvv2) {
    this.cvv2 = cvv2;
    return this;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaymentCard paymentCard = (PaymentCard) o;
    return Objects.equals(this.name, paymentCard.name) &&
        Objects.equals(this.validTo, paymentCard.validTo) &&
        Objects.equals(this.number, paymentCard.number) &&
        Objects.equals(this.cvv2, paymentCard.cvv2);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, validTo, number);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaymentCard {\n");

    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    validTo: ").append(toIndentedString(validTo)).append("\n");
    sb.append("    number: ").append(toIndentedString(number)).append("\n");
    sb.append("    cvv2: ").append(toIndentedString(cvv2)).append("\n");
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
