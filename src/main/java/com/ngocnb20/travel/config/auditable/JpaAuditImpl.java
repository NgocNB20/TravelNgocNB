package com.ngocnb20.travel.config.auditable;


import com.ngocnb20.travel.util.SecurityUtil;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

//setting để auto add data createby lastmodifyby...
@Component
public class JpaAuditImpl implements AuditorAware<Long> {//truyền vào Long tại vì kiểu dữ liệu 2 file cần nhận là Long

    //dữ liệu của hàm này ném vào 2 file auto add data
    @Override
    public Optional<Long> getCurrentAuditor() {

        return Optional.ofNullable(SecurityUtil.getIdCurrentUserLogin());//có thể trả về 0,khi giá trị = 0 thì data do hệ thống
        //tụ add
    }
}
