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

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.Optional;


public final class CookieUtil {

    public static Optional<String> getValueByName(Cookie[] cookies, String name) {
        return Arrays.stream(cookies).
                filter(c -> c.getName().equals(name)).
                map(Cookie::getValue).
                findFirst();
    }
}
