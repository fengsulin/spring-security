package com.lin.security.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.lin.security.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails {
    private SysUser sysUser;
    private List<String> permissions;
    @JSONField(serialize = false)   // SimpleGrantedAuthority类型存入redis序列化时会报错
    private Set<SimpleGrantedAuthority> auths;

    public LoginUser(SysUser sysUser, List<String> permissions) {
        this.sysUser = sysUser;
        this.permissions = permissions;
    }

    /**
     * 重写获取权限方法
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(Objects.nonNull(auths)) return auths;
        auths = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        return auths;
    }

    @Override
    public String getPassword() {
        return  sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
