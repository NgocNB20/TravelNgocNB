/*-============================================================================
 * PizSoft. PROPRIETARY
 * Copyright© 2021 PizSoft.
 * UNPUBLISHED WORK
 * ALL RIGHTS RESERVED
 *
 * This software is the confidential and proprietary information of
 * PizSoft. ("Proprietary Information"). Any use, reproduction,
 * distribution or disclosure of the software or Proprietary Information,
 * in whole or in part, must comply with the terms of the license
 * agreement, nondisclosure agreement or contract entered into with
 * PizSoft. providing access to this software.
 *
 *=============================================================================
 */
package com.ngocnb20.travel.util;

import java.time.LocalDateTime;
import java.time.ZoneId;


public final class DateTimeUtil {

    public static Long convertToUnixTime(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }

}
