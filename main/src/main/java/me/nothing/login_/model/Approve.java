package me.nothing.login_.model;


import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Approve {
  private String decision;
  private String reason;
  
  
//   public Approve(String decision, String comment) {
//     this.decision = decision;
//   }

  @Override
  public String toString() {
    return "Approve [decision=" + decision + "]";
  }

  @Override
  public int hashCode() {
    return Objects.hash(decision);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Approve other = (Approve) obj;
    return Objects.equals(decision, other.decision);
  }

  
}
