package com.quick.immi.ai.entity.familyBased.i864;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssetsSupplementIncome {
  // Your Assets (Optional)
  // 270 - Enter the balance of all savings and checking accounts
  private String balanceSavingsCheckingAccounts;

  // 271 - Enter the net cash value of real-estate holdings (Net value = assessed value - mortgage debt)
  private String netRealEstateHoldings;

  // 275 - Enter the net cash value of all stocks, bonds, certificates of deposit, and other assets
  private String netStocksBondsAssets;

  // 272 - Add together Item Numbers 1 - 3 and enter the total here
  private String totalAssets;

  // Assets from Form I-864A, Part 4, Item Number 3.d, for:
  // 273 - Name of Relative
  private String nameOfRelative;

  // 274 - Your household member's assets from Form I-864A (optional)
  private String householdMemberAssets;

  // Assets of the principal sponsored immigrant (optional)
  // 276 - Balance of the principal immigrant's savings and checking accounts
  private String principalImmigrantSavingsCheckingBalance;

  // 279 - Net cash value of all the principal immigrant's real estate holdings
  private String principalImmigrantRealEstateHoldings;

  // 280 - Current cash value of the principal immigrant's stocks, bonds, and other assets
  private String principalImmigrantStocksBondsAssets;

  // 281 - Add together Item Numbers 6 - 8 and enter the number here
  private String totalPrincipalImmigrantAssets;

  // Total Value of Assets
  // 282 - Add together Item Numbers 4, 5.b, and 9 and enter the total here
  private String totalValueOfAssets;
}
