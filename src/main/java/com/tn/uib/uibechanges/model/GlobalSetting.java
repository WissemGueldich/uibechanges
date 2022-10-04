package com.tn.uib.uibechanges.model;

import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "global_settings",uniqueConstraints = {@UniqueConstraint(columnNames = { "setting" })})
public class GlobalSetting {
	
    @Id
    @Basic(optional = false)
    @Column(name = "setting")
    private String setting;
    
    @Basic(optional = false)
    @Column(name = "value")
    private String value;

    public GlobalSetting() {
    }

    public GlobalSetting(String setting) {
        this.setting = setting;
    }

    public GlobalSetting(String setting, String value) {
        this.setting = setting;
        this.value = value;
    }

    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.setting);
        return hash;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GlobalSetting other = (GlobalSetting) obj;
        if (!Objects.equals(this.setting, other.setting)) {
            return false;
        }
        return true;
    }

}
